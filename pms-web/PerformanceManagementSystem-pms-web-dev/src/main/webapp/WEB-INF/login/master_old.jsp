<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <title>Dashboard</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <META Http-Equiv="Cache-Control" Content="no-cache"/>
  <META Http-Equiv="Pragma" Content="no-cache"/>
  <META Http-Equiv="Expires" Content="0"/>
  <link rel="icon" href="<c:url value='/common/favicon.png'/>" type="image/png"/>
  <link rel="stylesheet" href="<c:url value='/lib/jquery/jqueryUI/jquery-ui.min.css'/>">	
  <link rel="stylesheet" href="<c:url value='/lib/bootstrap-3.3.7/css/bootstrap.css'/>" />
  <link rel="stylesheet" href="<c:url value='/lib/font-awesome/css/font-awesome.min.css'/>" />
  <link rel="stylesheet" href="<c:url value='/lib/jquery/loading/showLoading.css'/>" />
  <link rel="stylesheet" href="<c:url value='/lib/toaster/toastr.min.css'/>" />
  <link rel="stylesheet" href="<c:url value='/lib/bootstrap-datepicker/datepicker.css'/>" />
  <link rel="stylesheet" href="<c:url value='/lib/select2/css/select2.css'/>" />
  <link rel="stylesheet" href="<c:url value='/lib/summernote/summernote.css'/>" />
  <link rel="stylesheet" href="<c:url value='/lib/summernote/summernote-bs3.css'/>" />	
  <link rel="stylesheet" href="<c:url value='/lib/jquery/dataTable/jquery.dataTables.css'/>" />	
  <link rel="stylesheet" href="<c:url value='/lib/jquery/multiselect/bootstrap-multiselect.css'/>" />	
  
  
  <style><%@include file="/WEB-INF/login/css/masterpagestyle.css"%></style>
  
  <script type="text/javascript" src="<c:url value='/lib/jquery/dataTable/jquery-3.3.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/jquery/jqueryUI/jquery-ui.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/bootstrap-3.3.7/js/bootstrap.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/bootstrap-datepicker/bootstrap-datepicker.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/jquery/loading/jquery.showLoading.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/toaster/toastr.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/moment/moment.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/summernote/summernote.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/select2/js/select2.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/competency/competency.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/admin/adminPanel.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/jquery/dataTable/jquery.dataTables.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/jquery/multiselect/bootstrap-multiselect.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/jquery/multiselect/popper.js'/>"></script>
</head>
<body style="font-family:Segoe UI !important;height:100% !important;background:#F6F6F6;">

	<div class="row" style="background:#f8f8f8;margin-left:0px;margin-right:0px;">
		 <div class="col-sm-7">	
			<img src="images/third-i-logo-color.png"  style="padding: 2.2% 1.2% 2% 3%; width:20%;height:auto;" alt="ThirdI Logo">
		</div>
		 <div class="col-sm-5 pull-right" style="margin-bottom:0.5%;">
		   <label class="pull-right" style="color:white;margin-top:3.5%;text-align:right;font-size:16px;padding-right:4%;">Welcome, <label id="username">User</label>
			<div class="dropdown">
			    <i class="dropbtn fa fa-caret-down" style="padding:2px !important;"></i>
				<div class="dropdown-content">
						<a href="<c:url value='/j_spring_security_logout' />"><i class="fa fa-power-off" style="margin-right:5%;"></i>Logout</a>
						
						<a id="adminDashboardLink" onclick="showAdminDashboard()"><i class="fa fa-dashboard" style="margin-right:5%;"></i>Admin Panel</a>
				</div>
			</div>
		   <img src="images/user.png" style="width:10%;border-radius:50%;margin-left:1%;"></img>
		</div>
	</div>
	<div id="userDashboard" class="col-sm-12" style="margin-top:0%;background:#f7f7f7;">
		<div class="col-sm-1 sidebar-collapse" style="border-right: 1px solid #D5D3D3;max-height:100%;" id="sideDiv">
			<ul id="menubar" class="nav nav-pills nav-stacked" style="margin-top:3%;">
					<!-- <li id="dashboard" class="active" onclick="showDashboard()" data-toggle="tooltip" data-placement="right" title="Dashbaord"><a href="#tab1" data-toggle="pill" style="padding:5% 0% 5% 10%;margin:15%;"><img id="dashboardImage" src="images/introduction.png" alt="" style="width:67%;"></img></a></li> -->
					<li id="dashboard" class="active" onclick="showDashboard()" data-toggle="tooltip" data-placement="right" title="Dashboard"><a href="#tab2" data-toggle="pill" style="padding:5% 0% 5% 15%;margin:10%;"><img id="dashboardImage" src="images/introduction.png" alt="" style="width:60%;"></img></a></li>
					<!-- <li id="goal" onclick="showGoal()" data-toggle="tooltip" data-placement="right" title="Goal"><a href="#tab3" data-toggle="pill" style="padding:5% 0% 5% 10%;margin:10%;"><img id="goalImage" src="images/goal.png" alt="" style="width:70%;"></img></a></li>
		 -->			<li id="competency" data-toggle="tooltip" data-placement="right" title="My Form" onclick="showCompetencies('')"><a href="#tab4" data-toggle="pill" style="padding:5% 0% 5% 15%;margin:12%;"><img id="myformImage" src="images/my-form.png" alt="" style="width:60%;"></img></a></li>
			<!-- 		<li id="history"  onclick="showHistory()" data-toggle="tooltip" data-placement="right" title="History"><a href="#tab5" data-toggle="pill" style="padding:5% 0% 5% 10%;margin:15%;"><img id="historyImage" src="images/history.png" alt="" style="width:75%;"></img></a></li>
					<li id="logbook" onclick="showLogbook()" data-toggle="tooltip" data-placement="right" title="Feedback"><a href="#tab6" data-toggle="pill" style="padding:5% 0% 5% 10%;margin:15%;"><img id="feedbackImage" src="images/feedback.png" alt="" style="width:90%;"></img></a></li>
	         --></ul>	
		</div>
		<div class="col-sm-11">
		 	<!-- <div class="col-sm-12" style="margin-top:2%;">
				<div class="col-sm-3">
					<span class="col-sm-12" style="margin-top:1%;"></span>
					<span class="col-sm-12" style="margin-top:1%;font-weight:600"></span>
				</div> -->	
				<div style="width: 157%;">
