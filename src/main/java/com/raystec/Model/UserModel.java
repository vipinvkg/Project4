package com.raystec.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.Bean.UserBean;
import com.raystec.Util.EmailBuilder;
import com.raystec.Util.EmailMessage;
import com.raystec.Util.EmailUtility;
import com.raystec.Util.JDBCDataSource;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;
import com.raystec.exception.RecordNotFoundException;


/**
 * A user model for performing user related business operation
 * @author Vipin Gupta
 *
 */
public class UserModel {

	private static Logger log = Logger.getLogger(UserModel.class);

	/*
	 * private long Roleid;
	 * 
	 * public long getRoleid() { return Roleid; }
	 * 
	 * public void setRoleid(long roleid) { this.Roleid = roleid; }
	 * 
	 */	public int nextPK() throws ApplicationException {
		log.debug("nextpk method started");
		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("nextpk method end");
		return pk + 1;
	}

	public int add(UserBean bean) throws ApplicationException,DuplicateRecordException {
		log.debug("Add method started");
		int pk = 0;
		Connection conn = null;
		UserBean existbean = findByLogin(bean.getLogin());
		  
		  if (existbean != null) { throw new
		  DuplicateRecordException("Login Id already exists"); }
		 
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_USER VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getFirstName());
			ps.setString(3, bean.getLastName());
			ps.setString(4, bean.getLogin());
			ps.setString(5, bean.getPassword());
			ps.setString(6, bean.getConfirmPassword());
			ps.setDate(7, new java.sql.Date(bean.getDob().getTime()));
			ps.setString(8, bean.getMobileNo());
			ps.setLong(9, bean.getRoleId());
			ps.setString(10, bean.getGender());
			ps.setString(11, bean.getCreatedBy());
			ps.setString(12, bean.getModifiedBy());
			ps.setTimestamp(13, bean.getCreatedDatetime());
			ps.setTimestamp(14, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in add rollback" + e2.getMessage());
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
		log.debug("Add method end");
		return pk;
	}

	public void delete(UserBean bean) throws ApplicationException {
		log.debug("delete method started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: delete rollback " + e2.getMessage());
			}
			throw new ApplicationException("Exception in delete user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("delete method end");
	}

	public UserBean findByLogin(String login) throws ApplicationException {
		log.debug("findbylogin method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		Connection conn = null;
		UserBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findbylogin method started");
		return bean;
	}

	public UserBean findByPk(long pk) throws ApplicationException {
		log.debug("findbypk method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		Connection conn = null;
		UserBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting user by id");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findbypk method end");
		return bean;
	}

	
	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Update method started");
		Connection conn = null;
		UserBean beanExist = findByLogin(bean.getLogin());
		
		if (beanExist != null && !(beanExist.getId()==bean.getId())) {
			throw new DuplicateRecordException("Login id already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,CONFIRM_PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,GENDER=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getFirstName());
			ps.setString(2, bean.getLastName());
			ps.setString(3, bean.getLogin());
			ps.setString(4, bean.getPassword());
			ps.setString(5, bean.getConfirmPassword());
			ps.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			ps.setString(7, bean.getMobileNo());
			ps.setLong(8, bean.getRoleId());
			ps.setString(9, bean.getGender());
			ps.setString(10, bean.getCreatedBy());
			ps.setString(11, bean.getModifiedBy());
			ps.setTimestamp(12, bean.getCreatedDatetime());
			ps.setTimestamp(13, bean.getModifiedDatetime());
			ps.setLong(14, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: delete rollback exception");
			}
			throw new ApplicationException("Exception in getting user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Update method end");
	}

	public List search(UserBean bean) throws ApplicationException {
		return search(bean, 0, 0);

	}

	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("search method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like'" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like'" + bean.getLastName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like'" + bean.getLogin() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like'" + bean.getPassword() + "%'");
			}
			if (bean.getConfirmPassword() != null && bean.getConfirmPassword().length() > 0) {
				sql.append(" AND CONFIRM PASSWORD like'" + bean.getConfirmPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB =" + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO =" + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + bean.getRoleId());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like'" + bean.getGender() + "%'");
			}
		}
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search method end");
		return list;
	}

	public List list() throws ApplicationException {
		return list(0, 0);

	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("list method started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("list method end");
		return list;
	}

	public UserBean authenticate(String login, String password) throws ApplicationException {
		log.debug("authenticate method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in get details");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("authenticate method end");
		return bean;
	}

	public List getRoles(UserBean bean) throws ApplicationException {
		log.debug("getroles method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ROLE_Id=?");
		Connection conn = null;
		List list = new ArrayList();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, bean.getRoleId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting by Role_id");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("getlist method started");
		return list;
	}

	public boolean changePassword(long id, String oldPassword, String newPassword)
			throws ApplicationException, RecordNotFoundException {
		log.debug("changepassword method started");
		boolean flag = false;
		UserBean beanExist = null;
		beanExist = findByPk(id);
		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (DuplicateRecordException e) {

				throw new ApplicationException("Login id is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject("RAYS ORS Password has been changed successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		log.debug("changepasword method started");
		return flag;
	}
	public UserBean updateAccess(UserBean bean) throws ApplicationException{
		return null;

	}
	public int registerUser(UserBean bean) throws ApplicationException,DuplicateRecordException {
		int pk = add(bean);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		
		String message = EmailBuilder.getChangePasswordMessage(map);
		
		EmailMessage msg = new EmailMessage();
		
		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORS Project");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(msg);
		
		return pk;

	}
	public boolean resetPassword(UserBean bean) throws ApplicationException{
		
		String newPassword = String.valueOf(new Date().getTime()).substring(0,4);
		UserBean userData = findByPk((int) bean.getId());
		userData.setPassword(newPassword);
		
		try {
			update(userData);
		} catch (Exception e) {
			return false;
			
			
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());
		
		String message = EmailBuilder.getChangePasswordMessage(map);
		
		EmailMessage msg = new EmailMessage();
		
		msg.setTo(bean.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(msg);
		
		return true;
	}
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserBean userData = findByLogin(login);
		boolean flag = false;
		
		if (userData == null) {
			throw new RecordNotFoundException("Email id does not exist");
			
		}
		
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("login", userData.getLogin());
			map.put("password", userData.getPassword());
			map.put("firstName", userData.getFirstName());
			map.put("lastName", userData.getLastName());
			
			String message = EmailBuilder.getChangePasswordMessage(map);
			
			EmailMessage msg = new EmailMessage();
			msg.setTo(login);
			msg.setSubject("Rays ORS Password reset");
			msg.setMessage(message);
			msg.setMessageType(EmailMessage.HTML_MSG);
			
			boolean check = EmailUtility.sendMail(msg);
			flag = check;
			
			return flag;
	}
}
