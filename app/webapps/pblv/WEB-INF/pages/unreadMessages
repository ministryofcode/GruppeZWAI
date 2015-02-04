<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Ungelesene Nachrichten:</h1>
<form action="unreadMessages.htm" method="post">    
        <input type="submit" value="Zeig mir meine Nachrichten">

<table>
  <c:forEach items="${unreadMessages}" var="item">
    <tr>
      <td><c:out value="${item}" /></td>
    </tr>
  </c:forEach>
</table>


</form>
<jsp:include page="../../foot.jsp"/>
