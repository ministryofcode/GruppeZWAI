<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Benutzersuche</h1>
<h2><c:out value="${msg}"/></h2>

<a href="profilpage.htm?user=${id}">
	Benutzerpinnwand besuchen
</a>
<br><br>

<form action="lookupuser.htm" method="post">
	<label for="mname" >User Name: </label>
    <input type="text" name="mname" id="mname">
    <input type="submit" value="Suche">
</form>

<br>

<jsp:include page="../../foot.jsp"/>
