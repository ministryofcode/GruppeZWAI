<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<h1>Willkommen!</h1>
<h2><c:out value="${msg}"/></h2>
<form action="register.htm" method="post">
    <label for="mname" >Benutzername:</label>
    <input type="text" name="mname" id="mname" pattern="^[a-zA-Z]{1,8}$" required>
    
    <label for="mpwd">Passwort:</label>
    <input type="password" name="mpwd" id="mpwd" required>
    <br>
    <br>
    <label>Bitte gewünschtes Profilbild auswählen:</label>
    <br>
    <br>
    <label>Bild 1:</label>
    <img src="img/pic1_th.jpg">
    <label>Bild 2:</label>
    <img src="img/pic2_th.jpg">
    <label>Bild 3:</label>
    <img src="img/pic3_th.jpg">
    <label>Bild 4:</label>
    <img src="img/pic4_th.jpg">
    <br>
    <br>
    <p>
    <select name ="choosepicture" size="4">
    	<option>Bild 1</option>
    	<option>Bild 2</option>
    	<option>Bild 3</option>
    	<option>Bild 4</option>
    </select>
    </p>
    <input type="submit" value="Registrieren">
</form>
<jsp:include page="../../foot.jsp"/>
