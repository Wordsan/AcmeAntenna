<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<p><spring:message code="maintenanceRequest.user"/>: <jstl:out value="${maintenanceRequest.user.name }"></jstl:out></p>
	<p><spring:message code="maintenanceRequest.creationTime"/>: <jstl:out value="${maintenanceRequest.creationTime }"></jstl:out></p>
	<p><spring:message code="maintenanceRequest.description"/>: <jstl:out value="${maintenanceRequest.description }"></jstl:out></p>
	<p><spring:message code="maintenanceRequest.antenna"/>: <jstl:out value="${maintenanceRequest.antenna.serialNumber }"></jstl:out></p>
</div>

<form:form action="maintenanceRequests/handyworker/service.do" modelAttribute="maintenanceRequest">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="user"/>
	<form:hidden path="creationTime"/>
	<form:hidden path="creditCard"/>
	<form:hidden path="handyworker"/>
	<form:hidden path="description"/>
	<form:hidden path="antenna"/>
	<form:hidden path="doneTime"/>
	
	<form:label path="resultsDescription">
		<spring:message code="maintenanceRequest.resultsDescription" />:
	</form:label>
	<form:textarea path="resultsDescription" />
	<form:errors cssClass="error" path="resultsDescription" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="maintenanceRequest.service" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="maintenanceRequest.cancel" />"
		onclick="javascript: relativeRedir('maintenanceRequests/handyworker/listServiced.do');" />
	<br />


</form:form>