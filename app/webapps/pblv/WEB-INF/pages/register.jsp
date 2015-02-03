<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Welcome to Registration!</h1>
<h2><c:out value="${msg}"/></h2>


<form action="register.htm" method="post">
    <label for="mname" >User Name:</label>
    <input type="text" name="mname" id="mname" pattern="^[a-zA-Z]{1,8}$" required>
    
    <label for="mpwd">Password:</label>
    <input type="password" name="mpwd" id="mpwd" required>
    
    <input type="submit" value="Register">
</form>


<jsp:include page="../../foot.jsp"/>