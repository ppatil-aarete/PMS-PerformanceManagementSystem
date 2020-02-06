<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
   <link rel="stylesheet" href="<c:url value='/lib/jquery/dataTable/dataTables.checkboxes.css'/>" />	
  <link rel="stylesheet" href="<c:url value='/lib/jquery/multiselect/bootstrap-multiselect.css'/>" />	
  <linl rel="stylesheet" href="<c:url value='/WEB-INF/login/css/masterpagestyle.css'/>"/>  
  <link rel="stylesheet" href="<c:url value='/lib/rater/rateremp.css'/>"/>
  <link rel="stylesheet" href="<c:url value='/lib/rater/raterappr.css'/>"/>
  <link rel="stylesheet" href="<c:url value='/lib/rater/raterrev.css'/>"/>
  <link href="https://fonts.googleapis.com/css?family=Nunito+Sans" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  
  
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
  <script type="text/javascript" src="<c:url value='/lib/jquery/dataTable/dataTables.checkboxes.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/jquery/multiselect/bootstrap-multiselect.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/jquery/multiselect/popper.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/rater/rateremp.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/rater/customrateremp.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/rater/raterappr.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/rater/customraterappr.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/rater/raterrev.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/rater/customraterrev.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/lib/Export/table2excel.js'/>"></script>
  <%-- <script type="text/javascript" src="<c:url value='/lib/chart/canvasjs.min.js'/>"></script>
   --%>
  <style><%@include file="/WEB-INF/login/css/masterpagestyle.css"%></style>
</head>
<body>
					<!-- <div id="extendDates" class="row" style="display:none;margin-bottom:1%;"> -->
					<div class="col-sm-12" style="border:1px soild #EEEEEEE;margin-top:3px;background:white;">
						 <label style="width:90%;padding:1% 0 1% 0%;font-size:18px;"><i class="fa fa-plus" style="padding-left:0%;margin-right:1%;color:#18A689;"></i> Create Cycle</label>
					</div>
					<div class="col-sm-12" style="padding:0.5% 0 0% 0;">
						    
				               <div class="ibox"> 
				                  
				                  <div class="ibox-content" style="">
				                         
										  
										  <div class="form-group">
												<label class="font-normal">Set End Date :</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="extend_cycle_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="extendCycleEndDate" id="extendCycleEndDate" size="16" class="form-control" style="width:20%;" required> 
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
												<label class="font-normal">Set end date for self assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="extend_self_ap_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="extendSelfApprEndDate" id="extendSelfApprEndDate" size="16" class="form-control" style="width:20%;" required> 
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
												<label class="font-normal">Set end date for appraiser assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="extend_mng_ap_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="extendMngApprEndDate" id="extendMngApprEndDate" size="16" class="form-control" style="width:20%;" required> 
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
												<label class="font-normal">Set end date for reviewer assessment:</label>
												<div data-date="" data-date-format="dd-mm-yyyy" id="extend_rev_ap_enddate" class="input-group date">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input type="text" readonly placeholder="DD-MM-YYYY" name="extendRevApprEndDate" id="extendRevApprEndDate" size="16" class="form-control" style="width:20%;" required> 
												</div>
										  </div>
				                  </div>
				                </div>  
							</div>
							
				<!-- </div> -->
</body>
</html>