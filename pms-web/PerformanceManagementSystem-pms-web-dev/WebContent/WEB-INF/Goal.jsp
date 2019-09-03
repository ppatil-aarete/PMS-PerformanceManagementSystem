<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <style>
    .container {
    width: 30em;
    overflow-x: auto;
    white-space: nowrap;
}
</style>
</head>
</head>
<body>
	<form:form action="submitForm" modelAttribute="goal">
		 <div id="myTable" class="container" style="border: 1px solid black;width: 1350px;">
           <div>
               <p>
                  <span>Key Area</span>
                  <span style="margin-left: 122px;">KRA Weightage</span>
                  <span style="margin-left: 71px;">Goal Weightage</span>
                  <span style="margin-left: 88px;">Outcome</span>
				  <span style="margin-left: 72px;">Measurement Criteria</span>
                  <span style="margin-left: 88px;">Target</span>
                  <span style="margin-left: 97px;">Self Comments</span>
				  <span style="margin-left: 57px;">Appraiser Comments</span>
                  <span style="margin-left: 40px;"> Ratings</span>
                  <span style="margin-left: 52px;">Reviewer Comments</span>
				  <span style="margin-left: 38px;"> Ratings</span>
               </p>
               <div id="br" style="display:inline-flex">
                   <div style="width:150px;display:inline-block;height:auto;min-height:40px">
                       Business Result
                   </div>
                   <span>
                       <INPUT type="text" name="txt" placeholder="kra weightage" />
                   </span>
                   <div id="test1" style="display:inline-block">
                       <span><INPUT type="text" name="txt" /></span>
                       <span><INPUT type="text" name="txt" /></span>
					   <span><INPUT type="text" name="txt" /></span>
					   <span><INPUT type="text" name="txt" /></span>
					   <span><INPUT type="text" name="txt" /></span>
					   <span><INPUT type="text" name="txt" /></span>
					   <span><SELECT name="apprRating" style="width: 100px;">
                        <OPTION value="1">1</OPTION>
                        <OPTION value="2">2</OPTION>
                        <OPTION value="3">3</OPTION>
                        <OPTION value="4">4</OPTION>
                        <OPTION value="5">5</OPTION>
                </SELECT></span>
                       <span><INPUT type="text" name="txt" /></span>
                       <span><SELECT name="revRating"style="width: 100px;">
                        <OPTION value="1">1</OPTION>
                        <OPTION value="2">2</OPTION>
                        <OPTION value="3">3</OPTION>
                        <OPTION value="4">4</OPTION>
                        <OPTION value="5">5</OPTION>
                </SELECT></span>
                   </div>
                   <div style="display:inline-block">
                       <input type="button" style="margin-left:10px;" id="business" value="Add">
                   </div>
               </div>


          <div>
                   <span>
                       Impact To Customer
                   </span>
                   <span>
                       <INPUT type="text" style="margin-left:14px;" name="txt" placeholder="kra weightage" />
                   </span>
                   <div id="test2" style="display:inline-block">
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><SELECT name="apprRating" style="width: 100px;">
                        <OPTION value="1">1</OPTION>
                        <OPTION value="2">2</OPTION>
                        <OPTION value="3">3</OPTION>
                        <OPTION value="4">4</OPTION>
                        <OPTION value="5">5</OPTION>
                </SELECT></span>
                       <span><INPUT type="text" name="txt" /></span>
                       <span><SELECT name="revRating"style="width: 100px;">
                        <OPTION value="1">1</OPTION>
                        <OPTION value="2">2</OPTION>
                        <OPTION value="3">3</OPTION>
                        <OPTION value="4">4</OPTION>
                        <OPTION value="5">5</OPTION>
                </SELECT></span>
                </div>
                   <span><input type="button" style="margin-left:10px;"  id="customerImpact" value="Add"> </span>
               </div>
               <div>
                   <span>
                       Value Add
                   </span>
                   <span>
                       <INPUT type="text" name="txt" style="margin-left:77px;"placeholder="kra weightage" />
                   </span>
                   <div id="test3" style="display:inline-block">
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><INPUT type="text" name="txt" /></span>
                   <span><SELECT name="apprRating" style="width: 100px;">
                        <OPTION value="1">1</OPTION>
                        <OPTION value="2">2</OPTION>
                        <OPTION value="3">3</OPTION>
                        <OPTION value="4">4</OPTION>
                        <OPTION value="5">5</OPTION>
                </SELECT></span>
                       <span><INPUT type="text" name="txt" /></span>
                       <span><SELECT name="revRating"style="width: 100px;">
                        <OPTION value="1">1</OPTION>
                        <OPTION value="2">2</OPTION>
                        <OPTION value="3">3</OPTION>
                        <OPTION value="4">4</OPTION>
                        <OPTION value="5">5</OPTION>
                </SELECT></span>
                </div>
                   <span><input type="button" style="margin-left:10px;" id="valueAdd" value="Add"> </span>
               </div> 
       </div> 
        </div>
                   <span><input type="submit" style="margin-left:0px;margin-top:10px;"  value="Submit"> </span>
               </div> 
  </div>
  </form:form>
   <script type="text/javascript">
       $(document).ready(function () {
                   //for Business result
           $('#business').click(function () {
               $('#test1').append(
                   '<div><span><INPUT type="text"  name="txt" /></span><span><INPUT type="text" name="txt" /></span> <span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><SELECT name="apprRating" style="width: 100px;"><OPTION value="1">1</OPTION><OPTION value="2">2</OPTION><OPTION value="3">3</OPTION><OPTION value="4">4</OPTION><OPTION value="5">5</OPTION></SELECT></span><span><INPUT type="text" name="txt" /></span><span><SELECT name="revRating" style="width: 100px;"><OPTION value="1">1</OPTION><OPTION value="2">2</OPTION><OPTION value="3">3</OPTION><OPTION value="4">4</OPTION><OPTION value="5">5</OPTION></SELECT></span></div>')
           });
           $('#customerImpact').click(function () {
               $('#test2').append(
                   '<div><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span> <span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><SELECT name="apprRating" style="width: 100px;"><OPTION value="1">1</OPTION><OPTION value="2">2</OPTION><OPTION value="3">3</OPTION><OPTION value="4">4</OPTION><OPTION value="5">5</OPTION></SELECT></span><span><INPUT type="text" name="txt" /></span><span><SELECT name="revRating" style="width: 100px;"><OPTION value="1">1</OPTION><OPTION value="2">2</OPTION><OPTION value="3">3</OPTION><OPTION value="4">4</OPTION><OPTION value="5">5</OPTION></SELECT></span></div>')
           });
           $('#valueAdd').click(function () {
               $('#test3').append(
                   '<div><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span> <span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><INPUT type="text" name="txt" /></span><span><SELECT name="apprRating" style="width: 100px;"><OPTION value="1">1</OPTION><OPTION value="2">2</OPTION><OPTION value="3">3</OPTION><OPTION value="4">4</OPTION><OPTION value="5">5</OPTION></SELECT></span><span><INPUT type="text" name="txt" /></span><span><SELECT name="revRating" style="width: 100px;"><OPTION value="1">1</OPTION><OPTION value="2">2</OPTION><OPTION value="3">3</OPTION><OPTION value="4">4</OPTION><OPTION value="5">5</OPTION></SELECT></span></div>')
           });
       });
   </script>
</body>
</html>

