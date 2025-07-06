/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.autoclicker;

import java.util.ArrayDeque;
import java.util.Deque;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.SwingEvent;

@CheckInfo(
	name = "AutoClicker (G)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = false,
	credits = "§c§lCREDITS: §aMexican §7made this check."
)
public final class AutoClickerG extends PacketCheck {
	private final Deque<Integer> delays = new ArrayDeque<>();
	private int delay;
	private int vl;

	public AutoClickerG(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SwingEvent) {
			boolean valid = !this.data.isPlacing() && !this.data.isHasDig() && !this.data.isUsingItem();
			if (this.data.isNewerThan8()) {
				if (this.delay <= 5 && valid && this.data.elapsedMS(((SwingEvent)packet).getTimeStamp(), this.data.getLastFlying()) <= 70L) {
					this.delays.add(this.delay);
				}
			} else if (this.delay <= 5 && valid) {
				this.delays.add(this.delay);
			}

			if (this.delays.size() == 27) {
				int delta = this.delays.stream().mapToInt(i -> i).max().orElse(0) - this.delays.stream().mapToInt(i -> i).min().orElse(0);
				if (delta == 1) {
					if (++this.vl > 22) {
						this.fail("* Impossible large-sample sequence", this.getBanVL(), 10000L);
					}
				} else if (delta == 2) {
					this.vl = Math.max(this.vl - 6, 0);
				} else {
					this.vl = Math.max(this.vl - 12, 0);
				}

				this.delays.clear();
			}

			this.delay = 0;
		} else if (packet instanceof FlyingEvent) {
			++this.delay;
		}
	}
}
