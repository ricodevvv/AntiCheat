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
import club.imanity.katana.event.TransactionEvent;

@CheckInfo(
	name = "Killaura (N)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = true
)
public final class KillauraN extends PacketCheck {
	public int targetAmount;
	public int lastEntity;

	public KillauraN(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof AttackEvent) {
			int currentTarget = ((AttackEvent)packet).getEntityId();
			if (currentTarget != this.lastEntity) {
				++this.targetAmount;
			}

			this.lastEntity = currentTarget;
		} else if (packet instanceof FlyingEvent) {
			if (this.data.isPossiblyTeleporting()) {
				this.targetAmount = 0;
				return;
			}

			if (this.data.getClientVersion().getProtocolVersion() <= 47 && this.targetAmount > 1) {
				this.fail("* Multiaura\n §f* targets: §b" + this.targetAmount + "\n §f* cps: §b" + this.format(3, Double.valueOf(this.data.getCps())), this.getBanVL(), 300L);
			}

			this.targetAmount = 0;
		} else if (packet instanceof TransactionEvent && this.data.getClientVersion().getProtocolVersion() > 47) {
			if (this.targetAmount > 1) {
				this.fail("* hMultiaura (1.9)\n §f* targets: §b" + this.targetAmount + "\n §f* cps: §b" + this.format(3, Double.valueOf(this.data.getCps())), this.getBanVL(), 300L);
			}

			this.targetAmount = 0;
		}
	}
}
