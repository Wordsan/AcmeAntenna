<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<app:redir-button action="antennas/new.do" code="misc.actions.new" />
<display:table name="antennas"
	       id="antenna"
	       pagesize="${displayTagPageSize}"
	       requestURI="antennas/index.do">

    <display:column property="serialNumber" titleKey="antennas.serialNumber" escapeXml="true" sortable="true" />
    <display:column property="model" titleKey="antennas.model" escapeXml="true" sortable="true" />
    <display:column property="positionForDisplay" titleKey="antennas.position" escapeXml="true" />
    <display:column property="satellite.name" titleKey="antennas.satellite" escapeXml="true" sortable="true" />
    <display:column titleKey="misc.actions">
        <app:redir-button code="misc.actions.edit" action="antennas/edit.do?id=${antenna.id}" />
        <app:delete-button code="misc.actions.delete" action="antennas/delete.do" id="${antenna.id}" />
    </display:column>

</display:table>