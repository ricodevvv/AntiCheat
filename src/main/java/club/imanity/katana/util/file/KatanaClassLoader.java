/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util.file;

import java.net.URL;
import java.net.URLClassLoader;

public class KatanaClassLoader extends URLClassLoader {
	private final URLClassPath ucp = new URLClassPath(this);

	public KatanaClassLoader(URL url, ClassLoader classLoader) {
		super(new URL[]{url}, classLoader);
	}

	@Override
	public void addURL(URL url) {
		this.ucp.addURL(url);
	}

	static {
		ClassLoader.registerAsParallelCapable();
	}
}
