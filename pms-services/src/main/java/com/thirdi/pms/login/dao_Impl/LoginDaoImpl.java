package com.thirdi.pms.login.dao_Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdi.pms.admin.CycleStatus;
import com.thirdi.pms.admin.RoleStatus;
import com.thirdi.pms.admin.modal.AppraisalCycle;
import com.thirdi.pms.competency.PhaseStatus;
import com.thirdi.pms.login.dao.LoginDao;
import com.thirdi.pms.login.model.EmployeeDetails;
import com.thirdi.pms.login.model.LoginUser;
import com.thirdi.pms.login.model.Menu;

@Repository
public class LoginDaoImpl implements LoginDao {

	
	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate template; 
	
	private LoginUser glogin;
	
	public int checkLogin(String username,String password){
		int cnt=0;
		String sqlcnt="select count(*) uCount from tx_ohrm_user where user_name='"+username+"'";
		cnt=template.queryForObject(sqlcnt,Integer.class);
		if (cnt==0) return 0; else
		{
			String sql="select user_name,User_Password,isnull(apprempid,999999) as apprempid, employee_id, emp_firstname, user_role, emp_work_email from view_userapprempid where user_name='"+username+"'";
			glogin=template.queryForObject(sql,new LoginMapper());
			cnt=checkPassword(password,glogin.getPassword());
			if(cnt==0)return 0; else
			return glogin.getApprempid();
		}	
	}  
	
	public LoginUser getUser(String username) {
		String sql="select user_name,isnull(apprempid,999999) as apprempid, employee_id, emp_firstname,user_role,emp_work_email,is_superadmin from view_userapprempid where user_name='"+username+"'";
		glogin=template.queryForObject(sql,new LoginMapper());
		return glogin;
	}
	
	public List<Menu> getMenu(String username){  
	    return template.query("select * from view_usermenu where user_name='"+username+"'",new RowMapper<Menu>(){  
	        public Menu mapRow(ResultSet rs, int row) throws SQLException {  
	        	Menu e=new Menu(); 
	        	e.setModule_id(rs.getInt("sub_module_id"));
	            e.setModulename(rs.getString("module_name"));  
	            e.setSubmodulename(rs.getString("sub_module_name"));  
	            e.setLink(rs.getString("spring_link"));
	            return e;
	        }  
	    });  
	} 	
	
