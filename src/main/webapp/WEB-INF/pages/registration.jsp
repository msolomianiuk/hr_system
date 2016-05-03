<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login &amp; Register Templates</title>

    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="<c:url value="/static/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/font-awesome/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/css/form-elements.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Favicon and touch icons -->
    <link rel="shortcut icon" href="<c:url value="/static/ico/favicon.png"/>">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="<c:url value="/static/ico/apple-touch-icon-144-precomposed.png"/>">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="<c:url value="/static/ico/apple-touch-icon-114-precomposed.png"/>">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="<c:url value="/static/ico/apple-touch-icon-72-precomposed.png"/>">
    <link rel="apple-touch-icon-precomposed" href="<c:url value="/static/ico/apple-touch-icon-57-precomposed.png"/>">

</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">

            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <%@ include file="include/H1title.jsp" %>
                    <h1>Register Forms</h1>

                    <div class="description">
                        <!-- <p>
                            This is a free responsive <strong>"login and register forms"</strong> template made with Bootstrap.
                            Download it on <a href="http://azmind.com" target="_blank"><strong>AZMIND</strong></a>,
                            customize and use it as you like!
                        </p> -->
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-3"></div>
                <div class="col-sm-6">
                    <div class="form-box">
                        <c:choose>
                            <c:when test="${success != null}">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Sign up</h3>

                                        <p>You have successfully signed up!</p>

                                        <p>Please, <a href="<c:url value="/login"/>">log in</a></p>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>

                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Sign up now</h3>

                                        <p>Fill in the form below to get instant access:</p>
                                    </div>
                                    <div class="form-top-right">
                                        <i class="fa fa-pencil"></i>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                        ${error}
                                    <form role="form" action="<c:url value="/registration" />" method="post"
                                          class="registration-form" modelAttribute="user">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-surname">Surname</label>
                                            <input type="text" name="form-surname" placeholder="Surname..."
                                                   class="form-surname form-control" id="form-surname">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name">Name</label>
                                            <input type="text" name="form-name" placeholder="Name..."
                                                   class="form-name form-control" id="form-name">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-patronymic">Patronymic</label>
                                            <input type="text" name="form-patronymic" placeholder="Patronymic..."
                                                   class="form-patronymic form-control" id="form-patronymic">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-email">Email</label>
                                            <input type="text" name="form-email" placeholder="Email..."
                                                   class="form-email form-control" id="form-email">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-password">Password</label>
                                            <input type="password" name="form-password" placeholder="Password..."
                                                   class="form-password form-control" id="form-password">
                                        </div>
                                        <button type="submit" class="btn">Sign me up!</button>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </div>

                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="col-sm-3"></div>


            </div>
        </div>

    </div>
</div>

<%@ include file="include/footer.jsp" %>

<!-- Javascript -->
<script src="<c:url value="/static/js/jquery-1.11.1.min.js"/>"/>
<script src="<c:url value="/static/bootstrap/js/bootstrap.min.js"/>"/>
<script src="<c:url value="/static/js/scripts.js"/>"/>

<!--[if lt IE 10]>
<script src="<c:url value="/static/js/placeholder.js"/>"/>
<![endif]-->

</body>

</html>