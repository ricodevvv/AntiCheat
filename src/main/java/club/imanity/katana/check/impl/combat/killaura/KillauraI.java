/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.killaura;

import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.AttackEvent;
import club.imanity.katana.event.DigEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.InteractEvent;

@CheckInfo(
	name = "Killaura (I)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false
)
public final class KillauraI extends PacketCheck {
	private long sentDig;

	public KillauraI(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (!(packet instanceof AttackEvent) && !(packet instanceof InteractEvent)) {
			if (packet instanceof DigEvent) {
				DiggingAction type = ((DigEvent)packet).getDigType();
				if (type != DiggingAction.DROP_ITEM_STACK && type != DiggingAction.DROP_ITEM) {
					this.sentDig = ((DigEvent)packet).getNow();
				}
			}
		} else {
			long now;
			if (packet instanceof AttackEvent) {
				now = ((AttackEvent)packet).getNow();
			} else {
				now = ((InteractEvent)packet).getNow();
			}

			long delay = (long)((double)(now - this.sentDig) / 1000000.0);
			if (delay >= 10L || this.data.elapsed(this.data.getLastPacketDrop()) <= 5 || this.getKatana().isServerLagging(now) || !(this.getKatana().getTPS() >= 19.95)) {
				this.violations = Math.max(this.violations - 0.35, 0.0);
			} else if (++this.violations > 5.0) {
				this.fail("* Illegal block order", this.getBanVL(), 60L);
			}
		}
	}
}
