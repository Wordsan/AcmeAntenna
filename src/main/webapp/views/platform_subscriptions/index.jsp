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

<app:redir-button action="platform_subscriptions/new.do?returnAction=${appfn:escapeUrlParam(returnActionForHere)}" code="misc.actions.new" />

<display:table name="platformSubscriptions"
	       id="platformSubscription"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="platform_subscriptions/index.do">

    <display:column property="platform.name" titleKey="platform_subscriptions.platform" escapeXml="true" sortable="true" href="platforms/show.do" paramId="id" paramProperty="platform.id" />
    <display:column property="startDate" titleKey="platform_subscriptions.startDate" sortable="true" format="{0,date,dd/MM/yyyy}" />
    <display:column property="endDate" titleKey="platform_subscriptions.endDate" sortable="true" format="{0,date,dd/MM/yyyy}" />
    <display:column property="creditCard.obscuredNumber" titleKey="platform_subscriptions.creditCard" escapeXml="true" sortable="true" />
    <display:column property="keyCode" titleKey="platform_subscriptions.keyCode" escapeXml="true" sortable="true" />
</display:table>