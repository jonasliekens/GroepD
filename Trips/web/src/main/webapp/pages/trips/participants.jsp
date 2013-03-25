<%--
  Created by IntelliJ IDEA.
  User: Nick
  Date: 4/03/13
  Time: 3:30
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
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Stops</title>
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
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="user.firstname"/>
                        </th>
                        <th>
                            <spring:message code="user.lastname"/>
                        </th>
                        <th>
                            <spring:message code="trip.started"/>
                        </th>
                        <th>
                            <spring:message code="trip.finished"/>
                        </th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${fn:length(participatedTrips) == 0}">
                        <tr>
                            <td class="text-center muted" colspan="5">
                                <spring:message code="trip.noParticipants"/>
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach var="participatedTrip" items="${participatedTrips}">
                        <tr>
                            <td>
                                    ${participatedTrip.user.firstName}
                            </td>
                            <td>
                                    ${participatedTrip.user.lastName}
                            </td>
                            <td>
                                <c:if test="${participatedTrip.started}">
                                    <spring:message code="common.yes"/>
                                </c:if>
                                <c:if test="${participatedTrip.started==false}">
                                    <spring:message code="common.no"/>
                                </c:if>

                            </td>
                            <td>
                                <c:if test="${participatedTrip.finished}">
                                    <spring:message code="common.yes"/>
                                </c:if>
                                <c:if test="${participatedTrip.finished==false}">
                                    <spring:message code="common.no"/>
                                </c:if>

                            </td>
                            <td>
                                <c:set var="blocked" value="false"/>
                                <c:forEach var="blockedUser" items="${participatedTrip.user.blockedUsers}">
                                    <c:if test="${blockedUser.id eq user.id}">
                                        <c:set var="blocked" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${blocked == false and user.id != participatedTrip.user.id}">
                                    <a class="btn" href="chat/user/${participatedTrip.user.id}"><spring:message
                                            code="chat.chat"/></a>
                                </c:if>

                                <c:set var="contains" value="false"/>
                                <c:forEach var="blockedUser" items="${user.blockedUsers}">
                                    <c:if test="${blockedUser.id eq participatedTrip.user.id and user.id != participatedTrip.user.id}">
                                        <c:set var="contains" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contains == false and user.id != participatedTrip.user.id}">
                                    <a class="btn"
                                       href="trips/${participatedTrip.trip.id}/block/${participatedTrip.user.id}"><spring:message
                                            code="chat.block"/></a>
                                </c:if>

                                <c:if test="${contains}">
                                    <a class="btn"
                                       href="trips/${participatedTrip.trip.id}/unblock/${participatedTrip.user.id}"><spring:message
                                            code="chat.unblock"/></a>
                                </c:if>
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
</body>
</html>