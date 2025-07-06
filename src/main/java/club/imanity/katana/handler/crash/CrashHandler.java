/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.crash;

import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import club.imanity.katana.katana;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.handler.interfaces.ICrashHandler;
import club.imanity.katana.manager.alert.MiscellaneousAlertPoster;
import club.imanity.katana.util.location.CustomLocation;
import net.kyori.adventure.text.Component;

public final class CrashHandler implements ICrashHandler {
	private final KatanaPlayer data;
	private double nonMoves;
	private double fPackets;
	private int customPayloads;
	private int slotPackets;
	private int armAnimations;
	private int windowClicks;
	private int windowClicks2;
	private int places;
	private int lastSlot;
	private boolean logged;

	@Override
	public void handleFlying(boolean moved, boolean looked, CustomLocation location, CustomLocation lastLocation) {
		if (++this.fPackets > (double)(this.data.getClientVersion().isOlderThan(ClientVersion.V_1_9) ? 300 : 900) && this.shouldPunish("Move spam")) {
			this.handleKickAlert("Move spam");
		}

		if (moved
			&& !this.data.isPossiblyTeleporting()
			&& !this.data.recentlyTeleported(3)
			&& (this.data.deltas.deltaXZ > 100.0 || Math.abs(this.data.deltas.motionY) + (double)((float)this.data.getJumpBoost() * 0.1F) > 100000.0)
			&& this.shouldPunish("Large move")) {
			this.handleKickAlert("Large move");
		}

		this.armAnimations = 0;
		this.slotPackets = 0;
		this.windowClicks = 0;
		this.windowClicks2 = 0;
		this.customPayloads = 0;
		this.places = 0;
	}

	@Override
	public void handleArm() {
		if (++this.armAnimations > 200 && this.shouldPunish("Arm")) {
			this.handleKickAlert("Arm");
		}
	}

	@Override
	public void handleSlot() {
		if (++this.slotPackets > 200 && this.shouldPunish("Slot")) {
			this.handleKickAlert("Slot");
		}
	}

	@Override
	public void handleWindowClick(int slot, int mode, int id, int button) {
		if ((Math.abs(slot - this.lastSlot) == 1 && id == 0 && ++this.windowClicks > 50 || ++this.windowClicks2 > (this.data.isNewerThan8() ? 125 : 5)) && this.shouldPunish("Window")) {
			this.handleKickAlert("Window");
		}
	}

	@Override
	public void handleClientKeepAlive() {
		this.fPackets = 0.0;
	}

	@Override
	public void handleCustomPayload() {
		if (++this.customPayloads > 30 && this.shouldPunish("Payload")) {
			this.handleKickAlert("Payload");
		}
	}

	@Override
	public void handlePlace() {
		if (++this.places > 200 && this.shouldPunish("Place")) {
			this.handleKickAlert("Place");
		}
	}

	public boolean shouldPunish(String type) {
		if (katana.getInstance().getConfigManager().isAnticrash()) {
			switch (type) {
				case "Place":
					if (!katana.getInstance().getConfigManager().isPlaceSpam()) {
						return true;
					}
					break;
				case "Arm":
					if (!katana.getInstance().getConfigManager().isArmSpam()) {
						return true;
					}
					break;
				case "Slot":
					if (!katana.getInstance().getConfigManager().isSlotSpam()) {
						return true;
					}
					break;
				case "Window":
					if (!katana.getInstance().getConfigManager().isWindowSpam()) {
						return true;
					}
					break;
				case "Large move":
					if (!katana.getInstance().getConfigManager().isLargeMove()) {
						return true;
					}
					break;
				case "Move spam":
					if (!katana.getInstance().getConfigManager().isMoveSpam()) {
						return true;
					}
					break;
				case "Payload":
					if (!katana.getInstance().getConfigManager().isPayloadSpam()) {
						return true;
					}
					break;
				default:
					return false;
			}
		}

		return false;
	}

	public void handleKickAlert(String type) {
		if (!this.logged) {
			this.data.getUser().sendPacket(new WrapperPlayServerDisconnect(this.fixMessage(katana.getInstance().getConfigManager().getAnticrashKickMsg())));
			this.data.getUser().closeConnection();
			MiscellaneousAlertPoster.postMisc(
				katana.getInstance().getConfigManager().getAntiCrashMessage().replaceAll("%debug%", type).replaceAll("%player%", this.data.getName()), this.data, "Crash"
			);
			katana.getInstance().getPlug().getLogger().warning("-----------------Katana Anticrash-----------------");
			katana.getInstance().getPlug().getLogger().warning(this.data.getName() + " was kicked for suspicious packets (" + type + ")");
			katana.getInstance().getPlug().getLogger().warning("Keep an eye on the player!");
			katana.getInstance().getPlug().getLogger().warning("-----------------Katana Anticrash-----------------");
			this.logged = true;
		}
	}

	public Component fixMessage(String msg) {
		return katana.getInstance().getComponentSerializer().deserialize(msg);
	}

	public CrashHandler(KatanaPlayer data) {
		this.data = data;
	}
}
