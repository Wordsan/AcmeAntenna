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

<form:form action="maintenanceRequests/user/create.do" modelAttribute="maintenanceRequest">
    <app:preserve-return-action />
	<app:entity-editor />

	<form:hidden path="user"/>
	<form:hidden path="creationTime"/>
	<form:hidden path="doneTime"/>
	<form:hidden path="resultsDescription"/>
	
	<app:textbox path="description" code="maintenanceRequest.description" />
	<app:credit-card-editor path="creditCard" />
	<app:select path="handyworker" items="${handyworkers}" itemLabel="name" code="maintenanceRequest.handyworker" />
	<app:select path="antenna" items="${antennas}" itemLabel="serialNumber" code="maintenanceRequest.antenna" />

	<app:submit name="save" code="maintenanceRequest.make" />
	<app:cancel-button action="maintenanceRequests/user/listNotServiced.do" />
</form:form>