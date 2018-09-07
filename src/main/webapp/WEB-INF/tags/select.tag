<%--
 * select.tag
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="appfn" uri="/WEB-INF/appfn.tld" %>

<%-- Attributes --%> 

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="itemLabel" required="false" %>
<%@ attribute name="itemCodePrefix" required="false" %>
<%@ attribute name="optional" required="false" %>
<%@ attribute name="readonly" required="false" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="onChange" required="false" %>

<c:if test="${id == null}">
	<c:set var="id" value="${UUID.randomUUID().toString()}" />
</c:if>

<c:if test="${onChange == null}">
	<c:set var="onChange" value="javascript: return true;" />
</c:if>

<%-- Definition --%>

<div>
	<form:label path="${path}">
		<spring:message code="${code}" />
	</form:label>
	<form:select id="${id}" path="${path}" onchange="${onChange}" disabled="${readonly != null and readonly == true}">
	    <c:if test="${optional != null && optional == true}">
		    <form:option value="0" label="----" />
		</c:if>
		<c:if test="${itemCodePrefix == null}">
		    <form:options items="${items}" itemValue="id" itemLabel="${itemLabel}" />
		</c:if>
		<c:if test="${itemCodePrefix != null}">
            <c:forEach var="item" items="${items}">
                <spring:message code="${itemCodePrefix}.${item}" var="label" />
                <form:option value="${item}" label="${label}" />
            </c:forEach>
        </c:if>
	</form:select>
	<form:errors path="${path}" cssClass="error" />
</div>


