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

<form:form action="${formAction}" modelAttribute="platformSubscription">
    <app:preserve-return-action />
    <app:entity-editor />
    <form:hidden path="user" />
    <form:hidden path="keyCode" />
    <app:select path="platform" items="${platforms}" itemLabel="name" code="platform_subscriptions.platform" />
    <app:datepicker path="startDate" code="platform_subscriptions.startDate" />
    <app:datepicker path="endDate" code="platform_subscriptions.endDate" />
    <app:textbox path="creditCard" code="platform_subscriptions.creditCard" />

    <div>
        <app:submit entity="${platformSubscription}" />
        <app:cancel-button />
    </div>
</form:form>

