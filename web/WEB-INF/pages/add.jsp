<%@ page import="java.util.List" %>
<%@ page import="fr.eni.javaee.suividesrepas.messages.Reader" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<link href="<%=request.getContextPath() %>/styles/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajout</title>
</head>
<body>
	<h1>AJOUT</h1>

		<%
			List<Integer> erreurs = (List<Integer>)request.getAttribute("erreurs");
			if(erreurs != null) {
		%>
				<p style="color:red;">Erreur, le repas n'a pas pu être ajouté :</p>
		<% for(int code : erreurs) { %>
					<p><%=Reader.getMessageErreur(code)%></p>
		<% }} %>
	
		<form action="<%=request.getContextPath()%>/ServletAddRepas" method="post">
			<div class="saisie">
				<label for="date">Date : </label>
				<br>
				<input type="date" name="date" id="date" value="<%=request.getParameter("date")%>"/>
			</div>
			<div class="saisie">
				<label for="heure">Heure : </label>
				<br>
				<input id="heure" type="time" name="heure" value="<%=request.getParameter("heure")%>"/>
			</div>
			<div class="saisie">
				<label for="aliments">Aliments : </label>
				<br>
				<textarea rows="5" cols="30" id="aliments" name="aliments" ><%=request.getParameter("aliments") != null ? request.getParameter("aliments") : ""%></textarea>
				<p><i>Les aliments doivent être séparés par une virgule.</i></p>
			</div>
			
			<div>
				<button type="submit">Valider</button>
			</div>
		</form>
	<a href="<%=request.getContextPath()%>/index.jsp"><button>Annuler</button></a>
</body>
</html>