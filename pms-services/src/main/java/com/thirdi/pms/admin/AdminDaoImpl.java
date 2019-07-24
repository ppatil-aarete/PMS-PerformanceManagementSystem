package com.thirdi.pms.admin;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.thirdi.pms.login.dao.LoginDao;

@Repository
public class AdminDaoImpl implements AdminDao{

	@Autowired
	JdbcTemplate template;
	
	@Autowired
	LoginDao loginDao;
	
	public Map<Integer, String> getFunctionOfEmployeeWithIdMap() {
		String sqlQuery = "select DISTINCT tx_appr_empl.ApprEmpId,lu_appr_function.appr_function_name,tx_ohrm_user.employee_id from "
				+ "lu_appr_function left join tx_ohrm_user on tx_ohrm_user.appr_function_id=lu_appr_function.appr_function_id \r\n" + 
				"  left join  tx_appr_empl on tx_appr_empl.hs_hr_employee_id = tx_ohrm_user.employee_id\r\n" + 
				"  where tx_ohrm_user.employee_id is not null";
		return template.query(sqlQuery, new ResultSetExtractor<Map<Integer, String>>(){
		    public Map<Integer,String> extractData(ResultSet rs) throws SQLException,DataAccessException {
		        Map<Integer,String> mapRet= new HashMap<Integer,String>();
		        while(rs.next()){
		            mapRet.put(rs.getInt("ApprEmpId"),rs.getString("appr_function_name"));
		        }
		        return mapRet;
		    }
		});
	}
	
	
	/*private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}*/
	public Boolean creatNewCycle(Map<String, String> cycleMap) {
		String updateSQL = "Insert into lu_appr_cycle(ApprCycleName,StartDate,EndDate,SelfApprStartDate,SelfApprEndDate,"
				+ "MgrApprStartDate,MgrApprEndDate,RevApprStartDate,RevApprEndDate,isFinalized,Create_date)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		
		final Map<String, String> cycleDataMap = reformatCycleMapDatesAccordingSqlSupport(cycleMap);
		return template.execute(updateSQL,new PreparedStatementCallback<Boolean>(){  
			     public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {  
					   ps.setString(1,cycleDataMap.get("title").toString()); 
					   ps.setString(2,cycleDataMap.get("cycle_sd").toString());
					   ps.setString(3,cycleDataMap.get("cycle_ed").toString());  
					   ps.setString(4,cycleDataMap.get("self_sd").toString());  
					   ps.setString(5,cycleDataMap.get("self_ed").toString());  
					   ps.setString(6,cycleDataMap.get("appr_sd").toString());  
					   ps.setString(7,cycleDataMap.get("appr_ed").toString());  
					   ps.setString(8,cycleDataMap.get("rev_sd").toString());  
					   ps.setString(9,cycleDataMap.get("rev_ed").toString());  
					   ps.setInt(10,CycleStatus.isRunning.ordinal());  
					   ps.setTimestamp(11,new Timestamp(System.currentTimeMillis()));
					   return ps.execute();  
			     } 
		 });
			
		
	}
	
