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
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.PositionEvent;
import club.imanity.katana.util.MathUtil;

@CheckInfo(
	name = "Timer (C)",
	category = Category.PACKET,
	subCategory = SubCategory.TIMER,
	experimental = true
)
public final class TimerC extends PacketCheck {
	private final Deque<Long> packets = new LinkedList<>();
	private Long lastFlyingPacket = null;
	private long lastFlag;

	public TimerC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
		this.violations = -1.0;
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent && !this.data.recentlyTeleported(5)) {
			long now = ((FlyingEvent)packet).getCurrentTimeMillis();
			if (this.lastFlyingPacket != null) {
				long timeDiff = now - this.lastFlyingPacket;
				this.packets.add(timeDiff);
				if (this.packets.size() >= 50) {
					double average = MathUtil.getAverage(this.packets);
					double timer = 50.0 / average;
					double speed = !this.data.isNewerThan8() ? 1.25 : 1.1;
					if (timer > speed && this.data.getTotalTicks() > 200) {
						if (++this.violations > 3.0) {
							this.fail("* Timer\n§f* TS §b" + this.format(2, Double.valueOf(timer)) + "\n§f* BADS §b" + this.packets.stream().filter(l -> l < 50L).count() + "/50", this.getBanVL(), 300L);
						}
					} else if (now - this.lastFlag > 12500L) {
						this.violations = Math.max(this.violations - 1.25, -2.0);
					} else {
						this.violations = Math.max(this.violations - 0.65, -1.0);
					}

					this.packets.clear();
				}
			}

			this.lastFlyingPacket = now;
		} else if (packet instanceof PositionEvent) {
			this.packets.add(150L);
		} else if (packet instanceof BlockPlaceEvent && this.data.getClientVersion().getProtocolVersion() > 754) {
			this.packets.add(100L);
		}
	}
}