	private static int checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;
		if(null == stored_hash && !stored_hash.startsWith("$2a$")) {
			//throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
			return 0;
		}
		if(stored_hash != null && !stored_hash.startsWith("$2a$")) {
			return 1;
		}
		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);
		return(password_verified?1:0);
	}

	public AppraisalCycle getCurrentAppraisalCycle() {
		String query = "select * from lu_appr_cycle where IsFinalized = 0";
		AppraisalCycle apprCycle = (AppraisalCycle)template.queryForObject(query, new Object[] {}, new CycleMapper());
			
		return apprCycle;
	}

	public Map<Integer, String> getNameOfUserById() {
		return template.query("select employee_id,emp_lastname,emp_firstname from tx_hs_hr_employee", new ResultSetExtractor<Map<Integer, String>>(){
		    public Map<Integer,String> extractData(ResultSet rs) throws SQLException,DataAccessException {
		        Map<Integer,String> mapRet= new HashMap<Integer,String>();
		        while(rs.next()){
		            mapRet.put(rs.getInt("employee_id"),rs.getString("emp_firstname")+ " " + rs.getString("emp_lastname"));
		        }
		        return mapRet;
		    }
		});
	}

	public Map<Integer, String> getDesignationOfUserById() {
		return template.query("select e.employee_id as employee_id , j.job_title as job_title from tx_hs_hr_employee e INNER JOIN lu_ohrm_job_title j ON e.job_title_code= j.id;", new ResultSetExtractor<Map<Integer, String>>(){
		    public Map<Integer,String> extractData(ResultSet rs) throws SQLException,DataAccessException {
		        Map<Integer,String> mapRet= new HashMap<Integer,String>();
		        while(rs.next()){
		            mapRet.put(rs.getInt("employee_id"),rs.getString("job_title"));
		        }
		        return mapRet;
		    }
		});
	}
	
	public List<EmployeeDetails> getAllPhaseStatusAndSuperiorsDetails(Integer empId,Boolean isTeamData,Integer currentCycleId) {
		String query = "";
		if(isTeamData){
			 query = "select ApprEmpId,phaseid,hs_hr_employee_id,Status,Completion_date from tx_appr_empl_flow where hs_hr_employee_id = "+empId+
					 " and cycle_id=" + currentCycleId + " and (phaseid = 2 OR phaseid = 3)";
		}else {
			query = "select ApprEmpId, phaseid, hs_hr_employee_id, Status, Completion_date from tx_appr_empl_flow where ApprEmpId = "+empId+ " and cycle_id="+currentCycleId;
		}
		return template.query(query, new ResultSetExtractor<List<EmployeeDetails>>(){
		    public List<EmployeeDetails> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	List<EmployeeDetails> empFlowDetailList = new ArrayList<EmployeeDetails>();
		        while(rs.next()){
		            EmployeeDetails details = new EmployeeDetails();
		            details.setSelfId(rs.getInt("ApprEmpId"));
		            details.setNextUserRoleId(rs.getInt("hs_hr_employee_id"));
		            details.setCurrentPhase(PhaseStatus.values()[rs.getInt("phaseid")]);
		            details.setStatus(rs.getInt("Status"));
		            details.setCompletionDate(rs.getString("Completion_date"));
		            empFlowDetailList.add(details);
		        }
		        return empFlowDetailList;
		    }
		});
	}
	
	public Set<Integer> getMappingOfEmpAndAppr(Integer employee_id, Integer currentCycleId) {
		String query = "select ApprEmpId from tx_appr_empl_flow where hs_hr_employee_id= "+employee_id+ " and cycle_id = "+currentCycleId + " and (phaseid = 2 OR phaseid = 3)"; 
		return template.query(query, new ResultSetExtractor<Set<Integer>>(){
		    public Set<Integer> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	Set<Integer> empFlowDetailList = new HashSet<Integer>();
		        while(rs.next()){
		            empFlowDetailList.add(rs.getInt("ApprEmpId"));
		        }
		        return empFlowDetailList;
		    }
		});	
	}

	public List<EmployeeDetails> getAllPhaseStatusAndSuperiorsDetailsOfAllTeamMembers(Set<Integer> apprAndEmpMappingList) {
		if(apprAndEmpMappingList != null && apprAndEmpMappingList.size() > 0){
			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
			sqlParameters.addValue("ids", apprAndEmpMappingList);
			String query = "select ApprEmpId,phaseid,hs_hr_employee_id,Status,Completion_date from tx_appr_empl_flow where ApprEmpId IN (:ids)";
			NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
			return (List<EmployeeDetails>) namedTemplate.query(query, sqlParameters, new RowMapper<EmployeeDetails>() {
				public EmployeeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
					EmployeeDetails object = new EmployeeDetails();
					object.setCompletionDate(rs.getString("Completion_date"));
					object.setCurrentPhase(PhaseStatus.values()[rs.getInt("phaseid")]);
					object.setNextUserRoleId(rs.getInt("hs_hr_employee_id"));
					object.setSelfId(rs.getInt("ApprEmpId"));
					object.setStatus(rs.getInt("Status"));
					return object;
				}
				
			});
		}
		return null;
	}

	public Integer getCountOfLoginUserByName(String username) {
		int count=0;
		String sqlcnt="select count(*) uCount from tx_ohrm_user where user_name='"+username+"'";
		count=template.queryForObject(sqlcnt,Integer.class);
		return count;
	}
	
	@Transactional(readOnly=true)
	public LoginUser getLoginUserByName(String username) {

		String sql="select user_name, isnull(apprempid,999999) as apprempid, employee_id, emp_firstname, user_role, emp_work_email, is_superadmin from view_userapprempid where user_name='"+username+"'";
		return template.queryForObject(sql,new LoginMapper());
	}

	public Set<Integer> getActiveEmpMemberIdsSet(Integer currentCycleId) {
		String sqlQuery = "select ApprEmpId from tx_appr_empl where ApprCycleId = "+currentCycleId;
		return template.query(sqlQuery, new ResultSetExtractor<Set<Integer>>(){
		    public Set<Integer> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	Set<Integer> empFlowDetailList = new HashSet<Integer>();
		        while(rs.next()){
		            empFlowDetailList.add(rs.getInt("ApprEmpId"));
		        }
		        return empFlowDetailList;
		    }
		});	
	}
	
	public Integer getEmpNumberFromApprEmpId(Integer empId) {
		String sqlcnt="select hs_hr_employee_id from tx_appr_empl where ApprEmpId=" + empId;
		Integer employee_id = template.queryForObject(sqlcnt,Integer.class);
		return employee_id;
	}

	public Integer getNumberOfCurrentAppraisalCycle() {
		String query = "select count(*) from lu_appr_cycle where IsFinalized = 0";
		Integer count=template.queryForObject(query,Integer.class);
		return count;
	}

} 

class LoginMapper implements RowMapper<LoginUser> {
		public LoginUser mapRow(ResultSet rs, int arg1) throws SQLException {
			LoginUser login = new LoginUser();
			login.setUsername(rs.getString("user_name"));
			//login.setPassword(rs.getString("User_Password"));
			login.setApprempid(rs.getInt("apprempid"));
			login.setRole(rs.getString("user_role"));
			login.setEmailid(rs.getString("emp_work_email"));
			login.setEmpid(rs.getInt("employee_id"));
			login.setRoleStatus(RoleStatus.values()[rs.getInt("is_superadmin")]);
			return login;
		}
}

class CycleMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		AppraisalCycle apprCycle = new AppraisalCycle();
		apprCycle.setCycleId(rs.getInt("ApprCycleId"));
		apprCycle.setCycleName(rs.getString("ApprCycleName"));
		apprCycle.setIsFinalized(CycleStatus.values()[rs.getInt("IsFinalized")]);
		//apprCycle.setFinalizedDate(rs.getString("finalizedDate"));
		//apprCycle.setFinancialYear(rs.getString("financial_year"));
		apprCycle.setEndate(rs.getString("EndDate"));
		apprCycle.setStartDate(rs.getString("StartDate"));
		apprCycle.setSelfApprStartDate(rs.getString("SelfApprStartDate"));
		apprCycle.setSelfApprEndDate(rs.getString("SelfApprEndDate"));
		apprCycle.setMngApprStartDate(rs.getString("MgrApprStartDate"));
		apprCycle.setMngApprEndDate(rs.getString("MgrApprEndDate"));
		apprCycle.setRevApprStartDate(rs.getString("RevApprStartDate"));
		apprCycle.setRevApprEndDate(rs.getString("RevApprEndDate"));
		return apprCycle;
	}
}
