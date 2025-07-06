/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.scaffold;

import java.util.LinkedList;
import java.util.Queue;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.util.MathUtil;
import org.bukkit.inventory.ItemStack;

@CheckInfo(
	name = "Scaffold (Q)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = true
)
public final class ScaffoldQ extends PacketCheck {
	private final Queue<Integer> delays = new LinkedList<>();
	private int movements;

	public ScaffoldQ(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
		this.setSetback(false);
	}

	@Override
	public void handle(Event packet) {
		if (!this.data.isNewerThan16()) {
			if (packet instanceof BlockPlaceEvent) {
				if (this.movements != 4 && this.movements < 10) {
					ItemStack item = ((BlockPlaceEvent)packet).getItemStack();
					if (item != null && item.getType().isBlock() && this.delays.add(this.movements) && this.delays.size() == 35) {
						double avg = MathUtil.getAverage(this.delays);
						double stDev = MathUtil.getStandardDeviation(this.delays);
						double cps = 20.0 / avg;
						if (avg < 6.0 && stDev < 0.225) {
							String info = String.format("CPS %.3f AVG %s STD %s", cps, avg, stDev);
							this.fail("* Rightclicker\n" + info, this.getBanVL(), 250L);
						}

						this.delays.clear();
					}
				}

				this.movements = 0;
			} else if (packet instanceof FlyingEvent) {
				++this.movements;
			}
		}
	}
}
