<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="3" class="displaytag" keepStatus="true"
	name="maintenanceRequests" requestURI="${requestURI}" id="row">
	
	
	<spring:message code="maintenanceRequest.creationTime" var="creationTimeHeader" />
	<display:column property="creationTime" title="${creationTimeHeader}" sortable="true" />

	<spring:message code="maintenanceRequest.creditCard" var="creditCardHeader" />
	<display:column property="creditCard" title="${creditCardHeader}" sortable="true" />


	<spring:message code="maintenanceRequest.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />

	<spring:message code="maintenanceRequest.antenna" var="antennaHeader" />
	<display:column property="antenna.serialNumber" title="${antennaHeader}" sortable="false" />
	
	<security:authorize access="hasRole('USER')">
		<spring:message code="maintenanceRequest.handyworker" var="handyworkerHeader" />
		<display:column property="handyworker.name" title="${handyworkerHeader}" sortable="true" />
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<spring:message code="maintenanceRequest.user" var="userHeader" />
		<display:column property="user.name" title="${userHeader}" sortable="true" />
	</security:authorize>
	
	<jstl:if test="${done }">
		<spring:message code="maintenanceRequest.doneTime" var="doneTimeHeader" />
		<display:column property="doneTime" title="${doneTimeHeader}" sortable="true" />
	
		<spring:message code="maintenanceRequest.resultsDescription" var="resultsDescriptionHeader" />
		<display:column property="resultsDescription" title="${resultsDescriptionHeader}" sortable="true" />
	</jstl:if>

	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${check }">
			<display:column>
				<a href="maintenanceRequests/handyworker/service.do?maintenanceRequestId=${row.id }">
					<spring:message code="maintenanceRequest.service"/>
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<div>
		<a href="maintenanceRequests/user/create.do">
			<spring:message code="maintenanceRequest.create"/>
		</a>
	</div>
</security:authorize>