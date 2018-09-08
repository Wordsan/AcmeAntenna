<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="services.BannerService"%>
<%@page import="domain.Banner"%>

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
	<img src="images/logo.png" alt="Acme Antenna, Inc." />
</div>
<div>

<a href="${targetPage}" target="_blank"><img src="${image}" width="50%"/></a>
</div>
<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<li><a class="fNiv" href="welcome/index.do"><spring:message code="master.page.welcome" /></a></li>

        <security:authorize access="hasRole('USER')">
            <li>
                <a class="fNiv" href="antennas/index.do">
                    <spring:message code="master.page.antennas" />
                </a>
            </li>
        </security:authorize>

        <security:authorize access="hasRole('USER')">
        
       		 <li>
                <a class="fNiv">
                    <spring:message code="master.page.maintenanceRequests" />
                </a>
                <ul>
                    <li class="arrow"></li>
                    <li><a href="maintenanceRequests/user/listNotServiced.do"><spring:message code="master.page.maintenanceRequests.notServiced.list" /> </a></li>
                    <li><a href="maintenanceRequests/user/listServiced.do"><spring:message code="master.page.maintenanceRequests.serviced.list" /> </a></li>
                </ul>
            </li>
        
            <li>
                <a class="fNiv">
                    <spring:message code="master.page.platforms" />
                </a>
                <ul>
                    <li class="arrow"></li>
                    <li><a href="platforms/index.do"><spring:message code="master.page.platforms.list" /> </a></li>
                    <li><a href="platform_subscriptions/index.do"><spring:message code="master.page.platforms.my_subscriptions" /> </a></li>
                </ul>
            </li>
        </security:authorize>
        <security:authorize access="!hasRole('USER')">
            <li>
                <a class="fNiv" href="platforms/index.do">
                    <spring:message code="master.page.platforms" />
                </a>
            </li>
        </security:authorize>

		<security:authorize access="hasRole('HANDYWORKER')">
			<li>
                <a class="fNiv">
                    <spring:message code="master.page.maintenanceRequests" />
                </a>
                <ul>
                    <li class="arrow"></li>
                    <li><a href="maintenanceRequests/handyworker/listNotServiced.do"><spring:message code="master.page.maintenanceRequests.notServiced.list" /> </a></li>
                    <li><a href="maintenanceRequests/handyworker/listServiced.do"><spring:message code="master.page.maintenanceRequests.serviced.list" /> </a></li>
                </ul>
            </li>
		</security:authorize>

        <li>
            <a class="fNiv" href="satellites/index.do">
                <spring:message code="master.page.satellites" />
            </a>
        </li>

        <li>
            <a class="fNiv" href="tutorials/index.do">
                <spring:message code="master.page.tutorials" />
            </a>
        </li>

        <security:authorize access="hasRole('ADMINISTRATOR')">
        	<li><a class="fNiv" href="actors/index.do"><spring:message code="master.page.actors" /></a></li>
        	<li><a class="fNiv" href="banners/index.do"><spring:message code="master.page.banners" /></a></li>
            <li><a class="fNiv" href="administrators/dashboard.do"><spring:message code="master.page.administrator_dashboard" /></a></li>
        </security:authorize>

		<security:authorize access="hasRole('AGENT')">
			<li><a class="fNiv" href="banners/index.do"><spring:message code="master.page.banners" /></a></li>
		</security:authorize>

		<li><a class="fNiv" href="handyworkers/list.do"><spring:message code="master.page.handyworkers" /></a></li>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
            <li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="users/new.do"><spring:message code="master.page.register.user" /></a></li>
					<li><a href="handyworkers/new.do"><spring:message code="master.page.register.handyworker" /></a></li>
					<li><a href="agents/new.do"><spring:message code="master.page.register.agent" /></a></li>
				</ul>
			</li>
        </security:authorize>

		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv">
					<spring:message code="master.page.profile" />
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actors/edit_own_password.do?returnAction=${appfn:escapeUrlParam(returnActionForHere)}"><spring:message code="master.page.actor.edit_own_password" /> </a></li>
                    <li><a href="actors/edit.do?returnAction=${appfn:escapeUrlParam(returnActionForHere)}"><spring:message code="master.page.actor.edit" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

