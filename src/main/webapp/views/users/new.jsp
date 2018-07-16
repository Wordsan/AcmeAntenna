<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form action="users/create.do" modelAttribute="form">
    <form:hidden path="user.userAccount.authorities[0]" />
    <app:textbox path="user.userAccount.username" code="actors.username" />
    <app:password path="user.userAccount.password" code="actors.password" />
    <app:password path="repeatPassword" code="actors.repeatPassword" />

    <br/>

    <app:actor-editor path="user" />

    <div>
        <app:submit name="submit" code="misc.actions.create" />
        <app:cancel code="misc.actions.cancel" />
    </div>

    <c:if test="${message == ''}">
        <span class="error"><spring:message code="${message}" /></span>
    </c:if>
</form:form>

