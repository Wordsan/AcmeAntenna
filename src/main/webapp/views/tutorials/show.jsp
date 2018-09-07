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

<div>
	<span class="label"><spring:message code="tutorials.title" />: </span>
	<span class="content"><c:out value="${tutorial.title}" /></span>
</div>

<div>
	<span class="label"><spring:message code="tutorials.lastUpdateTime" />: </span>
	<span class="content"><c:out value="${tutorial.lastUpdateTime}" /></span>
</div>

<div>
	<span class="label"><spring:message code="tutorials.user" />: </span>
	<span class="content"><c:out value="${tutorial.user.fullName}" /></span>
</div>

<br/>

<div class="multiline"><c:out value="${tutorial.text}" /></div>

<c:if test="${!empty tutorial.pictureUrls}">
    <br/>

    <div class="tutorial-pictures">
        <c:forEach items="${tutorial.pictureUrls}" var="pictureUrl">
            <a href="<c:out value="${pictureUrl}" />"><img class="tutorial-picture" src="<c:out value="${pictureUrl}" />" /></a>
        </c:forEach>
    </div>
</c:if>

<div>
    <br/>

    <c:if test="${principal == tutorial.user}">
        <app:redir-button code="misc.actions.edit" action="tutorials/edit.do?id=${tutorial.id}&returnAction=${appfn:escapeUrlParam(returnActionForHere)}" />
    </c:if>
    <security:authorize access="hasRole('ADMINISTRATOR')">
        <app:delete-button code="misc.actions.delete" action="tutorials/delete.do?id=${tutorial.id}&returnAction=tutorials/index.do" />
    </security:authorize>
</div>

<security:authorize access="hasRole('USER')">
    <br/>

    <h3><spring:message code="tutorials.postAComment" /></h3>

    <spring:message code="tutorial_comments.postConfirm" htmlEscape="false" javaScriptEscape="true" var="confirmMessage" />
    <form:form action="tutorials/create_comment.do" modelAttribute="tutorialComment" onsubmit="return confirm('${confirmMessage}')">
        <form:hidden path="user" />
        <form:hidden path="tutorial" />
        <app:textbox path="title" code="tutorial_comments.title" />
        <app:textarea path="text" code="tutorial_comments.text" />
        <form:hidden path="creationTime" />
        <app:stringlist path="pictureUrls" items="${tutorialComment.pictureUrls}" code="tutorial_comments.pictureUrls" />
        <app:submit code="misc.actions.submit" />
    </form:form>

</security:authorize>
<security:authorize access="hasRole('USER') || hasRole('ADMINISTRATOR')">
    <br/>

    <h3><spring:message code="tutorials.comments" /></h3>

    <c:if test="${!empty comments.content}">
        <c:forEach items="${comments.content}" var="comment">
            <div class="tutorial-comment-outer">
                <div class="tutorial-comment-inner">
                    <div class="tutorial-comment-header">
                        <div class="tutorial-comment-title">
                            <c:out value="${comment.title}" />
                        </div>
                        <div class="tutorial-comment-meta">
                            <span class="tutorial-comment-author">
                                <c:out value="${comment.user.fullName}" />
                            </span>
                            <span class="tutorial-comment-creationTime">
                                <fmt:formatDate value="${comment.creationTime}" pattern="dd/MM/yyyy HH:mm:ss" />
                            </span>
                        </div>
                    </div>
                    <div class="tutorial-comment-body multiline"><c:out value="${comment.text}" /></div>
                    <c:if test="${!empty comment.pictureUrls}">
                        <div class="tutorial-comment-pictures">
                            <c:forEach items="${comment.pictureUrls}" var="pictureUrl">
                                <a href="<c:out value="${pictureUrl}" />"><img class="tutorial-comment-picture" src="<c:out value="${pictureUrl}" />" /></a>
                            </c:forEach>
                        </div>
                    </c:if>
                    <security:authorize access="hasRole('ADMINISTRATOR')">
                        <app:delete-button code="misc.actions.delete" action="tutorials/delete_comment.do?id=${comment.id}" />
                    </security:authorize>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty comments.content}">
        <div><spring:message code="misc.nothingFoundToDisplay" /></div>
    </c:if>
    <c:if test="${!empty comments.content || comments.number != 0}">
        <app:paginator page="${comments}" />
    </c:if>
</security:authorize>