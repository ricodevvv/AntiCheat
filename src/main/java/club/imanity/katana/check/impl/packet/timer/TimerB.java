/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.timer;

import java.util.Deque;
import java.util.LinkedList;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.util.MathUtil;

@CheckInfo(
	name = "Timer (B)",
	category = Category.PACKET,
	subCategory = SubCategory.TIMER,
	experimental = false
)
public final class TimerB extends PacketCheck {
	private final Deque<Long> packets = new LinkedList<>();
	private Long lastFlyingPacket = null;

	public TimerB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent) {
			long now = ((FlyingEvent)packet).getCurrentTimeMillis();
			if (club.imanity.katana.katana.getInstance().hasRecentlyDropped(2250L)) {
				this.packets.clear();
			}

			if (this.lastFlyingPacket != null && !club.imanity.katana.katana.getInstance().hasRecentlyDropped(5000L)) {
				long timeDiff = now - this.lastFlyingPacket;
				this.packets.add(timeDiff);
				if (this.packets.size() >= 50) {
					double average = MathUtil.getAverage(this.packets);
					double timer = 50.0 / average;
					double speed = !this.data.isNewerThan8() ? 0.75 : 0.02;
					double deviation = MathUtil.getStandardDeviation(this.packets);
					double addition = deviation < 8.0 ? 2.5 : 1.25;
					addition += timer <= speed / 3.0 ? 1.25 : 0.0;
					if (timer <= speed && deviation < 150.0) {
						if ((this.violations += addition) > 3.5) {
							this.fail("* Timer\n§f* TS §b" + this.format(2, Double.valueOf(timer)) + "\n§f* DEV §b" + deviation, this.getBanVL(), 400L);
						}
					} else {
						this.violations = Math.max(this.violations - 0.65, 0.0);
					}

					this.packets.clear();
				}
			}

			this.lastFlyingPacket = now;
		}
	}
}
