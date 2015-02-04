<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Gelesene Nachrichten:</h1>
<form action="readMessages.htm" method="post">    
        <input type="submit" value="Zeig mir meine Nachrichten">

<table>
  <c:forEach items="${readMessages}" var="item">
    <tr>
      <td><c:out value="${item}" /></td>
    </tr>
  </c:forEach>
</table>


</form>
<jsp:include page="../../foot.jsp"/>
