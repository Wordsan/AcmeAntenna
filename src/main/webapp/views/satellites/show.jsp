<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<div>
	<span class="label"><spring:message code="satellites.name" />: </span>
	<span class="content"><c:out value="${satellite.name}" /></span>
</div>

<div>
	<span class="label"><spring:message code="satellites.description" />: </span>
	<span class="content"><c:out value="${satellite.description}" /></span>
</div>

<h3><spring:message code="satellites.platforms" /></h3>

<display:table name="platforms"
	       id="platform"
	       pagesize="${displayTagPageSize}"
	       requestURI="satellites/show.do?id=${platform.id}">

    <display:column property="name" titleKey="platforms.name" escapeXml="true" sortable="true" href="platforms/show.do" paramId="id" paramProperty="id" />
    <display:column property="description" titleKey="platforms.description" escapeXml="true" sortable="true" />
</display:table>