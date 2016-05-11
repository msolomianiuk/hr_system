<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- meta -->
    <%@ include file="include/links/linksMeta.jsp" %>
    <title>Interview Page</title>

    <script src="<c:url value="/static/js/custom/baseUrl.js"/>"></script>
    <!-- bootstrap core -->
    <%@ include file="include/links/linksBootstrapCore.jsp" %>
    <!-- IE9 -->
    <%@ include file="include/links/linksForIE9JS.jsp" %>

</head>
<body class="nav-md">
<%@ include file="include/personal/questionsOnInterview.jsp"%>
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
                    <h2> Interview Page </h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <div class="hidden subject-selection-block">
                        <select class="form-control col-md-7 col-xs-12 select-and-text" name="subjectId"></select>
                    </div>
                    <%@ include file="include/personal/searchField.jsp"%>
                    <%@ include file="include/personal/candidateDetails.jsp"%>
                    <%@ include file="include/personal/interviewResultForm.jsp"%>
                </div>
            </div>
            <!-- footer content -->
            <%@ include file="include/footer.jsp" %>
        </div>
    </div>
</div>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/nprogress.js"/>"></script>
<!-- bootstrap progress js -->
<%@ include file="include/links/linksBootstrapProgressJs.jsp" %>
<!-- custom css+js -->
<%@ include file="include/links/linksCustomCSSAndJS.jsp" %>
<!-- js for PNotify -->
<%@ include file="include/links/linksPNotify.jsp" %>



<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/drawQuestion.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/ajax/addQuestionForInterview.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/addQuestionModal.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/ajax/editQuestionForInterview.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/editQuestionModal.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/ajax/deleteQuestionForInterview.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/deleteQuestionModal.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/drawQuestionsForInterview.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/questionsForInterview/ajax/loadingQuestionsForInterview.js"/>"></script>

<script src="<c:url value="/static/js/custom/Personal/ajax/loadQuestions.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/triggerForCandidateDetailsInterview.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/drawCandidateDetails.js"/>"></script>
<script src="<c:url value="/static/js/custom/Personal/ajax/loadCandidateById.js"/>"></script>


</body>
</html>