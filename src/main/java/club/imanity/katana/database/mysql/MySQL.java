/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import club.imanity.katana.katana;
import club.imanity.katana.database.Query;
import org.bukkit.configuration.file.FileConfiguration;

public class MySQL {
	private static Connection conn;

	public static void init() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				FileConfiguration config = katana.getInstance().getConfigManager().getConfig();
				conn = DriverManager.getConnection(
					"jdbc:mysql://" + config.getString("mysql.address") + ":" + config.getString("mysql.port") + "/?useSSL=false",
					config.getString("mysql.user"),
					config.getString("mysql.password")
				);
				conn.setAutoCommit(true);
				Query.use(conn);
				Query.prepare("CREATE DATABASE IF NOT EXISTS `" + config.getString("mysql.database") + "`").execute();
				Query.prepare("USE `" + config.getString("mysql.database") + "`").execute();
				katana.getInstance().printCool("&b| &aConnection to SQLite has been established.");
			}
		} catch (Exception var11) {
			katana.getInstance().printCool("&b| &cConnection to SQLite has failed.");
			var11.printStackTrace();
		}
	}

	public static void use() {
		try {
			init();
		} catch (Exception var1) {
			var1.printStackTrace();
		}
	}
}
