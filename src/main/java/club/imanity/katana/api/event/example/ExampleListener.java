/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.example;

import club.imanity.katana.api.data.CheckData;
import club.imanity.katana.api.event.KatanaEvent;
import club.imanity.katana.api.event.KatanaListener;
import club.imanity.katana.api.event.impl.KatanaAlertEvent;
import org.bukkit.entity.Player;

public final class ExampleListener implements KatanaListener {
	@Override
	public void onEvent(KatanaEvent event) {
		if (event instanceof KatanaAlertEvent) {
			CheckData check = ((KatanaAlertEvent)event).getCheck();
			Player var3 = ((KatanaAlertEvent)event).getPlayer();
		}
	}
}
