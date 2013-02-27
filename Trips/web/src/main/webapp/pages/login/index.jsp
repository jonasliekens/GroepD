<%--
  User: Sander
  Date: 20/02/13
  Time: 9:34
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
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


<%@include file="../../template/header.jsp" %>

<section>
    <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <h1>Trips - Login</h1>

                <h2><spring:message code="register.loginTitle"/></h2>
                <form:form action="login/checkLogin" method="post" modelAttribute="loginForm">
                    <table>
                        <form:errors/>
                        <tr>
                            <td><form:label path="email"/></td>
                            <td><form:input path="email"/></td>
                        </tr>
                        <tr>
                            <td><form:label path="password"/></td>
                            <td><form:password path="password"/></td>
                        </tr>
                        <tr>
                        <spring:message code="control.login" var="btnLogin"/>
                        <td colspan="2"><input type="submit" value="${btnLogin}"/></td>
                        </tr>
                    </table>
                </form:form>

                <h2><spring:message code="register.title"/></h2>
                <form:form action="login/register" method="post" modelAttribute="registerForm">
                    <table>
                        <tr>
                            <td><form:label path="firstname"><spring:message code="user.firstname"/></form:label></td>
                            <td><form:input path="firstname"/></td>
                            <td><form:errors path="firstname"/></td>
                        </tr>
                        <tr>
                            <td><form:label path="lastname"><spring:message code="user.lastname"/></form:label></td>
                            <td><form:input path="lastname"/></td>
                            <td><form:errors path="lastname"/></td>
                        </tr>
                        <tr>
                            <td><form:label path="birthday"><spring:message code="user.birthday"/></form:label></td>
                            <td><form:input path="birthday"/></td>
                            <td><form:errors path="birthday"/></td>
                        </tr>
                        <tr>
                            <td><form:label path="email"><spring:message code="user.email"/></form:label></td>
                            <td><form:input path="email"/></td>
                            <td><form:errors path="email"/></td>
                        </tr>
                        <tr>
                            <td><form:label path="password"><spring:message code="user.password"/></form:label></td>
                            <td><form:password path="password"/></td>
                            <td><form:errors path="password"/></td>
                        </tr>

                        <tr>
                            <spring:message code="control.register" var="btnRegister"/>
                            <td colspan="3"><input type="submit" value="${btnRegister}"/></td>
                        </tr>
                    </table>
                </form:form>
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