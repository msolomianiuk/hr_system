<%--
  Created by IntelliJ IDEA.
  User: Серый
  Date: 29.04.2016
  Time: 23:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <sec:csrfMetaTags />
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Settings </title>

    <!-- Bootstrap core CSS -->
    <script src="<c:url value="/static/js/custom/baseUrl.js"/>"></script>
    <link href="<c:url value="/static/admin/css/bootstrap.min.css"/>" rel="stylesheet">

    <link href="<c:url value="/static/admin/fonts/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/admin/css/animate.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/admin/css/adminSetting.css"/>" rel="stylesheet">
    <!-- Custom styling plus plugins -->
    <link href="<c:url value="/static/admin/css/custom.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/admin/css/maps/jquery-jvectormap-2.0.3.css"/>"/>
    <link href="<c:url value="/static/admin/css/icheck/flat/green.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/admin/css/floatexamples.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/admin/css/selectSettings.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/admin/css/style.css"/>" rel="stylesheet">

    <link href="<c:url value="/static/admin/js/datatables/jquery.dataTables.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/static/admin/js/datatables/buttons.bootstrap.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/static/admin/js/datatables/fixedHeader.bootstrap.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/static/admin/js/datatables/responsive.bootstrap.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/static/admin/js/datatables/scroller.bootstrap.min.css"/>" rel="stylesheet"
          type="text/css"/>


    <script src="<c:url value="/static/admin/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/static/admin/js/nprogress.js"/>"></script>

</head>


<body class="nav-md">

