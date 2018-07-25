<%@tag language="java" body-content="empty" %>

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

<%@ attribute name="path" required="false" %>

<c:if test="${path != null}">
	<c:set var="path" value="${path}." />
</c:if>
<c:if test="${path == null}">
	<c:set var="path" value="" />
</c:if>

<app:textbox path="${path}name" code="actors.name" />
<app:textbox path="${path}surname" code="actors.surname" />
<app:textbox path="${path}email" code="actors.email" />
<app:textbox path="${path}phoneNumber" code="actors.phoneNumber" />
<app:textbox path="${path}postalAddress" code="actors.postalAddress" />
<app:textbox path="${path}pictureUrl" code="actors.pictureUrl" />