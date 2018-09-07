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
	<span class="label"><spring:message code="satellites.name" />: </span>
	<span class="content"><c:out value="${satellite.name}" /></span>
</div>

<div>
	<span class="label"><spring:message code="satellites.description" />: </span>
	<span class="content multiline"><c:out value="${satellite.description}" /></span>
</div>

<c:if test="${not satellite.deleted}">
<security:authorize access="hasRole('ADMINISTRATOR')">
    <app:redir-button code="misc.actions.edit" action="satellites/edit.do?id=${satellite.id}&returnAction=${appfn:escapeUrlParam(returnActionForHere)}" />
    <app:delete-button code="misc.actions.delete" action="satellites/delete.do?id=${satellite.id}&returnAction=satellites/index.do" />
</security:authorize>
</c:if>

<h3><spring:message code="satellites.platforms" /></h3>

<display:table name="platforms"
	       id="platform"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="satellites/show.do?id=${platform.id}">

    <display:column property="name" titleKey="platforms.name" escapeXml="true" sortable="true" href="platforms/show.do" paramId="id" paramProperty="id" />
    <display:column property="description" titleKey="platforms.description" escapeXml="true" sortable="true" />
</display:table>