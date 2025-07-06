/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.check;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import club.imanity.katana.katana;
import club.imanity.katana.check.api.Check;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistA;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistB;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistC;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistD;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistE;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistF;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistG;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistH;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistI;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistJ;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistM;
import club.imanity.katana.check.impl.combat.aimassist.AimAssistN;
import club.imanity.katana.check.impl.combat.aimassist.analysis.AnalysisA;
import club.imanity.katana.check.impl.combat.aimassist.analysis.AnalysisB;
import club.imanity.katana.check.impl.combat.aimassist.analysis.AnalysisC;
import club.imanity.katana.check.impl.combat.aimassist.analysis.AnalysisD;
import club.imanity.katana.check.impl.combat.aimassist.analysis.AnalysisE;
import club.imanity.katana.check.impl.combat.aimassist.analysis.AnalysisF;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerA;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerB;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerC;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerD;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerE;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerF;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerG;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerH;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerI;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerJ;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerK;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerL;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerP;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerU;
import club.imanity.katana.check.impl.combat.autoclicker.AutoClickerW;
import club.imanity.katana.check.impl.combat.hitbox.HitboxA;
import club.imanity.katana.check.impl.combat.killaura.KillauraA;
import club.imanity.katana.check.impl.combat.killaura.KillauraB;
import club.imanity.katana.check.impl.combat.killaura.KillauraC;
import club.imanity.katana.check.impl.combat.killaura.KillauraE;
import club.imanity.katana.check.impl.combat.killaura.KillauraF;
import club.imanity.katana.check.impl.combat.killaura.KillauraG;
import club.imanity.katana.check.impl.combat.killaura.KillauraH;
import club.imanity.katana.check.impl.combat.killaura.KillauraI;
import club.imanity.katana.check.impl.combat.killaura.KillauraJ;
import club.imanity.katana.check.impl.combat.killaura.KillauraK;
import club.imanity.katana.check.impl.combat.killaura.KillauraM;
import club.imanity.katana.check.impl.combat.killaura.KillauraN;
import club.imanity.katana.check.impl.combat.reach.ReachA;
import club.imanity.katana.check.impl.combat.velocity.VelocityA;
import club.imanity.katana.check.impl.combat.velocity.VelocityB;
import club.imanity.katana.check.impl.movement.fly.FlyA;
import club.imanity.katana.check.impl.movement.fly.FlyB;
import club.imanity.katana.check.impl.movement.fly.FlyC;
import club.imanity.katana.check.impl.movement.fly.FlyD;
import club.imanity.katana.check.impl.movement.fly.FlyE;
import club.imanity.katana.check.impl.movement.fly.FlyF;
import club.imanity.katana.check.impl.movement.inventory.InventoryA;
import club.imanity.katana.check.impl.movement.inventory.InventoryB;
import club.imanity.katana.check.impl.movement.motion.MotionA;
import club.imanity.katana.check.impl.movement.motion.MotionB;
import club.imanity.katana.check.impl.movement.motion.MotionC;
import club.imanity.katana.check.impl.movement.motion.MotionD;
import club.imanity.katana.check.impl.movement.motion.MotionE;
import club.imanity.katana.check.impl.movement.motion.MotionF;
import club.imanity.katana.check.impl.movement.motion.MotionI;
import club.imanity.katana.check.impl.movement.motion.MotionJ;
import club.imanity.katana.check.impl.movement.omnisprint.OmniSprintA;
import club.imanity.katana.check.impl.movement.speed.SpeedA;
import club.imanity.katana.check.impl.movement.speed.SpeedB;
import club.imanity.katana.check.impl.movement.speed.SpeedC;
import club.imanity.katana.check.impl.movement.step.StepA;
import club.imanity.katana.check.impl.movement.vehicle.VehicleFly;
import club.imanity.katana.check.impl.movement.water.JesusA;
import club.imanity.katana.check.impl.movement.water.JesusB;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsA;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsB;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsC;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsD;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsE;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsF;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsG;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsH;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsI;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsJ;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsK;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsM;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsN;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsO;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsP;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsQ;
import club.imanity.katana.check.impl.packet.badpackets.BadPacketsR;
import club.imanity.katana.check.impl.packet.pingspoof.PingA;
import club.imanity.katana.check.impl.packet.timer.TimerA;
import club.imanity.katana.check.impl.packet.timer.TimerB;
import club.imanity.katana.check.impl.packet.timer.TimerC;
import club.imanity.katana.check.impl.world.block.BlockReach;
import club.imanity.katana.check.impl.world.block.FastBreakA;
import club.imanity.katana.check.impl.world.block.FastBreakB;
import club.imanity.katana.check.impl.world.block.FastBreakC;
import club.imanity.katana.check.impl.world.block.GhostBreak;
import club.imanity.katana.check.impl.world.block.NoLookBreak;
import club.imanity.katana.check.impl.world.ground.GroundA;
import club.imanity.katana.check.impl.world.ground.GroundB;
import club.imanity.katana.check.impl.world.ground.GroundC;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldA;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldB;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldC;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldD;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldE;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldF;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldG;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldH;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldI;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldJ;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldK;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldL;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldM;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldN;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldO;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldP;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldQ;
import club.imanity.katana.check.impl.world.scaffold.ScaffoldR;
import club.imanity.katana.manager.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class CheckState {
	private final Set<Class<? extends Check>> checkClasses = new HashSet<>();
	private final Map<String, Boolean> enabledMap = new ConcurrentHashMap<>();
	private final Map<String, Boolean> autobanMap = new ConcurrentHashMap<>();
	private final Map<String, Boolean> banwaveMap = new ConcurrentHashMap<>();
	private final Map<String, Integer> vlMap = new ConcurrentHashMap<>();
	private final Map<String, Integer> banwaveVlMap = new ConcurrentHashMap<>();
	private final Map<String, Integer> pullbackVlMap = new ConcurrentHashMap<>();

	public Set<Class<? extends Check>> loadOrGetClasses() {
		if (this.checkClasses.isEmpty()) {
			this.checkClasses.add(AimAssistA.class);
			this.checkClasses.add(AimAssistB.class);
			this.checkClasses.add(AimAssistC.class);
			this.checkClasses.add(AimAssistD.class);
			this.checkClasses.add(AimAssistE.class);
			this.checkClasses.add(AimAssistF.class);
			this.checkClasses.add(AimAssistG.class);
			this.checkClasses.add(AimAssistH.class);
			this.checkClasses.add(AimAssistI.class);
			this.checkClasses.add(AimAssistJ.class);
			this.checkClasses.add(AimAssistM.class);
			this.checkClasses.add(AimAssistN.class);
			this.checkClasses.add(AnalysisA.class);
			this.checkClasses.add(AnalysisB.class);
			this.checkClasses.add(AnalysisC.class);
			this.checkClasses.add(AnalysisD.class);
			this.checkClasses.add(AnalysisE.class);
			this.checkClasses.add(AnalysisF.class);
			this.checkClasses.add(AutoClickerA.class);
			this.checkClasses.add(AutoClickerB.class);
			this.checkClasses.add(AutoClickerC.class);
			this.checkClasses.add(AutoClickerD.class);
			this.checkClasses.add(AutoClickerE.class);
			this.checkClasses.add(AutoClickerF.class);
			this.checkClasses.add(AutoClickerG.class);
			this.checkClasses.add(AutoClickerH.class);
			this.checkClasses.add(AutoClickerI.class);
			this.checkClasses.add(AutoClickerJ.class);
			this.checkClasses.add(AutoClickerK.class);
			this.checkClasses.add(AutoClickerL.class);
			this.checkClasses.add(AutoClickerP.class);
			this.checkClasses.add(AutoClickerU.class);
			this.checkClasses.add(AutoClickerW.class);
			this.checkClasses.add(ReachA.class);
			this.checkClasses.add(HitboxA.class);
			this.checkClasses.add(VelocityA.class);
			this.checkClasses.add(VelocityB.class);
			this.checkClasses.add(KillauraA.class);
			this.checkClasses.add(KillauraB.class);
			this.checkClasses.add(KillauraC.class);
			this.checkClasses.add(KillauraE.class);
			this.checkClasses.add(KillauraF.class);
			this.checkClasses.add(KillauraG.class);
			this.checkClasses.add(KillauraH.class);
			this.checkClasses.add(KillauraI.class);
			this.checkClasses.add(KillauraJ.class);
			this.checkClasses.add(KillauraK.class);
			this.checkClasses.add(KillauraM.class);
			this.checkClasses.add(KillauraN.class);
			this.checkClasses.add(FlyA.class);
			this.checkClasses.add(FlyB.class);
			this.checkClasses.add(FlyC.class);
			this.checkClasses.add(FlyD.class);
			this.checkClasses.add(FlyE.class);
			this.checkClasses.add(FlyF.class);
			this.checkClasses.add(VehicleFly.class);
			this.checkClasses.add(JesusA.class);
			this.checkClasses.add(JesusB.class);
			this.checkClasses.add(InventoryA.class);
			this.checkClasses.add(InventoryB.class);
			this.checkClasses.add(SpeedA.class);
			this.checkClasses.add(SpeedB.class);
			this.checkClasses.add(SpeedC.class);
			this.checkClasses.add(OmniSprintA.class);
			this.checkClasses.add(MotionA.class);
			this.checkClasses.add(MotionB.class);
			this.checkClasses.add(MotionC.class);
			this.checkClasses.add(MotionD.class);
			this.checkClasses.add(MotionE.class);
			this.checkClasses.add(MotionF.class);
			this.checkClasses.add(MotionI.class);
			this.checkClasses.add(MotionJ.class);
			this.checkClasses.add(StepA.class);
			this.checkClasses.add(TimerA.class);
			this.checkClasses.add(TimerB.class);
			this.checkClasses.add(TimerC.class);
			this.checkClasses.add(BadPacketsA.class);
			this.checkClasses.add(BadPacketsB.class);
			this.checkClasses.add(BadPacketsC.class);
			this.checkClasses.add(BadPacketsD.class);
			this.checkClasses.add(BadPacketsE.class);
			this.checkClasses.add(BadPacketsF.class);
			this.checkClasses.add(BadPacketsG.class);
			this.checkClasses.add(BadPacketsH.class);
			this.checkClasses.add(BadPacketsI.class);
			this.checkClasses.add(BadPacketsJ.class);
			this.checkClasses.add(BadPacketsK.class);
			this.checkClasses.add(BadPacketsM.class);
			this.checkClasses.add(BadPacketsN.class);
			this.checkClasses.add(BadPacketsO.class);
			this.checkClasses.add(BadPacketsP.class);
			this.checkClasses.add(BadPacketsQ.class);
			this.checkClasses.add(BadPacketsR.class);
			this.checkClasses.add(PingA.class);
			this.checkClasses.add(ScaffoldA.class);
			this.checkClasses.add(ScaffoldB.class);
			this.checkClasses.add(ScaffoldC.class);
			this.checkClasses.add(ScaffoldD.class);
			this.checkClasses.add(ScaffoldE.class);
			this.checkClasses.add(ScaffoldF.class);
			this.checkClasses.add(ScaffoldG.class);
			this.checkClasses.add(ScaffoldH.class);
			this.checkClasses.add(ScaffoldI.class);
			this.checkClasses.add(ScaffoldJ.class);
			this.checkClasses.add(ScaffoldK.class);
			this.checkClasses.add(ScaffoldL.class);
			this.checkClasses.add(ScaffoldM.class);
			this.checkClasses.add(ScaffoldN.class);
			this.checkClasses.add(ScaffoldO.class);
			this.checkClasses.add(ScaffoldP.class);
			this.checkClasses.add(ScaffoldQ.class);
			this.checkClasses.add(ScaffoldR.class);
			this.checkClasses.add(FastBreakA.class);
			this.checkClasses.add(FastBreakB.class);
			this.checkClasses.add(FastBreakC.class);
			this.checkClasses.add(GhostBreak.class);
			this.checkClasses.add(BlockReach.class);
			this.checkClasses.add(NoLookBreak.class);
			this.checkClasses.add(GroundA.class);
			this.checkClasses.add(GroundB.class);
			this.checkClasses.add(GroundC.class);
		}

		return this.checkClasses;
	}

	public int getCheckVl(String name) {
		return this.vlMap.getOrDefault(name, 20);
	}

	public int getBanwaveVl(String name) {
		return this.banwaveVlMap.getOrDefault(name, 10);
	}

	public boolean isAutoban(String name) {
		return this.autobanMap.getOrDefault(name, true);
	}

	public boolean isBanwave(String name) {
		return this.banwaveMap.getOrDefault(name, false);
	}

	public boolean isEnabled(String name) {
		return this.enabledMap.getOrDefault(name, true);
	}

	public void initConfig(FileConfiguration checkConfiguration) {
		for (Class<? extends Check> chs : this.checkClasses) {
			CheckInfo annotation = chs.getAnnotation(CheckInfo.class);
			if (!annotation.silent()) {
				String name = annotation.name();
				String category = annotation.category().name();
				boolean exp = annotation.experimental();
				String[] idk;
				if (name.contains(" ")) {
					idk = name.split(" ");
				} else {
					idk = new String[]{name, "(A)"};
				}

				String realTypeName = idk[0];
				String typeChars = idk[1].replaceAll("[^a-zA-Z0-9]", "");
				if (!checkConfiguration.isSet(category + "." + realTypeName + "." + typeChars + ".enabled")) {
					if (exp) {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".enabled", false);
					} else {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".enabled", true);
					}
				}

				if (!checkConfiguration.isSet(category + "." + realTypeName + "." + typeChars + ".autoban")) {
					if (exp) {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".autoban", false);
					} else {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".autoban", true);
					}
				}

				if (!checkConfiguration.isSet(category + "." + realTypeName + "." + typeChars + ".punish-vl")) {
					if (exp) {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".punish-vl", 30);
					} else {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".punish-vl", 20);
					}
				}

				if (!checkConfiguration.isSet(category + "." + realTypeName + "." + typeChars + ".mode")) {
					if (exp) {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".mode", "KICK");
					} else {
						checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".mode", "BAN");
					}
				}

				if (!checkConfiguration.isSet(category + "." + realTypeName + "." + typeChars + ".banwave")) {
					checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".banwave", false);
				}

				if (!checkConfiguration.isSet(category + "." + realTypeName + "." + typeChars + ".banwave-vl")) {
					checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".banwave-vl", 10);
				}

				int banVL = checkConfiguration.getInt(category + "." + realTypeName + "." + typeChars + ".punish-vl");
				int banwaveVL = checkConfiguration.getInt(category + "." + realTypeName + "." + typeChars + ".banwave-vl");
				boolean enabled = checkConfiguration.getBoolean(category + "." + realTypeName + "." + typeChars + ".enabled");
				boolean autoban = checkConfiguration.getBoolean(category + "." + realTypeName + "." + typeChars + ".autoban");
				boolean banwave = checkConfiguration.getBoolean(category + "." + realTypeName + "." + typeChars + ".banwave");
				this.enabledMap.put(name, enabled);
				this.autobanMap.put(name, autoban);
				this.banwaveMap.put(name, banwave);
				this.vlMap.put(name, banVL);
				this.banwaveVlMap.put(name, banwaveVL);
			}
		}
	}

	public boolean isBanning(Check check) {
		CheckInfo annotation = check.getCheckInfo();
		String name = annotation.name();
		String category = annotation.category().name();
		String[] idk;
		if (name.contains(" ")) {
			idk = name.split(" ");
		} else {
			idk = new String[]{name, "(A)"};
		}

		String realTypeName = idk[0];
		String typeChars = idk[1].replaceAll("[^a-zA-Z0-9]", "");
		String mode = katana.getInstance().getConfigManager().getChecks().getString(category + "." + realTypeName + "." + typeChars + ".mode");
		return mode != null && mode.equalsIgnoreCase("BAN");
	}

	public void updateChecks() {
		ConfigManager checkConfig = katana.getInstance().getConfigManager();
		FileConfiguration checkConfiguration = checkConfig.getChecks();

		for (Class<? extends Check> chs : this.checkClasses) {
			CheckInfo annotation = chs.getAnnotation(CheckInfo.class);
			String name = annotation.name();
			String category = annotation.category().name();
			String[] idk;
			if (name.contains(" ")) {
				idk = name.split(" ");
			} else {
				idk = new String[]{name, "(A)"};
			}

			String realTypeName = idk[0];
			String typeChars = idk[1].replaceAll("[^a-zA-Z0-9]", "");
			boolean enabled = checkConfiguration.getBoolean(category + "." + realTypeName + "." + typeChars + ".enabled", true);
			boolean autoban = checkConfiguration.getBoolean(category + "." + realTypeName + "." + typeChars + ".autoban", true);
			boolean banwave = checkConfiguration.getBoolean(category + "." + realTypeName + "." + typeChars + ".banwave", false);
			this.enabledMap.put(name, enabled);
			this.autobanMap.put(name, autoban);
			this.banwaveMap.put(name, banwave);
			this.updateVls(name, category);
		}
	}

	public void setAutoban(String name, boolean b) {
		this.autobanMap.put(name, b);
	}

	public void setEnabled(String name, boolean b) {
		this.enabledMap.put(name, b);
	}

	public void setPunishVl(String name, int amount) {
		this.vlMap.put(name, amount);
	}

	public void updateVls(String name, String category) {
		String[] idk;
		if (name.contains(" ")) {
			idk = name.split(" ");
		} else {
			idk = new String[]{name, "(A)"};
		}

		String realTypeName = idk[0];
		String typeChars = idk[1].replaceAll("[^a-zA-Z0-9]", "");
		if (!katana.getInstance().getConfigManager().getChecks().isSet(category + "." + realTypeName + "." + typeChars + ".punish-vl")) {
			katana.getInstance().getConfigManager().getChecks().set(category + "." + realTypeName + "." + typeChars + ".punish-vl", 20);
		}

		if (!katana.getInstance().getConfigManager().getChecks().isSet(category + "." + realTypeName + "." + typeChars + ".banwave-vl")) {
			katana.getInstance().getConfigManager().getChecks().set(category + "." + realTypeName + "." + typeChars + ".banwave-vl", 10);
		}

		int banVL = katana.getInstance().getConfigManager().getChecks().getInt(category + "." + realTypeName + "." + typeChars + ".punish-vl");
		int banwaveVL = katana.getInstance().getConfigManager().getChecks().getInt(category + "." + realTypeName + "." + typeChars + ".banwave-vl");
		this.vlMap.put(name, banVL);
		this.banwaveVlMap.put(name, banwaveVL);
	}

	public Set<Class<? extends Check>> getCheckClasses() {
		return this.checkClasses;
	}

	public Map<String, Boolean> getEnabledMap() {
		return this.enabledMap;
	}

	public Map<String, Boolean> getAutobanMap() {
		return this.autobanMap;
	}

	public Map<String, Boolean> getBanwaveMap() {
		return this.banwaveMap;
	}

	public Map<String, Integer> getVlMap() {
		return this.vlMap;
	}

	public Map<String, Integer> getBanwaveVlMap() {
		return this.banwaveVlMap;
	}

	public Map<String, Integer> getPullbackVlMap() {
		return this.pullbackVlMap;
	}
}
