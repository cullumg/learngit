package com.cullumg.carpark.dataaccess;

import com.cullumg.carpark.data.CarPark;
import com.cullumg.carpark.dataaccess.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

public class CarParkManager {
	private static CarParkManager thisManager = null;

	public static CarParkManager getInstance() {
		if (thisManager == null) {
			thisManager = new CarParkManager();
		}

		return thisManager;
	}

	public List<CarPark> getCarParks() {
		Vector<CarPark> theCarParkList = null;

		try {
			Connection arg4 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg4.prepareStatement("SELECT * FROM carpark;");

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); theCarParkList.add(new CarPark(
					rs1.getInt("carparkid"), rs1.getString("carparkname"), rs1.getString("carparkaddress")))) {
				if (theCarParkList == null) {
					theCarParkList = new Vector<CarPark>();
				}
			}
		} catch (Exception arg41) {
			arg41.printStackTrace();
		}

		return theCarParkList;
	}

	public CarPark getCarParkById(int carparkid) {
		CarPark theCarPark = null;

		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5.prepareStatement("SELECT * FROM carpark WHERE carparkid = ?;");
			query1.setInt(1, carparkid);

			for (ResultSet rs1 = query1.executeQuery(); rs1.next(); theCarPark = new CarPark(rs1.getInt("carparkid"),
					rs1.getString("carparkname"), rs1.getString("carparkaddress"))) {
				;
			}
		} catch (Exception arg51) {
			arg51.printStackTrace();
		}

		return theCarPark;
	}

	public void createCarPark(CarPark theCarPark) {
		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3.prepareStatement(
					"INSERT INTO carpark (carparkid, carparkname, carparkaddress) SELECT IFNULL(MAX(carparkid),0)+1, ?, ?  FROM carpark;");
			query1.setString(1, theCarPark.getCarparkname());
			query1.setString(2, theCarPark.getCarparkaddress());
			query1.executeUpdate();
		} catch (Exception arg31) {
			arg31.printStackTrace();
		}

	}

	public void modifyCarPark(CarPark theCarPark) {
		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3
					.prepareStatement("UPDATE carpark SET carparkname = ?, carparkaddress = ? WHERE carparkid = ?;");
			query1.setString(1, theCarPark.getCarparkname());
			query1.setString(2, theCarPark.getCarparkaddress());
			query1.setInt(3, theCarPark.getCarparkid());
			query1.executeUpdate();
		} catch (Exception arg31) {
			arg31.printStackTrace();
		}

	}

	public void deleteCarPark(int carparkid) {
		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3.prepareStatement("DELETE FROM carpark WHERE carparkid = ?;");
			query1.setInt(1, carparkid);
			query1.executeUpdate();
		} catch (Exception arg31) {
			arg31.printStackTrace();
		}

	}
}