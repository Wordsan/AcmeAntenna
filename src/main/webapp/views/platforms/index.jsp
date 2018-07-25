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

<form:form method="GET" action="platforms/index.do" modelAttribute="searchForm">
    <app:textbox path="terms" code="misc.searchTerms" />
    <app:submit code="misc.actions.search" />
</form:form>

<security:authorize access="hasRole('ADMINISTRATOR')">
    <app:redir-button action="platforms/new.do?cancelAction=${appfn:escapeUrlParam(currentRelativeUrl)}" code="misc.actions.new" />
</security:authorize>

<div>
    <display:table name="platforms"
               id="platform"
               pagesize="${displayTagPageSize}"
               sort="list"
               requestURI="platforms/index.do">

        <display:column property="name" titleKey="platforms.name" escapeXml="true" sortable="true" href="platforms/show.do" paramId="id" paramProperty="id" />
        <display:column property="description" titleKey="platforms.description" escapeXml="true" sortable="true" />

        <security:authorize access="hasRole('ADMINISTRATOR')">
            <display:column titleKey="misc.actions">
                <app:redir-button code="misc.actions.edit" action="platforms/edit.do?id=${platform.id}&cancelAction=${appfn:escapeUrlParam(currentRelativeUrl)}" />
                <app:delete-button code="misc.actions.delete" action="platforms/delete.do?id=${platform.id}" />
            </display:column>
        </security:authorize>
    </display:table>
</div>