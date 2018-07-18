<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

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

<h3><spring:message code="administrators.antennaCountPerModel" /></h3>

<canvas id="antennaCountPerModelChart" width="600" height="400"></canvas>

<h3><spring:message code="administrators.mostPopularAntennas" /></h3>

<display:table name="mostPopularAntennas"
	       id="antenna"
	       pagesize="${displayTagPageSize}"
	       requestURI="antennas/index.do">

    <display:column value="${antenna[0]}" titleKey="antennas.model" escapeXml="true" sortable="true" />
    <display:column value="${antenna[1]}" titleKey="administrators.antennaCount" escapeXml="true" sortable="true" />
</display:table>

<script>
    let antennaCountPerModelChartLabels = [
    <c:forEach items="${antennaCountPerModel}" var="item">
        "<spring:escapeBody htmlEscape="false" javaScriptEscape="true">${item[0]}</spring:escapeBody>",
    </c:forEach>
    ];
    let antennaCountPerModelChartData = [
        <c:forEach items="${antennaCountPerModel}" var="item">
            "<spring:escapeBody htmlEscape="false" javaScriptEscape="true">${item[1]}</spring:escapeBody>",
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