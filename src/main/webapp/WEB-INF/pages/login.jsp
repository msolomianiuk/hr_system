<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login &amp; Register Templates</title>

    <!-- CSS -->
    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value='/static/fonts/css/font-awesome.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/static/css/form-elements.css'/>">
    <link rel="stylesheet" href="<c:url value='/static/css/style.css'/>">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">

            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <%@ include file="include/H1title.jsp" %>
                    <h1>Login Forms</h1>
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
                <div class="col-sm-4"></div>
                <div class="col-sm-4">

                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3>Login to our site</h3>
                                <p>Enter username and password to log on:</p>
                            </div>
                            <div class="form-top-right">
                                <i class="fa fa-lock"></i>
                            </div>
                        </div>
                        <div class="form-bottom">
                            ${error}
                            ${logout}
                            <form role="form" action="<c:url value="/login" />" method="post" class="login-form">
                                <div class="form-group">
                                    <label class="sr-only" for="form-username">Email</label>
                                    <input type="text" name="form-username" placeholder="Email..."
                                           class="form-username form-control" id="form-username">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-password">Password</label>
                                    <input type="password" name="form-password" placeholder="Password..."
                                           class="form-password form-control" id="form-password">
                                </div>
                                <button type="submit" class="btn">Sign in!</button>
                                <div class="form-group rememberMe">
                                    <label class="rememberMeLabel" for="form-remember-me">Remember Me</label>
                                    <input type="checkbox" checked name="form-rememberMe" class="rememberMeCheckbox"
                                           id="form-remember-me">
                                </div>
                                <div class="form-group registration">
                                    <a href="<c:url value="/registration"/>">Registration</a>
                                </div>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </div>
                    </div>

                    <div class="col-sm-4"></div>

                </div>
            </div>

        </div>
    </div>

</div>

<%@ include file="include/footer.jsp" %>

<!-- Javascript -->
<script src="<c:url value="/static/js/jquery.min.js"/>"/>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"/>
<script src="<c:url value="/static/js/scripts.js"/>"/>

<!--[if lt IE 10]>
<script src="<c:url value="/static/js/placeholder.js"/>"/>
<![endif]-->

</body>

</html>