/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.database;

import java.sql.SQLException;
import java.util.List;
import club.imanity.katana.check.api.BanWaveX;
import club.imanity.katana.check.api.BanX;
import club.imanity.katana.check.api.Check;
import club.imanity.katana.check.api.ViolationX;
import club.imanity.katana.data.KatanaPlayer;

public interface Storage {
	void init() throws SQLException;

	void addAlert(ViolationX violationX);

	void addBan(BanX banX);

	List<ViolationX> getViolations(String string, Check check, int integer3, int integer4, long long5, long long6);

	List<ViolationX> getAllViolations(String string);

	List<String> getBanwaveList();

	boolean isInBanwave(String string);

	void addToBanWave(BanWaveX banWaveX);

	void removeFromBanWave(String string);

	int getViolationAmount(String string);

	void loadActiveViolations(String string, KatanaPlayer katanaPlayer);

	void purge(String string, boolean boolean2);

	int getAllViolationsInStorage();

	List<BanX> getRecentBans();


	void setAlerts(String string, int integer);

	boolean getAlerts(String string);
}
