<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form action="platform_subscriptions/create.do" modelAttribute="platformSubscription">
    <form:hidden path="user" />
    <form:hidden path="keyCode" />
    <app:select path="platform" items="${platforms}" itemLabel="name" code="platform_subscriptions.platform" />
    <app:datepicker path="startDate" code="platform_subscriptions.startDate" />
    <app:datepicker path="endDate" code="platform_subscriptions.endDate" />
    <app:textbox path="creditCard" code="platform_subscriptions.creditCard" />

    <div>
        <app:submit name="submit" code="misc.actions.create" />
        <app:cancel-button code="misc.actions.cancel" />
    </div>

    <c:if test="${message == ''}">
        <span class="error"><spring:message code="${message}" /></span>
    </c:if>
</form:form>

