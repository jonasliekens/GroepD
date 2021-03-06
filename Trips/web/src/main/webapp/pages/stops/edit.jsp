<%--
  Created by IntelliJ IDEA.
  User: Nick De Waele
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
    <title>Edit a stop</title>
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

    <section id="content">
        <div class="container">
            <div class="row-fluid">
                <div class="span12">
                    <form:form method="post" modelAttribute="stopForm">
                        <form:label path="name"><spring:message code="stop.name" /></form:label>
                        <form:input path="name"/>
                        <form:errors path="name" cssClass="alert alert-error"/>

                        <form:label path="description"><spring:message code="stop.description" /></form:label>
                        <form:input path="description"/>
                        <form:errors path="description" cssClass="alert alert-error"/>
                        <form:label path="latitude"><spring:message code="stop.latitude" /></form:label>
                        <form:input id="latitude" path="latitude"/>
                        <form:errors path="latitude" cssClass="alert alert-error"/>
                        <form:label path="longitude"><spring:message code="stop.longitude" /></form:label>
                        <form:input id="longitude" path="longitude"/>
                        <form:errors path="longitude" cssClass="alert alert-error"/>
                        <form:label path="accuracy"><spring:message code="stop.accuracy" /></form:label>
                        <form:input path="accuracy"/>
                        <form:errors path="accuracy" cssClass="alert alert-error"/>

                        <label><spring:message code="stop.orderNumber"/></label>
                        <form:select path="orderOption">
                            <form:option value="first"><spring:message code="label.first"/></form:option>
                            <form:option value="after"><spring:message code="label.after"/></form:option>
                            <form:option value="last"><spring:message code="label.last"/></form:option>
                        </form:select>

                        <form:select path="orderNumber">
                            <form:options items="${stops}" />
                        </form:select>

                        <spring:message code="control.edit" var="btnEdit"></spring:message>
                        <input type="submit" value="${btnEdit}"/>
                    </form:form>
                </div>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <div id="map_canvas" data-mode="edit"></div>
                </div>
            </div>
        </div>
    </section>


    <%@include file="../../template/footer.jsp" %>


    <!-- The JavaScript files -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>

    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDJXL1bwZ0C3Hdus-DOgbxOGedijvCRpPc&sensor=true&language=${pageContext.response.locale}"></script>

    <script src="js/maps.stop.min.js"></script>
</body>
</html>