/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.SneakyThrows;
import club.imanity.katana.katana;
import club.imanity.katana.check.api.BanWaveX;
import club.imanity.katana.check.api.BanX;
import club.imanity.katana.check.api.Check;
import club.imanity.katana.check.api.ViolationX;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.database.Query;
import club.imanity.katana.database.Storage;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.NetUtil;

public class MySQLStorage implements Storage {
	private ConcurrentLinkedQueue<ViolationX> violations = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<BanX> bans = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<BanWaveX> banWaveQueue = new ConcurrentLinkedQueue<>();

	@SneakyThrows
	@Override
	public void init() {
		MySQL.init();
		Query.prepare(
						"CREATE TABLE IF NOT EXISTS `ALERTS` (`UUID` TEXT NOT NULL,`MODULE` TEXT NOT NULL,`VL` SMALLINT NOT NULL,`TIME` LONG NOT NULL,`EXTRA` TEXT,`COORDS` TEXT,`WORLD` TEXT,`PING` LONG NOT NULL,`TPS` DOUBLE NOT NULL)"
				)
				.execute();
		Query.prepare("CREATE TABLE IF NOT EXISTS `ALERTSTATUS` (`UUID` VARCHAR(36) NOT NULL,`STATUS` TINYINT(1) NOT NULL,PRIMARY KEY (UUID))").execute();
		Query.prepare("CREATE TABLE IF NOT EXISTS `BANS` (`UUID` TEXT NOT NULL,`MODULE` TEXT NOT NULL,`TIME` LONG NOT NULL,`EXTRA` TEXT,`PING` LONG NOT NULL,`TPS` DOUBLE NOT NULL)")
				.execute();
		Query.prepare("CREATE TABLE IF NOT EXISTS `BANWAVE` (`UUID` TEXT NOT NULL,`MODULE` TEXT NOT NULL,`TIME` LONG NOT NULL,`TOTALLOGS` SMALLINT NOT NULL)").execute();
		Query.prepare("ALTER TABLE ALERTS ADD COLUMN IF NOT EXISTS COORDS TEXT").execute();
		Query.prepare("ALTER TABLE ALERTS ADD COLUMN IF NOT EXISTS WORLD TEXT").execute();
		new Thread(
				() -> {
					while (katana.getInstance() != null && katana.getInstance().getPlug().isEnabled()) {
						NetUtil.sleep(5000L);
						if (!this.violations.isEmpty() || !this.bans.isEmpty() || !this.banWaveQueue.isEmpty()) {
							if (!this.violations.isEmpty()) {
								for (ViolationX violation : this.violations) {
									try {
										MySQL.use();
										Query.prepare("INSERT INTO `ALERTS` (`UUID`, `MODULE`, `VL`, `TIME`, `EXTRA`, `COORDS`, `WORLD`, `PING`, `TPS`) VALUES (?,?,?,?,?,?,?,?,?)")
												.append(violation.player)
												.append(violation.type)
												.append(violation.vl)
												.append(violation.time)
												.append(violation.data)
												.append(violation.location)
												.append(violation.world)
												.append(violation.ping)
												.append(violation.TPS)
												.execute();
									} catch (Exception e) {
										throw new RuntimeException(e);
									}

									this.violations.clear();
								}

								if (!this.bans.isEmpty()) {
									for (BanX ban : this.bans) {
										MySQL.use();
                                        try {
                                            Query.prepare("INSERT INTO `BANS` (`UUID`, `MODULE`, `TIME`, `EXTRA`, `PING`, `TPS`) VALUES (?,?,?,?,?,?)")
                                                    .append(ban.player)
                                                    .append(ban.type)
                                                    .append(ban.time)
                                                    .append(ban.data)
                                                    .append(ban.ping)
                                                    .append(ban.TPS)
                                                    .execute();
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }

									this.bans.clear();
								}

								if (!this.banWaveQueue.isEmpty()) {
									for (BanWaveX wave : this.banWaveQueue) {
										MySQL.use();
                                        try {
                                            Query.prepare("INSERT INTO `BANWAVE` (`UUID`, `MODULE`, `TIME`, `TOTALLOGS`) VALUES (?,?,?,?)")
                                                    .append(wave.player)
                                                    .append(wave.type)
                                                    .append(wave.time)
                                                    .append(wave.totalLogs)
                                                    .execute();
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }

									this.banWaveQueue.clear();
								}
							}
						}
					}
				},
				"KatanaMySQLCommitter"
		)
				.start();
	}

	@Override
	public void addAlert(ViolationX violation) {
		this.violations.add(violation);
	}

	@Override
	public void addBan(BanX ban) {
		this.bans.add(ban);
	}

	@SneakyThrows
	@Override
	public void setAlerts(String uuid, int status) {

		MySQL.use();
		Query.prepare("INSERT INTO `ALERTSTATUS` (`UUID`, `STATUS`) VALUES (?,?) ON DUPLICATE KEY UPDATE STATUS=" + status).append(uuid).append(status).execute();

	}

	@SneakyThrows
	@Override
	public boolean getAlerts(String uuid) {

		try {
			MySQL.use();
			List<Integer> alert = new ArrayList<>();
			Query.prepare("SELECT * FROM `ALERTSTATUS` WHERE `UUID` = ? limit 1").append(uuid).execute(rs -> alert.add(rs.getInt(2)));
			if (alert.isEmpty()) {
				this.setAlerts(uuid, 1);
				return true;
			} else {
				return MathUtil.getIntAsBoolean(alert.get(0));
			}
		} catch (Exception var3) {
			this.setAlerts(uuid, 1);
			return true;
		}
	}

	@SneakyThrows
	@Override
	public void loadActiveViolations(String uuid, KatanaPlayer data) {

		MySQL.use();
		List<ViolationX> violations = new ArrayList<>();
		Map<String, Integer> validVls = new HashMap<>();
		Query.prepare("SELECT `MODULE`, `VL`, `TIME`, `EXTRA`, `COORDS`, `WORLD`, `PING`, `TPS` FROM `ALERTS` WHERE `UUID` = ? ORDER BY `TIME`")
				.append(uuid)
				.execute(
						rs -> violations.add(new ViolationX(uuid, rs.getString(1), rs.getInt(2), rs.getLong(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7), rs.getDouble(8)))
				);

		for (ViolationX v : violations) {
			if (System.currentTimeMillis() - v.time < 200000L) {
				if (!validVls.containsKey(v.type)) {
					validVls.put(v.type, v.vl);
				} else if (v.vl > validVls.get(v.type)) {
					validVls.replace(v.type, v.vl);
				}
			}
		}

		for (Check c : data.getCheckManager().getChecks()) {
			if (validVls.containsKey(c.getName())) {
				data.addViolations(c, validVls.get(c.getName()));
				int vl = data.getViolations(c, 100000L);
				data.setCheckVl(vl, c);
			}
		}

	}

	@SneakyThrows
	@Override
	public List<ViolationX> getViolations(String uuid, Check type, int page, int limit, long from, long to) {

		MySQL.use();
		List<ViolationX> violations = new ArrayList<>();
		Query.prepare("SELECT `MODULE`, `VL`, `TIME`, `EXTRA`, `COORDS`, `WORLD`, `PING`, `TPS` FROM `ALERTS` WHERE `UUID` = ? ORDER BY `TIME` DESC LIMIT ?,?")
				.append(uuid)
				.append(page * limit)
				.append(limit)
				.execute(
						rs -> violations.add(new ViolationX(uuid, rs.getString(1), rs.getInt(2), rs.getLong(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7), rs.getDouble(8)))
				);
		return violations;

	}

	@SneakyThrows
	@Override
	public int getViolationAmount(String uuid) {

		MySQL.use();
		AtomicInteger violations = new AtomicInteger();
		Query.prepare("SELECT `MODULE`, `VL`, `TIME`, `EXTRA`, `COORDS`, `WORLD`, `PING`, `TPS` FROM `ALERTS` WHERE `UUID` = ? ORDER BY `TIME`")
				.append(uuid)
				.execute(rs -> violations.incrementAndGet());
		return violations.get();

	}

	@SneakyThrows
	@Override
	public List<ViolationX> getAllViolations(String uuid) {

		MySQL.use();
		List<ViolationX> violations = new ArrayList<>();
		Query.prepare("SELECT `MODULE`, `VL`, `TIME`, `EXTRA`, `COORDS`, `WORLD`, `PING`, `TPS` FROM `ALERTS` WHERE `UUID` = ? ORDER BY `TIME`")
				.append(uuid)
				.execute(
						rs -> violations.add(new ViolationX(uuid, rs.getString(1), rs.getInt(2), rs.getLong(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7), rs.getDouble(8)))
				);
		return violations;

	}

	@SneakyThrows
	@Override
	public int getAllViolationsInStorage() {

		List<ViolationX> violations = new ArrayList<>();
		Query.prepare("SELECT `MODULE`, `VL`, `TIME`, `EXTRA`, `COORDS`, `WORLD`, `PING`, `TPS` FROM `ALERTS` ORDER BY `TIME`")
				.execute(
						rs -> violations.add(new ViolationX("s", rs.getString(1), rs.getInt(2), rs.getLong(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7), rs.getDouble(8)))
				);
		return violations.size();

	}

	@Override
	@SneakyThrows
	public List<BanX> getRecentBans() {

		List<BanX> bans = new ArrayList<>();
		Query.prepare("SELECT `UUID`, `MODULE`, `TIME`, `EXTRA`, `COORDS`, `PING`, `TPS` FROM `BANS` ORDER BY `TIME`")
				.execute(rs -> bans.add(new BanX(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4), rs.getLong(5), rs.getDouble(6))));
		Collections.reverse(bans);
		return bans.subList(0, Math.min(bans.size(), 10));

	}

	@SneakyThrows
	@Override
	public void purge(String uuid, boolean all) {

		Query.prepare("DELETE FROM `ALERTS` WHERE UUID = ?").append(uuid).execute();
		Query.prepare("DELETE FROM `BANS` WHERE UUID = ?").append(uuid).execute();

	}

	@SneakyThrows
	@Override
	public List<String> getBanwaveList() {

		MySQL.use();
		List<String> players = new ArrayList<>();
		Query.prepare("SELECT `UUID` FROM `BANWAVE` ORDER BY `TIME`").execute(rs -> players.add(rs.getString(1)));
		return players;

	}

	@Override
	public boolean isInBanwave(String uuid) {
		try {
			try {
				ResultSet rs = Query.prepare("SELECT `MODULE`, `TIME`, `TOTALLOGS` FROM `BANWAVE` WHERE `UUID` = ? ORDER BY `TIME` DESC LIMIT ?,?").append(uuid).executeQuery();
				return rs.first();
			} catch (Exception var3) {
				return false;
			}
		} catch (Throwable var4) {
			throw var4;
		}
	}

	@Override
	public void addToBanWave(BanWaveX bwRequest) {
		if (!this.isInBanwave(bwRequest.player)) {
			this.banWaveQueue.add(bwRequest);
		}
	}

	@SneakyThrows
	@Override
	public void removeFromBanWave(String uuid) {

		Optional<BanWaveX> bwx = this.banWaveQueue.stream().filter(bw -> bw.player.equals(uuid)).findFirst();
		bwx.ifPresent(banWaveX -> this.banWaveQueue.remove(banWaveX));
		Query.prepare("DELETE FROM `BANWAVE` WHERE UUID = ?").append(uuid).execute();

	}

}