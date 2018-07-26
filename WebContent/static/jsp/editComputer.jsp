<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right"> id :${computer.id }</div>
					<h1><spring:message code="dashboard.edit"/></h1>

					<form action="editComputer" method="POST">
						<input type="hidden" value="${computer.id}" id="id" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="dashboard.computername"/><span class="requis"></span></label> 
								<input type="text" class="form-control" id="name" name="name" value="${computer.name}" placeholder="name"> 
								<span class="erreur">${erreurs['name']}</span>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="dashboard.introduced"/></label> 
								<input	type="date" class="form-control" id="introduced" name= "introduced" value="${computer.introduced}" placeholder="Introduced date">
									<span class="erreur">${erreurs['introduced']}</span>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="dashboard.dicontinued"/></label> 
								<input type="date" class="form-control" id="discontinued" name="discontinued" value="${computer.discontinued}"
									placeholder="Discontinued date">
									<span class="erreur">${erreurs['discontinued']}</span>
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="dashboard.company"/></label> 
								<select
									class="form-control" id="companyId" name="companyId" >
									<option value=0 ><spring:message code="none"/></option>
									<c:forEach var="company" items="${companies}">
										<option value="${company.id}" >${company.name}</option>
									</c:forEach>
								</select>
								<span class="erreur">${erreurs['id_company']}</span>
							</div>
						</fieldset>
						<div class="actions pull-right">
						<input type="hidden" class="form-control" id="computerId" name="computerId" value="${computer.id }" placeholder="computerId" >
							<input type="submit"  value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default"><spring:message code="cancel"/></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="static/js/validation.js"></script>
</body>
</html>