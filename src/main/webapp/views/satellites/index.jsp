<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form method="GET" action="satellites/index.do" modelAttribute="searchForm">
    <app:textbox path="terms" code="misc.searchTerms" />
    <app:submit code="misc.actions.search" />
</form:form>

<security:authorize access="hasRole('ADMINISTRATOR')">
    <app:redir-button action="satellites/new.do" code="misc.actions.new" />
</security:authorize>

<display:table name="satellites"
	       id="satellite"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="satellites/index.do">

    <display:column property="name" titleKey="satellites.name" escapeXml="true" sortable="true" href="satellites/show.do" paramId="id" paramProperty="id" />
    <display:column property="description" titleKey="satellites.description" escapeXml="true" sortable="true" />
    <security:authorize access="hasRole('ADMINISTRATOR')">
        <display:column titleKey="misc.actions">
            <app:redir-button code="misc.actions.edit" action="satellites/edit.do?id=${satellite.id}" />
            <app:delete-button code="misc.actions.delete" action="satellites/delete.do" id="${satellite.id}" />
        </display:column>
    </security:authorize>
</display:table>