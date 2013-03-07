<%--
  Created by IntelliJ IDEA.
  User: Nick.DeWaele
  Date: 6/03/13
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" xmlns:fb="http://ogp.me/ns/fb#"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Login</title>
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

<spring:message code="user.email" var="lblEmail"/>
<spring:message code="user.password" var="lblPassword"/>
<spring:message code="control.login" var="btnLogin"/>
<spring:message code="user.firstname" var="lblFirstname"/>
<spring:message code="user.lastname" var="lblLastname"/>
<spring:message code="user.birthday" var="lblBirthday"/>
<spring:message code="control.edit" var="btnEdit"/>

<div id="fb-root"></div>

<%@include file="../../template/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<section id="content">
    <div class="container">
        <div class="row-fluid">
            <div class="span6">
                <div class="loginForm">
                    <h3><spring:message code="editprofile.title"/></h3>

                    <form:form cssClass="form-horizontal" action="login/editprofile" method="post"
                               modelAttribute="editprofileform">
                    <div class="control-group">
                        <form:label path="firstname" cssClass="control-label">${lblFirstname}</form:label>
                        <div class="controls">
                            <form:input path="firstname" placeholder="${lblFirstname}"/>
                            <form:errors path="firstname" cssClass="help-inline"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <form:label path="lastname" cssClass="control-label">${lblLastname}</form:label>
                        <div class="controls">
                            <form:input path="lastname" placeholder="${lblLastname}"/>
                            <form:errors path="lastname" cssClass="help-inline"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <form:label path="birthday" cssClass="control-label">${lblBirthday}</form:label>
                        <div class="controls">
                            <form:input path="birthday" placeholder="${lblBirthday}"/>
                            <form:errors path="birthday" cssClass="help-inline"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <form:label path="receiveMails" cssClass="control-label"><spring:message code="user.receivemails"/></form:label>
                        <div class="controls">
                            <form:checkbox path="receiveMails"/>
                            <form:errors path="receiveMails" cssClass="alert alert-error"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <form:label path="shareLocation" cssClass="control-label"><spring:message code="user.sharelocation"/></form:label>
                        <div class="controls">
                            <form:checkbox path="shareLocation"/>
                            <form:errors path="shareLocation" cssClass="alert alert-error"/>
                        </div>

                        <div class="control-group">
                            <div class="controls">
                                <spring:message code="control.edit" var="btnEdit"></spring:message>
                                <input type="submit" value="${btnEdit}"/>
                            </div>
                        </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
</section>

<%@include file="../../template/footer.jsp" %>

</body>
</html>