/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.block;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;

@CheckInfo(
	name = "GhostBreak (A)",
	category = Category.WORLD,
	subCategory = SubCategory.BLOCK,
	experimental = false,
	credits = "§c§lCREDITS: §aIslandscout §7for the check. https://github.com/HawkAnticheat/Hawk"
)
public final class GhostBreak extends PacketCheck {
	public GhostBreak(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
	}
}
