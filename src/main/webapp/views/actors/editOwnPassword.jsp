<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form action="actors/updateOwnPassword.do" modelAttribute="form">
    <app:password path="oldPassword" code="actors.oldPassword" />
    <app:password path="newPassword" code="actors.newPassword" />
    <app:password path="repeatNewPassword" code="actors.repeatNewPassword" />

    <div>
        <app:submit name="submit" code="misc.actions.update" />
        <app:cancel-button code="misc.actions.cancel" />
    </div>
</form:form>