package com.thirdi.pms.competency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.thirdi.pms.admin.RecipientMail;

@Repository
public class CompetencyDaoImpl implements CompetencyDao {

	JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public Map<Integer, Integer> getMappingOfIds() {
		return template.query("select ApprEmpId,hs_hr_employee_id from tx_appr_empl",
				new ResultSetExtractor<Map<Integer, Integer>>() {
					public Map<Integer, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, Integer> map = new HashMap<Integer, Integer>();
						while (rs.next()) {
							map.put(rs.getInt(1), rs.getInt(2));
						}
						return map;
					}
				});
	}

	public Boolean updateMappingsInEployeeTable(final Map<Integer, Integer> idMapper) {
		String updateSQL = "UPDATE tx_appr_empl_flow SET rep_hs_hr_employee_id = ? where ApprEmpId = ?";
		System.out.println(updateSQL);
		if (idMapper != null && idMapper.size() > 0) {
			for (final Integer id : idMapper.keySet()) {
				template.execute(updateSQL, new PreparedStatementCallback<Boolean>() {
					public Boolean doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						ps.setInt(1, idMapper.get(id));
						ps.setInt(2, id);
						return ps.execute();
					}
				});
			}
		}
		return null;
	}

	public Boolean updateRemarksForCompetencies(final Integer phaseId, final Integer empId, final Integer questionId,
			final String remarks) {
		String query = "update tx_appr_empl_rating SET remarks = ? where appr_phase_id = ? and ApprEmpId = ? and ApprQuestionId = ?";
		System.out.println(query);
		return template.execute(query, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, remarks);
				ps.setInt(2, phaseId);
				ps.setInt(3, empId);
				ps.setInt(4, questionId);
				return ps.execute();
			}
		});
	}

	public Boolean updateRatingsForCompetencies(final Integer phaseId, final Integer empId, final Integer questionId,
			final Integer remarks) {
		String query = "update tx_appr_empl_rating SET remarks = ? where appr_phase_id = ? and ApprEmpId = ? and ApprQuestionId = ?";
		System.out.println(query);
		return template.execute(query, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, remarks);
				ps.setInt(2, phaseId);
				ps.setInt(3, empId);
				ps.setInt(4, questionId);
				return ps.execute();
			}
		});
	}

	public Integer getCurrentPhaseOfEmployee(Integer empId) {
		String query = "select curr_phase_id from tx_appr_empl where ApprEmpId=" + empId;
		System.out.println(query);
		Integer current_phase = template.queryForObject(query, Integer.class);
		return current_phase;
	}

	public Boolean updateCurrentPhaseOfEmployee(Integer empId) {
		String query = "update tx_appr_empl SET curr_phase_id = ? where ApprEmpId = ?";
		System.out.println(query);
		return template.execute(query, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.execute();
			}
		});
	}

	public Boolean updateIsFinalizedStatus(Integer empId) {
		return null;
	}

	public List<SelfAppraisal> getListOfEmpAppraisals(Integer empId, Integer currentCycleId, Boolean flag) {
		return (List<SelfAppraisal>) template.query("select * from  view_getappraisalrecordsallphase where apprempid="
				+ empId + " and ApprCycleId=" + currentCycleId + "order by SectionColOrder,QuestionColOrder",
				new RowMapper<SelfAppraisal>() {
					public SelfAppraisal mapRow(ResultSet rs, int row) throws SQLException {
						SelfAppraisal object = new SelfAppraisal();
						object.setCycleId(rs.getInt("ApprCycleId"));
						object.setEmpId(rs.getInt("ApprEmpId"));
						object.setSection(rs.getString("section"));
						object.setQuestionId(rs.getInt("ApprQuestionId"));
						object.setQuestion(rs.getString("question"));
						object.setWeightage(rs.getDouble("weightage"));
						object.setSectionColOrder(rs.getInt("sectioncolorder"));
						object.setQuestionColOrder(rs.getInt("questioncolorder"));
						object.setpIndicatorId1(rs.getString("performanceind1"));
						object.setpIndicatorId2(rs.getString("performanceind2"));
						object.setpIndicatorId3(rs.getString("performanceind3"));
						object.setRatingYN(rs.getString("RatingYN"));
						object.setRatingId1(rs.getInt("ratingid1"));
						object.setRemark1(rs.getString("remarks1"));
						object.setRating1(rs.getInt("rating1"));
						object.setPhase1Status(rs.getInt("phase1status"));
						object.setRatingId2(rs.getInt("ratingid2"));
						object.setRemark2(rs.getString("remarks2"));
						object.setRating2(rs.getInt("rating2"));
						object.setPhase2Status(rs.getInt("phase2status"));
						object.setRatingId3(rs.getInt("ratingid3"));
						object.setRemark3(rs.getString("remarks3"));
						object.setRating3(rs.getInt("rating3"));
						object.setPhase3Status(rs.getInt("phase3status"));
						object.setIsFinalized(rs.getBoolean("isfinalized"));
						object.setCuurent_Phase_Id(PhaseStatus.values()[rs.getInt("curr_phase_id")]);
						object.setUserName(rs.getString("user_name"));
						return object;
					}
				});
	}

	public Boolean updatePhaseOfEmployeeEntity(final Integer empId, final Integer cycleId,
			final PhaseStatus currentPhase) {
		String query = "update tx_appr_empl SET curr_phase_id = ? where ApprEmpId = ? and ApprCycleId = ?";
		return template.execute(query, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, currentPhase.ordinal());
				ps.setInt(2, empId);
				ps.setInt(3, cycleId);
				return ps.execute();
			}
		});
	}

	public Boolean updatePhaseStatusOfEmplFlowEntity(final Integer empId, Integer cycleId,
			final PhaseStatus currentPhase) {
		String query = "update tx_appr_empl_flow SET status = 1, Completion_date = ? where phaseid = ? and ApprEmpId = ?";
		System.out.println(query);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		final String currentDate = sdf.format(date);
		return template.execute(query, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, currentDate);
				ps.setInt(2, currentPhase.ordinal());
				ps.setInt(3, empId);
				return ps.execute();
			}
		});
	}

	public Double[] getFinalScoreOfEachPhase(final Integer empId) {
		return template.query("select * from view_appraisalscore where ApprEmpId=" + empId,
				new ResultSetExtractor<Double[]>() {
					public Double[] extractData(ResultSet rs) throws SQLException, DataAccessException {
						Double[] dataArray = new Double[3];
						while (rs.next()) {
							dataArray[0] = rs.getDouble("Phase1Rating");
							dataArray[1] = rs.getDouble("Phase2Rating");
							dataArray[2] = rs.getDouble("Phase3Rating");
						}
						return dataArray;
					}
				});
	}

	public Boolean updateStrengthAndWeakness(final Map<Integer, List<StrengthWeakness>> strWeakContainerMap) {
		String updateSQL = "insert into tx_appr_emp_strength(ApprEmpId,Description,Strength_weakness,phase_id,nextRoleId) values (?,?,?,?,?)";
		System.out.println(updateSQL);
		if (strWeakContainerMap != null && strWeakContainerMap.size() > 0) {
			Map.Entry<Integer, List<StrengthWeakness>> swEntryObject = strWeakContainerMap.entrySet().iterator().next();
			final List<StrengthWeakness> swContainerList = swEntryObject.getValue();
			final Integer empId = swEntryObject.getKey();
			for (final StrengthWeakness sw : swContainerList) {
				template.execute(updateSQL, new PreparedStatementCallback<Boolean>() {
					public Boolean doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						ps.setInt(1, empId);
						ps.setString(2, sw.getDescription());
						ps.setString(3, sw.getIsStrengthOrWeakness());
						ps.setInt(4, sw.getPhaseId());
						ps.setInt(5, sw.getNextRoleId());
						return ps.execute();
					}
				});
			}
		}
		return null;
	}

	public List<StrengthWeakness> getStrengthsAndWeaknesses(Integer empId) {
		// TODO Auto-generated method stub
		return template.query("select * from tx_appr_emp_strength where ApprEmpId=" + empId,
				new RowMapper<StrengthWeakness>() {
					public StrengthWeakness mapRow(ResultSet rs, int rowNum) throws SQLException {
						StrengthWeakness object = new StrengthWeakness();
						object.setDescription(rs.getString("Description"));
						object.setIsStrengthOrWeakness(rs.getString("Strength_weakness"));
						object.setNextRoleId(rs.getInt("nextRoleId"));
						object.setPhaseId(rs.getInt("phase_id"));
						return object;
					}
				});
	}

	public Map<Integer, Double[]> getMapOfFinalScore(Integer cycleId) {
		String sql = "select * from view_appraisalscore where ApprCycleId=" + cycleId;
		System.out.println(sql);
		return template.query(sql, new ResultSetExtractor<Map<Integer, Double[]>>() {
			public Map<Integer, Double[]> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, Double[]> finalScoreMapAllEmp = new HashMap<Integer, Double[]>();
				while (rs.next()) {
					Double[] scoreArray = new Double[4];
					scoreArray[0] = rs.getDouble("Phase1Rating");
					scoreArray[1] = rs.getDouble("Phase2Rating");
					scoreArray[2] = rs.getDouble("Phase3Rating");
					scoreArray[3] = rs.getDouble("NormalizedRating");

					if (scoreArray[0] == null) {
						scoreArray[0] = 0.0;
					}
					if (scoreArray[1] == null) {
						scoreArray[1] = 0.0;
					}
					if (scoreArray[2] == null) {
						scoreArray[2] = 0.0;
					}
					if (scoreArray[3] == null) {
						scoreArray[3] = 0.0;
					}
					finalScoreMapAllEmp.put(rs.getInt("ApprEmpId"), scoreArray);
				}

				return finalScoreMapAllEmp;
			}
		});
	}

	public Boolean updateReviewerRatings(final Integer empId, final Float ratings, Integer revId, Integer cycleId) {
		String sql1 = " select ApprEmpId from tx_appr_empl where hs_hr_employee_id =" + revId + " and ApprCycleId= "
				+ cycleId;
		Integer revID = template.queryForObject(sql1, Integer.class);
		String sql = "update tx_reviewer_remarks set rating = " + ratings + ", reviewer_id=" + revID
				+ ", status = 1 where appr_empid = " + empId + "  and cycleId =" + cycleId;

		return template.execute(sql, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.execute();

			}
		});
	}

	public Boolean updateReviewerRemarks(final Integer empId, final String remarks, Integer revId, Integer cycleId) {

		String sql1 = " select ApprEmpId from tx_appr_empl where hs_hr_employee_id =" + revId + " and ApprCycleId= "
				+ cycleId;
		;
		Integer revID = template.queryForObject(sql1, Integer.class);
		String sql2 = "update tx_reviewer_remarks set remarks = ?, reviewer_id= " + revID
				+ ", status= 1 where appr_empid = ? and cycleId =" + cycleId;
		return template.execute(sql2, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, remarks);
				ps.setInt(2, empId);
				return ps.execute();
			}
		});
	}

	public Map<Integer, String> getRemarkOfUser(Integer cycleId) {

		String sql = "select * from view_appraisalscore where ApprCycleId=" + cycleId;
		System.out.println(sql);
		return template.query(sql, new ResultSetExtractor<Map<Integer, String>>() {
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> finalRemark = new HashMap<Integer, String>();
				while (rs.next()) {
					String remarkArray = new String();
					remarkArray = rs.getString("NormalizedRemarks");
					finalRemark.put(rs.getInt("ApprEmpId"), remarkArray);
				}
				return finalRemark;
			}
		});
	}

	public Boolean updateCompetenciesOfEmployee(final Integer empId, final PhaseStatus phaseId,
			final Map<Integer, Object[]> questionAndResponseMap, Integer cycleId, final String statusOfMember)
			throws Exception {
		if (statusOfMember == "") {
			String updateSQL = "UPDATE tx_appr_empl_rating SET Rating = ?,remarks = ? WHERE ApprEmpId = ? and ApprQuestionId = ? and appr_phase_id = ?";
			System.out.println(updateSQL);
			if (questionAndResponseMap != null && questionAndResponseMap.size() > 0) {
				for (final Integer questionId : questionAndResponseMap.keySet()) {
					template.execute(updateSQL, new PreparedStatementCallback<Boolean>() {
						public Boolean doInPreparedStatement(PreparedStatement ps)
								throws SQLException, DataAccessException {
							Object[] dataArray = questionAndResponseMap.get(questionId);
							if (StringUtils.hasText(dataArray[1].toString())) {
								Integer ratings = Integer.valueOf(dataArray[1].toString());
								ps.setInt(1, Integer.valueOf(dataArray[1].toString()));
							} else {
								ps.setInt(1, 0);
							}
							if (StringUtils.hasText(dataArray[0].toString())) {
								ps.setString(2, (String) dataArray[0]);
							} else {
								ps.setString(2, "");
							}
							ps.setInt(3, empId);
							ps.setInt(4, questionId);
							ps.setInt(5, 2);
							return ps.execute();
						}
					});
				}
			}
			return true;
		} else {

			String updateSQL = "UPDATE tx_appr_empl_rating SET Rating = ?,remarks = ? WHERE ApprEmpId = ? and ApprQuestionId = ? and appr_phase_id = ?";
			System.out.println(updateSQL);
			if (questionAndResponseMap != null && questionAndResponseMap.size() > 0) {
				for (final Integer questionId : questionAndResponseMap.keySet()) {
					template.execute(updateSQL, new PreparedStatementCallback<Boolean>() {
						public Boolean doInPreparedStatement(PreparedStatement ps)
								throws SQLException, DataAccessException {
							Object[] dataArray = questionAndResponseMap.get(questionId);
							if (StringUtils.hasText(dataArray[1].toString())) {
								Integer ratings = Integer.valueOf(dataArray[1].toString());
								ps.setInt(1, Integer.valueOf(dataArray[1].toString()));
							} else {
								ps.setInt(1, 0);
							}
							if (StringUtils.hasText(dataArray[0].toString())) {
								ps.setString(2, (String) dataArray[0]);
							} else {
								ps.setString(2, "");
							}
							ps.setInt(3, empId);
							ps.setInt(4, questionId);
							ps.setInt(5, phaseId.ordinal());
							return ps.execute();
						}
					});
				}
			}
			return true;

		}
	}

	public List<RecipientMail> fetchMailIdForEmp(Integer empId, Integer cycleId) {
		String sql = "";
		if (empId != null) {
			sql = "SELECT DISTINCT A.EMP_WORK_EMAIL AS SELF_EMAIL,B.EMP_WORK_EMAIL AS MANAGER_EMAIL,E.EMP_WORK_EMAIL AS REVIEWER_EMAIL FROM [HRMS_Copy].[dbo].[tx_hs_hr_employee] A JOIN [HRMS_Copy].[dbo].[tx_hs_hr_employee] B ON A.report_to = B.employee_id JOIN [HRMS_Copy].[dbo].[tx_appr_empl] C ON A.employee_id = C.hs_hr_employee_id JOIN [HRMS_Copy].[dbo].[tx_appr_empl_rating] D ON C.ApprEmpId = D.ApprEmpId JOIN [HRMS_Copy].[dbo].[tx_hs_hr_employee] E ON D.hs_hr_employee_id = E.employee_id WHERE D.ApprEmpId="
					+ empId + " AND D.cycle_id =" + cycleId + " AND D.appr_phase_id = 3";
		} else {
			sql = "SELECT DISTINCT A.EMP_WORK_EMAIL AS SELF_EMAIL,B.EMP_WORK_EMAIL AS MANAGER_EMAIL,E.EMP_WORK_EMAIL AS REVIEWER_EMAIL FROM [HRMS_Copy].[dbo].[tx_hs_hr_employee] A JOIN [HRMS_Copy].[dbo].[tx_hs_hr_employee] B ON A.report_to = B.employee_id JOIN [HRMS_Copy].[dbo].[tx_appr_empl] C ON A.employee_id = C.hs_hr_employee_id JOIN [HRMS_Copy].[dbo].[tx_appr_empl_rating] D ON C.ApprEmpId = D.ApprEmpId JOIN [HRMS_Copy].[dbo].[tx_hs_hr_employee] E ON D.hs_hr_employee_id = E.employee_id";
		}
		return template.query(sql, new ResultSetExtractor<List<RecipientMail>>() {
			public List<RecipientMail> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<RecipientMail> recipientMails = new ArrayList<RecipientMail>();
				while (rs.next()) {
					RecipientMail rc = new RecipientMail();
					rc.setSelfEmail(rs.getString("SELF_EMAIL"));
					rc.setApprEmail(rs.getString("MANAGER_EMAIL"));
					rc.setRevEmail(rs.getString("REVIEWER_EMAIL"));
					recipientMails.add(rc);
				}
				return recipientMails;
			}
		});
	}

	public float getReviewerFinalRating(Integer cycleId, Integer empId) {
		String sql = "SELECT COALESCE([rating], -1) as rating FROM [HRMS_Copy].[dbo].[tx_reviewer_remarks] where appr_empid="
				+ empId + " and cycleid=" + cycleId;
		float finalReviewerRating = template.queryForObject(sql, Float.class);
		return finalReviewerRating;
	}

}
