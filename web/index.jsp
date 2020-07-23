<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<link href="<%=request.getContextPath() %>/styles/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil</title>
</head>
<body>
	<h1>ACCUEIL</h1>
	<a href="<%=request.getContextPath()%>/ServletAddRepas"><button>Ajouter un nouveau repas</button></a>
	<a href="<%=request.getContextPath()%>/ServletDisplayRepas"><button>Visualiser les repas</button></a>
</body>
</html>