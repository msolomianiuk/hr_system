<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="<c:url value="/static/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.min.css'/>">
</head>
<body>

<%@ include file="include/H1title.jsp" %>
<style>

    table {
        margin: auto;
        color: #fff;
    }
    .btn {
        width: auto;
        height: 50px;
        margin-top: 10px;
        padding: 0 20px;
        vertical-align: middle;
        background: #19b9e7;
        border: 0;
        font-family: 'Roboto', sans-serif;
        font-size: 16px;
        font-weight: 300;
        line-height: 50px;
        color: #fff;
        -moz-border-radius: 4px;
        -webkit-border-radius: 4px;
        border-radius: 4px;
        text-shadow: none;
        -moz-box-shadow: none;
        -webkit-box-shadow: none;
        box-shadow: none;
        -o-transition: all .3s;
        -moz-transition: all .3s;
        -webkit-transition: all .3s;
        -ms-transition: all .3s;
        transition: all .3s;
    }

    body {
        background: linear-gradient(to bottom, #2A3F54, #19B9E7);
        text-align: center;
    }

    footer {
        padding-bottom: 70px;
        color: #fff;
    }

    footer .footer-border {
        width: 200px;
        margin: 0 auto;
        padding-bottom: 30px;
        border-top: 1px solid #fff;
        border-top: 1px solid rgba(255, 255, 255, 0.6);
    }

    footer p {
        opacity: 0.8;
    }

    footer a {
        color: #fff;
    }

    footer a:hover,
    footer a:focus {
        color: #fff;
        border-bottom: 1px dotted #fff;
    }

    h1,
    h2 {
        margin-top: 10px;
        font-size: 38px;
        font-weight: 100;
        color: #fff;
        line-height: 50px;
    }

    .p1 {
        float: left;
    }

    .p2 {
        float: left;
        margin-left: 5px;
    }

    .p3 {
        float: left;
        margin-left: 5px;
    }

    .p4 {
        float: left;
        margin-left: 5px;
    }

    .p5 {
        float: left;
        margin-left: 5px;
    }

    .clear {
        clear: left;
    }

</style>
<div class="top-content">
    <div class="container">
        <div class="row">
            <form method="post">
                <table>
                    <h1/>Address
                    <tr>
                        <td>address</td>
                        <td><input name="address" class="field" placeholder=""></td>
                    </tr>
                    <tr>
                        <td>roomCapacity</td>
                        <td><input name="roomCapacity" class="field" placeholder=""></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" class='btn' id="ButtonInsert" value="Add">
                            <input type="button" class='btn' id="ButtonShow" value="Show all">
                        </td>
                    </tr>
                    <tr>
                        <td>id</td>
                        <td><input name="id" placeholder=""></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" class='btn' id="ButtonDelete" value="Delete">
                            <input type="button" class='btn' id="ButtonUpdate" value="Edit">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="../interviewDetails"> <input type="button" class='btn' id="ButtonReturn"
                                                       value="Return to Interview Details"> </a>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" class='btn' id="ButtonAddress"
                                                                  value="Test">
                        </td>
                    </tr>
                </table>
            </form>

            <div class="First">
            </div>

        </div>
    </div>
</div>
<%@ include file="include/footer.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
    $(document).ready(function () {

        $("#ButtonInsert").bind("click", function () {
            var address = $("input[name='address']").val();
            var roomCapacity = $("input[name='roomCapacity']").val();

            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/address_insert",
                type: "GET",
                dataType: "json",
                data: {'address': address, 'roomCapacity': roomCapacity},
                contentType: 'application/json',
                mimeType: 'application/json',
                success: funcForAjax,
                error: function (data) {
                    console.log(data);
                }
            });
        });

        $("#ButtonDelete").bind("click", function () {
            var id = $("input[name='id']").val();

            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/address_delete",
                type: "GET",
                dataType: "json",
                data: {'id': id},
                contentType: 'application/json',
                mimeType: 'application/json',
                success: funcForAjax,
                error: function (data) {
                    console.log(data);
                }
            });
        });

        $("#ButtonAddress").bind("click", function () {
//            var id = $("input[name='id']").val();

            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/address_getDateList",
                dataType: "json",
//                data: {'id': id},
                contentType: 'application/json',
                mimeType: 'application/json',
                success: funcForAjax,
                error: function (data) {
                    console.log(data);
                }
            });
        });

        $("#ButtonUpdate").bind("click", function () {
            var id = $("input[name='id']").val();
            var address = $("input[name='address']").val();
            var roomCapacity = $("input[name='roomCapacity']").val();

            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/address_update",
                type: "GET",
                dataType: "json",
                data: {
                    'id': id,
                    'address': address,
                    'roomCapacity': roomCapacity
                },
                contentType: 'application/json',
                mimeType: 'application/json',
                success: funcForAjax,
                error: function (data) {
                    console.log(data);
                }
            });
        });

        $("#ButtonShow").bind("click", function () {
            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/address_list",
                type: "GET",
                dataType: "json",
                contentType: 'application/json',
                mimeType: 'application/json',
                success: funcSuccess,
                error: function (data) {
                    console.log(data);
                }
            });
        });

        $("#ButtonReturn").bind("click", function () {
            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/service/interviewDetails",
                type: "GET",
                dataType: "json",
                contentType: 'application/json',
                mimeType: 'application/json',
                success: funcForAjax,
                error: function (data) {
                    console.log(data);
                }
            });
        });

    });
    function funcForAjax(data) {
        newData = data;
        $(".field").val("");
        $("#ButtonShow").click();
//        alert("Hello");
    }

    function funcSuccess(data) {
//        alert("Hello");
        $(".First").empty();
        dataNew = data;
        for (index in data) {
            $(".First").append(
                    '<p class="p1">' + data[index].id + '</p>' +
                    '<p class="p2">' + data[index].address + '</p>' +
                    '<p class="p3">' + data[index].roomCapacity + '</p>' +
                    '<div class="clear"></div>');
        }
    }

</script>
</body>
</html>
