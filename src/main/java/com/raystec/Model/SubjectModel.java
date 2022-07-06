package com.raystec.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.raystec.Bean.CourseBean;
import com.raystec.Bean.SubjectBean;
import com.raystec.Util.JDBCDataSource;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;


/**
 *  A Subject model for performing Subject related business operation
 * @author Vipin gupta
 *
 */
public class SubjectModel {

	private static Logger log = Logger.getLogger(SubjectModel.class);
	
	
	public int nextPK() throws ApplicationException {
		log.debug("nextpk method started");
		int pk = 0;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting pk");
		}
		log.debug("nextpk method end");
		return pk+1;
	}
	
	
public int add(SubjectBean bean) throws ApplicationException,DuplicateRecordException {
		log.debug("Add method started");
		int pk = 0;
		Connection conn = null;
		CourseModel cmodel = new CourseModel();
		CourseBean cbean = cmodel.findByPk(bean.getCourse_id());
		bean.setCourse_Name(cbean.getCourse_Name());
		try {
			conn =JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getSubject_Name());
			ps.setString(3, bean.getCourse_Name());
			ps.setInt(4, bean.getCourse_id());
			ps.setString(5, bean.getDiscription());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDatetime());
			ps.setTimestamp(9, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback subject");
			}
			throw new ApplicationException("Exception in adding Subject");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Add method end");
		return pk;
}


	public void delete(SubjectBean bean) throws ApplicationException {
		log.debug("Delete method started");
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback delete subject");
			}
			throw new ApplicationException("Exception in delete Subject");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Delete method end");
	}
	
	
	public void update(SubjectBean bean) throws ApplicationException {
		log.debug("Update method started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_SUBJECT SET Subject_Name=?,Course_NAME=?,Course_ID=?,Discription=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getSubject_Name());
			ps.setString(2, bean.getCourse_Name());
			ps.setInt(3, bean.getCourse_id());
			ps.setString(4, bean.getDiscription());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDatetime());
			ps.setTimestamp(8, bean.getModifiedDatetime());
			ps.setLong(9, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback of subject" + e2.getMessage());
			}
			throw new ApplicationException("Exception in update Subject");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Update method end");
	}
	
	
	public SubjectBean findByName(String name) throws ApplicationException {
		log.debug("findbyname method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE SUBJECT_NAME=?");
		Connection conn = null;
		SubjectBean bean = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubject_Name(rs.getString(2));
				bean.setCourse_Name(rs.getString(3));
				bean.setCourse_id(rs.getInt(4));
				bean.setDiscription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in subject find by name");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findbyname method end");
		return bean;		
	}
	
	public SubjectBean findByPk(long id) throws ApplicationException {
		log.debug("Findbypk method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, id);
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubject_Name(rs.getString(2));
				bean.setCourse_Name(rs.getString(3));
				bean.setCourse_id(rs.getInt(4));
				bean.setDiscription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in subject find by name");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Findbypk method end");
		return bean;	
	}
	
	public List search(SubjectBean bean) throws ApplicationException {
		return search(bean, 0, 0);

	}
	
	public List search(SubjectBean bean, int pageNo,int pageSize) throws ApplicationException {
		log.debug("Search method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");
		
		if (bean!=null) {
			if (bean.getId()>0) {
				sql.append(" AND ID =" + bean.getId());
			}
			if (bean.getCourse_id()>0) {
				sql.append(" AND COURSE_ID =" + bean.getCourse_id());
			}
			if (bean.getSubject_Name()!= null && bean.getSubject_Name().length()>0) {
				sql.append(" AND SUBJECT_NAME like'" + bean.getSubject_Name() + "%'");
			}
			if (bean.getCourse_Name()!=null && bean.getCourse_Name().length()>0) {
				sql.append(" AND COURSE_NAME like'" + bean.getCourse_Name() + "%'");
			}
			if (bean.getDiscription()!=null && bean.getDiscription().length()>0) {
				sql.append(" AND DISCRIPTION like'" + bean.getDiscription() + "%'");
			}
		}
		if (pageSize>0) {
			pageNo = (pageNo -1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		ArrayList list = new ArrayList();
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getInt(1));
				bean.setSubject_Name(rs.getString(2));
				bean.setCourse_Name(rs.getString(3));
				bean.setCourse_id(rs.getInt(4));
				bean.setDiscription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search of subject");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search method end");
		return list;
	}
	public List list() throws ApplicationException {
		return list(0, 0);
	}
	
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("List method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT");
		
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
				SubjectBean bean = new SubjectBean();
				bean.setId(rs.getInt(1));
				bean.setSubject_Name(rs.getString(2));
				bean.setCourse_Name(rs.getString(3));
				bean.setCourse_id(rs.getInt(4));
				bean.setDiscription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting list of subject");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("list method end");
		return list;
	}
}
