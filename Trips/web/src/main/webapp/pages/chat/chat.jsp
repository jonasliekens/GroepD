<%--
  Created by IntelliJ IDEA.
  User: bartverhavert
  Date: 6/03/13
  Time: 19:44
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
    <title>${title}</title>
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
                <div class="span4">
                    <h4><spring:message code="chat.chats" /></h4>
                    <ul class="chats">
                        <c:if test="${fn:length(chats) == 0}">
                            <li class="text-center muted">
                                <spring:message code="chat.noChats" />
                            </li>
                        </c:if>
                        <c:forEach var="chat" items="${chats}">
                            <li>
                                <a href="chat/${chat.id}" class="chat">
                                    Chat ...
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="span8">
                    <h4>Bart Verhavert</h4>
                    <ul class="messages">
                        <c:if test="${fn:length(messages) == 0}">
                            <li class="text-center muted">
                                <spring:message code="chat.noMessages" />
                            </li>
                        </c:if>
                        <c:forEach var="message" varStatus="iterator" items="${messages}">
                            <li>
                                <div class="info">
                                    <span class="sender">${message.sender.firstName} ${message.sender.lastName}</span>
                                    <span class="date">${message.date}</span>
                                </div>
                                <div class="message">
                                    ${message.message}
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="new-message">
                        <form method="post">
                            <spring:message code="chat.writeYourMessage" var="placeholder" />
                            <spring:message code="control.send" var="btnSend" />
                            <textarea name="message" rows="5" placeholder="${placeholder}"></textarea>
                            <input type="submit" class="btn" value="${btnSend}">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>


    <%@include file="../../template/footer.jsp" %>


    <!-- The JavaScript files -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>
</body>
</html>