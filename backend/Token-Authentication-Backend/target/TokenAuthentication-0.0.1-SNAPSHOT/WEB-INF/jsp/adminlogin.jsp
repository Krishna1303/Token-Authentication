<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
 <link type="text/css" href="css/mycss.css" rel="stylesheet" />


<div class="container">


<div class="row borders">

  <br/><br/><br/><br/><br/>
 


  <div class="col-md-4" >
  <a href="addUseForm"><img alt="" src="assets/user.png" class="rounded-circle" height="150px" width="150px"></img></a>
  <p>Add User</p>
  </div>
  <div class="col-md-4">
<a href="viewUser"><img alt="" src="assets/viewusers.png" class="rounded-circle" height="150px" width="150px"></img></a>
 <p>View Usersr</p>
  </div>
  <div class="col-md-4">
  <img alt="" src="assets/transcations.png" class="rounded-circle" height="150px" width="150px"></img>
   <p>Transactions</p>
  </div>
</div>

<p><a href="addMoneyForm">Add Money</a></p>

</div>




<%@ include file="common/footer.jspf" %>