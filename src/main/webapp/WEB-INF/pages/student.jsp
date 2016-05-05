<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Gentallela Alela! | </title>

    <!-- Bootstrap core CSS -->

    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet">

    <link href="<c:url value="/static/fonts/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/animate.min.css"/>" rel="stylesheet">

    <!-- Custom styling plus plugins -->
    <link href="<c:url value="/static/css/custom.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href=""
    <c:url value="/static/css/maps/jquery-jvectormap-2.0.3.css"/>" />
    <link href="<c:url value="/static/css/icheck/flat/green.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/floatexamples.css"/>" rel="stylesheet" type="text/css"/>

    <link href="<c:url value="/static/js/datatables/jquery.dataTables.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/js/datatables/buttons.bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/js/datatables/fixedHeader.bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/js/datatables/responsive.bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/js/datatables/scroller.bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>

    <!-- Custom styling plus plugins -->
    <link href="<c:url value="/static/css/custom.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/icheck/flat/green.css"/>" rel="stylesheet">

    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/static/js/nprogress.js"/>"></script>

    <!--[if lt IE 9]>
    <script src="<c:url value="/static/js/ie8-responsive-file-warning.js"/>"></script>
    <![endif]-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>


<body class="nav-md">


<div class="loading">
    <div class="load_text">
        <div class="please_text">Please Wait...</div>
        <div class="questions_text">Loading Questions...</div>
        <div class="answers_text hidden">Saving Answers...</div>
    </div>
</div>

<div class="container body">


    <div class="main_container">


        <!-- page content -->
        <div class="" role="main">
            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2> Profile </h2>
                            <div class="logout">
                                <form name="loginForm" action="<c:url value='/logout' />" method='POST'>

                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div>

                                    </div>
                                        <button class="btn btn-primary" type="submit" name="commit">
                                            Logout <i class="fa fa-sign-out pull-right"></i>
                                        </button>
                                </form>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <div class="profile row">
                                <div class="col-md-3 col-sm-3 col-xs-12">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <!-- <h3 class="page-header">Demo:</h3> -->
                                                <div class="img-container">
                                                    <img src="<c:url value="/static/images/user.png"/>" alt="Picture">
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="docs-preview clearfix">
                                                    <div class="img-preview preview-md"></div>
                                                </div>


                                                <div class="btn-group">

                                                    <label class="btn btn-primary btn-upload" for="inputImage"
                                                           title="Upload image file">
                                                        <form method="post" modelAttribute="spitter"
                                                              enctype="multipart/form-data">
                                                        <input class="sr-only" id="inputImage" name="image" type="file"
                                                               accept="image/*">
                                                            <span class="docs-tooltip" data-toggle="tooltip"
                                                                  title="Import image with Blob URLs">
                                                                <span class="fa fa-upload">Upload Photo</span>
                                                            </span>
                                                        </form>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <form class="form-horizontal form-label-left col-md-9 col-sm-9 col-xs-12" novalidate>
                                    <div class="questions"></div>
                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-3">
                                            <button id="save" type="button" class="btn btn-primary">Submit</button>
                                            <button id="cancel" type="button" class="btn btn-danger">Cancel</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="hidden">
                                <form class="form-horizontal form-label-left" novalidate>


                                    <!-- text input -->
                                    <div class="item form-group text-input-type">
                                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                                            vghvgh
                                        </label>
                                        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
                                            <input type="text" name="" required="required"
                                                   class=" form-control col-md-7 col-xs-12" placeholder="">
                                        </div>
                                    </div>

                                    <!-- number input -->
                                    <div class="item form-group int-input-type">
                                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                                            vghjvhj
                                        </label>
                                        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
                                            <input type="number" name="" required="required"
                                                   class="form-control col-md-7 col-xs-12" placeholder="">
                                        </div>
                                    </div>

                                    <!-- select input -->
                                    <div class="form-group select-input-type">
                                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                                            Select
                                        </label>
                                        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
                                            <select class="form-control col-md-7 col-xs-12">

                                            </select>
                                        </div>
                                    </div>

                                    <!-- Checkbox button -->
                                    <div class="item form-group check-input-type">
                                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">Heard us by
                                            *:</label>
                                        <div class="col-md-8 col-sm-8 col-xs-12 control-label" required>
                                            <div class="checkbox col-md-12 col-sm-12 col-xs-12">
                                                <div class="col-md-4 col-sm-4 col-xs-6 column-1 text-left">
                                                </div>
                                                <div class="col-md-4 col-sm-4 col-xs-6 column-2 text-left">
                                                </div>
                                                <div class="col-md-4 col-sm-4 col-xs-6 column-3 text-left">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- select and text input -->
                                    <div class="form-group textANDselect-input-type">
                                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                                            Select
                                        </label>
                                        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
                                            <select class="form-control col-md-7 col-xs-12 select-and-text">
                                                <option>Choose option</option>
                                                <option>Option one</option>
                                                <option>Option two</option>
                                                <option>Option three</option>
                                                <option class="other">other</option>
                                            </select>
                                            <input type="text" name="" required="required"
                                                   class="form-control col-md-7 col-xs-12 select-other" placeholder="">
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- footer content -->

            <footer>
                <div class="copyright-info">
                    <p class="pull-right">Gentelella - Bootstrap Admin Template by
                        <a href="https://colorlib.com">Colorlib</a>
                    </p>
                </div>
                <div class="clearfix"></div>
            </footer>
            <!-- /footer content -->
        </div>
        <!-- /page content -->

    </div>