<div class="col-sm-3">
<span class="col-sm-12" style="margin-top:1%;"> Self Assessment </span>
<span id="selfAssessmentED" class="col-sm-12" style="margin-top:1%;font-weight:600"></span>
</div>
<div class="col-sm-3">
<span class="col-sm-12" style="margin-top:1%;">Appraiser Assessment</span>
<span id="apprAssessmentED" class="col-sm-12" style="margin-top:1%;font-weight:600"> </span>
</div>
<div class="col-sm-3">
<span class="col-sm-12" style="margin-top:1%;">Reviewer Assessment</span>
<span id="revAssessmentED" class="col-sm-12" style="margin-top:1%;font-weight:600"></span>
</div>
</div>
<div class="col-sm-12" style="width:100%;margin-top:1%;">
  <label style="width:25.5%;float:left;margin-left:1%;">
<i class="fa fa-circle-o" style="float:left;margin-top:1%;"></i>
<div class="accent-bar" style="width: 173%;"></div>
  </label>
  <label style="width:26%;float:left;">
<i class="fa fa-circle-o" style="float:left;margin-top:1%;    margin-left: 75%;"></i>
<div class="accent-bar" style="    margin-left: 79%; width: 173%;"></div>
  </label>
  <label style="width:26%;float:left;">
<i class="fa fa-circle-o" style="float:left;margin-top:1%; margin-left: 152%;"></i>
<!-- <div class="accent-bar"></div>
  </label>
  <label style="width:20%;">
<i class="fa fa-circle-o" style="margin-top:1%;"></i>
  </label> -->
</div>
          	<div class="col-sm-12" id="headerOfFullContentDiv" style="padding-top:1%;padding-bottom:1%;">
				</div>
				<div class="col-sm-12" id="fullContentDiv" style="margin-bottom:5%;"></div>
			</div>
		</div>
</div>

