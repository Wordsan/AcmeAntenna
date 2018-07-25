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
	<span class="label"><spring:message code="antennas.serialNumber" />: </span>
	<span class="content"><c:out value="${antenna.serialNumber}" /></span>
</div>

<div>
	<span class="label"><spring:message code="antennas.model" />: </span>
	<span class="content"><c:out value="${antenna.model}" /></span>
</div>

<div>
	<span class="label"><spring:message code="antennas.position" />: </span>
	<span class="content"><c:out value="${antenna.positionForDisplay}" /></span>
</div>

<div>
	<span class="label"><spring:message code="antennas.rotationAzimuth" />: </span>
	<span class="content"><c:out value="${antenna.rotationAzimuth}" /></span>
</div>

<div>
	<span class="label"><spring:message code="antennas.rotationElevation" />: </span>
	<span class="content"><c:out value="${antenna.rotationElevation}" /></span>
</div>

<div>
	<span class="label"><spring:message code="antennas.signalQuality" />: </span>
	<span class="content"><c:out value="${antenna.signalQuality}%" /></span>
</div>

<div>
	<span class="label"><spring:message code="antennas.satellite" />: </span>
	<span class="content"><a href="satellites/show.do?id=<c:out value="${antenna.satellite.id}" />"><c:out value="${antenna.satellite.name}" /></a></span>
</div>

<app:redir-button code="misc.actions.edit" action="antennas/edit.do?id=${antenna.id}&cancelAction=${appfn:escapeUrlParam(currentRelativeUrl)}" />
<app:delete-button code="misc.actions.delete" action="antennas/delete.do?id=${antenna.id}" />