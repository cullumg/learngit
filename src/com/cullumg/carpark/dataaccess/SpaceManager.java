package com.cullumg.carpark.dataaccess;

import com.cullumg.carpark.data.Space;
import com.cullumg.carpark.dataaccess.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

public class SpaceManager {
	private static SpaceManager thisManager = null;

	public static SpaceManager getInstance() {
		if (thisManager == null) {
			thisManager = new SpaceManager();
		}

		return thisManager;
	}

	public void deleteSpace(int spaceid) {
		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3.prepareStatement("DELETE FROM space WHERE spaceid = ?");
			query1.setInt(1, spaceid);
			query1.executeUpdate();
		} catch (Exception arg31) {
			arg31.printStackTrace();
		}

	}

	public void modifySpace(Space theSpace) {
		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3.prepareStatement(
					"UPDATE space SET defaultownerid = ?, spacename = ?, spacedescription = ?, carparkid = ? WHERE spaceid = ?");
			query1.setString(1, theSpace.getDefaultownerid());
			query1.setString(2, theSpace.getSpaceName());
			query1.setString(3, theSpace.getDescription());
			query1.setInt(4, theSpace.getCarparkid());
			query1.setInt(5, theSpace.getSpaceId());
			query1.executeUpdate();
		} catch (Exception arg31) {
			arg31.printStackTrace();
		}

	}

	public void createSpace(Space theSpace) {
		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3.prepareStatement(
					"INSERT INTO space (spaceid, carparkid, defaultownerid, spacename, spacedescription) SELECT IFNULL(MAX(spaceid),0)+1, ?, ?, ?, ?  FROM space;");
			query1.setInt(1, theSpace.getCarparkid());
			query1.setString(2, theSpace.getDefaultownerid());
			query1.setString(3, theSpace.getSpaceName());
			query1.setString(4, theSpace.getDescription());
			query1.executeUpdate();
		} catch (Exception arg31) {
			arg31.printStackTrace();
		}

	}

	public List<Space> listSpacesInCarPark(int carparkid) {
		Vector<Space> theSpaces = null;

		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5.prepareStatement("SELECT * FROM space WHERE carparkid = ?;");
			query1.setInt(1, carparkid);

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); theSpaces
					.add(new Space(rs1.getInt("spaceid"), rs1.getString("spacename"), rs1.getString("spacedescription"),
							rs1.getInt("carparkid"), rs1.getString("defaultownerid")))) {
				if (theSpaces == null) {
					theSpaces = new Vector<Space>();
				}
			}
		} catch (Exception arg51) {
			arg51.printStackTrace();
		}

		return theSpaces;
	}

	public Space getSpaceById(int spaceid) {
		Space theSpace = null;

		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5.prepareStatement("SELECT * FROM space WHERE spaceid = ?;");
			query1.setInt(1, spaceid);

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); theSpace = new Space(rs1.getInt("spaceid"),
					rs1.getString("spacename"), rs1.getString("spacedescription"), rs1.getInt("carparkid"),
					rs1.getString("defaultownerid"))) {
				;
			}

			if (theSpace.getDefaultownerid() == null) {
				theSpace.setDefaultownerid("");
			}
		} catch (Exception arg51) {
			arg51.printStackTrace();
		}

		return theSpace;
	}
}