<div id="adminDashboard" class="col-sm-12" style="margin-top:0%;background:#f7f7f7;display:none;">
		<div class="col-sm-12" style="margin-top:1%;margin-bottom:2%;">
			    <div class="row" style="margin-bottom:1%;">
					    <button onclick="showUserDashboard(), window.location.reload(true);" class="btn btn-circle btn-close pull-right" style="margin-left:1%;" data-toggle="tooltip" data-placement="bottom" title="Close">
							<i class="fa fa-times" style="margin-right:1%;font-size:13px;"></i>
					    </button>
						<button onclick="backToDashboard();" class="btn btn-circle btn-close pull-right" style="margin-left:1%;" data-toggle="tooltip" data-placement="bottom" title="Back">
							<i class="fa fa-mail-reply-all" style="margin-right:1%;font-size:11px;"></i>
					    </button>
				</div>
			    <div class="row" id="adminDashboardHeader" style="padding-top:1%;padding-bottom:1%;background:white;">
			    	 	<div class="col-sm-1" style="padding-top:2%;"> 
			    	 		<i class="fa fa-crosshairs fa-3x fa-fw" style="cursor:pointer;color:#18A689;" onclick="backToDashboard()"></i>
			    	 	</div>
			    	 	<div class="col-sm-10">
			    	 		<label style="width:100%;"><h2 id="currentCycleName">Performance Review Cycle April-2018</h2></label>
			    	 		<label class="label label-warning" id="durationOfCycleSD" style="width:20%;background-color:#18A689"> NA  </label>
			    	 			<label class="label label-default" style="width:5%;">to</label>
			    	 		<label class="label label-warning" id="durationOfCycleED" style="width:20%;background-color:#18A689"> NA  </label>	
			    	 	</div>
			    	 	<div class="col-sm-1" style="padding-top:4%;text-align:center;"> 
			    	 		<h2 class="label label-warning" id="totalCycles" style="width:100%;font-size:16px;background-color:#18A689;">1</h2>
			    	 	    <label style="width:100%;float:none;font-size:12px; font-weight:500;color:#999999;text-align:center;">cycles</label>
			    	 	</div>
			    </div>
				<div class="row" id="actionContainer">
					    <div class="col-sm-12" style="text-align:left;padding-top:3%;padding-bottom:1%;background:white;margin-top:0px;padding-left:3%;font-weight:600;font-size:15px;"> 
					    		Avail Actions
					    </div>
						<div class="col-sm-12" id="createNewCycle" onclick="createNewCycle()" style="cursor:pointer;padding-top:1%;padding-bottom:2%;border-bottom:1px solid #eeeeee;background:white">
								<div class="col-sm-1" style="width:5% !important;cursor:pointer">
									<i class="fa fa-plus fa-2x" style="color:#18A689;"></i>
								</div>
								<div class="col-sm-10">
									<label style="width:100%;float:none;font-size:14px; font-weight:600;cursor:pointer" onclick="CycleNameCheck();">Create Cycle</label>
									<label style="width:100%;float:none;font-size:12px; font-weight:500;color:#999999;cursor:pointer">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</label>
								</div>
								<div class="col-sm-1" style="text-align:right;padding:1% 0 1% 0;">
									 <i class="fa fa-arrow-right fa-2x" style="color:#18A689;cursor:pointer"></i>
								</div>
						</div>
						<div class="col-sm-12" id="showEmployeeProgressView" onclick="showEmployeeProgressView()" style="cursor:pointer;padding-top:1%;padding-bottom:2%;border-bottom:1px solid #eeeeee;background:white">
								<div class="col-sm-1" style="width:5% !important;cursor:pointer;">
									<i class="fa fa-line-chart fa-2x" style="color:#18A689;"></i>
								</div>
								<div class="col-sm-10">
									<label style="cursor:pointer;width:100%;float:none;font-size:14px; font-weight:600;">View Progress</label>
									 <label style="cursor:pointer;width:100%;float:none;font-size:12px; font-weight:500;color:#999999;">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</label>
								</div>
								<div class="col-sm-1" style="text-align:right;padding:1% 0 1% 0;">
									 <i class="fa fa-arrow-right fa-2x" style="color:#18A689;cursor:pointer;"></i>
								</div>
						</div>
						<div class="col-sm-12" id="generateEmployeeMetricSheet" onclick="generateEmployeeMetricSheet()" style="cursor:pointer;padding-top:1%;padding-bottom:2%;border-bottom:1px solid #eeeeee;background:white">
								<div class="col-sm-1" style="width:5% !important;">
									<i class="fa fa-file-text-o fa-2x" style="color:#18A689;"></i>
								</div>
								<div class="col-sm-10">
									<label style="cursor:pointer;width:100%;float:none;font-size:14px; font-weight:600;">Employee Metric Sheet</label>
									 <label style="cursor:pointer;width:100%;float:none;font-size:12px; font-weight:500;color:#999999;">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</label>
								</div>
								<div class="col-sm-1" style="text-align:right;padding:1% 0 1% 0;">
									 <i class="fa fa-arrow-right fa-2x" style="color:#18A689;cursor:pointer;"></i>
								</div>
						</div>
						
					
					<div class="col-sm-12" id="mapHRAdminForReviewerLevel2View" onclick="mapHRAdminForReviewerLevel2View()" style="cursor:pointer;padding-top:1%;padding-bottom:2%;border-bottom:1px solid #eeeeee;background:white">
								<div class="col-sm-1" style="width:5% !important;">
									<i class="fa fa-user fa-2x" style="color:#18A689;"></i>
								</div>
								<div class="col-sm-10">
									<label style="cursor:pointer;width:100%;float:none;font-size:14px; font-weight:600;">Assign Admin</label>
									 <label style="cursor:pointer;width:100%;float:none;font-size:12px; font-weight:500;color:#999999;">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</label>
								</div>
								<div class="col-sm-1" style="text-align:right;padding:1% 0 1% 0;">
									 <i class="fa fa-arrow-right fa-2x" style="color:#18A689;cursor:pointer;"></i>
								</div>
						</div>	
						
					<!-- 	<div class="col-sm-12" id="mapEmployeeWithReviewerView" onclick="mapEmployeeWithReviewerView()" style="cursor:pointer;padding-top:1%;padding-bottom:2%;border-bottom:1px solid #eeeeee;background:white">
								<div class="col-sm-1" style="width:5% !important;">
									<i class="fa fa-users fa-2x" style="color:#18A689;"></i>
								</div>
								<div class="col-sm-10">
									<label style="cursor:pointer;width:100%;float:none;font-size:14px; font-weight:600;">Assign Reviewer</label>
									<label style="cursor:pointer;width:100%;float:none;font-size:12px; font-weight:500;color:#999999;">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</label>
								</div>
								<div class="col-sm-1" style="text-align:right;padding:1% 0 1% 0;">
									 <i class="fa fa-arrow-right fa-2x" style="color:#18A689;cursor:pointer;"></i>
								</div>
						</div> -->
						
						
						
					<!-- 	<div class="col-sm-12" id="mapHRAdminForReviewerLevel2View" onclick="mapHRAdminForReviewerLevel2View()" style="cursor:pointer;padding-top:1%;padding-bottom:2%;border-bottom:1px solid #eeeeee;background:white">
								<div class="col-sm-1" style="width:5% !important;">
									<i class="fa fa-user fa-2x" style="color:#18A689;"></i>
								</div>
								<div class="col-sm-10">
									<label style="cursor:pointer;width:100%;float:none;font-size:14px; font-weight:600;">Assign Admin</label>
									<label style="cursor:pointer;width:100%;float:none;font-size:12px; font-weight:500;color:#999999;">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</label>
								</div>
								<div class="col-sm-1" style="text-align:right;padding:1% 0 1% 0;">
									 <i class="fa fa-arrow-right fa-2x" style="color:#18A689;cursor:pointer;"></i>
								</div>
						</div> -->
				</div>
				
