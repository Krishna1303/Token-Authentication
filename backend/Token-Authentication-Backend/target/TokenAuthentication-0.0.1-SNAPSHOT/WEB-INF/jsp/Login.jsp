<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="container-fluid">
<br/><br/>
<div class="row">
<div class="col-md-3 panel-primary"></div>
<div class="col-md-1 panel-primary"></div>
  <div class="col-md-3 panel-primary">
  
  <form action="adminLogin" method="post">
<div class="form-group">
    <label for="exampleInputEmail1">Enter UserName</label>
    <input type="text" class="form-control" id="exampleInputEmail1"  name="username" placeholder="Username">
  </div>
<div class="form-group">
    <label for="exampleInputPassword1">Password</label>
    <input type="password" class="form-control" id="exampleInputPassword1" name="password"  placeholder="Password">
  </div>
 <button type="submit" class="button primary">Submit</button>
</form>
  </div>

</div>


</div>
</body>
<%@ include file="common/footer.jspf" %>
</html>