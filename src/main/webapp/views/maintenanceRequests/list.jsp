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

<display:table name="maintenanceRequests" id="maintenanceRequest" requestURI="${currentRequestUri}" pagesize="${displayTagPageSize}" sort="list">
	<spring:message code="maintenanceRequest.creationTime" var="creationTimeHeader" />
	<display:column property="creationTime" title="${creationTimeHeader}" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" />

	<spring:message code="maintenanceRequest.creditCard" var="creditCardHeader" />
	<display:column property="creditCard" title="${creditCardHeader}" sortable="true" />


	<spring:message code="maintenanceRequest.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />

    <security:authorize access="hasRole('USER')">
        <display:column property="antenna.serialNumber" titleKey="maintenanceRequest.antenna" sortable="false" href="antennas/show.do" paramId="id" paramProperty="antenna.id" />
    </security:authorize>
    <security:authorize access="hasRole('HANDYWORKER')">
        <c:if test="${check}">
            <display:column property="antenna.serialNumber" titleKey="maintenanceRequest.antenna" sortable="false" href="antennas/show.do" paramId="id" paramProperty="antenna.id" />
        </c:if>
        <c:if test="${done}">
            <display:column property="antenna.serialNumber" titleKey="maintenanceRequest.antenna" sortable="false" />
        </c:if>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<spring:message code="maintenanceRequest.handyworker" var="handyworkerHeader" />
		<display:column property="handyworker.name" title="${handyworkerHeader}" sortable="true" />
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<spring:message code="maintenanceRequest.user" var="userHeader" />
		<display:column property="user.name" title="${userHeader}" sortable="true" />
	</security:authorize>
	
	<c:if test="${done }">
		<spring:message code="maintenanceRequest.doneTime" var="doneTimeHeader" />
		<display:column property="doneTime" title="${doneTimeHeader}" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" />
	
		<spring:message code="maintenanceRequest.resultsDescription" var="resultsDescriptionHeader" />
		<display:column property="resultsDescription" title="${resultsDescriptionHeader}" sortable="true" />
	</c:if>

	<security:authorize access="hasRole('HANDYWORKER')">
		<c:if test="${check }">
			<display:column titleKey="misc.actions">
				<app:redir-button action="maintenanceRequests/handyworker/service.do?maintenanceRequestId=${maintenanceRequest.id}" code="maintenanceRequest.service" />
			</display:column>
		</c:if>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<div>
		<app:redir-button action="maintenanceRequests/user/create.do" code="maintenanceRequest.create" />
	</div>
</security:authorize>