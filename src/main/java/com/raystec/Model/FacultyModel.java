package com.raystec.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.Bean.FacultyBean;
import com.raystec.Util.JDBCDataSource;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;

/**
 * JDBC Implementation of FacultyModel
 * A Faculty model for performing faculty related business operation
 * @author Vipin Gupta
 */


public class FacultyModel {
	
	private static Logger log = Logger.getLogger(FacultyModel.class);

	public int nextPK() throws ApplicationException {
		log.debug("model nextpk method started");
		int pk = 0;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
				}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting pk");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model nextpk method end");
		return pk+1;
	}
	
	public int add(FacultyBean bean) throws ApplicationException,DuplicateRecordException {
		log.debug("model add method started");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getFirst_Name());
			ps.setString(3, bean.getLast_Name());
			ps.setString(4, bean.getGender());
			ps.setDate(5, new java.sql.Date(bean.getDOJ().getTime()));
			ps.setString(6, bean.getQualification());
			ps.setString(7, bean.getEmail_id());
			ps.setString(8, bean.getMobile_No());
			ps.setInt(9, bean.getCollege_id());
			ps.setString(10, bean.getCollege_Name());
			ps.setInt(11, bean.getCourse_id());
			ps.setString(12, bean.getCourse_Name());
			ps.setInt(13, bean.getSubject_id());
			ps.setString(14, bean.getSubject_Name());
			ps.setString(15, bean.getCreatedBy());
			ps.setString(16, bean.getModifiedBy());
			ps.setTimestamp(17, bean.getCreatedDatetime());
			ps.setTimestamp(18, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in add rollback" + e2.getMessage());
			}
			throw new ApplicationException("Exception in adding record");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add method end");
		return pk;
	}
	
	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("model delete method started");	
		
		Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
				ps.setLong(1, bean.getId());
				ps.executeUpdate();
				conn.commit();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (Exception e2) {
					throw new ApplicationException("Exception in delete rollback ");
				}
				throw new ApplicationException("Exception in delete record");
			}
			finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("model delete method end");
	}
	
	public void update(FacultyBean bean) throws ApplicationException {
		log.debug("model update method started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_FACULTY SET  FIRST_NAME=?, LAST_NAME=?, GENDER=?, DOJ=?,QUALIFICATION=?, EMAIL_ID=?, MOBILE_NO=? , COLLEGE_ID=?, COLLEGE_NAME=?,COURSE_ID=?,COURSE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?, CREATED_BY=? , MODIFIED_BY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=? WHERE ID= ?");
			ps.setString(1, bean.getFirst_Name());
			ps.setString(2, bean.getLast_Name());
			ps.setString(3, bean.getGender());
			ps.setDate(4,new java.sql.Date(bean.getDOJ().getTime()));
			ps.setString(5, bean.getQualification());
			ps.setString(6, bean.getEmail_id());
			ps.setString(7, bean.getMobile_No());
			ps.setInt(8, bean.getCollege_id());
			ps.setString(9, bean.getCollege_Name());
			ps.setInt(10, bean.getCourse_id());
			ps.setString(11, bean.getCourse_Name());
			ps.setInt(12, bean.getSubject_id());
			ps.setString(13, bean.getSubject_Name());
			ps.setString(14, bean.getCreatedBy());
			ps.setString(15, bean.getModifiedBy());
			ps.setTimestamp(16, bean.getCreatedDatetime());
			ps.setTimestamp(17, bean.getModifiedDatetime());
			ps.setLong(18, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in update rollback");
			}
			throw new ApplicationException("Exception to update faculty");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model update method end");
	}
	
	public FacultyBean findByEmail(String email) throws ApplicationException {
		log.debug("model findbyemail method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
		Connection conn =null;
		FacultyBean bean = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
			    bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting by email");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbyemail method end");
		return bean;
	}
	
	public FacultyBean findByPk(long id) throws ApplicationException {
		log.debug("model findbypk method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		FacultyBean bean =null;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean =new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
			    bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting faculty by id");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbypk method end");
		return bean;
	}
	
	public FacultyBean findByName(String name) throws ApplicationException {
		log.debug("Model findbyname method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE FIRST_NAME=?");
		FacultyBean bean = null;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
			    bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting faculty by name");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbyname method end");
		return bean;
	}
	
	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);

	}
	
	public List search(FacultyBean bean,int pageNo, int pageSize) throws ApplicationException {
		log.debug("model search method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
		
		if (bean!=null) {
			if (bean.getId()>0) {
				sql.append(" AND ID =" + bean.getId());
			}
			if (bean.getCourse_id()>0) {
				sql.append(" AND COURSE_ID =" + bean.getCourse_id());
			}
			if (bean.getFirst_Name()!=null && bean.getFirst_Name().length()>0) {
				sql.append(" AND FIRST_NAME like'" + bean.getFirst_Name() + "%'");
			}
			if (bean.getLast_Name()!=null && bean.getLast_Name().length()>0) {
				sql.append(" AND LAST_NAME like'" + bean.getLast_Name() + "%'");
			}
			if (bean.getEmail_id()!=null && bean.getEmail_id().length()>0) {
				sql.append(" AND EMAIL_ID like'" + bean.getEmail_id() + "%'");
			}
			if (bean.getGender()!=null && bean.getGender().length()>0) {
				sql.append(" AND GENDER like'" + bean.getGender() + "%'");
			}
			if (bean.getMobile_No()!=null && bean.getMobile_No().length()>0) {
				sql.append(" AND MOBILE_NO like'" + bean.getMobile_No() + "%'");
			}
			if (bean.getCollege_Name()!=null && bean.getCollege_Name().length()>0) {
				sql.append(" AND COLLEGE_NAME like'" + bean.getCollege_Name() + "%'");
			}
			if (bean.getSubject_id()>0) {
				sql.append(" AND SUBJECT_ID like'" + bean.getSubject_id());
			}
			if (bean.getSubject_Name()!=null && bean.getSubject_Name().length()>0) {
				sql.append(" AND SUBJECT_NAME like'" + bean.getSubject_Name() + "%'");
			}
		}
		if (pageSize>0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));				
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
				bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
                list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search faculty");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model search method end");
		return list;
	}
	
	public List list() throws ApplicationException {
		return list(0, 0);

	}
	
	public List list(int pageNo,int pageSize ) throws ApplicationException {
		log.debug("model list method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
		
		if (pageSize>0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		
		ArrayList list = new ArrayList();
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));				
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
				bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
                list.add(bean);
			}
		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting list");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list method end");
		return list;
	}
}