<div class="container body">


    <div class="main_container">

        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">

                <div class="navbar nav_title" style="border: 0;">
                    <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>Net<strong>Cracker</strong></span></a>
                </div>
                <div class="clearfix"></div>

                <!-- menu prile quick info -->
                <div class="profile">
                    <div class="profile_pic">
                        <img src="<c:url value="/static/admin/images/img.jpg"/>" alt="..."
                             class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>Welcome,</span>

                        <h2><sec:authentication property="principal.name"/>&nbsp;<sec:authentication property="principal.surname"/></h2>
                    </div>
                </div>
                <!-- /menu prile quick info -->

                <br/>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">

                    <div class="menu_section">
                        <h3>Administrator</h3>
                        <ul class="nav side-menu">
                            <li><a href="../"><i class="fa fa-home"></i> Home </a></li>
                            <li><a href="students"><i class="fa fa-list"></i> Students List </a></li>
                            <li><a href="personal"><i class="fa fa-list-ul"></i> Personal List </a></li>
                            <li><a href="interview_schedule"><i class="fa fa-calendar"></i> Interview Schedule </a></li>
                            <li><a href="admin_settings"><i class="fa fa-cogs"></i> System Setting </a></li>
                            <li><a href="report"><i class="fa fa-calendar"></i> Reports
                            </a>
                            </li>
                            <li><a href="template"><i class="fa fa-home"></i> Template </a></li>
                        </ul>
                    </div>

                </div>
                <!-- /sidebar menu -->

                <!-- /menu footer buttons -->
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">

            <div class="nav_menu">
                <nav class="" role="navigation">
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="false">
                                <img src="<c:url value="/static/admin/images/img.jpg"/>" alt="">
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
                                <li><a href="javascript:;"> Profile</a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        <span class="badge bg-red pull-right">50%</span>
                                        <span>Settings</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:;">Help</a>
                                </li>
                                <li><a href="../logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>

        </div>
        <!-- /top navigation -->

        <!— page content —>
        <div class="right_col" role="main">
            <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2> System Setting </h2>

                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <p class="text-muted font-13 m-b-30">
                            </p>


                            <div class="panel panel-success">
                                <div class="panel-heading" role="button" data-toggle="collapse" href="#main-setting1"
                                     aria-expanded="true" aria-controls="main-setting1">
                                    <h3 class="panel-title">Course Settings</h3>
                                </div>
                                <div class="panel-body" id="main-setting1">
                                    <div class="col-md-4 col-sm-12 col-xs-12 col-lg-4">
                                        <div class="form-group">
                                            <label for="reg_start_date">Registration start day:</label>
                                            <input type="text" name="registration_start_date" class="form-control"
                                                   id="reg_start_date">
                                        </div>
                                        <div class="form-group">
                                            <label for="reg_end_date">Registration end day:</label>
                                            <input type="text" name="registration_end_date" class="form-control"
                                                   id="reg_end_date">
                                        </div>
                                        <div class="form-group">
                                            <label for="int_start_date">Interview start date:</label>
                                            <input type="text" name="interview_start_date" class="form-control"
                                                   id="int_start_date">
                                        </div>
                                        <div class="form-group">
                                            <label for="interview_end_date">Interview end date:</label>
                                            <input type="text" name="interview_end_date" class="form-control"
                                                   id="interview_end_date">
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-sm-12 col-xs-12 col-lg-4">
                                        <div class="form-group">
                                            <label for="course_start_date">Course start date:</label>
                                            <input type="text" name="course_start_date" class="form-control"
                                                   id="course_start_date">
                                        </div>
                                        <div class="form-group">
                                            <label for="interview_time_for_student">Interview time for student:</label>
                                            <input type="text" name="interview_time_for_student" class="form-control"
                                                   id="interview_time_for_student">
                                        </div>
                                        <div class="form-group">
                                            <label for="student_for_interview_count">Student for interview
                                                count:</label>
                                            <input type="text" name="student_for_interview_count" class="form-control"
                                                   id="student_for_interview_count">
                                        </div>
                                        <div class="form-group">
                                            <label for="student_for_course_count">Student for course count:</label>
                                            <input type="text" name="student_for_course_count" class="form-control"
                                                   id="student_for_course_count">
                                        </div>
                                    </div>

                                    <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                                        <button type="button" id="ButtonIn" class="btn btn-success">Add course settings</button>
                                    </div>
                                </div>
                            </div>


                            <div class="panel panel-success">
                                <div class="panel-heading" role="button" data-toggle="collapse" href="#main-setting2"
                                     aria-expanded="true" aria-controls="main-setting2">
                                    <h3 class="panel-title">Add new personal</h3>
                                </div>
                                <div class="panel-body" id="main-setting2">
                                    <div class="col-md-4 col-sm-12 col-xs-12 col-lg-4">
                                        <p>Choithe role:</p>
                                        <select class="selectpicker" id="PersonalRole">
                                            <option>Admin</option>
                                            <option>BA</option>
                                            <option>HR</option>
                                            <option>DEV</option>
                                            <option>Student</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4 col-sm-12 col-xs-12 col-lg-4">
                                        <div class="form-group">
                                            <label for="name_personal">Name:</label>
                                            <input type="text" name="name_peronal" class="form-control"
                                                   id="name_personal">
                                        </div>
                                        <div class="form-group">
                                            <label for="sername_personal">Surname:</label>
                                            <input type="text" name="sername_peronal" class="form-control"
                                                   id="sername_personal">
                                        </div>
                                        <div class="form-group">
                                            <label for="patronymic_personal">Patronymic:</label>
                                            <input type="text" name="patronymic_peronal" class="form-control"
                                                   id="patronymic_personal">
                                        </div>
                                        <div class="form-group">
                                            <label for="email_personal">Email:</label>
                                            <input type="email" name="email_peronal" class="form-control"
                                                   id="email_personal">
                                        </div>
                                        <div class="form-group">
                                            <label for="password_personal">Password:</label>
                                            <input type="password" name="password_peronal" class="form-control"
                                                   id="password_personal">
                                        </div>
                                    </div>
                                    <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                                        <button type="button" id="ButtonPersonal" class="btn btn-success">Add Personal</button>
                                    </div>
                                </div>
                            </div>


                            <div class="panel panel-success">
                                <div class="panel-heading" role="button" data-toggle="collapse" href="#main-setting4"
                                     aria-expanded="true" aria-controls="main-setting4">
                                    <h3 class="panel-title">Anketa</h3>
                                </div>
                                <div class="panel-body" id="main-setting5">
                                    <div id="Anketa">
                                        <h1>Anketa</h1>
                                        <div class="x_content">
                                            <div class="candidate-profile row">
                                                <form class="form-horizontal form-label-left col-md-9 col-sm-9 col-xs-12" novalidate>
                                                    <div class="questions"></div>
                                                    <div class="ln_solid"></div>
                                                </form>
                                            </div>
                                            <div class="hidden">
                                                <%@ include file="include/Anketa.jsp" %>
                                            </div>
                                        </div>
                                    </div>
                                    </div>

                                </div>
                            </div>


                            <div class="panel panel-success">
                                <div class="panel-heading" role="button" data-toggle="collapse" href="#main-setting3"
                                     aria-expanded="true"
                                     aria-controls="main-setting3">
                                    <h3 class="panel-title">Create Question</h3>
                                </div>
                                <div class="panel-body" id="main-setting3">
                                    <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                                        <div class="row">
                                            <div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
                                                <div class="form-group">
                                                    <label for="Caption">Question:</label>
                                                    <input type="text" name="Caption" class="form-control"
                                                           id="Caption">
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
                                                <div class="TypeOfQuestionNew">
                                                    <select  id="TypeOfQuestion">
                                                        <option selected>Choose Type</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="ComboBox">
                                            <div id="HowToAdd">
                                                <div>
                                                    <div class="form-group NewVariant">
                                                        <span>Possible answers to the question:</span>
                                                        <input type="text" name="VariantQuestion" class="VariantQuestion form-control">
                                                    </div>
                                                    <span class="glyphicon glyphicon-plus-sign plus"></span>
                                                    <span class="glyphicon glyphicon-minus-sign minus"></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                                            <button type="button" id="ButtonQuestion" class="btn btn-success">Add Question</button>
                                        </div>
                                    </div>
                                </div>
                            </div>




                        <div class="panel panel-success">
                            <div class="panel-heading" role="button" data-toggle="collapse" href="#main-setting4"
                                 aria-expanded="true" aria-controls="main-setting4">
                                <h3 class="panel-title">Edit Question parametres</h3>
                            </div>
                            <div class="panel-body" id="main-setting4">
                                <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                                    <table id="dataTableForQuestion" class="table table-striped table-bordered clicked-tr">
                                        <thead>
                                        <tr>
                                            <th>Caption</th>
                                            <th>Type</th>
                                            <th>Is Mandatory</th>
                                        </tr>
                                        </thead>
                                        <tbody id="TableQuestionFull">

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>


                        </div>
                    </div>
                </div>
            </div>
            <!-- footer content -->

            <footer>
                <div class="copyright-info">
                    <p class="pull-right">Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
                    </p>
                </div>
                <div class="clearfix"></div>
            </footer>
            <!-- /footer content -->
        </div>
        <!-- /page content -->

    </div>

