<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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

<display:table name="actors" id="actor" requestURI="${currentRequestUri}" pagesize="${displayTagPageSize}" sort="list" >
	<display:column property="name" titleKey="actors.name" sortable="true" escapeXml="true" />
	<display:column property="surname" titleKey="actors.surname" sortable="true" escapeXml="true" />
	<display:column property="email" titleKey="actors.email" sortable="true" escapeXml="true" />
	<display:column property="phoneNumber" titleKey="actors.phoneNumber" sortable="true" escapeXml="true" />

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column titleKey="misc.actions">
			<c:choose>
				<c:when test="${not actor.banned}">
					<app:post-button action="actors/set_banned.do?id=${actor.id}&banned=1&returnAction=${appfn:escapeUrlParam(returnActionForHere)}" code="actors.ban" confirmCode="misc.areYouSure" />
				</c:when>
				<c:otherwise>
					<app:post-button action="actors/set_banned.do?id=${actor.id}&banned=0&returnAction=${appfn:escapeUrlParam(returnActionForHere)}" code="actors.unban" confirmCode="misc.areYouSure" />
				</c:otherwise>
			</c:choose>
		</display:column>
	</security:authorize>

</display:table>