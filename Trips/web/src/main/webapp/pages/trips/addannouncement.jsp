<%--
  Created by IntelliJ IDEA.
  User: Nick.DeWaele
  Date: 7/03/13
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add Announcement</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="css/main.css">

    <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body>
<%@include file="../../template/header.jsp" %>

    <section id="content">
        <div class="container">
            <div class="row-fluid">
                <div class="span12">
                    <form:form method="post" modelAttribute="announcementform">
                        <form:label path="message"><spring:message code="announcement.message" /></form:label>
                        <form:textarea path="message"/>
                        <form:errors path="message" cssClass="alert alert-error"/>
                        <br/>
                        <spring:message code="control.add" var="btnAdd"></spring:message>
                        <input type="submit" value="${btnAdd}"/>
                    </form:form>
                </div>
            </div>
        </div>
    </section>

</body>
</html>