/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.database.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import club.imanity.katana.katana;
import club.imanity.katana.database.Query;

public class SQLite {
	public static Connection conn;

	public static void init() {
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:" + katana.getInstance().getPlug().getDataFolder().getAbsolutePath() + File.separator + "database.sqlite";
			conn = DriverManager.getConnection(url);
			Query.use(conn);
			katana.getInstance().printCool("&b| &fConnection to &bSQLite&f has been established.");
		} catch (Exception var11) {
			katana.getInstance().printCool("&b| &cConnection to SQLite has failed.");
			var11.printStackTrace();
		}
	}

	public static void use() {
		try {
			if (conn.isClosed()) {
				String url = "jdbc:sqlite:" + katana.getInstance().getPlug().getDataFolder().getAbsolutePath() + File.separator + "database.sqlite";
				conn = DriverManager.getConnection(url);
				Query.use(conn);
			}
		} catch (Exception var11) {
			var11.printStackTrace();
		}
	}
}
