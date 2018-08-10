<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
<title><spring:message code="dashboard.title" /></title>
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
		<%-- <div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message code="dashboard.subtitle" /></a>
		</div> --%>
		<div>
		<a class="navbar-brand" href="dashboard"> <spring:message code="dashboard.subtitle" /></a>
			  <a class="navbar-brand pull-right" href="?lang=<spring:message code="alter_language"/>">
			 <img src="static/image/flag_<spring:message code="alter_language"/>.png"
			style="height:50dp;width:50dp;margin:15dp;" alt=<spring:message code="alter_language"/> /></a>
</div>
	</header>
	

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${nbComputers} <spring:message code="dashboard.nbcomputer" /></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						 <input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" value = "${seach}"/> <a id="search" href="dashboard"><input
							type="submit" id="searchsubmit"  value="Filter by name"
							class="btn btn-primary" />
							</a>
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="dashboard.add" />
						<spring:message code="dashboard.computer" /></a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="delete"   method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
		
						<th class="editMode" style="width: 60px; height: 22px;">
						<input type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="delete"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="dashboard.computername" /></th>
						<th><spring:message code="dashboard.introduced" /></th>
						<th><spring:message code="dashboard.discontinued" /></th>
						<th><spring:message code="dashboard.company" /></th>
						<th class="editMode" style="width: 60px; height: 22px;"> delete company </th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">

					<c:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="editComputer?computerId=${computer.id}" onclick="">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.company.name} </td>
							<td class="editMode" style="width: 60px; height: 22px;"><span
							style="vertical-align: top;"><a href="delete"
								id="deleteSelectedCompany" onclick="$.fn.deleteSelectedCompany();"> <i 
									class="fa fa-trash-o fa-lg"><input type="hidden" name="cbcompany"
								class="cbcompany" value="${computer.company.id}"></i></a></span></td>
						</tr>
					</c:forEach>



				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:choose>
					<c:when test="${numeropage >= 3 }">
						<li><a href="dashboard?page=${numeropage -1 }"
							aria-label="Previous"> <spanaria-hidden="true">&laquo;</span></a></li>
						<li><a href="dashboard?page=${numeropage -2 }"> ${numeropage -1 }</a></li>
						<li><a href="dashboard?page=${numeropage -1 }">${numeropage  }</a></li>
						<li><a href="dashboard?page=${numeropage    }">${numeropage +1   }</a></li>
						<li><a href="dashboard?page=${numeropage +1 }">${numeropage +2 }</a></li>
						<li><a href="dashboard?page=${numeropage +2 }">${numeropage +3 }</a></li>
						<li><a href="dashboard?page=${numeropage +1 }"
						 aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>

					</c:when>
					<c:otherwise>
					<li><a href="dashboard?page=${numeropage -1 }"
						aria-label="Previous"> <spanaria-hidden="true">&laquo;</span></a></li>
					<li><a href="dashboard?page=0">1</a></li>
					<li><a href="dashboard?page=1">2</a></li>
					<li><a href="dashboard?page=2">3</a></li>
					<li><a href="dashboard?page=3">4</a></li>
					<li><a href="dashboard?page=4">5</a></li>
					<li><a href="dashboard?page=${numeropage +1 }" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
						</c:otherwise>
				</c:choose>
			</ul >
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a  href="dashboard?perpage=10" > <button type="button" onclick="dashboard?perpage=50" class="btn btn-default">10</button></a>
				<a  href="dashboard?perpage=50" > <button type="button" onclick="dashboard?perpage=50" class="btn btn-default">50</button></a>
				<a  href="dashboard?perpage=100" ><button type="button" onclick="dashboard?perpage=100" class="btn btn-default">100</button> </a> 
			</div>
	</footer>

	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>
</body>
</html>