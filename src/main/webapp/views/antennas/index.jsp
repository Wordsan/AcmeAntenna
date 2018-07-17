<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<display:table name="antennas"
	       id="antenna"
	       pagesize="${displayTagPageSize}"
	       requestURI="antennas/index.do">

    <display:column property="serialNumber" titleKey="antennas.serialNumber" escapeXml="true" sortable="true" paramProperty="id" paramId="id" href="antennas/show.do" />
    <display:column property="model" titleKey="antennas.model" escapeXml="true" sortable="true" />
    <display:column property="positionForDisplay" titleKey="antennas.position" escapeXml="true" />
    <display:column property="satellite" titleKey="antennas.model" escapeXml="true" sortable="true" />

</display:table>