<%--
  User: Sander
  Date: 20/02/13
  Time: 9:34
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<h1>Trips - Login</h1>

<h2><spring:message code="register.title"/></h2>
<form:form action="login/register" method="post" modelAttribute="registerForm">
    <table>
        <tr>
            <td><form:label path="firstname"><spring:message code="user.firstname"/></form:label></td>
            <td><form:input path="firstname" /></td>
        </tr>
        <tr>
            <td><form:label path="lastname"><spring:message code="user.lastname"/></form:label></td>
            <td><form:input path="lastname" /></td>
        </tr>
        <tr>
            <td><form:label path="birthday"><spring:message code="user.birthday"/></form:label></td>
            <td><form:input path="birthday" /></td>
        </tr>
        <tr>
            <td><form:label path="email"><spring:message code="user.email"/></form:label></td>
            <td><form:input path="email" /></td>
        </tr>
        <tr>
            <td><form:label path="password"><spring:message code="user.password"/></form:label></td>
            <td><form:password path="password" /></td>
        </tr>

        <tr>
            <spring:message code="register.btnRegister" var="btnRegister"/>
            <td colspan="2"><input type="submit" value="${btnRegister}" /></td>
        </tr>
    </table>
</form:form>

</body>
</html>