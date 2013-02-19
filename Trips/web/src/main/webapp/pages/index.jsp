<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<body>
	<h1>Message : ${message}</h1>
    <h1>TEST: <spring:message code="TEST"/></h1>
    <h1>${pageContext.response.locale}</h1>
    <a href="?lang=en">English</a> | <a href="?lang=nl">Nederlands</a>
</body>
</html>