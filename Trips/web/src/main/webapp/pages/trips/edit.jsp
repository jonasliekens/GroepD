<%--
  Created by IntelliJ IDEA.
  User: bartverhavert
  Date: 22/02/13
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit a Trip</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="css/main.css">

    <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body>
<%@include file="../../template/header.jsp" %>
<section>
    <section>
        <div class="container">
            <div class="row-fluid">
                <div class="span12">
                    <form:form method="post" modelAttribute="tripForm">
                        <form:label path="name"><spring:message code="trip.name" /></form:label>
                        <form:input path="name"/>
                        <form:errors path="name" cssClass="alert alert-error"/>

                        <form:label path="privateTrip"><spring:message code="trip.privateQuestion" /></form:label>
                        <form:checkbox path="privateTrip"/>
                        <form:errors path="privateTrip" cssClass="alert alert-error"/>

                        <a href="trips/${trip.id}/stops"><spring:message code="trip.stops" /></a>
                        <spring:message code="control.edit" var="btnEdit"></spring:message>
                        <input type="submit" value="${btnEdit}"/>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</section>
</body>
</html>