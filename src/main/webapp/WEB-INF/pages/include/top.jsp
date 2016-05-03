<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

Hello <sec:authentication property="principal.name"/>&nbsp;<sec:authentication property="principal.surname"/>
<form name="loginForm" action="<c:url value='/logout' />" method='POST'>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <p class="submit"><input type="submit" name="commit" value="Logout"></p>
</form>

