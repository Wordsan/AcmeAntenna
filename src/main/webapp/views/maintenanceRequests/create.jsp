<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="maintenanceRequests/user/create.do" modelAttribute="maintenanceRequest">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="user"/>
	<form:hidden path="creationTime"/>
	<form:hidden path="doneTime"/>
	<form:hidden path="resultsDescription"/>
	
	
	<form:label path="description">
	<spring:message code="maintenanceRequest.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="creditCard">
		<spring:message code="maintenanceRequest.creditCard" />:
	</form:label>
	<form:input path="creditCard" />
	<form:errors cssClass="error" path="creditCard" />
	<br />

	<form:label path="handyworker">
		<spring:message code="maintenanceRequest.handyworker" />:
	</form:label>
	<form:select id="handyworkers" path="handyworker" >
		<form:option value="0" label="----" />		
		<form:options items="${handyworkers}" itemValue="id"
			itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="handyworker" />

	<form:label path="antenna">
		<spring:message code="maintenanceRequest.antenna" />:
	</form:label>
	<form:select id="antennas" path="antenna">
		<form:option value="0" label="----" />		
		<form:options items="${antennas}" itemValue="id" itemLabel="serialNumber" />
	</form:select>
	<form:errors cssClass="error" path="antenna" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="maintenanceRequest.make" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="maintenanceRequest.cancel" />"
		onclick="javascript: relativeRedir('maintenanceRequests/user/listNotServiced.do');" />
	<br />


</form:form>