package club.imanity.katana.check.impl.world.scaffold;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import org.bukkit.util.Vector;

@CheckInfo(
		name = "Scaffold (N)",
		category = Category.WORLD,
		subCategory = SubCategory.SCAFFOLD,
		experimental = true,
		credits = "§c§lCREDITS: §afrogsmasha §7for the base idea."
)
public final class ScaffoldN extends PacketCheck {
	public ScaffoldN(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent) {
			Vector pos = ((BlockPlaceEvent)packet).getBlockPos();
			if (pos.getX() != -1.0 && (pos.getY() != 255.0 || pos.getY() != -1.0) && pos.getZ() != -1.0) {
				int face = ((BlockPlaceEvent)packet).getFace();
				if (face < 0 || face > 6) {
					this.fail("* Impossible block face\n §f* face: §b" + face, this.getBanVL(), 120L);
				}
			} else if (pos.getX() == -1.0 && (pos.getY() == 255.0 || pos.getY() == -1.0) && pos.getZ() == -1.0) {
				int face = ((BlockPlaceEvent)packet).getFace();
				if (face >= 0 && face <= 6) {
					this.fail("* Impossible block face\n §f* face: §b" + face, this.getBanVL(), 120L);
				}
			}
		}

	}
}