/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.vehicle;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.VehicleEvent;
import org.bukkit.entity.Boat;

@CheckInfo(
	name = "VehicleFly (A)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.FLY,
	experimental = true
)
public final class VehicleFly extends PacketCheck {
	private double lastX;
	private double lastY;
	private double lastZ;
	private boolean lastGround;
	private boolean lastGravity;
	private double violationsZero;
	private int ticks;

	public VehicleFly(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event event) {
		if (event instanceof VehicleEvent) {
			double deltaX = Math.abs(this.data.getVehicleX() - this.lastX);
			double deltaY = this.data.getVehicleY() - this.lastY;
			double deltaZ = Math.abs(this.data.getVehicleZ() - this.lastZ);
			if (this.data.getVehicle() != null) {
				boolean gravity = this.data.getVehicle().hasGravity();
				boolean ground = this.data.getVehicle().isOnGround();
				if (gravity && this.lastGravity) {
					if (++this.ticks > 3) {
						if (deltaY > 1.5) {
							this.fail("* Moving upwards with " + this.data.getVehicle().getType().getName(), 300L);
							this.setLast(ground);
							return;
						}

						if (deltaY > 0.01 && this.data.getVehicle() instanceof Boat && !ground && !this.lastGround) {
							this.fail("* Moving upwards with boat", 300L);
							this.setLast(ground);
							return;
						}

						if (deltaY > 0.5 && this.data.getVehicle() instanceof Boat) {
							this.fail("* Moving upwards with boat (2)", 300L);
							this.setLast(ground);
							return;
						}

						if (deltaY == 0.0 && (deltaX > 0.0 || deltaZ > 0.0) && this.data.getVehicle() instanceof Boat && !ground && !this.lastGround) {
							if (++this.violationsZero > 2.0) {
								this.fail("* Moving 0 vertical with boat", 300L);
								this.setLast(ground);
								return;
							}
						} else {
							this.violationsZero = Math.max(this.violationsZero - 0.1, 0.0);
						}
					}

					this.setLast(ground);
				}

				this.lastGravity = gravity;
			}
		}

		if (this.data.getVehicleId() == -1) {
			this.ticks = 0;
		}
	}

	private void setLast(boolean ground) {
		this.lastX = this.data.getVehicleX();
		this.lastY = this.data.getVehicleY();
		this.lastZ = this.data.getVehicleZ();
		this.lastGround = ground;
	}
}
