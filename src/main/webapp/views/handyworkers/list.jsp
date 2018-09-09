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

<display:table name="handyworkers" id="handyworker" requestURI="${currentRequestUri}" pagesize="${displayTagPageSize}" sort="list">
	<display:column property="name" titleKey="handyworker.name" sortable="true" escapeXml="true" />
	<display:column property="surname" titleKey="handyworker.surname" sortable="true" escapeXml="true" />
	<display:column property="email" titleKey="handyworker.email" sortable="true" escapeXml="true" />
	<display:column property="phoneNumber" titleKey="handyworker.phoneNumber" sortable="true" escapeXml="true" />
</display:table>