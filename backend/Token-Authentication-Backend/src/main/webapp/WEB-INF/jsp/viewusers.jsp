<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <div class="container">
 
 <table class="table table-striped">
 <tr>
 <td ><p class="font-weight-bold">Account number</p></td>
 <td class="font-weight-bold">Name</td>
 <td class="font-weight-bold">Email id</td>
 <td class="font-weight-bold">Address</td>
 <td class="font-weight-bold">mobile</td>
 <td class="font-weight-bold">Adhar</td>

    
 </tr>
<c:forEach var="temp" items="${user}">

 <tr>
 <td>${temp.id}</td>
 <td>${temp.name}</td>
 <td>${temp.email}</td>
 <td>${temp.address}</td>
 <td>${temp.mobile }</td>
 <td>${temp.adhar}</td>
 

 </tr>



</c:forEach>
  </table>
  
  </div>