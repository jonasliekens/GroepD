<%--
  User: Sander
  Date: 20/02/13
  Time: 9:34
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
    <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
    <![endif]-->

    <spring:message code="user.email" var="lblEmail" />
    <spring:message code="user.password" var="lblPassword" />
    <spring:message code="control.login" var="btnLogin" />
    <spring:message code="user.firstname" var="lblFirstname" />
    <spring:message code="user.lastname" var="lblLastname" />
    <spring:message code="user.birthday" var="lblBirthday" />
    <spring:message code="control.register" var="btnRegister"/>

    <div id="fb-root"></div>

    <%@include file="../../template/header.jsp" %>

    <section id="content">
        <div class="container">
            <c:if test="${not empty error}">
            <div class="row-fluid">
                <div class="span12">
                    <div class="alert alert-error">
                        <c:choose>
                            <c:when test="${error == 'FAILED'}">
                                FAILED
                            </c:when>
                            <c:when test="${error == 'REQUIRED'}">
                                REQUIRED
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
            </c:if>

            <div class="row-fluid">
                <div class="span6">
                    <div class="loginForm">
                        <h3><spring:message code="register.loginTitle"/></h3>

                        <form class="form-horizontal" method="post" action="j_spring_security_check">
                            <div class="control-group">
                                <label for="email" class="control-label">${lblEmail}</label>
                                <div class="controls">
                                    <input id="email" type="text" name="j_username" placeholder="${lblEmail}" />
                                </div>
                            </div>

                            <div class="control-group">
                                <label for="password" class="control-label">${lblPassword}</label>
                                <div class="controls">
                                    <input id="password" type="password" name="j_password" placeholder="${lblPassword}" />
                                </div>
                            </div>

                            <div class="control-group">
                                <div class="controls">
                                    <input type="submit" class="btn" value="${btnLogin}"/>
                                </div>
                            </div>
                        </form>

                        <fb:login-button show-faces="true" width="200" max-rows="1" onlogin="getData()" perms="email,user_birthday,publish_actions"></fb:login-button>
                    </div>
                </div>
                <div class="span6">
                    <div class="loginForm">
                        <h3><spring:message code="register.title"/></h3>

                        <form:form cssClass="form-horizontal" action="register" method="post" modelAttribute="registerForm">
                            <div class="control-group">
                                <form:label path="firstname" cssClass="control-label">${lblFirstname}</form:label>
                                <div class="controls">
                                    <form:input path="firstname" placeholder="${lblFirstname}" />
                                    <form:errors path="firstname" cssClass="help-inline" />
                                </div>
                            </div>

                            <div class="control-group">
                                <form:label path="lastname" cssClass="control-label">${lblLastname}</form:label>
                                <div class="controls">
                                    <form:input path="lastname" placeholder="${lblLastname}" />
                                    <form:errors path="lastname" cssClass="help-inline" />
                                </div>
                            </div>

                            <div class="control-group">
                                <form:label path="birthday" cssClass="control-label">${lblBirthday}</form:label>
                                <div class="controls">
                                    <form:input path="birthday" placeholder="${lblBirthday}" />
                                    <form:errors path="birthday" cssClass="help-inline" />
                                </div>
                            </div>

                            <div class="control-group">
                                <form:label path="email" cssClass="control-label">${lblEmail}</form:label>
                                <div class="controls">
                                    <form:input path="email" placeholder="${lblEmail}" />
                                    <form:errors path="email" cssClass="help-inline" />
                                </div>
                            </div>

                            <div class="control-group">
                                <form:label path="password" cssClass="control-label">${lblPassword}</form:label>
                                <div class="controls">
                                    <form:password path="password" placeholder="${lblPassword}" />
                                    <form:errors path="password" cssClass="help-inline" />
                                </div>
                            </div>

                            <div class="control-group">
                                <div class="controls">
                                    <input type="submit" class="btn" value="${btnRegister}"/>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <%@include file="../../template/footer.jsp" %>


    <!-- The JavaScript files -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>

    <script src="js/facebook.js"></script>

    <script>
        window.fbAsyncInit = function () {
            initFB();
            checkLoginStatus();
        };
    </script>
</body>
</html>