<!------------------------------------------------------------------NEW CYCLE START----------------------------------------------------------------->
				
				<div id="createNewCycelContainer" class="row" style="display:none;margin-bottom:1%;">
					<div class="col-sm-12" style="border:1px soild #EEEEEEE;margin-top:3px;background:white;">
						 <label style="width:90%;padding:1% 0 1% 0%;font-size:18px;"><i class="fa fa-plus" style="padding-left:0%;margin-right:1%;color:#18A689;"></i> Create Cycle</label>
					</div>
					<div class="col-sm-12" style="padding:0.5% 0 0% 0;">
						     <div class="ibox">
						  		  <div class="ibox-title">
				                      <a class="collapse-link" href="" style="color:#000 !important;">
				                      	<label style="width:50%;">
				                      		<label class="label label-success" style="margin-right:2%;background-color:#18A689">1</label> 
				                      		Set title of appraisal cycle
				                      	</label>
				                      </a>
				                      <div class="ibox-tools col-sm-2 pull-right" style="text-align:right;">
				                          <a class="collapse-link" href="">
				                              <i class="fa fa-chevron-up"></i>
				                          </a>
				                      </div>
				                  </div>
				                  <div class="ibox-content" style="">
				                       <input type="text" class="emp_remarks_sw" id="cycle_title" placeholder="Ex. Performance Review Cycle April-2018" style="width:95%;margin-top:1%;margin-bottom:1%;resize: vertical;margin-left:2%;float:none;" required>
				                  </div>
				              </div>   
				              <div class="ibox"> 
				                  <div class="ibox-title">
				                      <a class="collapse-link" href="" style="color:#000 !important;">
				                      	<label style="width:50%;">
				                      		<label class="label label-success" style="margin-right:2%;background-color:#18A689">2</label>
				                      		Set duration of appraisal cycle
				                      	</label>
				                      </a>
				                      <div class="ibox-tools col-sm-2 pull-right" style="text-align:right;">
				                          <a class="collapse-link" href="">
				                              <i class="fa fa-chevron-up"></i>
				                          </a>
				                      </div>
				                  </div>
				                  <div class="ibox-content" style="">
				                          <div class="form-group">
												<label class="font-normal">Set Start Date :</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="cycle_startdate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="cycleStartDate" id="cycleStartDate" size="16" class="form-control" style="width:20%;" required=true> 
												</div>
										  </div>
										  
										  <div class="form-group">
												<label class="font-normal">Set End Date :</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="cycle_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="cycleEndDate" id="cycleEndDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
				                  </div>
				                </div>
				                <div class="ibox">  
				                  <div class="ibox-title">
				                      <a class="collapse-link" href="" style="color:#000 !important;">
				                      	<label style="width:50%;">
				                      		<label class="label label-success" style="margin-right:2%;background-color:#18A689">3</label> 
				                      		Set duration for self assessment	
				                      	</label>
				                      </a>
				                      <div class="ibox-tools col-sm-2 pull-right" style="text-align:right;">
				                          <a class="collapse-link" href="">
				                              <i class="fa fa-chevron-up"></i>
				                          </a>
				                      </div>
				                  </div>
				                  <div class="ibox-content" style="">
				                          <div class="form-group">
												<label class="font-normal">Set start date for self assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="self_ap_startdate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="selfApprStartDate" id="selfApprStartDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
										  <div class="form-group">
												<label class="font-normal">Set end date for self assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="self_ap_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="selfApprEndDate" id="selfApprEndDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
				                  </div>
				               </div>
				               <div class="ibox">   
				                  <div class="ibox-title">
				                      <a class="collapse-link" href="" style="color:#000 !important;"><label style="width:50%;"><label class="label label-success" style="margin-right:2%;background-color:#18A689">4</label> Set duration for appraiser assessment</label>
				                      </a>
				                      <div class="ibox-tools col-sm-2 pull-right" style="text-align:right;">
				                          <a class="collapse-link" href="">
				                              <i class="fa fa-chevron-up"></i>
				                          </a>
				                      </div>
				                  </div>
				                  <div class="ibox-content" style="">
				                          <div class="form-group">
												<label class="font-normal">Set start date for appraiser assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="mng_ap_startdate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="mngApprStartDate" id="mngApprStartDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
										  <div class="form-group">
												<label class="font-normal">Set end date for appraiser assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="mng_ap_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="mngApprEndDate" id="mngApprEndDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
				                  </div>
				               </div>  
				               <div class="ibox"> 
				                  <div class="ibox-title">
				                      <a class="collapse-link" style="color:#000 !important;">
				                      	<label style="width:50%;">
				                      		<label class="label label-success" style="margin-right:2%;background-color:#18A689;">5</label>
				                      		 Set duration of reviewer assessment
				                        </label>
				                      </a>
				                      <div class="ibox-tools col-sm-2 pull-right" style="text-align:right;">
				                          <a class="collapse-link">
				                              <i class="fa fa-chevron-up"></i>
				                          </a>
				                      </div>
				                  </div>
				                  <div class="ibox-content" style="">
				                          <div class="form-group">
												<label class="font-normal">Set start date for reviewer assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="rev_ap_startdate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="revApprStartDate" id="revApprStartDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
										  <div class="form-group">
												<label class="font-normal">Set end date for reviewer assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="rev_ap_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="revApprEndDate" id="revApprEndDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
				                  </div>
				                </div>  
							</div>
							<div class="row">
								<button  id="CheckMandatoryFields" class="btn btn-outline-green btn-success" style="border-radius:0px !important;margin-left:1%;padding-left:2%;padding-right:2%;">Create</button>
							</div>
				</div>
				<div class="row" id="adminDashboardContentArea" style="background:#fff;margin-top:3px;display:none;padding-top:1%;padding-bottom:1%;"></div>
		</div>
</div>

<!------------------------------------------------------------- Admin panel view ends --------------------------------------------------------------->



