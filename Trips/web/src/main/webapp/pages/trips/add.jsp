<%--
  Created by IntelliJ IDEA.
  User: bartverhavert
  Date: 22/02/13
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Add a trip</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <base href="${pageContext.request.contextPath}/" />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="css/main.css">

    <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body>
    <!--[if lt IE 7]>
    <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
    <![endif]-->


    <%@include file="../../template/header.jsp"%>


    <section id="content">
        <div class="container">
            <div class="row-fluid">
                <div class="span12">
                    <h3><spring:message code="trip.add" /></h3>

                    <form:form method="post" cssClass="form-horizontal" modelAttribute="tripForm">
                        <div class="control-group">
                            <form:label path="name" cssClass="control-label"><spring:message code="trip.name" /></form:label>
                            <div class="controls">
                                <form:input path="name" />
                                <form:errors path="name" cssClass="help-inline" />
                            </div>
                        </div>

                        <div class="control-group">
                            <form:label path="privateTrip" cssClass="control-label"><spring:message code="trip.privateQuestion" /></form:label>
                            <div class="controls">
                                <form:checkbox id="privateTrip" path="privateTrip" />
                                <form:errors path="privateTrip" cssClass="help-inline" />
                            </div>
                        </div>

                        <div class="control-group">
                            <form:label path="communicationByChat" cssClass="control-label"><spring:message code="trip.communicationByChatQuestion" /></form:label>
                            <div class="controls">
                                <form:checkbox id="communicationByChat" path="communicationByChat" />
                                <form:errors path="communicationByChat" cssClass="help-inline" />
                            </div>
                        </div>

                        <div class="control-group">
                            <form:label path="communicationByLocation" cssClass="control-label"><spring:message code="trip.communicationByLocationQuestion" /></form:label>
                            <div class="controls">
                                <form:checkbox id="communicationByLocation" path="communicationByLocation" />
                                <form:errors path="communicationByLocation" cssClass="help-inline" />
                            </div>
                        </div>

                        <div class="control-group">
                            <form:label path="tripType" cssClass="control-label"><spring:message code="trip.tripTypeQuestion" /></form:label>
                            <div class="controls">
                                <form:select path="tripType">
                                    <form:options items="${tripTypes}"/>
                                </form:select>
                            </div>
                        </div>

                        <div class="control-group">
                            <form:label path="travelType" cssClass="control-label"><spring:message code="trip.travelTypeQuestion" /></form:label>
                            <div class="controls">
                                <form:select path="travelType">
                                    <form:options items="${travelTypes}"/>
                                </form:select>
                            </div>
                        </div>

                        <div class="control-group">
                            <div class="controls">
                                <spring:message code="control.add" var="btnAdd"></spring:message>
                                <input class="btn" type="submit" value="${btnAdd}" />
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>


    <%@include file="../../template/footer.jsp"%>


    <!-- The JavaScript files -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>

    <script src="js/main.js"></script>
</body>
</html>