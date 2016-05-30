<%--
  Created by IntelliJ IDEA.
  User: Владимир
  Date: 04.05.2016
  Time: 9:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <%--<script src='<c:url value="/static/admin/js/report/jquery.table2excel.js"/>'></script>--%>
    <script src='<c:url value="/static/admin/js/report/script.js"/>'></script>
    <link rel="stylesheet" href='<c:url value="/static/admin/css/report/style.css"/>'/>

    <!-- Bootstrap core CSS -->

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
    <script src="<c:url value="/static/admin/js/report/jquery.validate.min.js"/>"></script>
    <script src="<c:url value="/static/admin/js/textarea/autosize.min.js"/>"></script>
    <script src="<c:url value="/static/admin/js/nprogress.js"/>"></script>
    <script src="<c:url value="/static/js/custom/baseUrl.js"/>"></script>
</head>

<body class="nav-md">
<div class="modals">
    <!-- Modal -->
    <%@ include file="include/uploadPhotoModal.jsp" %>
</div>
<div class="container body">


    <div class="main_container">

        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">

                <div class="navbar nav_title" style="border: 0;">
                    <a href="#" class="site_title"><i class="fa fa-paw"></i>
                        <span>Net<strong>Cracker</strong></span></a>
                </div>
                <div class="clearfix"></div>

                <!-- menu prile quick info -->
                <%@ include file="include/profilePicInf.jsp" %>
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
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">

            <div class="nav_menu ">
                <nav class="" role="navigation">
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="false">
                                <img src="<c:url value="${photo}"/>" alt="">
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">                                    <a href="logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                                <a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>

        </div>
        <!-- /top navigation -->


        <!-- page content -->
        <div class="right_col" role="main">
            <!-- top tiles -->
            <div class="row tile_count">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2> Reports </h2>

                            <div class="clearfix"></div>
                        </div>
                        <div class="panel panel-success">
                            <div class="panel-heading" role="button" data-toggle="collapse" href="#main-setting1"
                                 aria-expanded="true" aria-controls="main-setting1">
                                <h3 class="panel-title">Main Report</h3>
                                <p>Select course, students status and click button Show Reports </p>
                            </div>
                            <div class="panel-body" id="main-setting1">
                                <div class="main_form">
                                    <select class="btn btn-primary course_setting">
                                    </select>
                                    <select class="btn btn-primary status">
                                    </select>
                                    <button type="button" class="btn btn-success show_button" data-toggle="modal"
                                            data-target=".bs-example-modal-lg" value="main">Show Report
                                    </button>

                                </div>
                            </div>
                        </div>

                        <div class="panel panel-info">
                            <div class="panel-heading" role="button" data-toggle="collapse" href="#main-setting2"
                                 aria-expanded="true" aria-controls="main-setting2">
                                <h3 class="panel-title">Other Reports</h3>

                                <p>Select the required report and click on it</p>
                            </div>
                            <div class="panel-body" id="main-setting2">
                                <div class="main_form">
                                    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
                                         aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span
                                                            aria-hidden="true">×</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel">You can download this
                                                        report</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <%--<h4></h4>--%>
                                                    <div class="report_table">
                                                        <table class="report table table-striped table-bordered"
                                                               id="table2excel">
                                                        </table>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" id=" btn-close"
                                                            data-dismiss="modal">
                                                        Close
                                                    </button>
                                                    <a href=<c:url value="/admin/service/getReportInXlSX"/>>
                                                        <button type="button" class="btn btn-primary export_button">
                                                            Export to Excel
                                                        </button>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="x_panel">
                                            <div class="x_content">
                                                <div class="row report_form">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <div class="panel panel-warning">
                            <div class="panel-heading collapsed" role="button" data-toggle="collapse"
                                 href="#main-setting3"
                                 aria-expanded="false" aria-controls="main-setting3">
                                <h3 class="panel-title">Developer Panel</h3>

                                <p>If You want to change reports and You are Developer. You are welcome.</p>

                                <p>Click on required report or create new report</p>
                            </div>
                            <div class="panel-body collapse" aria-expanded="false" id="main-setting3"
                                 style="height: 30px;">
                                <div class="main_form">
                                    <button type="button" class="btn btn-success btn-create" data-toggle="modal"
                                            data-target=".bs-example-modal-lg-dev">Create New
                                    </button>
                                    <button type="button" class="btn btn-success btn-show-deleted-reports" data-toggle="modal"
                                            data-target=".modal-lg-dev-del-reports">View Deleted Reports
                                    </button>
                                    <form class="report_developer_form">
                                    </form>
                                    <div class="modal fade bs-example-modal-lg-dev" tabindex="-1" role="dialog"
                                         aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span
                                                            aria-hidden="true">×</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel1">You can create
                                                        this report</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <form class="validate">
                                                        <div class="dev_panel_description">
                                                        </div>
                                                        <br>
                                                        <div class="dev_panel_query">
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" id="btn-close1"
                                                            data-dismiss="modal">
                                                        Close
                                                    </button>
                                                    <button type="button" class="btn btn-primary btn-update">Update
                                                    </button>
                                                    <button type="button" class="btn btn-primary btn-delete"
                                                            data-dismiss="modal">Delete
                                                    </button>
                                                    <button type="button" class="btn btn-primary btn-save">Save</button>
                                                    <%--<button type="button" class="btn btn-primary btn-cancel">Cancel--%>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade modal-lg-dev-del-reports" tabindex="-1" role="dialog"
                                         aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span
                                                            aria-hidden="true">×</span>
                                                    </button>
                                                    <h4 class="modal-title" id="myModalLabel2">You can return reports to
                                                        main list</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class = "deleted_reports">
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" id="btn-close"
                                                            data-dismiss="modal">
                                                        Close
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- footer content -->

        </div>
    </div>
</div>
<!-- footer content -->
<script src="<c:url value="/static/admin/js/bootstrap.min.js"/>"></script>

<!-- bootstrap progress js -->
<script src="<c:url value="/static/admin/js/progressbar/bootstrap-progressbar.min.js"/>"></script>
<script src="<c:url value="/static/admin/js/nicescroll/jquery.nicescroll.min.js"/>"></script>
<%--<script src="<c:url value="/static/admin/js/admnin_setting.js"/>"></script>--%>
<script src="<c:url value="/static/admin/js/selectSetting.js"/>"></script>
<%--<script src="<c:url value="/static/admin/js/SelectQuery.js"/>"></script>--%>


<!-- icheck -->
<script src="<c:url value="/static/admin/js/icheck/icheck.min.js"/>"></script>

<%--<script src="<c:url value="/static/admin/js/custom.js"/>"></script>--%>


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
<%-- --%>


<!-- pace -->
<script src="<c:url value="/static/admin/js/pace/pace.min.js"/>"></script>

</body>

</html>
