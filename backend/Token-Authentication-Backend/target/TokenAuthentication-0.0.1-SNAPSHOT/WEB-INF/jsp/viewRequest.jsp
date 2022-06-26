<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <div class="container">
 
 <table class="table table-bordered">
 <tr>
 <td ><p class="font-weight-bold">ID</p></td>
 <td class="font-weight-bold">EmailId</td>
 <td class="font-weight-bold">Name</td>
 <td class="font-weight-bold">Cloud</td>
 <td class="font-weight-bold">Send Key</td>

    
 </tr>
<c:forEach var="temp" items="${keys}">

 <tr>
 <td>${temp.id}</td>
 <td>${temp.emailid}</td>
 <td>${temp.name}</td>
 <td>${temp.cloudtype}</td>
 <td><a href="sendKeys?mailid=${temp.emailid}">Send</a></td>

 

 </tr>



</c:forEach>
  </table>
  
  </div>