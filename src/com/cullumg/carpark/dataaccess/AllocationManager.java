package com.cullumg.carpark.dataaccess;

import com.cullumg.carpark.data.Allocation;
import com.cullumg.carpark.dataaccess.ConnectionManager;
import com.cullumg.carpark.dataaccess.UserManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class AllocationManager {
	private static AllocationManager theAllocationManager = null;

	public static AllocationManager getInstance() {
		if (theAllocationManager == null) {
			theAllocationManager = new AllocationManager();
		}

		return theAllocationManager;
	}

	public List<Allocation> getAllocations(Date selectedDate) {
		Vector<Allocation> theAllocationList = null;

		try {
			Connection arg7 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg7.prepareStatement("SELECT * FROM space WHERE carparkid=1;");
			ResultSet rs1 = query1.executeQuery();

			while (rs1.next()) {
				PreparedStatement query2 = arg7.prepareStatement("SELECT * FROM allocation WHERE date=? and spaceid=?");
				query2.setDate(1, selectedDate);
				query2.setInt(2, rs1.getInt("spaceid"));
				ResultSet rs2 = query2.executeQuery();
				if (theAllocationList == null) {
					theAllocationList = new Vector<Allocation>();
				}

				if (rs2.next()) {
					theAllocationList.add(
							new Allocation(rs1.getInt("spaceid"), rs1.getString("spacename"), rs2.getString("userid"),
									UserManager.getInstance().getUserByEmailAddress(rs2.getString("userid"))
											.getUserName(),
									rs2.getDate("date"), rs1.getString("defaultownerid"), UserManager.getInstance()
											.getUserByEmailAddress(rs1.getString("defaultownerid")).getUserName()));
				} else {
					theAllocationList.add(new Allocation(rs1.getInt("spaceid"), rs1.getString("spacename"),
							(String) null, (String) null, (java.util.Date) null, rs1.getString("defaultownerid"),
							UserManager.getInstance().getUserByEmailAddress(rs1.getString("defaultownerid"))
									.getUserName()));
				}
			}
		} catch (SQLException arg71) {
			arg71.printStackTrace();
		}

		return theAllocationList;
	}

	public void claimSpace(int spaceid, String userid, Date selectedDate) {
		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5.prepareStatement(
					"INSERT INTO allocation (allocationid, date, spaceid, userid) SELECT IFNULL(max(allocationid),0)+1, ?, ?, ? FROM allocation;");
			query1.setDate(1, selectedDate);
			query1.setInt(2, spaceid);
			query1.setString(3, userid);
			query1.executeUpdate();
		} catch (SQLException arg51) {
			arg51.printStackTrace();
		}

	}

	public void relinquishSpace(int spaceid, String userid, Date selectedDate) {
		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query = arg5
					.prepareStatement("DELETE FROM allocation WHERE date=? AND spaceid = ? AND userid = ?");
			query.setDate(1, selectedDate);
			query.setInt(2, spaceid);
			query.setString(3, userid);
			query.executeUpdate();
		} catch (SQLException arg51) {
			arg51.printStackTrace();
		}

	}
}