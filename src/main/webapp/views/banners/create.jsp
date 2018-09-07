<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="banners/create.do" modelAttribute="banner">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="agent"/>
	

	<form:label path="pictureUrl">
		<spring:message code="banners.picture" />:
	</form:label>
	<form:input path="pictureUrl" />
	<form:errors cssClass="error" path="pictureUrl" />
	<br />
	
	<form:label path="targetPage">
		<spring:message code="banners.targetPage" />:
	</form:label>
	<form:input path="targetPage" />
	<form:errors cssClass="error" path="targetPage" />
	<br />
	
	<form:label path="creditCard">
		<spring:message code="banners.creditCard" />:
	</form:label>
	<form:input path="creditCard" />
	<form:errors cssClass="error" path="creditCard" />
	<br />
	
	

	<input type="submit" name="save"
		value="<spring:message code="banners.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="banners.cancel" />"
		onclick="javascript: relativeRedir('banners/list.do');" />
	<br />


</form:form>