<!------------------------------------------------------------ Competence modal box starts ------------------------------------------------------------->
 
 
  <div id="remarksAndScoreBox" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none; width:80% !important;height:600px !important;overflow-y:inherit !important;">
        <div class="modal-content clearfix" style="padding-bottom:0%;">
        	<div class="modal-header" style="border-bottom:2px solid #d3d5d3 !important;padding:1% !important;">
            <div class="col-sm-12">
            	<div class="col-sm-9" style="padding-left:0px !important;padding-right:0px !important;">
            			<span class="col-sm-9 pull-left" style="padding-left:0px !important;padding-top:1%;">
            				<!--  <i id="previousQuestionIcon" class="fa fa-caret-left" style="margin-right:1%;font-size:1.2em !important;cursor:pointer;"></i>-->
            				<label id="questionLabel"> </label>
            				<!--  <i id="nextQuestionIcon" class="fa fa-caret-right" style="margin-left:1%;color:black;font-size:1.2em !important;cursor:pointer;"></i>-->
            			</span>
            			<span class="col-sm-3" style="padding-right:7%;">
            				<button id="saveAndContinueBtn" class="btn btn-default pull-right" data-dismiss="modal" aria-hidden="true" style="border-radius:0px !important;padding:5% 15% 5% 15%">Save</button>
            			</span>
            	</div>
            	<div class="col-sm-3" style="padding-right:1% !important;padding-top:0.5%;">
            		<i class="fa fa-times pull-right" class="close" data-dismiss="modal" aria-hidden="true" style="margin-right:0%;padding-top:1%;cursor:pointer;" onclick="$('#remarksAndScoreBox').modal('hide');"></i>
            		<i class="fa fa-info-circle pull-right" style="margin-right:5%;font-size:1.2em !important;padding-top:1%;cursor:pointer;" onclick="viewAndHidePI();"></i>
            	</div>
            </div>
        </div>
        <div class="modal-body">
            <form id="submitCommentsAndScoreForm">
				<div class="col-sm-12" style="padding-left:0% !important;">
					<div class="col-sm-9" style="border-right:1px solid #d3d5d3;padding-left:0% !important;" id="commentBoxDiv">
						<div class="col-sm-12" style="margin-top:0%;padding-top:2%;padding-bottom:2%;padding-left:0% !important;" id="modal_emp_sec">
							<div class="col-sm-6">
								<label class="pull-left" style="font-weight:500;">Associate</label>
							</div>	
							<div id="emp_score_dd_div" class="col-sm-6" style="text-align:right;">
								<label style="font-weight:500;"> Score  </label> 
								<select id="emp_dropdown" data-toggle="tooltip" data-placement="right" title="1 Does not meet expectations. &#13; 2 Sometimes meets the expectations. &#13; 3 Meets expectations. &#013;&#010; 4 Always meets expectations & sometimes exceeds.&#013;&#010; 5 Always exceeds expectations.&#013;&#010;"></select> 
							</div>
							<div class="col-sm-12">
								<label style="font-size:14px;width:100%;font-weight:500;" id="emp_commentLabel"><img src="images/comment.png" style="width:2%;margin-right:0.5%;"></img>Comments</label>
								<textarea rows="3" cols="100" id="emp_remarks" charswidth="23" class="emp_remarks" style="margin-top:1%;width:96%;margin-bottom:1%;resize: vertical;margin-left:4%;"></textarea>
							</div>
						</div>
						<div class="col-sm-12" style="margin-top:0%;padding-top:2%;padding-bottom:2%;padding-left:0% !important;" id="modal_appr_sec">
							<div class="col-sm-6">
								<label class="pull-left" style="font-weight:500;">Appraiser</label>
							</div>	
							<div id="appr_score_dd_div" class="col-sm-6" style="text-align:right;">
								<label style="font-weight:500;"> Score </label> 
								<select id="appr_dropdown" style="width:"></select> 
							</div>
							<div class="col-sm-12">
								<label style="font-size:14px;width:100%;font-weight:500;" id="appr_commentLabel"><img src="images/comment.png" style="width:2%;margin-right:0.5%;"></img>Comments</label>
								<textarea rows="3" cols="100" id="appr_remarks" charswidth="23" class="appr_remarks" style="margin-top:1%;width:96%;margin-bottom:1%;resize: vertical;margin-left:4%;"></textarea>
							</div>
						</div>
						<div class="col-sm-12" style="margin-top:0%;padding-top:2%;padding-bottom:0%;padding-left:0% !important;" id="modal_rev_sec">
							<div class="col-sm-6">
								<label class="pull-left" style="font-weight:500;">Reviewer</label>
							</div>	
							<div class="col-sm-6" style="text-align:right;">
								<label style="font-weight:500;"> Score </label> 
								<select id="rev_dropdown" style=""></select> 
							</div>
							<div class="col-sm-12">
								<label style="font-size:14px;width:100%;font-weight:500;"><img src="images/comment.png" style="width:2%;margin-right:0.5%;"></img>Comments</label>
								<textarea rows="3" cols="100" id="rev_remarks" charswidth="23" class="rev_remarks" style="margin-top:1%;width:96%;margin-bottom:1%;resize: vertical;margin-left:4%;"></textarea>
							</div>
						</div>
					</div>
					<div class="col-sm-3" style="padding-right:0px !important;" id="performanceIndicatorDiv">
						<label style="width:100%">
							<span class="pull-left"> Performance Indicator</span>
							<span class="pull-right" style="margin-right:2% !important;cursor:pointer;font-weight:500;" onclick="viewAndHidePI();">x</span>
						</label>
						<div class="col-sm-12" style="border-bottom:1px solid #d3d5d3;padding:0px !important;margin:0% 0 5% 0;">
							<label class="pull-left" style="float:none;font-weight:400;">Previous Level</label>
							<label id="pi_previous"  style="width:100%;font-weight:lighter;margin-bottom:5%;height:auto;cursor:pointer;"> </label>
							<textarea rows="5" cols="50" id="pi_previous_ta" charswidth="23" class="emp_remarks" style="margin-top:1%;width:100%;margin-bottom:5%;resize: vertical;display:none;" readonly></textarea>
						</div>
						<div class="col-sm-12" style="border-bottom:1px solid #d3d5d3;padding:0px !important;margin:0% 0 5% 0;">
							<label class="pull-left" style="float:none;font-weight:400;">Current Level</label>
							<label id="pi_current"  style="width:100%;font-weight:lighter;margin-bottom:5%;height:auto;"> </label>
							<textarea rows="5" cols="50" id="pi_current_ta" charswidth="23" class="emp_remarks" style="margin-top:1%;width:100%;margin-bottom:5%;resize: vertical;display:none;" readonly></textarea>
						</div>
						<div class="col-sm-12" style="padding:0px !important;margin:0% 0% 5% 0%;"> 
							<label class="pull-left" style="float:none;font-weight:400;">Next Level</label>
							<label id="pi_next"  style="width:100%;font-weight:lighter;margin-bottom:5%;height:auto;"></label>
							<textarea rows="5" cols="50" id="pi_next_ta" charswidth="23" class="emp_remarks" style="margin-top:1%;width:100%;margin-bottom:5%;resize: vertical;display:none;" readonly></textarea>
						</div>
					</div>
				</div>            	
          	  </form>   
        	</div>
      	</div>
