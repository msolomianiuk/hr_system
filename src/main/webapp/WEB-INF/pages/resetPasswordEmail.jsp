<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Bootstrap Login &amp; Register Templates</title>
    <!-- meta -->
    <%@ include file="include/links/linksMeta.jsp" %>
    <sec:csrfMetaTags/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS -->
    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="<c:url value="/static/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value='/static/fonts/css/font-awesome.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/static/css/form-elements.css'/>">
    <link rel="stylesheet" href="<c:url value='/static/css/style.css'/>">
    <link rel="stylesheet" href="<c:url value='/static/css/custome-restore.css'/>">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body >
<!-- Top content -->
<div class="top-content">
    <div class="inner-bg">
        <div class="loading">
            <div class="load_text">
                <div class="please_text">Please Wait...</div>
                <div class="questions_text">Loading...</div>
            </div>
        </div>
        <div class="container">

            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <%@ include file="include/H1title.jsp" %>
                    <h1>Reset password</h1>
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
                                <h3>Reset password</h3>
                                <p id="enter-email">Enter your email:</p>
                            </div>
                            <div class="form-top-right">
                                <i class="fa fa-lock"></i>
                            </div>
                        </div>

                        <div class="form-bottom">
                            <div id="email-restore" class="login-form">
                                <div class="form-group">
                                    <label class="sr-only" for="email">Email</label>
                                    <input type="text" name="email" placeholder="Email..."
                                           class="form-username form-control" id="email">
                                </div>
                                <button id="restore-p-email" type="submit" class="btn reset-btn">Restore password</button>
                            </div>
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
<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/scripts.js"/>"></script>
<script src="<c:url value="/static/js/custom/baseUrl.js"/>"></script>
<script src="<c:url value="/static/js/custom/RestorePassword/restore.js"/>"></script>
<!--[if lt IE 10]>
<script src="<c:url value="/static/js/placeholder.js"/>"/>
<![endif]-->
<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
</script>
</body>

</html>