</div>

<div id="custom_notifications" class="custom-notifications dsp_none">
    <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
    </ul>
    <div class="clearfix"></div>
    <div id="notif-group" class="tabbed_notifications"></div>
</div>
<script src="js/bootstrap.min.js"></script>

<!-- bootstrap progress js -->
<script src="js/progressbar/bootstrap-progressbar.min.js"></script>
<script src="js/nicescroll/jquery.nicescroll.min.js"></script>
<!-- icheck -->
<script src="js/icheck/icheck.min.js"></script>

<script src="js/custom.js"></script>


<!-- Datatables -->
<!-- <script src="js/datatables/js/jquery.dataTables.js"></script>
<script src="js/datatables/tools/js/dataTables.tableTools.js"></script> -->

<!-- Datatables-->
<script src="<c:url value="/static/js/datatables/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/static/js/datatables/dataTables.buttons.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/buttons.bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/jszip.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/pdfmake.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/vfs_fonts.js"/>"></script>
<script src="<c:url value="/static/js/datatables/buttons.html5.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/buttons.print.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/dataTables.fixedHeader.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/dataTables.keyTable.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/dataTables.responsive.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/responsive.bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/datatables/dataTables.scroller.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/cropping/cropper.min.js"/>"></script>
<script src="<c:url value="/static/js/cropping/main2.js"/>"></script>

<!-- bootstrap progress js -->
<script src="<c:url value="/static/js/progressbar/bootstrap-progressbar.min.js"/>"></script>
<script src="<c:url value="/static/js/nicescroll/jquery.nicescroll.min.js"/>"></script>
<!-- icheck -->
<script src="<c:url value="/static/js/icheck/icheck.min.js"/>"></script>
<!-- pace -->
<script src="<c:url value="/static/js/pace/pace.min.js"/>"></script>
<script src="<c:url value="/static/js/custom.js"/>"></script>
<!-- form validation -->
<script src="<c:url value="/static/js/validator/validator.js"/>"></script>

<!-- pace -->
<script src="<c:url value="/static/js/pace/pace.min.js"/>"></script>
<script>
    var handleDataTableButtons = function () {
                "use strict";
                0 !== $("#datatable-buttons").length && $("#datatable-buttons").DataTable({
                    dom: "Bfrtip",
                    buttons: [{
                        extend: "copy",
                        className: "btn-sm"
                    }, {
                        extend: "csv",
                        className: "btn-sm"
                    }, {
                        extend: "excel",
                        className: "btn-sm"
                    }, {
                        extend: "pdf",
                        className: "btn-sm"
                    }, {
                        extend: "print",
                        className: "btn-sm"
                    }],
                    responsive: !0
                })
            },
            TableManageButtons = function () {
                "use strict";
                return {
                    init: function () {
                        handleDataTableButtons()
                    }
                }
            }();
