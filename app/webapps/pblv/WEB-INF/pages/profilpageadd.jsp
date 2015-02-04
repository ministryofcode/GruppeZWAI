<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h2>Willkommen <c:out value="${user}"/></h2>

<form action="profilpageadd.htm" name="addfriend" method="post">
   
    <c:forEach var="locpic" items="${pictures}">
        <img src="img/${locpic}_th.jpg" alt="${locpic}">
    </c:forEach>
    
    <c:if test="${userID != loggedID}">
	    <p>
	    <input type="hidden" name="senderID" id="senderID" value="${loggedID}">
	    <input type="hidden" name="receiverID" id="receiverID" value="${userID}">
	    <input type="submit" value="${user} als Freund hinzufügen">
	    </p>
  	</c:if>
</form>


<jsp:include page="../../foot.jsp"/>