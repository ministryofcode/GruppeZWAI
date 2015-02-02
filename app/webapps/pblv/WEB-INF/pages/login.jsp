<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Login</h1>
<h2><c:out value="${msg}"/></h2>


<form action="login.htm" method="post">
    <input type="text" name="mname" id="mname">
    <label for="mname" >User Name</label>
    <input type="text" name="mpwd" id="mpwd">
    <label for="mpwd">Password</label>
    <input type="submit" value="Login">
</form>


<jsp:include page="../../foot.jsp"/>