</div>


<!------------------------------------------------------------------ competence modal box starts---------------------------------------------------------------------------------->


<!------------------------------------------------------------- strength and weakness modal box starts----------------------------------------------------------------------------->


<div id="strengthAndWeaknessBox" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none; width:80% !important;height:600px !important;overflow-y:inherit !important;">
        <div class="modal-content clearfix" style="padding-bottom:0%;">
        	<div class="modal-header" style="border-bottom:2px solid #d3d5d3 !important;padding:1% !important;">
	            <div class="col-sm-12">
	            	<div class="col-sm-9" style="padding-left:0px !important;padding-right:0px !important;">
	            			<span class="col-sm-9 pull-left" style="padding-left:0px !important;padding-top:1%;">
	            				<!-- <i id="moveToWeaknessBtn" onclick="moveToStrength()" class="fa fa-caret-left" style="margin-right:1%;font-size:1.2em !important;cursor:pointer;"></i>
 -->	            				<label id="questionLabelSW"> </label>
	           <!--  				<i id="moveToStrength" onclick="moveToWeakness()" class="fa fa-caret-right" style="margin-left:1%;color:black;font-size:1.2em !important;cursor:pointer;"></i>
	  -->           			</span>
	            		<!-- 	<span class="col-sm-3" style="padding-right:1%;">
	            				<button id="saveSW" class="btn btn-default pull-right" data-dismiss="modal" aria-hidden="true" style="border-radius:0px !important;padding:5% 15% 5% 15%">Save</button>
	            			</span> -->
	            	</div>
	            	<div class="col-sm-3" style="padding-right:1% !important;padding-top:0.5%;">
	            		<button class="btn btn-default pull-right" class="close" data-dismiss="modal" aria-hidden="true" style="border-radius:0px !important;padding:3% 10% 3% 10%;margin-left:3%;" onclick="$('#strengthAndWeaknessBox').modal('hide');"> Close </button>
	            		<button id="saveSW" class="btn btn-default pull-right" class="close" data-dismiss="modal" aria-hidden="true" style="border-radius:0px !important;padding:3% 10% 3% 10%"> Save</button>
	            		<!-- <i class="fa fa-times pull-right" class="close" data-dismiss="modal" aria-hidden="true" style="margin-right:0%;padding-top:1%;cursor:pointer;" onclick="$('#strengthAndWeaknessBox').modal('hide');"></i> -->
	            	</div>
	            </div>
        	</div>
	        <div class="modal-body">
					<div class="col-sm-12" style="padding-left:0% !important;">
						<div class="col-sm-12" style="padding-left:0% !important;">
							<div  class="col-sm-12" style="margin-top:0%;padding-top:2%;padding-bottom:2%;padding-left:0% !important;" id="modal_emp_sec_sw">
								<div class="col-sm-12">
									<label class="pull-left" style="font-weight:500;">Associate</label>
								</div>	
								<div class="col-sm-12">
									<label id="sOrwLabel" style="font-size:14px;width:100%;font-weight:400;"></label>
									<li><input type="text" class="emp_remarks_sw" id="emp_SW1" style="margin-top:1%;width:65%;margin-bottom:1%;resize: vertical;margin-left:1%;float:none;"></li>
									<li><input type="text" class="emp_remarks_sw" id="emp_SW2" style="margin-top:1%;width:65%;margin-bottom:1%;resize: vertical;margin-left:1%;float:none"></li>
									<li><input type="text" class="emp_remarks_sw" id="emp_SW3" style="margin-top:1%;width:65%;margin-bottom:1%;resize: vertical;margin-left:1%;float:none;"></li>
								</div>
							</div>
							<div class="col-sm-12" style="margin-top:0%;padding-top:2%;padding-bottom:2%;padding-left:0% !important;" id="modal_appr_sec_sw">
								<div class="col-sm-12">
									<label class="pull-left" style="font-weight:500;">Appraiser</label>
								</div>	
								<div class="col-sm-12">
									<label style="font-size:14px;width:100%;font-weight:400;">What your supervisor would say ?</label>
									<li><input type="text" id="appr_SW1" class="appr_remarks_sw" style="margin-top:1%;width:65%;margin-bottom:1%;resize: vertical;margin-left:1%;float:none;"></li>
									<li><input type="text" id="appr_SW2" class="appr_remarks_sw" style="margin-top:1%;width:65%;margin-bottom:1%;resize: vertical;margin-left:1%;float:none;"></li>
									<li><input type="text" id="appr_SW3" class="appr_remarks_sw" style="margin-top:1%;width:65%;margin-bottom:1%;resize: vertical;margin-left:1%;float:none;"></li>
								</div>
							</div>
						</div>
					</div>            	
	        </div>
	      </div>
</div>

<!------------------------------------------------ Email box starts--------------------------------------------------------->

