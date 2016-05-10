<%--
  Created by IntelliJ IDEA.
  User: Владимир
  Date: 04.05.2016
  Time: 9:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src='<c:url value="/static/admin/js/report/jquery.table2excel.js"/>'></script>
    <script src='<c:url value="/static/admin/js/report/script.js"/>'></script>
    <link rel="stylesheet" href='<c:url value="/static/admin/css/report/style.css"/>'/>
    <link rel="stylesheet" href="<c:url value="/static/admin/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value='/static/fonts/css/font-awesome.min.css'/>">
</head>

<body>
<%@ include file="include/H1title.jsp" %>
<div class="top-content">
    <div class="container">
        <div class="row">
            <div class="main_form">
                <div class="col-md-4">
                    <button type="button" class="btn create_button">Create New</button>
                    <div class="not_empty_form">
                        <button type="button" class="btn update_button">Update</button>
                        <br>
                        <button type="button" class="btn delete_button">Delete</button>
                        <br>
                        <button type="button" class="btn show_button">Show report</button>
                        <br>
                        <button type="button" class="btn show_all_button">Show All reports</button>
                    </div>
                </div>
                <div class="col-md-4">
                    <form class="report_form">
                    </form>
                    <div class="save_buttons">
                        <button type="button" class=" btn save_list_button">Save</button>
                        <button type="button" class="btn cancel_list_button">Cancel</button>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="immut_text">
                    </div>
                    <div class="show_table">
                        <div class="report_table">
                            <table width="600" border="1" class="report" id = "table2excel">
                            </table>
                        </div>
                        <br>
                        <button type="button" class="btn export_button">Export to Excel</button>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <form class="report_edit">
                        <h3>Brief description</h3>
                        <input type="text" class="text_description" size="45">

                        <h3>Report Query</h3>
                        <textarea class="text_query" cols="45">flfkfkfkfkf</textarea>
                        <br>
                        <button type="button" class=" btn save_button">Save</button>
                        <button type="button" class="btn cancel_button">Cancel</button>
                        <br>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp" %>
</body>

</html>
