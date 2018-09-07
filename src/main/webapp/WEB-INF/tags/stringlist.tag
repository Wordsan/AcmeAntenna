<%@tag language="java" body-content="empty" %>

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

<%@attribute name="path" required="true" %>
<%@attribute name="items" type="java.util.List" required="true" %>
<%@attribute name="code" required="true" %>

<div class="stringlist">
	<form:label path="${path}">
		<spring:message code="${code}" />
	</form:label>

	<input type="hidden" name="<c:out value="${path}" />" value="" />

    <div class="stringlist-items">
	<spring:bind path="${path}">
        <c:forEach var="item" items="${items}" varStatus="loop">
            <div class="stringlist-item">
                <input type="text" name="<c:out value="${path}[${loop.index}]" />" value="<c:out value="${item != null ? item : ''}" />" />
                <button type="button" onclick="javascript: stringlist_delete_item(this);">-</button>
            </div>
        </c:forEach>
    </spring:bind>
    </div>

    <div class="stringlist-item stringlist-dummy-item">
        <input type="text" name="${path}" value="" disabled="true" />
        <button type="button" onclick="javascript: stringlist_add_item(this);">+</button>
    </div>
	<form:errors path="${path}" cssClass="error" />
</div>
