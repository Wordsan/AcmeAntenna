<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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

<app:redir-button action="antennas/new.do?returnAction=${appfn:escapeUrlParam(currentRequestUriAndParams)}" code="misc.actions.new" />
<display:table name="antennas"
	       id="antenna"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="antennas/index.do">

    <display:column property="serialNumber" titleKey="antennas.serialNumber" escapeXml="true" sortable="true" href="antennas/show.do" paramId="id" paramProperty="id" />
    <display:column property="model" titleKey="antennas.model" escapeXml="true" sortable="true" />
    <display:column property="positionForDisplay" titleKey="antennas.position" escapeXml="true" />
    <display:column property="satellite.name" titleKey="antennas.satellite" escapeXml="true" sortable="true" href="satellites/show.do" paramId="id" paramProperty="satellite.id" />
    <display:column titleKey="misc.actions">
        <app:redir-button code="misc.actions.edit" action="antennas/edit.do?id=${antenna.id}&returnAction=${appfn:escapeUrlParam(currentRequestUriAndParams)}" />
        <app:delete-button code="misc.actions.delete" action="antennas/delete.do?id=${antenna.id}" />
    </display:column>

</display:table>