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
	<img src="${banner.pictureUrl }"/>
</div>

<div>
	<p><spring:message code="banners.agent"/>: <jstl:out value="${banner.agent.name }"></jstl:out></p>
	<p><spring:message code="banners.targetPage"/>: <jstl:out value="${banner.targetPage }"></jstl:out></p>
	<p><spring:message code="banners.creditCard"/>: <jstl:out value="${banner.creditCard }"></jstl:out></p>
</div>

<form:form action="banners/delete.do" modelAttribute="banner">

	<form:hidden path="id" />
	<form:hidden path="version" />
	 
	<input type="submit" name="delete"
			value="<spring:message code="banners.delete" />"
			onclick="return confirm('<spring:message code="banners.confirm.delete" />')" />&nbsp;
			
	<input type="button" name="cancel"
		value="<spring:message code="banners.cancel" />"
		onclick="javascript: relativeRedir('banners/list.do');" />
	<br />


</form:form>