/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import club.imanity.katana.katana;
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
import club.imanity.katana.check.impl.mouse.Mouse;
import club.imanity.katana.check.impl.mouse.Sensitivity;
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
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.check.type.PositionCheck;
import club.imanity.katana.check.type.RotationCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.APICaller;
import club.imanity.katana.util.benchmark.Benchmark;
import club.imanity.katana.util.benchmark.BenchmarkType;
import club.imanity.katana.util.benchmark.KatanaBenchmarker;

public final class CheckManager {
	private final Check[] checks;
	private final KatanaPlayer kp;
	private final List<RotationCheck> rotationChecks;
	private final List<PositionCheck> positionChecks;
	private final List<PacketCheck> packetChecks;

	public CheckManager(KatanaPlayer katanaPlayer, katana katana) {
		this.kp = katanaPlayer;
		List<Check> c = Arrays.asList(
			new AutoClickerA(katanaPlayer, katana),
			new AutoClickerB(katanaPlayer, katana),
			new AutoClickerC(katanaPlayer, katana),
			new AutoClickerD(katanaPlayer, katana),
			new AutoClickerE(katanaPlayer, katana),
			new AutoClickerF(katanaPlayer, katana),
			new AutoClickerG(katanaPlayer, katana),
			new AutoClickerH(katanaPlayer, katana),
			new AutoClickerI(katanaPlayer, katana),
			new AutoClickerJ(katanaPlayer, katana),
			new AutoClickerK(katanaPlayer, katana),
			new AutoClickerL(katanaPlayer, katana),
			new AutoClickerP(katanaPlayer, katana),
			new AutoClickerU(katanaPlayer, katana),
			new AutoClickerW(katanaPlayer, katana),
			new VelocityA(katanaPlayer, katana),
			new VelocityB(katanaPlayer, katana),
			new ReachA(katanaPlayer, katana),
			new HitboxA(katanaPlayer, katana),
			new AimAssistA(katanaPlayer, katana),
			new AimAssistB(katanaPlayer, katana),
			new AimAssistC(katanaPlayer, katana),
			new AimAssistD(katanaPlayer, katana),
			new AimAssistE(katanaPlayer, katana),
			new AimAssistF(katanaPlayer, katana),
			new AimAssistG(katanaPlayer, katana),
			new AimAssistH(katanaPlayer, katana),
			new AimAssistI(katanaPlayer, katana),
			new AimAssistJ(katanaPlayer, katana),
			new AimAssistM(katanaPlayer, katana),
			new AimAssistN(katanaPlayer, katana),
			new AnalysisA(katanaPlayer, katana),
			new AnalysisB(katanaPlayer, katana),
			new AnalysisC(katanaPlayer, katana),
			new AnalysisD(katanaPlayer, katana),
			new AnalysisE(katanaPlayer, katana),
			new AnalysisF(katanaPlayer, katana),
			new KillauraA(katanaPlayer, katana),
			new KillauraB(katanaPlayer, katana),
			new KillauraC(katanaPlayer, katana),
			new KillauraE(katanaPlayer, katana),
			new KillauraF(katanaPlayer, katana),
			new KillauraG(katanaPlayer, katana),
			new KillauraH(katanaPlayer, katana),
			new KillauraI(katanaPlayer, katana),
			new KillauraJ(katanaPlayer, katana),
			new KillauraK(katanaPlayer, katana),
			new KillauraM(katanaPlayer, katana),
			new KillauraN(katanaPlayer, katana),
			new FlyA(katanaPlayer, katana),
			new FlyB(katanaPlayer, katana),
			new FlyC(katanaPlayer, katana),
			new FlyD(katanaPlayer, katana),
			new FlyE(katanaPlayer, katana),
			new FlyF(katanaPlayer, katana),
			new VehicleFly(katanaPlayer, katana),
			new MotionA(katanaPlayer, katana),
			new MotionB(katanaPlayer, katana),
			new MotionC(katanaPlayer, katana),
			new MotionD(katanaPlayer, katana),
			new MotionE(katanaPlayer, katana),
			new MotionF(katanaPlayer, katana),
			new MotionI(katanaPlayer, katana),
			new MotionJ(katanaPlayer, katana),
			new StepA(katanaPlayer, katana),
			new SpeedA(katanaPlayer, katana),
			new SpeedB(katanaPlayer, katana),
			new SpeedC(katanaPlayer, katana),
			new OmniSprintA(katanaPlayer, katana),
			new JesusA(katanaPlayer, katana),
			new JesusB(katanaPlayer, katana),
			new InventoryA(katanaPlayer, katana),
			new InventoryB(katanaPlayer, katana),
			new BadPacketsA(katanaPlayer, katana),
			new BadPacketsB(katanaPlayer, katana),
			new BadPacketsC(katanaPlayer, katana),
			new BadPacketsD(katanaPlayer, katana),
			new BadPacketsE(katanaPlayer, katana),
			new BadPacketsF(katanaPlayer, katana),
			new BadPacketsG(katanaPlayer, katana),
			new BadPacketsH(katanaPlayer, katana),
			new BadPacketsI(katanaPlayer, katana),
			new BadPacketsJ(katanaPlayer, katana),
			new BadPacketsK(katanaPlayer, katana),
			new BadPacketsM(katanaPlayer, katana),
			new BadPacketsN(katanaPlayer, katana),
			new BadPacketsO(katanaPlayer, katana),
			new BadPacketsQ(katanaPlayer, katana),
			new BadPacketsR(katanaPlayer, katana),
			new PingA(katanaPlayer, katana),
			new TimerA(katanaPlayer, katana),
			new TimerB(katanaPlayer, katana),
			new TimerC(katanaPlayer, katana),
			new ScaffoldA(katanaPlayer, katana),
			new ScaffoldB(katanaPlayer, katana),
			new ScaffoldC(katanaPlayer, katana),
			new ScaffoldD(katanaPlayer, katana),
			new ScaffoldE(katanaPlayer, katana),
			new ScaffoldF(katanaPlayer, katana),
			new ScaffoldG(katanaPlayer, katana),
			new ScaffoldH(katanaPlayer, katana),
			new ScaffoldI(katanaPlayer, katana),
			new ScaffoldJ(katanaPlayer, katana),
			new ScaffoldK(katanaPlayer, katana),
			new ScaffoldL(katanaPlayer, katana),
			new ScaffoldM(katanaPlayer, katana),
			new ScaffoldN(katanaPlayer, katana),
			new ScaffoldO(katanaPlayer, katana),
			new ScaffoldP(katanaPlayer, katana),
			new ScaffoldQ(katanaPlayer, katana),
			new ScaffoldR(katanaPlayer, katana),
			new FastBreakA(katanaPlayer, katana),
			new FastBreakB(katanaPlayer, katana),
			new FastBreakC(katanaPlayer, katana),
			new GhostBreak(katanaPlayer, katana),
			new BlockReach(katanaPlayer, katana),
			new NoLookBreak(katanaPlayer, katana),
			new GroundA(katanaPlayer, katana),
			new GroundB(katanaPlayer, katana),
			new GroundC(katanaPlayer, katana),
			new Sensitivity(katanaPlayer, katana),
			new Mouse(katanaPlayer, katana)
		);
		this.checks = c.toArray(new Check[0]);
		this.packetChecks = this.getAllOfType(PacketCheck.class);
		this.positionChecks = this.getAllOfType(PositionCheck.class);
		this.rotationChecks = this.getAllOfType(RotationCheck.class);
	}

