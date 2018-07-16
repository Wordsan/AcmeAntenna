<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form action="antennas/create.do" modelAttribute="form">
    <form:hidden path="user" />
    <app:textbox path="serialNumber" code="antennas.serialNumber" />
    <app:textbox path="model" code="antennas.model" />
    <app:textbox path="model" code="antennas.model" />

    <div>
        <app:submit name="submit" code="misc.actions.create" />
        <app:cancel code="misc.actions.cancel" />
    </div>

    <c:if test="${message == ''}">
        <span class="error"><spring:message code="${message}" /></span>
    </c:if>
</form:form>

