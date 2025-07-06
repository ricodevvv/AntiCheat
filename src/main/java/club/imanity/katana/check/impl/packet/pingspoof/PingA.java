/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.pingspoof;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.ConnectionHeartbeatEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;

@CheckInfo(
	name = "Ping (A)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = true
)
public final class PingA extends PacketCheck {
	private boolean kReceived;
	private boolean suspicious;

	public PingA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof ConnectionHeartbeatEvent) {
			this.suspicious = Math.abs(this.data.getPing() - this.data.getTransactionPing()) > 150L;
			this.kReceived = true;
		} else if (packet instanceof FlyingEvent && this.kReceived) {
			if (!this.suspicious || this.data.getPing() <= this.data.getTransactionPing() + 200L) {
				this.violations = Math.max(this.violations - 0.25, 0.0);
			} else if (++this.violations >= 5.0) {
				this.fail(
					"§f* Spoofed ping\n §f* diff: §b"
						+ Math.abs(this.data.getPing() - this.data.getTransactionPing())
						+ "\n §f* TRANSAC: §b"
						+ this.data.getTransactionPing()
						+ "\n §f* KEEPAL: §b"
						+ this.data.getPing(),
					this.getMaxvl(),
					40L
				);
			}

			this.kReceived = false;
		}
	}
}