</div>
<div id="hider"></div>
<div class="ModelViewDays"></div>
<div id="custom_notifications" class="custom-notifications dsp_none">
    <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
    </ul>
    <div class="clearfix"></div>
    <div id="notif-group" class="tabbed_notifications"></div>
</div>

<script src="<c:url value="/static/admin/js/bootstrap.min.js"/>"></script>

<!-- bootstrap progress js -->
<script src="<c:url value="/static/admin/js/progressbar/bootstrap-progressbar.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/nicescroll/jquery.nicescroll.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/admnin_setting.js"/>"></script>
<script src="<c:url value="/static/admin/js/selectSetting.js"/>"></script>
<script src="<c:url value="/static/admin/js/SelectQuery.js"/>"></script>




<!-- icheck -->
<script src="<c:url value="/static/admin/js/icheck/icheck.min.js"/>"></script>

<script src="<c:url value="/static/admin/js/custom.js"/>"></script>


<!-- Datatables -->
<!-- <script src="js/datatables/js/jquery.dataTables.js"></script>
<script src="js/datatables/tools/js/dataTables.tableTools.js"></script> -->

<!-- Datatables-->
<script src="<c:url value="/static/admin/js/datatables/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/dataTables.buttons.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/buttons.bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/jszip.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/pdfmake.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/vfs_fonts.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/buttons.html5.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/buttons.print.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/dataTables.fixedHeader.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/dataTables.keyTable.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/dataTables.responsive.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/responsive.bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/datatables/dataTables.scroller.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/Question/drawQuestionInProfileForm.js" />"></script>
<script src="<c:url value="/static/admin/js/Question/insertAnswersInCandidateForm.js"/>"></script>
<script src="<c:url value="/static/admin/js/Question/loadingQuestions.js" />"></script>
<script src="<c:url value="/static/admin/js/Question/loadingAnswers.js" />"></script>




<!-- pace -->
<script src="<c:url value="/static/admin/js/pace/pace.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/admin/js/AdminSettings.js"/>"></script>
<!-- /datepicker -->
<!-- /footer content -->
</body>

</html>








