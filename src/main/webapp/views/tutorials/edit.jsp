<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form action="tutorials/update.do" modelAttribute="tutorial">
    <app:model-editor />

    <form:hidden path="user" />
    <form:hidden path="lastUpdateTime" />

    <app:textbox path="title" code="tutorials.title" />
    <app:textarea path="text" code="tutorials.text" />
    <app:stringlist path="pictureUrls" items="${tutorial.pictureUrls}" code="tutorials.pictureUrls" />

    <div>
        <app:submit name="submit" code="misc.actions.update" />
        <app:cancel-button code="misc.actions.cancel" />
    </div>
</form:form>

