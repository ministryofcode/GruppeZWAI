<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../../head.jsp" />
<h1>Neue Nachricht</h1>


<form action="newMessage.htm" method="post">

	<p>
		<label for="post">Empfaenger:</label>
		<br>
		<textarea name="Receiver" id="post" cols="10" rows="1" required maxlength="250"></textarea>
		<br /> <label for="post">Nachricht:</label>
		<br>
		<textarea name="Nachricht" id="post" cols="60" rows="5" required maxlength="250"></textarea>
		<br /> <select name="chooseType" size="2">
			<option>MIT Empfangsbestaetigung Senden (nur fuer Freunde)</option>
			<option>OHNE Empfangsbestaetigung Senden</option>
		<br>
		</select> <input type="submit" value="SENDEN">
	</p>

</form>


<jsp:include page="../../foot.jsp" />
