<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Ihre Profil Seite</h1>
<h2>Hallo <c:out value="${user}"/></h2>


<form action="profilpageadd.htm" name="addfriend" method="post">
   
    <label for="Hallo" >Hello world</label>
    <br>
    <c:forEach var="locpic" items="${pictures}">
        <img src="img/${locpic}_th.jpg" alt="${locpic}">
    </c:forEach>
    
    <c:if test="${userID != loggedID}">
	    <p>
	    <input type="submit" value="${user} als Freund hinzufügen">
	    </p>
  	</c:if>
</form>


<jsp:include page="../../foot.jsp"/>