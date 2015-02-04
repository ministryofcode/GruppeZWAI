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
    <p>
    <input type="submit" value="Als Freund hinzufügen">
    </p>
  	<label for="post">Eingaben für ein Post:</label>
  	<br>
    	<textarea name="post" id="post" cols="60" rows="5"></textarea>
  	<br>
 	<input type="submit" value="POSTEN">
	<br>
	<p>
	<c:forEach var="post" items="${posts}">
        <p>${post}<p>
    </c:forEach>
	</p>
</form>


<jsp:include page="../../foot.jsp"/>