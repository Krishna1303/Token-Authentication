<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
<br/><br/>
<div class="row">
<div class="col-md-3 panel-primary"></div>
<div class="col-md-1 panel-primary"></div>
  <div class="col-md-3 panel-primary">
  
  <form action="addMoney" method="post">
  <h3>${msg }</h3>



  <div class="form-group">
    <label for="exampleInputPassword1">Select Account Number</label>
   <select name="accid" class="form-control" id="exampleInputEmail1">
   
  <c:forEach var="temp" items="${accnos}">
  
<option  value="${temp}">${temp}</option>
 
  </c:forEach>
   
   </select>
    
 
  </div> 
  
 <div class="form-group">
    <label for="exampleInputEmail1">Enter  Amount</label>
    <input type="text" class="form-control" id="exampleInputEmail1"  name="amount" placeholder="Enter  Amount">
  </div>
  
  
 <button type="submit" class="button primary">Submit</button></form>

  </div>

</div>


</div>

<%@ include file="common/footer.jspf" %>