/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.check;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInfo {
	String name();

	String desc() default "";

	Category category();

	SubCategory subCategory();

	boolean experimental();

	boolean subCheck() default false;

	boolean silent() default false;

	String credits() default "";
}
