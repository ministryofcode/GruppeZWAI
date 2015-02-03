<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Ihre Profil Seite</h1>
<h2>Hallo <c:out value="${user}"/></h2>


<form action="somePage.htm" method="post">
   
    <label for="Hallo" >Hello world</label>
</form>


<jsp:include page="../../foot.jsp"/>