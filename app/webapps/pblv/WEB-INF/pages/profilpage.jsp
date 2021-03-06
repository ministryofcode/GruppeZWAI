<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Willkommen <c:out value="${user}"/></h1>

    <c:forEach var="locpic" items="${pictures}">
        <img src="img/${locpic}_th.jpg" alt="${locpic}">
    </c:forEach>
    
    <c:if test="${userID != loggedID}">
	    <p>
	    <form action="profilpage.htm" name="addFriend" method="post">
		    <input type="hidden" name="senderID" id="senderID" value="${loggedID}">
		    <input type="hidden" name="receiverID" id="receiverID" value="${userID}">
		    <input type="submit" value="${user} als Freund hinzufügen">
	    </form>
	    </p>
  	</c:if>
    
  	<c:if test="${userID == loggedID}">
  		<p>
  		<form action="profilpage.htm" name="posten" method="post">
  		<label for="post">Eingaben f&uuml;r ein Post:</label><br/>
    	<textarea name="post" id="post" cols="60" rows="5" maxlength="255" required></textarea><br/>
		<input type="checkbox" name="privacy" id="privacy" checked> Post nur f&uuml;r Freunde<br/>
		<input type="submit" value="POSTEN" onClick="window.location.reload()">
		</form>
		</p>
	</c:if>

	<p>
		<c:forEach var="post" items="${posts}">
	        <table border="1" width="450">
		        <tr><td width="340">
		        <p>${post.message}<p>
		        </td><td width="110">
			        <div align="center">
				         <form action="profilpage.htm" name="rating" method="post">
				         	<br>
				            <input type="hidden" name="userID" id="userID" value="${loggedID}">
				         	<input type="hidden" name="postID" id="postID" value="${post.id}">
				         	<input type="submit" name="TRUE" id="TRUE" value="KANJEG">
				         	<br>
				         	<font color="green"><p>${post.likes} KANJEG</p></font>
				         	<font color="red"><p>${post.dislikes} MOTVILJE</p></font>
				         	<br>
				         	<input type="submit" name="FALSE" id="FALSE" value="MOTVILJE">
				         </form>
			         </div>
		         </td></tr>
	         </table>
	    </c:forEach>
	</p>

<jsp:include page="../../foot.jsp"/>
