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
	<span class="label"><spring:message code="administrators.avgAntennaCountPerUser" />: </span>
	<span class="content"><c:out value="${avgAntennaCountPerUser}" /></span>
</div>

<div>
	<span class="label"><spring:message code="administrators.stdDevAntennaCountPerUser" />: </span>
	<span class="content"><c:out value="${stdDevAntennaCountPerUser}" /></span>
</div>

<div>
	<span class="label"><spring:message code="administrators.avgAntennaSignalQuality" />: </span>
	<span class="content"><c:out value="${avgAntennaSignalQuality}" /></span>
</div>

<div>
	<span class="label"><spring:message code="administrators.stdDevAntennaSignalQuality" />: </span>
	<span class="content"><c:out value="${stdDevAntennaSignalQuality}" /></span>
</div>

<div>
	<span class="label"><spring:message code="administrators.avgTutorialCountPerUser" />: </span>
	<span class="content"><c:out value="${avgTutorialCountPerUser}" /></span>
</div>

<div>
	<span class="label"><spring:message code="administrators.stdDevTutorialCountPerUser" />: </span>
	<span class="content"><c:out value="${stdDevTutorialCountPerUser}" /></span>
</div>

<div>
	<span class="label"><spring:message code="administrators.avgCommentCountPerTutorial" />: </span>
	<span class="content"><c:out value="${avgCommentCountPerTutorial}" /></span>
</div>

<div>
	<span class="label"><spring:message code="administrators.stdDevCommentCountPerTutorial" />: </span>
	<span class="content"><c:out value="${stdDevCommentCountPerTutorial}" /></span>
</div>

<h3><spring:message code="administrators.antennaCountPerModel" /></h3>

<div>
<canvas id="antennaCountPerModelChart" width="600" height="400"></canvas>
</div>

<h3><spring:message code="administrators.mostPopularAntennas" /></h3>

<div>
<display:table name="mostPopularAntennas"
	       id="antenna"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="administrators/dashboard.do">

    <display:column value="${antenna[0]}" titleKey="antennas.model" escapeXml="true" sortable="true" />
    <display:column value="${antenna[1]}" titleKey="administrators.antennaCount" escapeXml="true" sortable="true" />
</display:table>
</div>

<h3><spring:message code="administrators.topTutorialContributors" /></h3>

<div>
<display:table name="topTutorialContributors"
	       id="user"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="administrators/dashboard.do">

    <display:column property="fullName" titleKey="actors.name" escapeXml="true" sortable="true" />
    <display:column value="${fn:length(user.tutorials)}" titleKey="administrators.tutorialCount" escapeXml="true" sortable="true" />
</display:table>
</div>

<script>
    let antennaCountPerModelChartLabels = [
    <c:forEach items="${antennaCountPerModel}" var="item">
        "${appfn:escapeJs(item[0])}",
    </c:forEach>
    ];
    let antennaCountPerModelChartData = [
        <c:forEach items="${antennaCountPerModel}" var="item">
            "${appfn:escapeJs(item[1])}",
        </c:forEach>
    ];

    let antennaCountPerModelChart = new Chart($("#antennaCountPerModelChart"), {
        type: 'bar',
        data: {
            labels: antennaCountPerModelChartLabels,
            datasets: [{
                label: "Antenna count per model",
                data: antennaCountPerModelChartData,
                backgroundColor: palette('tol', antennaCountPerModelChartData.length).map(function(hex) { return '#' + hex; })
            }],
        },
        options: {
            responsive: false,
            legend: {
                display: false
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
</script>