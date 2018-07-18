<%--
 * cancel.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%>

<%@ attribute name="code" required="true" %>
<%@ attribute name="action" required="true" %>
<%@ attribute name="id" required="true" %>

<%-- Definition --%>

<button type="button" onclick='if (confirm("<spring:message code="misc.actions.confirmDelete" />")) { $.redirect("<spring:escapeBody htmlEscape="false" javaScriptEscape="true">${action}</spring:escapeBody>", { id: "<spring:escapeBody htmlEscape="false" javaScriptEscape="true">${id}</spring:escapeBody>" }, "POST") }' >
    <spring:message code="${code}" />
</button>