</script>
<script type="text/javascript">
    $(window).on('load', function () {
        $('.loading').attr('style', 'display: flex');
    });

    $(document).ready(function () {

        $('#datatable-ba').dataTable();
        $('#datatable-dev').dataTable();
        $('#datatable-hr').dataTable();
        $('#datatable-keytable').DataTable({
            keys: true
        });
        $('#datatable-responsive').DataTable();
        $('#datatable-scroller').DataTable({
            ajax: "js/datatables/json/scroller-demo.json",
            deferRender: true,
            scrollY: 380,
            scrollCollapse: true,
            scroller: true
        });
        var table = $('#datatable-fixed-header').DataTable({
            fixedHeader: true
        });
    });
    TableManageButtons.init();
</script>
<script>

    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    $("button#save").on("click", function () {

        $('.loading').attr('style', 'display: flex');
        $.ajax({
            url: "http://31.131.25.42:8080/hr_system-1.0-SNAPSHOT/service/saveAnswers",
            type: "GET",
            dataType: "json",
            data: {'answersJsonString': JSON.stringify($('.profile form').serializeObject())},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                location.reload();
                console.log(data);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });

    $(document).ready(function () {
        $.ajax({
            url: "http://31.131.25.42:8080/hr_system-1.0-SNAPSHOT/service/getAllMandatoryQuestions",
            type: "GET",
            dataType: "json",
            contentType: 'application/json',
            mimeType: 'application/json',
            success: drawQuestionForm,
            error: function (data) {
                console.log(data);
            }
        });
    });

    function drawQuestionForm(questionsList) {
        var questionType;
        var questionInput;
        var questionsAnswers;

        for (var index in questionsList) {
            switch (questionsList[index].typeValue) {
                case "String":
                    questionType = "text";
                    break
                case "int":
                    questionType = "int";
                    break
                case "combobox":
                    questionType = "select";
                    break
                case "checkbox":
                    questionType = "check";
                    break
                case 5:
                    questionType = "textANDselect";
                    break

            }
            questionInput = $(".hidden ." + questionType + "-input-type").clone();
            questionInput.find("label.caption").html(questionsList[index].caption);
            questionInput.find("label.caption").attr("for", "question-" + questionsList[index].id);
            questionInput.find("input").attr("name", "question-" + questionsList[index].id);
            questionInput.find("select").attr("name", "question-" + questionsList[index].id);
            questionInput.find("input").attr("id", "question-" + questionsList[index].id);

            questionsAnswers = questionsList[index].additionValue;

            if ((questionType == "select") || (questionType == "textANDselect")) {
                for (var indexAnswer in questionsAnswers) {
                    questionInput.find("select").append("<option value='" + questionsAnswers[indexAnswer] + "'>"
                            + questionsAnswers[indexAnswer] + "</option>");
                }
            }
            if (questionType == "check") {
                for (var indexAnswer in questionsAnswers) {

                    questionInput.find(".checkbox .column-" + (indexAnswer % 3 + 1)).append(
                            "<label class='' for='question-" + questionsList[index].id + "-" + indexAnswer + "'>" +
                            "<input id='question-" + questionsList[index].id + "-" + indexAnswer +
                            "' name='question-" + questionsList[index].id +
                            "' value = '" + questionsAnswers[indexAnswer] + "' type=\"checkbox\" class=\"flat\" >" +
                            questionsAnswers[indexAnswer] + "</label></br>");
                }
            }
            $(".profile form .questions").append(questionInput);
        }
        setTimeout(function () {
            $('.loading').css('display', 'none');
            $('.loading .questions_text').hide();
            $('.loading .answers_text').removeClass('hidden');
        }, 1000);

    }
</script>
</body>
</html>