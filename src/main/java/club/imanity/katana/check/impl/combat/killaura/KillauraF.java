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

@CheckInfo(
	name = "Killaura (F)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false
)
public final class KillauraF extends PacketCheck {
	public KillauraF(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof AttackEvent && (this.data.isPlacing() || this.data.isBlocking() && this.data.getBukkitPlayer().isBlocking())) {
			this.fail("* Illegal sword blocking order\n §f* P: §b" + this.data.isPlacing() + "\n §f* B: §b" + this.data.isBlocking(), this.getBanVL(), 600L);
		}
	}
}
