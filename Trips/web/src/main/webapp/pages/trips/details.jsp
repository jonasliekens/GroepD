<%--
  Created by IntelliJ IDEA.
  User: Nick
  Date: 28/02/13
  Time: 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>${trip.name}</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="css/main.css">

    <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to
    improve your experience.</p>
<![endif]-->


<%@include file="../../template/header.jsp" %>

<section id="content">
<section id="trip-banner">
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <img src="http://lorempixel.com/1170/200/nature/" alt="The banner for this trip"/>
            </div>
        </div>
    </div>
</section>
<section>
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <c:if test="${isAdmin}"><a class="btn" href="trips/participants/${trip.id}"><spring:message
                        code="trip.showParticipants"/></a></c:if>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="trip.name"/>
                        </th>
                        <th>
                            <spring:message code="trip.private"/>
                        </th>
                        <th>
                            <spring:message code="trip.start"/>
                        </th>
                        <th>
                            <spring:message code="trip.end"/>
                        </th>
                        <th>
                            <spring:message code="trip.numberOfStops"/>
                        </th>
                        <th>
                            <spring:message code="trip.participants"/>
                        </th>
                        <th>
                            <!--Controls-->
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            ${trip.name}
                        </td>
                        <td>
                            <c:if test="${trip.privateTrip == true}">
                                <spring:message code="common.yes"/>
                            </c:if>
                            <c:if test="${trip.privateTrip == false}">
                                <spring:message code="common.no"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${fn:length(stops) == 0}">
                                ${stops.get(0).latitude}, ${stops.get(0).longitude}
                            </c:if>

                        </td>
                        <td>
                            <c:if test="${fn:length(stops) == 0}">
                                ${stops.get(fn:length(trip.stops)-1).latitude}, ${stops.get(fn:length(trip.stops)-1).longitude}
                            </c:if>

                        </td>
                        <td>
                            ${fn:length(trip.stops)}
                        </td>
                        <td>
                            ${fn:length(trip.participatedTrips)}
                        </td>
                        <td>
                            <a href="trips/edit/${trip.id}"><spring:message code="control.edit"/></a>
                            <a href="trips/delete/${trip.id}"><spring:message code="control.delete"/></a>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <h5>
                    <spring:message code="trip.announcements"/>
                </h5>

                <c:if test="${isAdmin}">
                    <a class="btn" href="trips/${trip.id}/announcements/add"><spring:message code="control.add"/></a>
                </c:if>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="announcement.message"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${fn:length(announcements) == 0}">
                        <tr>
                            <td class="text-center muted" colspan="6">
                                <spring:message code="announcement.noAnnouncements"/>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach var="announcement" items="${announcements}" varStatus="loop">
                        <tr>
                            <td>
                                    ${announcement.message}
                            </td>
                            <td>
                                <a href="trips/announcements/edit/${announement.id}"><spring:message
                                        code="control.edit"/></a>
                                <a href="trips/announcements/delete/${announcement.id}"><spring:message
                                        code="control.delete"/></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <h5>
                    <spring:message code="trip.stops"/>
                </h5>
                <c:if test="${isAdmin}">
                    <a class="btn" href="trips/${trip.id}/stops/add"><spring:message code="control.add"/></a>
                </c:if>

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="stop.name"/>
                        </th>
                        <th>
                            <spring:message code="stop.orderNumber"/>
                        </th>
                        <th>
                            <spring:message code="stop.description"/>
                        </th>
                        <th>
                            <spring:message code="stop.latitude"/>
                        </th>
                        <th>
                            <spring:message code="stop.longitude"/>
                        </th>
                        <th>
                            <!--Controls-->
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${fn:length(stops) == 0}">
                        <tr>
                            <td class="text-center muted" colspan="6">
                                <spring:message code="stop.noStops"/>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach var="stop" items="${stops}" varStatus="loop">
                        <tr>
                            <td>
                                <a href="trips/${trip.id}/stops/details/${stop.id}">${stop.name}</a>
                            </td>
                            <td>
                                    ${stop.orderNumber}
                            </td>
                            <td>
                                    ${stop.description}
                            </td>
                            <td>
                                    ${stop.latitude}
                            </td>
                            <td>
                                    ${stop.longitude}
                            </td>
                            <td>
                                <a href="trips/${trip.id}/stops/edit/${stop.id}"><spring:message
                                        code="control.edit"/></a>
                                <a href="trips/${trip.id}/stops/delete/${stop.id}"><spring:message
                                        code="control.delete"/></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <c:if test="${sessionScope.userId > 0}">
                    <a class="btn" href="trips/register/${trip.id}"><spring:message code="trip.register"/></a>
                </c:if>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span8">
                <div id="map_canvas" data-trip-id="${trip.id}"></div>
            </div>
            <div class="span4">
                <div id="map_error" class="alert alert-error"><spring:message code="trip.notEnoughStops"/></div>
                <div id="directionsPanel"></div>
            </div>
        </div>
    </div>

</section>
</section>


<%@include file="../../template/footer.jsp" %>


<!-- The JavaScript files -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>

<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDJXL1bwZ0C3Hdus-DOgbxOGedijvCRpPc&sensor=true&language=${pageContext.response.locale}"></script>

<script src="js/maps.trip.min.js"></script>
</body>
</html>