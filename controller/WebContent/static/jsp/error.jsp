<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div>
		<a class="navbar-brand" href="dashboard"> <spring:message code="dashboard.subtitle" /></a>
			  <a class="navbar-brand pull-right" href="?lang=<spring:message code="alter_language"/>">
			 <img src="static/image/flag_<spring:message code="alter_language"/>.png"
			style="height:50dp;width:50dp;margin:15dp;" alt=<spring:message code="alter_language"/> /></a>
</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				<spring:message code="error"/> <br />
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>

</body>
</html>