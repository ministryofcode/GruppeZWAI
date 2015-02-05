<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Freundesliste</h1>
<form action="friendlist.htm" method="post">
    <label for="mname" >Hier kannst du deine Freunde anzeigen lassen</label>
    
        <input type="submit" value="Zeig meine Freunde">

<table>
  <c:forEach items="${friends}" var="item">
    <tr>
      <td><c:out value="${item}" /></td>
    </tr>
  </c:forEach>
</table>


</form>
<jsp:include page="../../foot.jsp"/>
