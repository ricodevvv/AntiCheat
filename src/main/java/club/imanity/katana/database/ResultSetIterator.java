/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.database;

import java.sql.ResultSet;

public interface ResultSetIterator {
	void next(ResultSet resultSet) throws Exception;
}
