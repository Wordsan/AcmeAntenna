<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form:form action="${requestURI}" modelAttribute="handyworker">	

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="pictureUrl"/>
	<form:hidden path="antennaModels"/>
	<form:hidden path="requests"/>
	<form:hidden path="userAccount.authorities"/>
	
	<form:label path="name">
	<spring:message code="handyworker.name" />:
	</form:label> 
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br><br>
	
	<form:label path="surname">
	<spring:message code="handyworker.surname" />:
	</form:label> 
	<form:textarea path="surname"/>
	<form:errors cssClass="error" path="surname"/>
	<br><br>
	
	<form:label path="email">
	<spring:message code="handyworker.email" />:
	</form:label> 
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	<br><br>
	
	<form:label path="phoneNumber">
	<spring:message code="handyworker.phoneNumber" />:
	</form:label> 
	<form:input path="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br><br>
	
	<form:label path="postalAddress">
	<spring:message code="handyworker.postalAddress" />:
	</form:label> 
	<form:input path="postalAddress"/>
	<form:errors cssClass="error" path="postalAddress"/>
	<br><br>
	
	<form:label path="userAccount.username">
	<spring:message code="handyworker.account" />:
	</form:label> 
	<form:input path="userAccount.username"/>
	<form:errors cssClass="error" path="userAccount.username"/>
	
	<br><br>
	
	<form:label path="userAccount.password">
	<spring:message code="handyworker.password" />:
	</form:label> 
	<form:password path="userAccount.password"/>
	<form:errors cssClass="error" path="userAccount.password"/>
	
	
	
	<br><br>
	<input type="submit" name="save"
		value="<spring:message code="handyworker.create" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="handyworker.cancel" />"
		onclick="javascript: relativeRedir('handyworker/list.do');" />
	<br />
	</form:form>