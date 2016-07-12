/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.cullumg.carpark.dataaccess;

import com.cullumg.carpark.data.Request;
import com.cullumg.carpark.dataaccess.AllocationManager;
import com.cullumg.carpark.dataaccess.ConnectionManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class RequestManager {
	private static RequestManager theRequestManager = null;

	public static RequestManager getInstance() {
		if (theRequestManager == null) {
			theRequestManager = new RequestManager();
		}

		return theRequestManager;
	}

	public Vector<Request> getRequestsForOwner(String ownerUserId, Date selectedDate) {
		Vector<Request> theRequestList = null;

		try {
			Connection arg6 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg6.prepareStatement(
					"SELECT * FROM requests WHERE carparkid=1 AND owneruserid =? AND requestdate = ?;");
			query1.setString(1, ownerUserId);
			query1.setDate(2, selectedDate);

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); theRequestList.add(new Request(
					rs1.getInt("requestid"), rs1.getInt("carparkid"), rs1.getInt("spaceid"),
					rs1.getString("requestoruserid"), rs1.getDate("requestdate"), rs1.getString("owneruserid")))) {
				if (theRequestList == null) {
					theRequestList = new Vector<Request>();
				}
			}
		} catch (Exception arg61) {
			arg61.printStackTrace();
		}

		return theRequestList;
	}

	public Vector<Request> getRequestsForOwner(String ownerUserId) {
		Vector<Request> theRequestList = null;
		if (theRequestList == null) {
			theRequestList = new Vector<Request>();
		}

		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5
					.prepareStatement("SELECT * FROM requests WHERE carparkid=1 AND owneruserid =?;");
			query1.setString(1, ownerUserId);
			ResultSet rs1 = query1.executeQuery();

			while (rs1.next()) {
				theRequestList.add(new Request(rs1.getInt("requestid"), rs1.getInt("carparkid"), rs1.getInt("spaceid"),
						rs1.getString("requestoruserid"), rs1.getDate("requestdate"), rs1.getString("owneruserid")));
			}
		} catch (Exception arg51) {
			arg51.printStackTrace();
		}

		return theRequestList;
	}

	public HashMap<Integer, Request> getRequestsFromUser(String requestorId, Date selectedDate) {
		HashMap<Integer, Request> theRequestList = null;

		try {
			Connection arg6 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg6.prepareStatement(
					"SELECT * FROM requests WHERE carparkid=1 AND requestorUserId = ? AND requestdate = ?;");
			query1.setString(1, requestorId);
			query1.setDate(2, selectedDate);

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); theRequestList.put(
					Integer.valueOf(rs1.getInt("spaceid")),
					new Request(rs1.getInt("requestid"), rs1.getInt("carparkid"), rs1.getInt("spaceid"),
							rs1.getString("requestoruserid"), rs1.getDate("requestdate"),
							rs1.getString("owneruserid")))) {
				if (theRequestList == null) {
					theRequestList = new HashMap<Integer, Request>();
				}
			}
		} catch (Exception arg61) {
			arg61.printStackTrace();
		}

		return theRequestList;
	}

	public void makeRequest(int spaceId, String requestorUserId, String ownerUserId, Date selectedDate) {
		try {
			Connection arg6 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg6.prepareStatement(
					"INSERT INTO requests (requestid,carparkid,spaceid,requestoruserid,owneruserid,requestdate) SELECT IFNULL(max(requestid),0)+1, 1, ?, ?, ?, ? FROM requests;");
			query1.setInt(1, spaceId);
			query1.setString(2, requestorUserId);
			query1.setString(3, ownerUserId);
			query1.setDate(4, selectedDate);
			query1.executeUpdate();
		} catch (SQLException arg61) {
			arg61.printStackTrace();
		}

	}

	public Request getRequest(int requestid) {
		Request theRequest = null;

		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5.prepareStatement("SELECT * FROM requests WHERE requestid = ?;");
			query1.setInt(1, requestid);

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); theRequest = new Request(rs1.getInt("requestid"),
					rs1.getInt("carparkid"), rs1.getInt("spaceid"), rs1.getString("requestoruserid"),
					rs1.getDate("requestdate"), rs1.getString("owneruserid"))) {
				;
			}
		} catch (Exception arg51) {
			arg51.printStackTrace();
		}

		return theRequest;
	}

	public void acceptRequest(int requestid) {
		Request theRequest = this.getRequest(requestid);
		AllocationManager.getInstance().claimSpace(theRequest.getSpaceid(), theRequest.getRequestorUserId(),
				theRequest.getRequestedDate());
		this.deleteRequestsForThisSpace(theRequest.getSpaceid(), theRequest.getRequestedDate());
	}

	public void denyRequest(int requestid) {
		this.deleteRequest(requestid);
	}

	public long getLastUpdate() {
		long lastupdate = 0L;

		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5.prepareStatement("SELECT MAX(lastchange) FROM updates");

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); lastupdate = rs1.getTimestamp(1).getTime()) {
				;
			}
		} catch (SQLException arg51) {
			arg51.printStackTrace();
		}

		return lastupdate;
	}

	public void deleteRequestsForThisSpace(int spaceid, Date requestDate) {
		try {
			Connection arg4 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg4
					.prepareStatement("DELETE FROM requests WHERE requestdate = ? AND spaceid = ?;");
			query1.setDate(1, requestDate);
			query1.setInt(2, spaceid);
			query1.executeUpdate();
		} catch (SQLException arg41) {
			arg41.printStackTrace();
		}

	}

	public void deleteRequest(int requestid) {
		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3.prepareStatement("DELETE FROM requests WHERE requestid = ?");
			query1.setInt(1, requestid);
			query1.executeUpdate();
		} catch (SQLException arg31) {
			arg31.printStackTrace();
		}

	}
}