/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerAbilities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerAbilities;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.location.CustomLocation;

public class AbilityManager {
	private final KatanaPlayer data;
	private int badTicks = -1;
	private boolean flySet;

	public AbilityManager(KatanaPlayer data) {
		this.data = data;
	}

	public void onFlying() {
		this.flySet = false;
		if (this.badTicks == 0) {
			this.badTicks = 1;
		} else if (this.badTicks == 1) {
			this.data.flying = false;
			this.badTicks = -1;
		}
	}

	public void onAbilityClient(WrapperPlayClientPlayerAbilities abilities) {
		boolean fly = abilities.isFlying();
		if (this.flySet && !abilities.isFlying()) {
			this.flySet = false;
			this.badTicks = 0;
		} else {
			if (abilities.isFlying()) {
				this.flySet = true;
			}

			this.data.flying = fly && this.data.allowFlying;
		}
	}

	public void onAbilityServer(WrapperPlayServerPlayerAbilities packet) {
		if (!packet.isFlightAllowed()) {
			CustomLocation location = this.data.getLocation();
			long time = this.data.getServerTick() - this.data.getLastTeleportPacket();
			if (this.data.getTeleportManager().teleportsPending <= 0 && time > 3L) {
				this.data.setFlyCancel(new CustomLocation(location.x, location.y, location.z));
			}

			this.data.confirmingFlying = true;
			this.data.setLastConfirmingState(this.data.getTotalTicks());
		}

		this.data.queueToPrePing(uid -> {
			this.data.flying = packet.isFlying();
			this.data.allowFlying = packet.isFlightAllowed();
			this.data.confirmingFlying = false;
			this.badTicks = -1;
		});
	}
}
