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

<%@attribute name="page" type="org.springframework.data.domain.Page" required="true" %>
<%@attribute name="paramName" required="false" %>

<c:if test="${paramName == null}">
    <c:set var="paramName" value="page" />
</c:if>

<nav aria-label="...">
    <ul class="pagination">

        <c:if test="${page.number == 0}" >
            <li class="page-item disabled">
                <a class="page-link" href="#" onclick="return false" tabindex="-1"><spring:message code="misc.previous" /></a>
            </li>
        </c:if>
        <c:if test="${page.number > 0}" >
            <li class="page-item">
                <a class="page-link" href="<c:out value="${appfn:withUrlParam(currentRequestUriAndParams, paramName, page.number - 1)}" />"><spring:message code="misc.previous" /></a>
            </li>
        </c:if>

        <c:forEach var="i" begin="0" end="10">
            <c:set var="offset" value="${i - 5}" />
            <c:if test="${page.number + offset >= 0 && page.number + offset < page.totalPages}">
                <c:if test="${offset == 0}">
                    <li class="page-item active"><a class="page-link" href="#" onclick="return false"><c:out value="${page.number + 1}" /> <span class="sr-only">(current)</span></a></li>
                </c:if>
                <c:if test="${offset != 0}">
                    <li class="page-item"><a class="page-link" href="<c:out value="${appfn:withUrlParam(currentRequestUriAndParams, paramName, page.number + offset)}" />"><c:out value="${page.number + offset + 1}" /></a></li>
                </c:if>
            </c:if>
        </c:forEach>

        <c:if test="${page.number + 1 < page.totalPages}" >
            <li class="page-item">
                <a class="page-link" href="<c:out value="${appfn:withUrlParam(currentRequestUriAndParams, paramName, page.number + 1)}" />"><spring:message code="misc.next" /></a>
            </li>
        </c:if>
        <c:if test="${page.number + 1 >= page.totalPages}" >
            <li class="page-item disabled">
                <a class="page-link" href="#" onclick="return false"><spring:message code="misc.next" /></a>
            </li>
        </c:if>
    </ul>
</nav>