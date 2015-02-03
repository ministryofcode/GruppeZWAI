<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Ihre Profil Seite</h1>
<h2>Hallo <c:out value="${user}"/></h2>


<form action="profilpage.htm" method="post">
   
    <label for="Hallo" >Hello world</label>
	<br>
   
    
    <c:forEach var="locpic" items="${pictures}">
    
        <img src="img/${locpic}_th.jpg" alt="${locpic}">
   
    </c:forEach>
</form>


<jsp:include page="../../foot.jsp"/>