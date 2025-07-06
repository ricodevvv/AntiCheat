/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.killaura;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.AttackEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.SwingEvent;

@CheckInfo(
	name = "Killaura (C)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = true
)
public final class KillauraC extends PacketCheck {
	private boolean swung;
	private int swungAt;
	private int swings;
	private int attacks;

	public KillauraC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (this.data.getClientVersion().getProtocolVersion() <= 47 && !club.imanity.katana.katana.getInstance().isViaRewind()) {
			if (packet instanceof AttackEvent) {
				if (!this.swung) {
					this.fail("* NoSwing\n* sw " + this.data.elapsed(this.swungAt), this.getBanVL(), 300L);
				}
			} else if (packet instanceof FlyingEvent) {
				this.swung = false;
			} else if (packet instanceof SwingEvent) {
				this.swung = true;
				this.swungAt = this.data.getTotalTicks();
			}
		} else if (packet instanceof AttackEvent) {
			++this.attacks;
		} else if (packet instanceof FlyingEvent) {
			if (this.attacks > 1) {
				if (this.swings < 1) {
					this.fail("* NoSwing (1.9+/ViaRw)\n* sw " + this.data.elapsed(this.swungAt), this.getBanVL(), 300L);
				}

				this.attacks = 0;
				this.swings = 0;
			}
		} else if (packet instanceof SwingEvent) {
			++this.swings;
			this.swungAt = this.data.getTotalTicks();
		}
	}
}
