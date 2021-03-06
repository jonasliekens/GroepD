<%--
  Created by IntelliJ IDEA.
  User: Nick
  Date: 28/02/13
  Time: 3:26
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
    <title>Trips</title>
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
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <c:if test="${sessionScope.userId != null}">
                    <div class="btn-toolbar">
                        <a class="btn" href="trips/add"><spring:message code="control.add"/></a>
                        <div class="btn-group">
                            <a class="btn" href="trips/own"><spring:message code="message.showmytrips.trip"/></a>
                            <a class="btn" href="trips/registered"><spring:message code="message.showusertrips.trip"/></a>
                            <a class="btn" href="trips/invitations"><spring:message code="message.showinvitations.trip"/></a>
                        </div>
                    </div>
                </c:if>
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
                            <spring:message code="trip.numberOfStops"/>
                        </th>
                        <th>
                            <spring:message code="message.invitations.confirm"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="pt" items="${invitations}" varStatus="loop">
                        <tr>
                            <td>
                                    ${pt.trip.name}
                            </td>
                            <td>
                                <c:if test="${pt.trip.privateTrip == true}">
                                    <spring:message code="common.yes"/>
                                </c:if>
                                <c:if test="${pt.trip.privateTrip == false}">
                                    <spring:message code="common.no"/>
                                </c:if>
                            </td>
                            <td>
                                    ${fn:length(pt.trip.stops)}
                            </td>
                            <td>
                                <!--<form method="POST" action="trips/invitations/accept/${pt.id}">
                                    <button type="submit"><spring:message code="participatedTrip.accept"/></button>
                                </form>
                                <form method="POST" action="trips/invitations/deny/${pt.id}">
                                    <button type="submit"><spring:message code="participatedTrip.deny"/></button>
                                </form>      -->

                                <a class="btn" href="trips/invitations/accept/${pt.id}"><spring:message
                                        code="participatedTrip.accept"/></a>
                                <a class="btn" href="trips/invitations/deny/${pt.id}"><spring:message
                                        code="participatedTrip.deny"/></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>


<%@include file="../../template/footer.jsp" %>


<!-- The JavaScript files -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>

<script src="js/main.js"></script>
</body>
</html>