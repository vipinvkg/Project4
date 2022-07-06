package com.raystec.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Example;

import com.raystec.Bean.CourseBean;
import com.raystec.Bean.SubjectBean;
import com.raystec.Bean.TimetableBean;
import com.raystec.Util.JDBCDataSource;
import com.raystec.exception.ApplicationException;
import com.raystec.exception.DuplicateRecordException;


/**
 * A Timetable model for performing timetable related business operation
 * @author Vipin Gupta
 *
 */
public class TimeTableModel {

	private static Logger log = Logger.getLogger(TimeTableModel.class);
	
	
	public int nextPK() throws ApplicationException {
		log.debug("Model nextpk method started");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
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

	public int add(TimetableBean bean) throws ApplicationException,DuplicateRecordException {
		log.debug("Add method started");
		int pk = 0;
		Connection conn = null;
		CourseModel cmodel = new CourseModel();
		CourseBean cbean = cmodel.findByPk(bean.getCourse_id());
		bean.setCourse_Name(cbean.getCourse_Name());
		
		SubjectModel smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPk(bean.getSubject_Id());
		bean.setSubject_Name(sbean.getSubject_Name());
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getCourse_Name());
			ps.setInt(3, bean.getCourse_id());
			ps.setString(4, bean.getSubject_Name());
			ps.setInt(5, bean.getSubject_Id());
			ps.setDate(6, new java.sql.Date(bean.getExam_Date().getTime()));
			ps.setString(7, bean.getExam_Time());
			ps.setString(8, bean.getSemester());
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11, bean.getCreatedDatetime());
			ps.setTimestamp(12, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback timetable" + e2.getMessage());
			}
			throw new ApplicationException("Exception in add timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("add method end");
		return pk;
	}

	
	public void delete(TimetableBean bean) throws ApplicationException {
		log.debug("Delete method started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in delete rollback of timetable");
			}
			throw new ApplicationException("Exception in delete Timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("delete method end");
	}
	
	
	public void update(TimetableBean bean) throws ApplicationException {
		log.debug("Update method started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_TIMETABLE SET COURSE_NAME=?,COURSE_ID=?,SUBJECT_NAME=?,SUBJECT_ID=?,EXAM_DATE=?,EXAM_TIME=?,SEMESTER=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getCourse_Name());
			ps.setInt(2, bean.getCourse_id());
			ps.setString(3, bean.getSubject_Name());
			ps.setInt(4, bean.getSubject_Id());
			ps.setDate(5, new java.sql.Date(bean.getExam_Date().getTime()));
			ps.setString(6, bean.getExam_Time());
			ps.setString(7, bean.getSemester());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.setLong(12, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in Timetable rollback");
			}
			throw new ApplicationException("Exception in update timetable");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Update method end");
	}
	
	
	public TimetableBean findByName(String name) throws ApplicationException {
		log.debug("findbyname method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE SUBJECT_NAME=?");
		Connection conn = null;
		TimetableBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			bean = new TimetableBean();
			bean.setId(rs.getLong(1));
			bean.setCourse_Name(rs.getString(2));
			bean.setCourse_id(rs.getInt(3));
			bean.setSubject_Name(rs.getString(4));
			bean.setSubject_Id(rs.getInt(5));
			bean.setExam_Date(rs.getDate(6));
			bean.setExam_Time(rs.getString(7));
			bean.setSemester(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifiedDatetime(rs.getTimestamp(12));
			
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in find by pk timetable");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findbyname method end");
		return bean;
		}
	
	
	public TimetableBean findByPk(long pk) throws ApplicationException {
		log.debug("findbypk method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimetableBean bean = null;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in find by pk timetable");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findbypk method end");
		return bean;
	}
	
	public List search(TimetableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}
	
	
	public List search(TimetableBean bean, int pageSize, int pageNo) throws ApplicationException {
		log.debug("Search method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");
		
		if (bean!=null) {
			
			if (bean.getId()>0) {
				sql.append(" AND ID =" + bean.getId());
			}
			if (bean.getCourse_id()>0) {
				sql.append(" AND COURSE_ID=" + bean.getCourse_id());
			}
			if (bean.getSubject_Id()>0) {
				sql.append(" AND SUBJECT_ID=" + bean.getSubject_Id());
			}
			if (bean.getExam_Date()!= null && bean.getExam_Date().getTime()>0) {
				Date d = new Date(bean.getExam_Date().getTime());
				sql.append(" AND EXAM_DATE ='" + d + "'");
			}
			if (bean.getCourse_Name()!=null && bean.getCourse_Name().length()>0) {
				sql.append(" AND COURSE_NAME like'" + bean.getCourse_Name() + "%'");
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
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in searching timetable");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Search method end");
		return list;
	}
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}
	
	
	public List list(int pageSize, int pageNo) throws ApplicationException {
		log.debug("List method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE");
		
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
			TimetableBean bean = new TimetableBean();
			bean.setId(rs.getLong(1));
			bean.setCourse_Name(rs.getString(2));
			bean.setCourse_id(rs.getInt(3));
			bean.setSubject_Name(rs.getString(4));
			bean.setSubject_Id(rs.getInt(5));
			bean.setExam_Date(rs.getDate(6));
			bean.setExam_Time(rs.getString(7));
			bean.setSemester(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifiedDatetime(rs.getTimestamp(12));
			list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in list of timetable");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("List method end");
		return list;
	}
	
	
	public TimetableBean checkByCss(long CourseId, long SubjectId, long Sem) throws ApplicationException {
		log.debug("checkbycss method started");
		Connection conn = null;
		TimetableBean bean = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Subject_ID=? AND Semester=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setLong(3, Sem);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in check by css timetable");
		}
		log.debug("checkbycss method end");
		return bean;
	}
	
	
	public TimetableBean checkByCds(int CourseId, int sem , Date ExamDate) throws ApplicationException {
		log.debug("checkbycds method started");
		TimetableBean bean = null;
		Connection conn = null;
		Date ExDate = new Date(ExamDate.getTime());
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Semester=? AND Exam_Date=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setInt(2, sem);
			ps.setDate(3, (java.sql.Date) ExamDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in check by Cds in timetable");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("checkbycds method end");
		return bean;
	}
	
	
	public static TimetableBean checkBysemester(long CourseId, long SubjectId, String semester, Date ExamDate) throws ApplicationException {
		log.debug("checkbysemester method started");
		Connection conn = null;
		TimetableBean bean = null;
		Date ExDate = new Date(ExamDate.getTime());
		
		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND\" + \" SEMESTER=? AND EXAM_DATE=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, (java.sql.Date) ExDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in check by semester method of timetable");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("checkbysemester method end");
		return bean;
	}
	
	public static TimetableBean checkByCourseName(int CourseId, Date ExamDate) throws ApplicationException {
		log.debug("checkycoursename method started");
		TimetableBean bean = null;
		Connection conn = null;
		Date ExDate = new Date(ExamDate.getTime());
		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? \" + \"AND EXAM_DATE=?");
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setDate(2, (java.sql.Date) ExDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in check by course in timetable");
			
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("checkbycoursename method end");
		return bean;
	}
}
