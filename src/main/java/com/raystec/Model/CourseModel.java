package com.raystec.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.Bean.CourseBean;
import com.raystec.Util.JDBCDataSource;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;



/**
 * JDBC Implementation of CourseModel
 * A Course model for performing course related business operation
 * @author Vipin Gupta
 */


public class CourseModel {
	private static Logger log = Logger.getLogger(CourseModel.class);
	
	public int nextPK() throws ApplicationException {
		log.debug("Model nextpk started");
		int pk = 0;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			ResultSet rs =ps.executeQuery();
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
		log.debug("model nextpk end");
		return pk+1;
	}
	public int add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add started");
		int pk = 0;
		Connection conn = null;
		CourseBean duplicateCourseName = findByName(bean.getCourse_Name());
		if(duplicateCourseName!=null){
			throw new DuplicateRecordException("Course Name already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getCourse_Name());
			ps.setString(3, bean.getDiscription());
			ps.setString(4, bean.getDuration());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDatetime());
			ps.setTimestamp(8, bean.getModifiedDatetime());
			
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback course");
			}
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");
		return pk;
	}
	
	public void delete(CourseBean bean) throws ApplicationException {
		log.debug("Model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback delete" + e2.getMessage());
			}
			throw new ApplicationException("Exception in delete record");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
	}
	
	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update started");
		Connection conn = null;
		CourseBean beanExist = findByName(bean.getCourse_Name());
		if(beanExist !=null && beanExist.getId()!=bean.getId()){
			throw new DuplicateRecordException("Course Already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_COURSE SET COURSE_NAME=?,DISCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getCourse_Name());
			ps.setString(2, bean.getDiscription());
			ps.setString(3, bean.getDuration());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDatetime());
			ps.setTimestamp(7, bean.getModifiedDatetime());
			ps.setLong(8, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
						
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback" + e2.getMessage());
			}
			throw new ApplicationException("Exception in update course");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update end");
	}
	
	public CourseBean findByName(String name) throws ApplicationException {
		log.debug("Model findbyname started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE COURSE_NAME=?");
		CourseBean bean = null;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setDiscription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in course model find by pk");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbyname end");
		return bean;
	}
	public CourseBean findByPk(long pk) throws ApplicationException {
		log.debug("model findbypk started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
		CourseBean bean = null;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setDiscription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in find by pk");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findbypk end");
		return bean;
	}
	
	public List<CourseBean> search(CourseBean bean) throws ApplicationException {
		return search(bean, 0, 0);

	}
	public List<CourseBean> search(CourseBean bean, int pageNo, int pageSize ) throws ApplicationException {
		log.debug("model search started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		if (bean!=null) {
			
			if (bean.getId()>0) {
				sql.append(" AND ID =" + bean.getId());
			}
			if (bean.getCourse_Name()!=null && bean.getCourse_Name().length()>0) {
				sql.append(" AND COURSE_NAME like'" + bean.getCourse_Name() + "%'");
			}
			if (bean.getDiscription()!= null && bean.getDiscription().length()>0) {
				sql.append(" AND DISCRIPTION like'" + bean.getDiscription() + "%'");
			}
			if (bean.getDuration()!=null && bean.getDuration().length()>0) {
				sql.append(" AND DURATION like'" + bean.getDuration() + "%'");
			}
		}
		if (pageSize>0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList<CourseBean> list = new ArrayList<CourseBean>();
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setDiscription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search courses");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model search end");
		return list;
	}
	
	
	public List list() throws ApplicationException {
		return list(0, 0);

	}
	public List list(int pageNo, int pageSize) throws ApplicationException{
		log.debug("model list started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE ");
		
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
				CourseBean bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setDiscription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting list");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}
}
