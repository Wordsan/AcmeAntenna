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

<form:form action="handyworkers/create.do" modelAttribute="form">
    <app:textbox path="handyworker.userAccount.username" code="actors.username" />
    <app:password path="handyworker.userAccount.password" code="actors.password" />
    <app:password path="repeatPassword" code="actors.repeatPassword" />

    <br/>

    <app:actor-editor path="handyworker" />

    <app:checkbox path="agreesToTerms" code="users.iAgreeToTerms" />

    <div>
        <app:submit name="submit" code="misc.actions.create" />
        <app:cancel-button code="misc.actions.cancel" />
    </div>
</form:form>

