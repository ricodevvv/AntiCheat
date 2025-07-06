/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.scaffold;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

@CheckInfo(
	name = "Scaffold (C)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = false
)
public final class ScaffoldC extends PacketCheck {
	private int delay;
	private int lastDelay;
	private int susClicks;
	private int clicks;

	public ScaffoldC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
		this.setSetback(false);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent) {
			BlockPlaceEvent place = (BlockPlaceEvent)packet;
			if (place.isUsableItem() || this.data.isRiding()) {
				return;
			}

			Location blockPos = place.get420Johannes();
			Block block = club.imanity.katana.katana.getInstance().getChunkManager().getChunkBlockAt(blockPos);
			if (block != null) {
				ItemStack stack = place.getItemStack() == null ? new ItemStack(Material.AIR) : place.getItemStack();
				boolean additionable = block.getType() == stack.getType();
				if (additionable && this.delay <= 8) {
					if (this.delay == this.lastDelay) {
						++this.susClicks;
					}

					if (++this.clicks == 100) {
						if (this.susClicks > 70) {
							this.fail("* Scaffold like click pattern\n§f* SU §b" + this.susClicks, this.getBanVL(), 250L);
						}

						this.susClicks = 0;
						this.clicks = 0;
					}
				}
			}

			this.lastDelay = this.delay;
			this.delay = 0;
		} else if (packet instanceof FlyingEvent) {
			++this.delay;
		}
	}
}
