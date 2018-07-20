<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form method="GET" action="platforms/index.do" modelAttribute="searchForm">
    <app:textbox path="terms" code="misc.searchTerms" />
    <app:submit code="misc.actions.search" />
</form:form>

<display:table name="platforms"
	       id="platform"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="platforms/index.do">

    <display:column property="name" titleKey="platforms.name" escapeXml="true" sortable="true" href="platforms/show.do" paramId="id" paramProperty="id" />
    <display:column property="description" titleKey="platforms.description" escapeXml="true" sortable="true" />
</display:table>