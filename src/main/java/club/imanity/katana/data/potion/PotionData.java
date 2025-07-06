/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.data.potion;

public class PotionData {
	private final PotionEffect potionEffect;
	private final int amplifier;

	public PotionData(PotionEffect potionEffect, int amplifier) {
		this.potionEffect = potionEffect;
		this.amplifier = amplifier;
	}

	public int getAmplifier() {
		return this.amplifier + 1;
	}

	public PotionEffect getPotionEffect() {
		return this.potionEffect;
	}
}