<div id="configMailModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;overflow-y:auto !important; width:60% !important;left:20% !important;">
     <div class="modal-content clearfix" style="padding-bottom:0%;">   
	        <div class="modal-header" style="border-bottom:1px solid #EEEEEE !important;padding:1% !important;">
	            
	            <h2 id="myModalLabel" style="padding-left:1%;padding-bottom:1%;font-weight:400;color:#9E9E9E;margin-top:15px !important">Compose mail
	            	 <button type="button" class="btn btn-danger btn-sm pull-right" style="margin-right:1.5%" data-dismiss="modal" aria-hidden="true" onclick="$('.configMailSummerNote').destroy();">
	            		<i class="fa fa-times" style="margin-right:0.5%;"></i>
	            		 Discard
	            	 </button>
	            </h2>
	        </div>
	        <div class="modal-body" id="configMailModalBody">
	            <form id="configMailform">
	            	<input type="hidden" id="empId" name="empId">
	            	<div class="row">
						<label style="padding-left:1%;font-weight:400;color:#9E9E9E;margin-right:4%;width:10%;float:left;padding-top:1%;">Subject: </label>	            		
	            		<input type="text" class="form-control" id="emailSubject" name="subject" style="height:25px !important;width:80%; box-sizing:content-box;" placeholder="Subject Title">
	            	</div>
	                <div class="row" style="border:1px solid #dddddd;margin-top:1.5%;">
	                        <textarea class="configMailSummerNote" id="configMailtext" name="configMailtext" rows="4" style="width:100%" placeholder="Enter Message"></textarea>
	                 </div>
	            </form>
	        </div>
	        <div class="modal-footer" style="border-top:none !important;padding:1px 20px 20px !important;margin-top:0px;">
	            <button class="btn btn-success" id="send_button" onclick="sendMailToSpecificEmp()"><i class="fa fa-send" style="margin-right:3px;"></i> Send Email</button>
	            <button class="btn btn-default-custom" data-dismiss="modal" aria-hidden="true" onclick="$('.configMailSummerNote').destroy();">Close</button>
	         </div>
     </div>    
</div>

<!-------------------------------------------------Email box ends --------------------------------------------------------->


<!------------------------------------------------------------- strength and weakness modal box starts------------------------------------------->

<!-- confirm box starts here -->
<div id="dialog-confirm" title="Submit Competence" style="display:none;">
  		<p><i class="fa fa-times" style="float:left; margin:12px 12px 20px 0;"></i>Are you sure you want to submit ? <br>You can not edit the form after this action.</p>
</div>
<!-- confirm box ends here -->
<script type='text/javascript'>

var logoutURL = '<c:url value="/login/logout.do"/>';
var getMyFormDataURL =  '<c:url value="/competency/getcompetencies.do"/>';
var updateCompetenciesURL = '<c:url value="/competency/updatecompetencies.do"/>';
var saveCompetenciesURL = '<c:url value="/competency/savecompetencies.do"/>';
var createNewCycleURL = '<c:url value="/admin/createnewcycle.do"/>';
var fetchEmpProgressURL = '<c:url value="/admin/employeestatusdata.do"/>';
var generateEmpMetricSheetURL = '<c:url value="/admin/generatemetricsheet.do"/>';
var sendEmailToAllURL = '<c:url value="/admin/sendemailtoallemp.do"/>';
var sendEmailToSingleEmpURL = '<c:url value="/admin/sendemailtosingleemp.do"/>';
var updateRevScoreAndRemarksURL = '<c:url value="/admin/updaterevscoreandremarks.do"/>';
var getActiveAndAboveConsulantEmpURL = '<c:url value="/admin/mapreviewerandemployee.do"/>';
var getHRAdminMemberURL = '<c:url value="/admin/getHRAdminMember.do"/>';
var mapReviewerWithHRAdminURL = '<c:url value="/admin/mapreviewerwithhradmin.do"/>';
var mapEmployeeWithReviewerURL = '<c:url value="/admin/mapreviewerwithemployees.do"/>';
	
var user = ${user};

var apprCycle = ${apprCycle};
var userGeneralInfo = ${userGeneralInfo};
var myTeamDataMap = ${myTeamData};
var totalCycles = ${totalCycles};

$(document).ready(function(event){
	$('[data-toggle="tooltip"]').tooltip({container: 'body',trigger : 'hover'});
	renderMenuBasedOnRole();
	competencyCache = new prepareCompetencyCache();
	adminCache = new prepareAdminPanelCache();
	showDashboard();
	setUserName();
	initializeDatePickers();
	//setEndDates();
	var heightOfWindow = ($(document).height()) - 50;
	$('#sideDiv').css({"height": heightOfWindow});
	setCycleDatesOnHeader();
});

function initializeDatePickers(){
	/* $("#cycle_startdate").datepicker().on("changeDate", function(ev) {
		$("#cycle_startdate").datepicker('hide');
	});
	$("#cycle_enddate").datepicker().on("changeDate", function(ev) {
			$("#cycle_enddate").datepicker('hide');
	});
	$("#self_ap_startdate").datepicker().on("changeDate", function(ev) {
		$("#self_ap_startdate").datepicker('hide');
	});
	$("#self_ap_enddate").datepicker().on("changeDate", function(ev) {
			$("#self_ap_enddate").datepicker('hide');
	});
	$("#mng_ap_startdate").datepicker().on("changeDate", function(ev) {
		$("#mng_ap_startdate").datepicker('hide');
	});
	$("#mng_ap_enddate").datepicker().on("changeDate", function(ev) {
		$("#mng_ap_enddate").datepicker('hide');
	});
	$("#rev_ap_startdate").datepicker().on("changeDate", function(ev) {
		$("#rev_ap_startdate").datepicker('hide');
	});
	$("#rev_ap_enddate").datepicker().on("changeDate", function(ev) {
		$("#rev_ap_enddate").datepicker('hide');
	});
 */}

