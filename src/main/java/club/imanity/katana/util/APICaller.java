/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util;

import club.imanity.katana.api.KatanaAPI;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.data.Category;
import club.imanity.katana.api.data.CheckData;
import club.imanity.katana.api.data.DebugData;
import club.imanity.katana.api.data.SubCategory;
import club.imanity.katana.api.event.KatanaEvent;
import club.imanity.katana.api.event.impl.KatanaAlertEvent;
import club.imanity.katana.api.event.impl.KatanaBanEvent;
import club.imanity.katana.api.event.impl.KatanaInitEvent;
import club.imanity.katana.api.event.impl.KatanaPlayerRegistrationEvent;
import club.imanity.katana.api.event.impl.KatanaPlayerUnregisterEvent;
import club.imanity.katana.api.event.impl.KatanaPostCheckEvent;
import club.imanity.katana.api.event.impl.KatanaPreCheckEvent;
import club.imanity.katana.api.event.impl.KatanaPullbackEvent;
import club.imanity.katana.check.api.Check;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class APICaller {
	private static CheckData checkData = new CheckData();

	public static void callInit(long loadMs) {
		KatanaAPI.getEventRegistry().fireEvent(new KatanaInitEvent(loadMs));
	}

	public static void callRegister(Player player) {
		KatanaAPI.getEventRegistry().fireEvent(new KatanaPlayerRegistrationEvent(player));
	}

	public static void callUnregister(Player player) {
		KatanaAPI.getEventRegistry().fireEvent(new KatanaPlayerUnregisterEvent(player));
	}

	public static boolean callAlert(Player player, CheckInfo chk, Check check, String debug, BaseComponent hover, int violations, int maxvl, long ping) {
		boolean sheesh = false;

		try {
			sheesh = KatanaAPI.getEventRegistry().fireEvent(new KatanaAlertEvent(player, toCheckData(check), new DebugData(debug, hover, ping, maxvl), violations));
		} catch (Exception var11) {
		}

		return sheesh;
	}

	public static boolean callBan(Player player, CheckInfo chk, Check check) {
		boolean sheesh = false;

		try {
			sheesh = KatanaAPI.getEventRegistry().fireEvent(new KatanaBanEvent(player, toCheckData(check)));
		} catch (Exception var5) {
		}

		return sheesh;
	}

	public static void callPostCheck(Player player, CheckInfo chk, Check check, Object packet) {
		try {
			KatanaAPI.getEventRegistry().fireEvent(new KatanaPostCheckEvent(check.isDidFail(), toCheckData(check), player, packet));
		} catch (Exception var5) {
		}
	}

	public static boolean callPreCheck(CheckInfo chk, Check check, Player player, Object packet) {
		boolean sheesh = false;

		try {
			sheesh = KatanaAPI.getEventRegistry().fireEvent(new KatanaPreCheckEvent(player, toCheckData(check), packet));
		} catch (Exception var6) {
		}

		return sheesh;
	}

	public static void callEvent(KatanaEvent event) {
		KatanaAPI.getEventRegistry().fireEvent(event);
	}

	public static boolean callPullback(Player player, CheckInfo chk, Check check, Location to) {
		return KatanaAPI.getEventRegistry().fireEvent(new KatanaPullbackEvent(player, toCheckData(chk), to));
	}

	public static boolean callCancellableEvent(KatanaEvent event) {
		return KatanaAPI.getEventRegistry().fireEvent(event);
	}

	public static CheckData toCheckData(Check check) {
		checkData.setName(check.getName());
		checkData.setDesc(check.getDesc());
		checkData.setCategory(Category.valueOf(check.getCategory().name()));
		checkData.setSubCategory(SubCategory.valueOf(check.getSubCategory().name()));
		checkData.setExperimental(check.isExperimental());
		checkData.setSilent(check.isSilent());
		checkData.setCredits(check.getCredits());
		return checkData;
	}

	public static CheckData toCheckData(CheckInfo info) {
		checkData.setName(info.name());
		checkData.setDesc(info.desc());
		checkData.setCategory(Category.valueOf(info.category().name()));
		checkData.setSubCategory(SubCategory.valueOf(info.subCategory().name()));
		checkData.setExperimental(info.experimental());
		checkData.setSilent(info.silent());
		checkData.setCredits(info.credits());
		return checkData;
	}
}
