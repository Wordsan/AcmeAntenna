<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

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

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Antenna, Inc.</b>

<div class="footer-menu">
	<ul>
		<li>
			<a href="legal/about_us.do"><spring:message code="master.page.footermenu.aboutus" /></a>
		</li>
		<li>
			<a href="legal/terms.do"><spring:message code="master.page.footermenu.terms" /></a>
		</li>
		<li>
			<a href="legal/privacy.do"><spring:message code="master.page.footermenu.privacy" /></a>
		</li>
		<li>
			<a href="legal/cookies.do"><spring:message code="master.page.footermenu.cookies" /></a>
		</li>
	</ul>
</div>
<div id="cookie-notice" class="cookie-notice hidden">
	<div id="close-button" class="close-button">X</div>
	<div class="cookie-notice-title">
		<spring:message code="master.page.cookies.notice.title" />
	</div>
	<div class="cookie-notice-text">
		<spring:message code="master.page.cookies.notice.text" />
		<a href="legal/cookies.do"><spring:message code="master.page.cookies.textlink" /></a>
	</div>
	<div class="text-center button-container">
		<a id="cookie-notice-button" class="button button-success text-bold"><spring:message code="master.page.cookies.accept" /></a>
	</div>
</div>