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

<form:form action="${formAction}" modelAttribute="tutorial">
    <app:model-editor />

    <form:hidden path="user" />
    <form:hidden path="lastUpdateTime" />

    <app:textbox path="title" code="tutorials.title" />
    <app:textarea path="text" code="tutorials.text" />
    <app:stringlist path="pictureUrls" items="${tutorial.pictureUrls}" code="tutorials.pictureUrls" />

    <div>
        <app:submit entity="${tutorial}" />
        <app:cancel-button />
    </div>
</form:form>

