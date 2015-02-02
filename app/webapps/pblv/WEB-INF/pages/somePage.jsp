<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>HalloWorld</h1>
<h2><c:out value="${someThing}"/></h2>


<form action="somePage.htm" method="post">
   
    <label for="Hallo" >Hello world</label>
</form>


<jsp:include page="../../foot.jsp"/>