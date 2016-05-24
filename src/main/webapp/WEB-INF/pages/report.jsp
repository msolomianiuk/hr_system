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
    <script src='<c:url value="/static/admin/js/report/jquery.table2excel.js"/>'></script>
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

</head>

<body class="nav-md">
<div class="container body">


    <div class="main_container">

        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">

                <div class="navbar nav_title" style="border: 0;">
                    <a href="testAdmin" class="site_title"><i class="fa fa-paw"></i>
                        <span>Net<strong>Cracker</strong></span></a>
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

                        <h2>Vasya Pupkin</h2>
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
                                <img src="<c:url value="/static/admin/images/img.jpg"/>" alt="">John Doe
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
                                <li>
                                    <a href="logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                                </li>
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
                                <h3 class="panel-title">Report</h3>
                            </div>
                            <div class="panel-body" id="main-setting1">
                               <div class="main_form">
                                        <button type="button" class="btn btn-success create_button">Create New</button>
                                        <div class="not_empty_form">
                                            <button type="button" class="btn btn-success update_button">Update</button>

                                            <button type="button" class="btn btn-success delete_button">Delete</button>

                                            <button type="button" class="btn btn-success show_button">Show report
                                            </button>

                                            <button type="button" class="btn btn-success show_all_button">Show All
                                                reports
                                            </button>
                                        </div>

                                        <form class="report_form">
                                        </form>
                                        <div class="save_buttons">
                                            <button type="button" class="btn btn-success save_list_button">Save</button>
                                            <button type="button" class="btn btn-success cancel_list_button">Cancel
                                            </button>
                                        </div>

                                        <form class="form-group">
                                        <div class="immut_text">
                                        </div>
                                        </form>
                                        <div class="show_table">
                                            <div class="report_table">
                                                <table width="600" border="1" class="report" id="table2excel">
                                                </table>
                                            </div>
                                            <br>
                                            <button type="button" class="btn btn-success export_button"><a
                                                    href="<c:url value="/admin/service/getReportInXlSX"/>">Export to
                                                Excel</a></button>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <form class="report_edit form-group">
                                                <h3>Report Name</h3>
                                                <input type="text" class="text_description" size="45">

                                                <h3>Report Query</h3>
                                                <textarea class="text_query">flfkfkfkfkf</textarea>
                                                <br>
                                                <button type="button" class=" btn btn-success save_button">Save</button>
                                                <button type="button" class="btn btn-success cancel_button">Cancel
                                                </button>
                                                <br>
                                            </form>
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



</body>

</html>
