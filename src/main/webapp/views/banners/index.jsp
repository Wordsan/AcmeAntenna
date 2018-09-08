<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
    <display:table name="banners" id="banner" requestURI="${currentRequestUri}" pagesize="${displayTagPageSize}" sort="list">
        <display:column property="targetPage" titleKey="banners.targetPage" sortable="true" />
        <display:column property="pictureUrl" titleKey="banners.picture" sortable="true" />
        <display:column property="creditCard" titleKey="banners.creditCard" sortable="true" />
        <security:authorize access="hasRole('ADMINISTRATOR')">
            <display:column property="agent.name" titleKey="banners.agent" sortable="false" />
        </security:authorize>

        <security:authorize access="hasRole('ADMINISTRATOR')">
            <display:column titleKey="misc.actions">
                <app:delete-button action="banners/delete.do?id=${banner.id}&returnAction=${appfn:escapeUrlParam(appfn:withoutDisplayTagParams(currentRequestUriAndParams, 'banner'))}" />
            </display:column>
        </security:authorize>
    </display:table>
</div>


<security:authorize access="hasRole('AGENT')">
	<div>
		<app:redir-button action="banners/new.do?returnAction=${appfn:escapeUrlParam(returnActionForHere)}" code="misc.actions.new" />
	</div>
</security:authorize>