	public void fillData()
	{
		 SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template.getDataSource()).withProcedureName("isp_next_cycle_employee");
	     simpleJdbcCall.execute();
		
	}

	private Map<String, String> reformatCycleMapDatesAccordingSqlSupport(Map<String, String> cycleDataMap) {
		Map<String,String> reformattedMap = new HashMap<String, String>();
		reformattedMap.put("title", cycleDataMap.get("title"));
		reformattedMap.put("cycle_sd", convertStringDateFormat(cycleDataMap.get("cycle_sd")));
		reformattedMap.put("cycle_ed", convertStringDateFormat(cycleDataMap.get("cycle_ed")));
		reformattedMap.put("self_sd", convertStringDateFormat(cycleDataMap.get("self_sd")));
		reformattedMap.put("self_ed", convertStringDateFormat(cycleDataMap.get("self_ed")));
		reformattedMap.put("appr_sd", convertStringDateFormat(cycleDataMap.get("appr_sd")));
		reformattedMap.put("appr_ed", convertStringDateFormat(cycleDataMap.get("appr_ed")));
		reformattedMap.put("rev_sd", convertStringDateFormat(cycleDataMap.get("rev_sd")));
		reformattedMap.put("rev_ed", convertStringDateFormat(cycleDataMap.get("rev_ed")));
		return reformattedMap;
	}
	
	private String convertStringDateFormat(String date) {
		try {
			SimpleDateFormat src_sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat dest_sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date utildate = src_sdf.parse(date);
			date = dest_sdf.format(utildate);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public Boolean stopOtherCycle() {
		String sql = "update lu_appr_cycle set IsFinalized = ?  where IsFinalized = ?";
		return template.execute(sql,new PreparedStatementCallback<Boolean>(){  
		    public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {  
		        ps.setInt(1, CycleStatus.isFinished.ordinal());
		    	ps.setInt(2,CycleStatus.isRunning.ordinal());  
		        return ps.execute();  
		    }  
		});  
	}
	
	public Integer getTotalCycle(){
		String query = "select count(ApprCycleId) from lu_appr_cycle";
		Integer totalCycles = template.queryForObject(query,Integer.class);
		return totalCycles;
	}
	
	
	/*public Boolean insertNextApprIdToRequiredTables(Integer currentCycleId) {
		String query = "select count(ApprEmpId) from tx_appr_empl";
		Integer lastApprEmpId = template.queryForObject(query,Integer.class);
		String sql2 = "Insert tx_appr_empl_flow('nextApprEmpId') values(?)";
		return template.execute(sql2,new PreparedStatementCallback<Boolean>(){  
		    public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {  
		        ps.setInt(1, CycleStatus.isFinished.ordinal());
		    	ps.setInt(2,CycleStatus.isRunning.ordinal());  
		        return ps.execute();  
		    }  
		});  
	}*/

	public List<Recipient> fetchAllRrecipients(Integer empId) {
		String sql = "";
		if(empId != null){
			sql = "select emp_lastname,emp_firstname,emp_work_email from tx_hs_hr_employee left join tx_appr_empl on tx_appr_empl.hs_hr_employee_id = tx_hs_hr_employee.employee_id where tx_appr_empl.ApprEmpId="+empId;
		}else {
			sql = "select emp_lastname,emp_firstname,emp_work_email from tx_hs_hr_employee";
		}
		return template.query(sql, new ResultSetExtractor<List<Recipient>>(){
		    public List<Recipient> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	List<Recipient> recipients= new ArrayList<Recipient>();
		        while(rs.next()){
		            Recipient rc = new Recipient();
		        	rc.setEmail(rs.getString("emp_work_email"));
		        	rc.setFirstName(rs.getString("emp_firstname"));
		        	rc.setLastName(rs.getString("emp_lastname"));
		       
		        	recipients.add(rc);
		        }
		        return recipients;
		    }
		});
	}
	
	public Map<Integer,String> getEmployeesAboveConsultantProfie(){
		Integer currentCycleId = loginDao.getCurrentAppraisalCycle().getCycleId();
		List<Integer> jobTitleIdList = new ArrayList<Integer>();
		jobTitleIdList.add(5);jobTitleIdList.add(7);jobTitleIdList.add(19);jobTitleIdList.add(35);jobTitleIdList.add(38);jobTitleIdList.add(42);
		jobTitleIdList.add(8);jobTitleIdList.add(25);jobTitleIdList.add(41);jobTitleIdList.add(12);
		String query = "select tx_appr_empl.ApprEmpId as empId, tx_hs_hr_employee.emp_firstname as firstName,tx_hs_hr_employee.emp_lastname as lastName "
				+ "from tx_hs_hr_employee  Left join  tx_appr_empl on tx_hs_hr_employee.employee_id=tx_appr_empl.hs_hr_employee_id join tx_ohrm_user on tx_hs_hr_employee.employee_id= tx_ohrm_user.employee_id \r\n" + 
				"  where tx_hs_hr_employee.job_title_code IN (:job_title_ids) and tx_appr_empl.ApprCycleId = (:cycleId) and tx_hs_hr_employee.termination_id IS NULL and tx_hs_hr_employee.termination_id IS NULL ";
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("job_title_ids", jobTitleIdList);
		sqlParameters.addValue("cycleId", currentCycleId);
		NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
		return (Map<Integer,String>) namedTemplate.query(query, sqlParameters, new ResultSetExtractor<Map<Integer,String>>() {
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer,String> aboveConsultProfileEmployee = new HashMap<Integer, String>();
				while(rs.next()){
					aboveConsultProfileEmployee.put(rs.getInt("empId"),rs.getString("firstName") + " " +rs.getString("lastName"));
		        }
				return aboveConsultProfileEmployee;
			}
		});
	}
	
	public Map<Integer,String> getHRAdminMember(){
		Integer currentCycleId = loginDao.getCurrentAppraisalCycle().getCycleId();
		String query = "select distinct tx_appr_empl.ApprEmpId as empId, tx_hs_hr_employee.emp_firstname as firstName,tx_hs_hr_employee.emp_lastname as lastName "
				+ "from tx_hs_hr_employee  Left join  tx_appr_empl on tx_hs_hr_employee.employee_id=tx_appr_empl.hs_hr_employee_id  Left join tx_ohrm_user "
				+ "on tx_hs_hr_employee.employee_id = tx_ohrm_user.employee_id "
				+ " \r\n" + 
				"  where tx_appr_empl.ApprCycleId = (:cycleId) and tx_ohrm_user.is_superadmin = 1  and tx_hs_hr_employee.termination_id IS NULL";
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("cycleId", currentCycleId);
		NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
		return (Map<Integer,String>) namedTemplate.query(query, sqlParameters, new ResultSetExtractor<Map<Integer,String>>() {
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer,String> hrAdminMember = new HashMap<Integer, String>();
				while(rs.next()){
					hrAdminMember.put(rs.getInt("empId"),rs.getString("firstName") + " " +rs.getString("lastName"));
		        }
				return hrAdminMember;
			}
		});
	}
	
	public Map<Integer,String> getAllActiveEmployees(){
		Integer currentCycleId = loginDao.getCurrentAppraisalCycle().getCycleId();
		String query = "select tx_appr_empl.ApprEmpId as empId, tx_hs_hr_employee.emp_firstname as firstName,tx_hs_hr_employee.emp_lastname as lastName "
				+ "from tx_hs_hr_employee  Left join  tx_appr_empl on tx_hs_hr_employee.employee_id=tx_appr_empl.hs_hr_employee_id \r\n" + 
				"  where tx_appr_empl.ApprCycleId = (:cycleId) and tx_hs_hr_employee.termination_id IS NULL";
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("cycleId", currentCycleId);
		NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
		return (Map<Integer,String>) namedTemplate.query(query, sqlParameters, new ResultSetExtractor<Map<Integer,String>>() {
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer,String> aboveConsultProfileEmployee = new HashMap<Integer, String>();
				while(rs.next()){
					aboveConsultProfileEmployee.put(rs.getInt("empId"),rs.getString("firstName") + " " +rs.getString("lastName"));
		        }
				return aboveConsultProfileEmployee;
			}
		});
	}

	public Boolean mapEmployeeWithReviewer(List<Integer> employeeList,Integer reviewerId,Integer cycleId) {
		String commaSepratedIds = StringUtils.join(employeeList, ",");
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template.getDataSource()).withProcedureName("isp_reviewer_rating");
		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("revId", reviewerId);
		sqlParameters.addValue("empList", commaSepratedIds);
		sqlParameters.addValue("cycleId", cycleId);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(sqlParameters);
		return true;
	}
	
	public List<Integer> getEmployeesMappedWithReviewer(Integer reviewerId){
		String sql = "select appr_empid from tx_reviewer_remarks where reviewer_id="+reviewerId;
		return template.query(sql, new ResultSetExtractor<List<Integer>>(){
		    public List<Integer> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	List<Integer> employeeList= new ArrayList<Integer>();
		        while(rs.next()){
		        	employeeList.add(rs.getInt("appr_empid"));
		        }
		        return employeeList;
		    }
		});
	}

	public Boolean mapReviewerWithHRAdmin(List<Integer> reviewerList, Integer hrId, Integer runningCycleId) {
     
		 String commaSepratedIds = StringUtils.join(reviewerList, ",");
		 SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template.getDataSource()).withProcedureName("isp_admin_reviewer");
			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
			sqlParameters.addValue("hrId", hrId);
			sqlParameters.addValue("reviewerList", commaSepratedIds);
			sqlParameters.addValue("cycleId", runningCycleId);
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(sqlParameters);
			return true;
		
		
	}

	public List<Integer> getReviewersMappedWithHRAdmin(Integer hrId) {
		String sql = "select reviewer_id from tx_reviewer_2_admin where hrAdmin_id="+hrId;
		return template.query(sql, new ResultSetExtractor<List<Integer>>(){
		    public List<Integer> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	List<Integer> employeeList= new ArrayList<Integer>();
		        while(rs.next()){
		        	employeeList.add(rs.getInt("reviewer_id"));
		        }
		        return employeeList;
		    }
		});	
		}


	public List<Recipient> fetchAllLevelTwoReviewer(List<Integer> reviewerList) {
		   String sql = "";
			if(reviewerList != null){
				sql = "select emp_lastname,emp_firstname,emp_work_email from tx_hs_hr_employee left join tx_appr_empl on tx_appr_empl.hs_hr_employee_id = tx_hs_hr_employee.employee_id where tx_appr_empl.ApprEmpId IN (:reviewer_list)";
			}else {
				sql = "select emp_lastname,emp_firstname,emp_work_email from tx_hs_hr_employee";
			}
			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
	        sqlParameters.addValue("reviewer_list",reviewerList);
	        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
	        //return (Map<Integer,String>) namedTemplate.query(query, sqlParameters, new ResultSetExtractor<Map<Integer,String>>()
	        return (List<Recipient>) namedTemplate.query(sql, sqlParameters, new ResultSetExtractor<List<Recipient>>(){
			    public List<Recipient> extractData(ResultSet rs) throws SQLException,DataAccessException {
			    	List<Recipient> recipients= new ArrayList<Recipient>();
			    	while(rs.next()){   
			    	Recipient rc = new Recipient();
			            rc.setEmail(rs.getString("emp_work_email"));
			        	rc.setFirstName(rs.getString("emp_firstname"));
			        	rc.setLastName(rs.getString("emp_lastname"));;
			            recipients.add(rc);
			        }
					return recipients;
				}
			});
	}  
	
	public List<Recipient> fetchAllRecipientsForReviewerReminder() {
		String sql = "";
			sql = "SELECT [emp_lastname],[emp_firstname],[emp_work_email] FROM [PMS].[dbo].[stg_tx_hs_hr_employee] where employee_id in (SELECT distinct [hs_hr_employee_id] FROM [PMS].[dbo].[tx_appr_empl_flow] where phaseid =3 and emp_lastname IN ('Kumari','Sabde'))";
		return template.query(sql, new ResultSetExtractor<List<Recipient>>(){
		    public List<Recipient> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	List<Recipient> recipients= new ArrayList<Recipient>();
		        while(rs.next()){
		            Recipient rc = new Recipient();
		            rc.setEmail(rs.getString("emp_work_email"));
		        	rc.setFirstName(rs.getString("emp_firstname"));
		        	rc.setLastName(rs.getString("emp_lastname"));;
		            recipients.add(rc);
		        }
		        return recipients;
		    }                 
		});
	}
	
}
