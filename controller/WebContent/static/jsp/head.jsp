<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div>
		<a class="navbar-brand" href="dashboard"> <spring:message
				code="dashboard.subtitle" /></a> <a class="navbar-brand pull-right"
			href="?lang=<spring:message code="alter_language"/>"> <img
			src="static/image/flag_<spring:message code="alter_language"/>.png"
			style="height: 50dp; width: 50dp; margin: 15dp;"
			alt=<spring:message code="alter_language"/> /></a>
	</div>
	</header>

</body>
</html>