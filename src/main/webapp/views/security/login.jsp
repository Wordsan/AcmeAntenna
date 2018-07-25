 <%--
 * login.jsp
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

<form:form action="j_spring_security_check" modelAttribute="credentials">

	<form:label path="username">
		<spring:message code="security.username" />
	</form:label>
	<form:input path="username" />	
	<form:errors class="error" path="username" />
	<br />

	<form:label path="password">
		<spring:message code="security.password" />
	</form:label>
	<form:password path="password" />	
	<form:errors class="error" path="password" />
	<br />
	
	<c:if test="${showError == true}">
		<div class="error">
			<spring:message code="security.login.failed" />
		</div>
	</c:if>
	
	<input type="submit" value="<spring:message code="security.login" />" />
	
</form:form>