<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Benutzersuche</h1>
<h2><c:out value="${msg}"/></h2>


<form action="lookupuser.htm" method="post">
	<label for="mname" >Benutzername </label>
    <input type="text" name="mname" id="mname" maxlength="250">
    <input type="submit" value="Suche">
</form>


<jsp:include page="../../foot.jsp"/>
