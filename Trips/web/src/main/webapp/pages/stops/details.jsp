<%--
  Created by IntelliJ IDEA.
  User: Nick.DeWaele
  Date: 28/02/13
  Time: 13:45
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
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="stop.name"/>
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
                            <spring:message code="stop.accuracy"/>
                        </th>
                        <th>
                            <spring:message code="stop.orderNumber"/>
                        </th>
                        <th>
                            <!--Controls-->
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            ${stop.name}
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
                            ${stop.accuracy}
                        </td>
                        <td>
                            ${stop.orderNumber}
                        </td>
                        <td>
                            <a href="trips/${trip.id}/stops/addquestion/${stop.id}"><spring:message code="control.question"/></a>
                            <a href="trips/${trip.id}/stops/addphoto/${stop.id}"><spring:message code="control.photo"/></a>
                            <a href="trips/${trip.id}/stops/edit/${stop.id}"><spring:message code="control.edit"/></a>
                            <a href="trips/${trip.id}/stops/delete/${stop.id}"><spring:message
                                    code="control.delete"/></a>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="stop.question"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="question" items="${stop.questions}">
                    <tr>
                        <td>
                            ${question.question}
                        </td>
                        <td>
                            <table class="table table-striped">
                                <thead>
                                    <th>
                                        <spring:message code="stop.answer"/>
                                    </th>
                                    <th>
                                        <spring:message code="stop.iscorrect"/>
                                    </th>
                                </thead>
                                <tbody>
                                    <c:forEach var="answer" items="${question.answers}">
                                        <tr>
                                            <td>
                                                ${answer.answer}
                                            </td>
                                            <td>
                                                <c:if test="${answer.correct == true}">
                                                    <spring:message code="common.yes"/>
                                                </c:if>
                                                <c:if test="${answer.correct == false}">
                                                    <spring:message code="common.no"/>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <a href="trips/${trip.id}/stops/addanswer/${stop.id}/${question.id}"><spring:message code="control.answer"/></a>
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