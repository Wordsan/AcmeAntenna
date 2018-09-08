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

<div>
	<p><spring:message code="maintenanceRequest.user"/>: <c:out value="${maintenanceRequest.user.name }" /></p>
	<p><spring:message code="maintenanceRequest.creationTime"/>: <c:out value="${maintenanceRequest.creationTime }" /></p>
	<p><spring:message code="maintenanceRequest.description"/>: <c:out value="${maintenanceRequest.description }" /></p>
	<p><spring:message code="maintenanceRequest.antenna"/>: <c:out value="${maintenanceRequest.antenna.serialNumber }" /></p>
</div>

<form:form action="maintenanceRequests/handyworker/service.do" modelAttribute="maintenanceRequest">
    <app:preserve-return-action />
    <app:entity-editor />

	<form:hidden path="user"/>
	<form:hidden path="creationTime"/>
	<form:hidden path="creditCard"/>
	<form:hidden path="handyworker"/>
	<form:hidden path="description"/>
	<form:hidden path="antenna"/>
	<form:hidden path="doneTime"/>

	<app:textarea path="resultsDescription" code="maintenanceRequest.resultsDescription" />

	<app:submit name="save" code="maintenanceRequest.service" />
	<app:cancel-button />
</form:form>