<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<display:table name="tutorials"
	       id="tutorial"
	       pagesize="${displayTagPageSize}"
	       requestURI="tutorials/index.do">

    <display:column property="title" titleKey="tutorials.title" escapeXml="true" sortable="true" href="tutorials/show.do" paramId="id" paramProperty="id" />
    <display:column property="lastUpdateTime" titleKey="tutorials.lastUpdateTime" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" />
    <display:column property="user.fullName" titleKey="tutorials.user" escapeXml="true" sortable="true" />
</display:table>