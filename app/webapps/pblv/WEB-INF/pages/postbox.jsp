<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Funktionen</h1>
<form action="postbox.htm" method="post">

    
        <select name ="chooseFunction" size="3">
    	<option>Neue PN</option>
    	<option>Ungelesene Nachrichten</option>
    	<option>Gelesene Nachrichten</option>
    </select>
    <br><br>
    <input type="submit" value="Select">

</form>
<jsp:include page="../../foot.jsp"/>
