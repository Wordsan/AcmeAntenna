<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="banners" requestURI="${requestURI}" id="row">
	
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="banners/delete.do?bannerId=${row.id}">
				<spring:message	code="banners.delete" />
			</a>
		</display:column>		
	</security:authorize>
	
	<spring:message code="banners.targetPage" var="targetPageHeader" />
	<display:column property="targetPage" title="${targetPageHeader}" sortable="true" />

	<spring:message code="banners.creditCard" var="creditCardHeader" />
	<display:column property="creditCard" title="${creditCardHeader}" sortable="true" />

	<spring:message code="banners.agent" var="agentHeader" />
	<display:column property="agent.name" title="${agentHeader}" sortable="false" />
	

</display:table>


<security:authorize access="hasRole('AGENT')">
	<div>
		<a href="banners/create.do"> <spring:message
				code="banners.create" />
		</a>
	</div>
</security:authorize>