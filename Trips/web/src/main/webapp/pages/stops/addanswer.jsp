<%--
  Created by IntelliJ IDEA.
  User: Nick
  Date: 1/03/13
  Time: 0:10
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


<section>
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <form:form method="post" modelAttribute="answerForm">
                    <form:label path="answer"><spring:message code="stop.answer" /></form:label>
                    <form:input path="answer" />
                    <form:errors path="answer" cssClass="alert alert-error" />
                    <form:label path="iscorrect"><spring:message code="stop.iscorrect" /></form:label>
                    <form:checkbox path="iscorrect" />
                    <form:errors path="iscorrect" cssClass="alert alert-error" />
                    <br/>


                    <spring:message code="control.add" var="btnAdd"></spring:message>
                    <input type="submit" value="${btnAdd}" />
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