/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.badpackets;

import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction.Action;
import java.util.ArrayDeque;
import java.util.Deque;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.ActionEvent;
import club.imanity.katana.event.DigEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.util.MathUtil;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

@CheckInfo(
	name = "BadPackets (M)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = true,
	credits = "§c§lCREDITS: §aWizzard §7made this check."
)
public final class BadPacketsM extends PacketCheck {
	Deque<Integer> interactions = new ArrayDeque<>();
	int flying;
	boolean released;

	public BadPacketsM(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (this.data.getClientVersion().getProtocolVersion() <= 47) {
			if (packet instanceof FlyingEvent) {
				if (((FlyingEvent)packet).hasMoved() || ((FlyingEvent)packet).hasLooked()) {
					++this.flying;
					this.released = false;
				}
			} else if (packet instanceof ActionEvent) {
				if (((ActionEvent)packet).getAction() != Action.START_SPRINTING) {
					return;
				}

				if (this.released) {
					return;
				}

				if (this.interactions.add(this.flying) && this.interactions.size() >= 25) {
					double std = new StandardDeviation().evaluate(MathUtil.dequeTranslator(this.interactions));
					if (std < 0.3) {
						this.fail(String.format("* Wtap\nstd: %.2f", std), this.getBanVL(), 125L);
					}

					this.interactions.clear();
				}

				this.flying = 0;
			} else if (packet instanceof DigEvent && ((DigEvent)packet).getDigType() == DiggingAction.RELEASE_USE_ITEM) {
				this.released = true;
			}
		}
	}
}
