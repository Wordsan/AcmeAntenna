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
	<span class="label"><spring:message code="platforms.name" />: </span>
	<span class="content"><c:out value="${platform.name}" /></span>
</div>

<div>
	<span class="label"><spring:message code="platforms.description" />: </span>
	<span class="content multiline"><c:out value="${platform.description}" /></span>
</div>

<c:if test="${not platform.deleted}">
<security:authorize access="hasRole('ADMINISTRATOR')">
    <app:redir-button code="misc.actions.edit" action="platforms/edit.do?id=${platform.id}&cancelAction=${appfn:escapeUrlParam(currentRelativeUrl)}" />
    <app:delete-button code="misc.actions.delete" action="platforms/delete.do?id=${platform.id}" />
</security:authorize>
</c:if>

<h3><spring:message code="platforms.satellites" /></h3>

<div>
<display:table name="platform.satellites"
	       id="satellite"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="platforms/show.do?id=${platform.id}">

    <display:column property="name" titleKey="satellites.name" escapeXml="true" sortable="true" href="satellites/show.do" paramId="id" paramProperty="id" />
    <display:column property="description" titleKey="satellites.description" escapeXml="true" sortable="true" />
</display:table>
</div>

<security:authorize access="hasRole('USER')">
    <br/>
    <app:redir-button code="platforms.subscribe" action="platform_subscriptions/new.do?platformId=${platform.id}&cancelAction=${appfn:escapeUrlParam(currentRelativeUrl)}" />
    <h3><spring:message code="platforms.platform_subscription.history" /></h3>

    <display:table name="platformSubscriptions"
               id="platformSubscription"
               pagesize="${displayTagPageSize}"
	           sort="list"
               requestURI="platforms/show.do?id=${platform.id}">

        <display:column property="startDate" titleKey="platform_subscriptions.startDate" sortable="true" format="{0,date,dd/MM/yyyy}" />
        <display:column property="endDate" titleKey="platform_subscriptions.endDate" sortable="true" format="{0,date,dd/MM/yyyy}" />
        <display:column property="obscuredCreditCard" titleKey="platform_subscriptions.creditCard" escapeXml="true" sortable="true" />
        <display:column property="keyCode" titleKey="platform_subscriptions.keyCode" escapeXml="true" sortable="true" />
    </display:table>
</security:authorize>