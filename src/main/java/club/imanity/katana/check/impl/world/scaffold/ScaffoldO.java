/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.scaffold;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction.Action;
import java.util.ArrayDeque;
import java.util.Deque;

import lombok.SneakyThrows;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.ActionEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.util.MathUtil;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.bukkit.Material;
import org.bukkit.block.Block;

@CheckInfo(
	name = "Scaffold (O)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = true
)
public final class ScaffoldO extends PacketCheck {
	Deque<Integer> interactions = new ArrayDeque<>();
	int flying;

	public ScaffoldO(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent) {
			if (((FlyingEvent)packet).hasMoved() || ((FlyingEvent)packet).hasLooked()) {
				++this.flying;
			}
		} else if (packet instanceof ActionEvent) {
			if (((ActionEvent)packet).getAction() != Action.STOP_SNEAKING) {
				return;
			}

			if (this.data.elapsed(this.data.getUnderPlaceTicks()) > 3 || !this.isNotGroundBridging()) {
				return;
			}

			if (this.interactions.add(this.flying) && this.interactions.size() >= 15) {
				double std = new StandardDeviation().evaluate(MathUtil.dequeTranslator(this.interactions));
				if (std < 0.325) {
					this.fail("* Eagle\n" + String.format("std: %.2f", std), this.getBanVL(), 125L);
				} else if (std < 0.65) {
					if (++this.violations > 2.0) {
						this.fail("* Eagle\n" + String.format("std: %.2f", std), this.getBanVL(), 125L);
					}
				} else {
					this.violations = Math.max(this.violations - 0.35, 0.0);
				}

				this.interactions.clear();
			}

			this.flying = 0;
		}
	}
@SneakyThrows
	public boolean isNotGroundBridging() {

			Block block = club.imanity.katana.katana.getInstance().getChunkManager().getChunkBlockAt(this.data.getLocation().clone().subtract(0.0, 2.0, 0.0).toLocation(this.data.getWorld()));
			if (block == null) {
				return false;
			} else {
				return block.getType() == Material.AIR;
			}

	}
}
