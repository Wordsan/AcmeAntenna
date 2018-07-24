<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form action="satellites/create.do" modelAttribute="satellite">
    <form:hidden path="deleted" />
    <app:textbox path="name" code="satellites.name" />
    <app:textarea path="description" code="satellites.description" />
    <app:select path="platforms" items="${platforms}" itemLabel="name" code="satellites.platforms" />

    <div>
        <app:submit name="submit" code="misc.actions.create" />
        <app:cancel-button code="misc.actions.cancel" />
    </div>
</form:form>

