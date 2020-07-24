<%@page import="java.util.List"%>
<%@ page import="fr.eni.javaee.suividesrepas.messages.Reader" %>
<%@ page import="fr.eni.javaee.suividesrepas.bo.Repas" %>
<%@ page import="fr.eni.javaee.suividesrepas.bo.Aliment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<link href="<%=request.getContextPath() %>/styles/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Consultation</title>
</head>
<body>
	<h1>HISTORIQUE</h1>
	<div class="contenu">
		<%
		String dateSelected="";
		if (request.getParameter("dateSelected") != null)
		{ dateSelected = request.getParameter("dateSelected"); }
		%>
		
		<form action="<%=request.getContextPath()%>/ServletDisplayRepas" method="post">
			<input type="date" name="dateSelected" value="<%=dateSelected%>"/>
			<button type="submit">Filtrer</button>
			<a href="<%=request.getContextPath()%>/ServletDisplayRepas"><button>Réinitialiser</button></a>
		</form>
	
		<%
			List<Integer> erreurs = (List<Integer>)request.getAttribute("erreurs");
			if (erreurs != null) {
		%>
				<p style="color:red;">Erreur :</p>
		<% for(int code : erreurs) { %>
					<p><%=Reader.getMessageErreur(code)%></p>
		<% }} %>
	
		<table align="center">
			<thead>
				<tr>
					<td>Date</td>
					<td>Heure</td>
					<td>Action</td>
				</tr>
			</thead>
				<%
					List<Repas> repas = (List<Repas>) request.getAttribute("repas");
					if (repas != null && !repas.isEmpty()) {
				%>
						<tbody>
							<% for(Repas chaqueRepas : repas) { %>
								<tr>
									<td><%=chaqueRepas.getDate()%></td>
									<td><%=chaqueRepas.getHeure()%></td>
									
									<td><a href="<%=request.getContextPath()%>/ServletDisplayRepas?detail=<%=chaqueRepas.getIdentifiant()%>&<%=dateSelected%>">détail</a></td>
								</tr>
							<% if(String.valueOf(chaqueRepas.getIdentifiant()).equals(request.getParameter("detail"))) { %>
									<tr>
										<td colspan="3">
											<ul>
												<% for(Aliment aliment : chaqueRepas.getAliments()) { %>
													<li><%=aliment.getNom()%></li>
												<% } %>
											</ul>
										</td>
									</tr>
							<% }} %>
						</tbody>
				<%} else { %>
					<p>Il n'y a aucun repas à afficher<P>
				<% } %>

		</table>
	
		<a href="<%=request.getContextPath()%>/ServletAddRepas"><input type="button" value="Ajouter un nouveau repas"/></a>
		<a href="<%=request.getContextPath()%>"><input type="button" value="Retour à l'accueil"/></a>
		
	</div>
	
</body>
</html>