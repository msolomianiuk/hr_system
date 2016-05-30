<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- meta -->
    <%@ include file="include/links/linksMeta.jsp" %>
    <sec:csrfMetaTags />
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Profile - </title>

    <script src="<c:url value="/static/js/custom/baseUrl.js"/>"></script>
    <!-- bootstrap core -->

    <%@ include file="include/links/linksBootstrapCore.jsp" %>
    <!-- IE9 -->
    <%@ include file="include/links/linksForIE9JS.jsp" %>

</head>
<body class="nav-md">
<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
</script>
<div class="modals">
    <!-- Modal -->
    <%@ include file="include/uploadPhotoModal.jsp" %>
</div>

<%@ include file="include/loadingSpinner.jsp" %>
<div class="container body">
    <div class="main_container">
        <!-- left menu -->
        <%@ include file="include/leftCol.jsp" %>

        <!-- top menu -->
        <%@ include file="include/topNavigation.jsp" %>

        <!-- page content -->
        <div class="right_col" role="main">
            <!-- top tiles -->
            <div class="x_panel">
                <div class="x_title">
                    <h2> Profile </h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="candidate-profile row">
                        <%@ include file="include/profile/profileForm.jsp" %>
                    </div>
                    <div class="hidden">
                        <%@ include file="include/profile/question/questionsFieldsTemplate.jsp" %>
                    </div>
                </div>
            </div>
            <!-- footer content -->
            <%@ include file="include/footer.jsp" %>
        </div>
    </div>
</div>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/nprogress.js"/>"></script>
<script src="<c:url value="/static/js/validator/validator.js"/>"></script>
<script src="<c:url value="/static/js/custom/Profile/validator.js"/>"></script>
<!-- bootstrap progress js -->
<%@ include file="include/links/linksBootstrapProgressJs.jsp" %>
<!-- custom css+js -->
<%@ include file="include/links/linksCustomCSSAndJS.jsp" %>
<!-- custom js for candidate -->
<%@ include file="include/profile/customJsLinks.jsp" %>
<!-- js for PNotify -->
<%@ include file="include/links/linksPNotify.jsp" %>


</body>
</html>