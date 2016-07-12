package com.cullumg.carpark.dataaccess;

import com.cullumg.carpark.data.User;
import com.cullumg.carpark.dataaccess.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class UserManager {
	private static UserManager theUserManager = null;
	private static HashMap<String, User> userList = null;

	public static UserManager getInstance() {
		if (theUserManager == null) {
			theUserManager = new UserManager();
		}

		return theUserManager;
	}

	public User getUserByEmailAddress(String emailAddress) {
		if (emailAddress != null && !emailAddress.equals("")) {
			if (userList == null) {
				userList = new HashMap<String, User>();
			}

			User theUser = null;
			theUser = (User) userList.get(emailAddress.toLowerCase());
			if (theUser == null) {
				try {
					Connection arg5 = ConnectionManager.getConnection();
					PreparedStatement query = arg5
							.prepareStatement("SELECT * FROM user WHERE LOWER(userid) = LOWER(?)");
					query.setString(1, emailAddress);
					ResultSet rs = query.executeQuery();

					while (rs.next()) {
						theUser = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getString(5), rs.getInt(6));
						userList.put(rs.getString("userid").toLowerCase(), theUser);
					}
				} catch (SQLException arg51) {
					arg51.printStackTrace();
				}
			}

			return theUser;
		} else {
			return new User((String) null, (String) null, (String) null, 0, (String) null, 0);
		}
	}

	public User getUserBySessionId(String sessionId) {
		if (sessionId != null && !sessionId.equals("")) {
			if (userList == null) {
				userList = new HashMap<String, User>();
			}

			User theUser = null;
			if (theUser == null) {
				try {
					Connection arg5 = ConnectionManager.getConnection();
					PreparedStatement query = arg5.prepareStatement("SELECT * FROM user WHERE persistentsessionid = ?");
					query.setString(1, sessionId);
					ResultSet rs = query.executeQuery();

					while (rs.next()) {
						theUser = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
								rs.getString(5), rs.getInt(6));
						userList.put(rs.getString("userid").toLowerCase(), theUser);
					}
				} catch (SQLException arg51) {
					arg51.printStackTrace();
				}
			}

			return theUser;
		} else {
			return new User((String) null, (String) null, (String) null, 0, (String) null, 0);
		}
	}

	public User login(String emailAddress, String password) {
		User theUser = this.getUserByEmailAddress(emailAddress);
		return theUser.getUserId() != null ? (theUser.getPassword().equals(password) ? theUser : null) : null;
	}

	public User login(String sessionId) {
		User theUser = this.getUserBySessionId(sessionId);
		return theUser.getUserId() != null ? theUser : null;
	}

	public void setPersistentSessionId(String emailAddress, String id) {
		try {
			Connection arg9 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg9
					.prepareStatement("UPDATE USER SET persistentsessionid = ? WHERE userid = ?;");
			query1.setString(2, emailAddress);
			query1.setString(1, id);
			query1.executeUpdate();
		} catch (Exception arg4) {
			arg4.printStackTrace();
		}

	}

	public List<User> getUserList() {
		Vector<User> theUserList = null;

		try {
			Connection arg5 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg5.prepareStatement("SELECT * FROM user;");
			ResultSet rs = query1.executeQuery();

			while (rs.next()) {
				if (theUserList == null) {
					theUserList = new Vector<User>();
				}

				User theUser = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getString(5), rs.getInt(6));
				theUserList.add(theUser);
				userList.put(theUser.getUserId(), theUser);
			}
		} catch (Exception arg51) {
			arg51.printStackTrace();
		}

		return theUserList;
	}

	public User registerUser(String emailAddress, String userName, String telephonenumber, int defaultcarparkid,
			String password, int isAdmin) {
		User theUser = this.getUserByEmailAddress(emailAddress);
		if (theUser == null) {
			theUser = new User(emailAddress, userName, telephonenumber, defaultcarparkid, password, isAdmin);
			userList.put(emailAddress, theUser);

			try {
				Connection arg9 = ConnectionManager.getConnection();
				PreparedStatement query1 = arg9.prepareStatement(
						"INSERT INTO user (userid, name, telephone, defaultcarparkid, password, isadmin) VALUES (?, ?, ?, ?, ?, ?);");
				query1.setString(1, theUser.getUserId());
				query1.setString(2, theUser.getUserName());
				query1.setString(3, theUser.getTelephone());
				query1.setInt(4, theUser.getDefaultcarparkid());
				query1.setString(5, theUser.getPassword());
				query1.setInt(6, theUser.isAdmin());
				query1.executeUpdate();
			} catch (Exception arg91) {
				arg91.printStackTrace();
			}
		}

		return theUser;
	}

	public User modifyUser(String emailAddress, String userName, String telephonenumber, int defaultcarparkid,
			String password, int isAdmin) {
		User theUser = new User(emailAddress, userName, telephonenumber, defaultcarparkid, password, isAdmin);
		userList.put(emailAddress, theUser);

		try {
			Connection arg9 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg9.prepareStatement(
					"UPDATE USER SET name = ?, telephone = ?, defaultcarparkid = ?, password = ?, isadmin = ? WHERE userid = ?;");
			query1.setString(6, theUser.getUserId());
			query1.setString(1, theUser.getUserName());
			query1.setString(2, theUser.getTelephone());
			query1.setInt(3, theUser.getDefaultcarparkid());
			query1.setString(4, theUser.getPassword());
			query1.setInt(5, theUser.isAdmin());
			query1.executeUpdate();
		} catch (Exception arg91) {
			arg91.printStackTrace();
		}

		return theUser;
	}

	public void deleteUser(String emailAddress) {
		userList.remove(emailAddress);

		try {
			Connection arg3 = ConnectionManager.getConnection();
			PreparedStatement query1 = arg3.prepareStatement("DELETE FROM USER WHERE userid = ?;");
			query1.setString(1, emailAddress);
			query1.executeUpdate();
		} catch (Exception arg31) {
			arg31.printStackTrace();
		}

	}
}