<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
${error}
<hr/>
<a href="<c:url value="/"/>">Go to the main page</a>
</body>
</html>