	public void runChecks(List<? extends Check> paskat, Object e, Object packet) {
		long start = System.nanoTime();

		for (Check c : paskat) {
			if (katana.getInstance().getCheckState().isEnabled(c.getName()) || c.isSilent()) {
				if (katana.isAPIAvailable()) {
					if (APICaller.callPreCheck(c.getCheckInfo(), c, this.kp.getBukkitPlayer(), packet)) {
						c.setDidFail(false);
						c.handle(e);
						APICaller.callPostCheck(this.kp.getBukkitPlayer(), c.getCheckInfo(), c, packet);
					}
				} else {
					c.setDidFail(false);
					c.handle(e);
				}
			}
		}

		Benchmark profileData = KatanaBenchmarker.getProfileData(BenchmarkType.CHECKS);
		profileData.insertResult(start, System.nanoTime());
	}

	public Check[] getChecks() {
		return this.checks;
	}

	public int checkAmount() {
		return this.checks.length;
	}

	public <T> T getCheck(Class<T> clazz) {
		return (T)Arrays.stream(this.checks).filter(check -> check.getClass() == clazz).findFirst().orElse(null);
	}

	private <T> List<T> getAllOfType(Class<T> clazz) {
		return (List<T>) Arrays.stream(this.checks).filter(clazz::isInstance).collect(Collectors.toList());
	}

	public List<RotationCheck> getRotationChecks() {
		return this.rotationChecks;
	}

	public List<PositionCheck> getPositionChecks() {
		return this.positionChecks;
	}

	public List<PacketCheck> getPacketChecks() {
		return this.packetChecks;
	}
}
