<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="appfn" uri="/WEB-INF/appfn.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut icon" href="favicon.ico"/>

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jquery-redirect.js"></script>
<script type="text/javascript" src="scripts/Chart.bundle.js"></script>
<script type="text/javascript" src="scripts/pallete.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>
<script type="text/javascript" src="scripts/datepicker-${pageContext.response.locale}.js"></script>
<script type="text/javascript" src="scripts/stringlist.js"></script>
<script type="text/javascript" src="scripts/multiple-select.js"></script>
<script type="text/javascript" src="scripts/functions.js"></script>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jquery-ui.css" media="screen"
	type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen"
	type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/multiple-select.css" type="text/css">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
		$(".date-picker").datepicker();
		$("select[multiple=multiple]").multipleSelect();
	});
</script>

</head>

<body>

	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<c:if test="${globalErrorMessage != null}">
			<div class="alert alert-danger"><spring:message code="${globalErrorMessage}" /></div>
		</c:if>
		<c:if test="${globalSuccessMessage != null}">
            <div class="alert alert-success"><spring:message code="${globalSuccessMessage}" /></div>
        </c:if>
		<c:if test="${globalInfoMessage != null}">
            <div class="alert alert-info"><spring:message code="${globalInfoMessage}" /></div>
        </c:if>
		<c:if test="${globalWarningMessage != null}">
            <div class="alert alert-warning"><spring:message code="${globalWarningMessage}" /></div>
        </c:if>
		<h1>
			<tiles:insertAttribute name="title" />
		</h1>
		<tiles:insertAttribute name="body" />
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>

</body>
</html>
