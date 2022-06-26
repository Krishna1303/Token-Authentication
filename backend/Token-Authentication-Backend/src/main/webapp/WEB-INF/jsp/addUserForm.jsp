<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="container-fluid">
<br/><br/>
<div class="row">
<div class="col-md-3 panel-primary"></div>
<div class="col-md-1 panel-primary"></div>
  <div class="col-md-3 panel-primary">
  
  <form:form action="addUser" method="post" commandName="user">
  <h3>${msg }</h3>
  <h3>${accno }</h3>
<div class="form-group">
    <label for="exampleInputEmail1">Enter  Name</label>
    <form:input type="text" class="form-control" id="exampleInputEmail1"  path="name" placeholder="Enter  name"/>
  </div>
  
  <div class="form-group">
    <label for="exampleInputEmail1">Enter  Address</label>
    <form:input type="text" class="form-control" id="exampleInputEmail1"  path="address" placeholder="Enter  Address"/>
  </div>
  
   <div class="form-group">
    <label for="exampleInputEmail1">Enter  Email Address</label>
    <form:input type="text" class="form-control" id="exampleInputEmail1"  path="email" placeholder="Enter  Address"/>
  </div>

 <%--  <div class="form-group">
    <label for="exampleInputPassword1">Select Role of developer</label>
   <form:select path="role" class="form-control" id="exampleInputEmail1">
   <form:option value="--select---"></form:option>
  <form:option value="Team Leader"></form:option>
  <form:option value="Code Developer"></form:option>
  <form:option value="Tester"></form:option>
   
   </form:select>
  </div> --%>
  
   <div class="form-group">
    <label for="exampleInputPassword1">Mobile</label>
  
      <form:input type='text' path="mobile"  id="exampleInputEmail1"  class="form-control" placeholder="Enter mobile number" />
                   
                
  </div>
  
  
   <div class="form-group">
    <label for="exampleInputPassword1">Enter Adhar Card</label>
  
      <form:input type='text' path="adhar"  id="exampleInputEmail1"  class="form-control" placeholder="Enter Adhar Number" />
                   
                
  </div>
  
  
 <button type="submit" class="button primary">Submit</button>
</form:form>
  </div>

</div>


</div>

<%@ include file="common/footer.jspf" %>