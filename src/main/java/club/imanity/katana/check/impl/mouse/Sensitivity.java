/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.mouse;

import java.util.Deque;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.RotationCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.evictinglist.ConcurrentEvictingList;
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.mc.MathHelper;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "Sensitivity (A)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = false,
	silent = true
)
public final class Sensitivity extends RotationCheck {
	private final Deque<Float> pitchGcdList = new ConcurrentEvictingList<>(50);
	private final Deque<Float> pitchGcdList2 = new ConcurrentEvictingList<>(50);
	private float lastDeltaPitch;
	public float pitchMode;
	public double sensPercent;
	public float sensitivityY;

	public Sensitivity(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float deltaPitch = Math.abs(to.getPitch() - from.getPitch());
		if (!this.data.isPossiblyTeleporting() && deltaPitch < 4.0F) {
			float pitchGcd = MathUtil.getGcd(deltaPitch, this.lastDeltaPitch);
			if ((double)pitchGcd > 0.009 && (double)Math.abs(to.pitch) < 0.6 && (double)Math.abs(from.pitch) < 0.6) {
				this.pitchGcdList.add(pitchGcd);
				if (this.pitchGcdList.size() == 5) {
					this.pitchMode = MathUtil.getMode(this.pitchGcdList);
					float test1 = this.convertToMouseDelta(this.pitchMode);
					this.sensPercent = (double)MathHelper.floor_double((double)test1 * 200.0);
					this.data.setSensitivity((int)this.sensPercent);
					this.data.setSensitivityY(test1);
					if (this.pitchGcdList.size() == 5) {
						this.pitchGcdList.clear();
					}
				}
			}

			if ((double)pitchGcd > 0.009) {
				this.pitchGcdList2.add(pitchGcd);
				if (this.pitchGcdList2.size() > 40) {
					this.pitchMode = MathUtil.getMode(this.pitchGcdList2);
					float test1 = this.convertToMouseDelta(this.pitchMode);
					this.sensPercent = (double)MathHelper.floor_double((double)test1 * 200.0);
					this.data.setSensitivity((int)this.sensPercent);
					this.data.setSmallestRotationGCD(this.pitchMode);
					if (this.pitchGcdList2.size() == 50) {
						this.pitchGcdList2.clear();
					}
				}
			}

			if ((double)pitchGcd > 0.008) {
				this.data.setPitchGCD(Math.min(this.data.getPitchGCD(), deltaPitch));
			}
		}

		this.lastDeltaPitch = deltaPitch;
	}

	private float convertToMouseDelta(float value) {
		return ((float)Math.cbrt((double)(value / 0.15F / 8.0F)) - 0.2F) / 0.6F;
	}
}
