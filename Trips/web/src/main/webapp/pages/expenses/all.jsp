<%--
  Created by IntelliJ IDEA.
  User: bartverhavert
  Date: 12/03/13
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Expenses</title>
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
                    <a href="trip/${tripId}/expenses/add"><spring:message code="control.add"/></a>

                    <table class="table">
                        <thead>
                        <tr>
                            <th>
                                <spring:message code="expenses.price" />
                            </th>
                            <th>
                                <spring:message code="expenses.description" />
                            </th>
                            <c:if test="${isAdmin}">
                                <th>
                                    <spring:message code="expenses.user" />
                                </th>
                            </c:if>
                            <th>
                                <spring:message code="expenses.date" />
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${fn:length(expenses) == 0}">
                            <tr>
                                <td class="text-center muted" colspan="3">
                                    <spring:message code="expenses.noExpenses" />
                                </td>
                            </tr>
                        </c:if>
                        <c:forEach var="expense" items="${expenses}">
                            <tr>
                                <td>
                                    ${expense.price}
                                </td>
                                <td>
                                    ${expense.description}
                                </td>
                                <c:if test="${isAdmin}">
                                    <td>
                                            ${expense.user.firstName} ${expense.user.lastName}
                                    </td>
                                </c:if>
                                <td>
                                    ${expense.date}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>


    <%@include file="../../template/footer.jsp"%>
</body>
</html>