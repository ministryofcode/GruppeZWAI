<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Sie sind erfolgreich eingeloggt!</h1>
<a href="postbox.htm">
<h2><c:out value="${msg}"/></h2>
</a>
<br><br>
<h3>Viel Spaß!</h3>
	<form action="logintrue.htm" method="post">
</form>
<jsp:include page="../../foot.jsp"/>