function setUserName(){
		var username = (user.username).split('.')[0];
		username = username.toLowerCase().replace(/\b[a-z]/g, function(letter) {
		    return letter.toUpperCase();
		});
		document.getElementById('username').innerHTML = username;	
}

function logout(){
  	window.location = logoutURL;
  	alert("wrong password");
  	sessionStorage.setItem('id',"");
	sessionStorage.invalidate();
}

function renderMenuBasedOnRole(){
	debugger
	var userrole = user.roleStatus; 
	if(userrole != undefined && userrole != null && userrole != ""){
		 if(userrole == "hrAdmin"|| userrole =="admin"){
			 $('#adminDashboardLink').show();
		 }
		 else
			 {
			 $('#adminDashboardLink').hide();
			 }
	}
}

function showToster(title, msg, time, status) {
	toastr.options = {
		"closeButton" : true,
		"debug" : false,
		"progressBar" : true,
		"positionClass" : "toast-top-right",
		"onclick" : null,
		"showDuration" : "100",
		"hideDuration" : "1000",
		"timeOut" : time * 1000,
		"extendedTimeOut" : "1000",
		"showEasing" : "swing",
		"hideEasing" : "linear",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut",
		"preventDuplicates" : true
	}
	var $toast = toastr[status](msg, title);
	$("#toast-container").attr('style','margin-top:10px');
}

function displayError(response, error, thrownError) {

	if (response.status == 0) {
		showToster(thrownError + "!",
				'You are offline!!\n Please Check Your Network.', 10, 'error');
	} else if (response.status == 404) {
		showToster(thrownError + "!", "Requested URL not found.", 10, 'error');
	} else if (response.status == 403) {
		showToster("Security Access Lock !", getHeaderContent(response.responseText), 10, 'error');
	} else if (response.status == 500) {
		showToster("Wrong input !", getHeaderContent(response.responseText), 10, 'error');
	} else if (error == 'parsererror') {
		showToster(thrownError + "!", 'Error.\nParsing JSON Request failed.', 10, 'error');
	} else if (error == 'timeout') {
		showToster(thrownError + "!", 'Request Time out.',10, 'error');
	} else if (response.status == 000) {

	} else {
		showToster(thrownError + "!", 'Unknow Error.', 10, 'error');
		alert('Unknow Error.\n' + response.responseText);
	}
}

// custom confirm box.
$( function() {
    $( "#dialog-confirm" ).dialog({
      resizable: false,
      height: "auto",
      width: 400,
      modal: true,
      autoOpen: false,
      buttons: {
        "ok": function() {
          updateCompetencies();
          $( this ).dialog( "close" );
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      }
    });
 } );

$('body').on("click",function(){
	var heightOfWindow = $(document).height();
	$('#sideDiv').css({"height": heightOfWindow});
});

$('.collapse-link').on('click', function (e) {
    e.preventDefault();
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.children('.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

function showAdminDashboard(){
	debugger
	var userrole = user.roleStatus; 
	if(userrole != undefined && userrole != null && userrole != ""){
		 if(userrole == "hrAdmin"){
			 $('#adminDashboard').show();
			 $('#userDashboard').hide();
			 $('#adminDashboardContentArea').hide();
			 $('#adminDashboardContentArea').html();
				
		 }
		 else if(userrole=="admin"){
			 $('#adminDashboard').show();
			 $('#userDashboard').hide();
			 $('#createNewCycle').hide();
			 $('#showEmployeeProgressView').hide();
			 $('#generateEmployeeMetricSheet').show();
			 $('#mapEmployeeWithReviewerView').hide();
			 $('#mapHRAdminForReviewerLevel2View').hide();
			 $('#adminDashboardContentArea').hide();
			 $('#adminDashboardContentArea').html();
			 
		 }
		 
		
	}
	setCycleDetailsInHeaders();
}

/* function showAdminDashboard(){
	$('#adminDashboard').show();
	$('#userDashboard').hide();
	setCycleDetailsInHeaders();
} */

function showUserDashboard(){
	$('#adminDashboard').hide();
	$('#userDashboard').show();
}

function setCycleDatesOnHeader(){
	$('#selfAssessmentED').html('Due-'+ moment(apprCycle.selfApprEndDate).format('DD-MMM-YY'));
	$('#apprAssessmentED').html('Due-' + moment(apprCycle.mngApprEndDate).format('DD-MMM-YY'));
	$('#revAssessmentED').html('Due-' + moment(apprCycle.revApprEndDate).format('DD-MMM-YY'));
}

 document.getElementById('CheckMandatoryFields').onclick = function() {
	if((!mandatoryField(cycle_title, "Cycle Title"))||(!mandatoryField(cycleStartDate, "Cycle Start Date")) ||(!mandatoryField(cycleEndDate, "Cycle End Date"))||(!mandatoryField(selfApprStartDate, "Self Start Date"))||(!mandatoryField(selfApprEndDate, "Self End Date"))||(!mandatoryField(mngApprStartDate, "Appraiser Start Date"))||(!mandatoryField(mngApprEndDate, "Appraiser End Date"))||(!mandatoryField(revApprStartDate, "Reviewer Start Date"))||(!mandatoryField(revApprEndDate, "Reviewer End Date"))){
	return false;
	}
	else {
		createNewCycleAjax();
    }
    return true;
}
/* function nWin() {
	  var w = window.open();
	  var html = $("#toNewWindow").html();
	    $(w.document.body).html(html);
}
$(function() {
	    $("a#print").click(nWin);
});
 */
</script>  
</body>
</html>