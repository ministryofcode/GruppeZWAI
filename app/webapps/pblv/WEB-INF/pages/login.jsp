<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Login</h1>
<h2><c:out value="${msg}"/></h2>


<form action="login.htm" method="post">
	<label for="mname" >Benutzername: </label>
    <input type="text" name="mname" id="mname" maxlength="250">
    <label for="mpwd">Passwort:</label>
    <input type="password" name="mpwd" id="mpwd" maxlength="250">
    <input type="submit" value="Anmelden">
</form>


<jsp:include page="../../foot.jsp"/>
