<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<%@ include file="include/top.jsp" %>
<style>
    .p1{
        float:left;
    }
    .p2{
        float:left;
        margin-left:15px;
    }
    .clear{
        clear:left;
    }
</style>
<h1>Privet Admin</h1>

<h1>Results</h1>

<form>
    <input type="text" name="TestInput">
    <input type="button" id="ButtonIn" value="ClickMe">
</form>
<button class="buttomsearch">Click</button>
<div class="First">

</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
    $(document).ready(function () {

        $("#ButtonIn").bind("click", function (){
            id = parseInt($("input[name='TestInput']").val());

            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/user",
                type: "GET",
                //   contentType : "application/json",
                //beforeSend: funcbefor,
                dataType: "json",
                data:{'id':id},
                contentType: 'application/json',
                mimeType: 'application/json',
                success: funcForAjax,
                error: function (data) {
                    console.log(data);
                }
            });
        });

        $(".buttomsearch").bind("click", function () {

            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/student/",
                type: "GET",
                //beforeSend: funcbefor,
                dataType: "json",
                success: funcSuccess,
                error: function (data) {
                    console.log(data);
                }
            });
        });
    });

    //    function funcbefor() {
    //        $("#NameTable").text("Загрузка данных");
    //    }
    function funcSuccess(data) {
        $(".First").empty();
        dataNew = data;
        for (var index in data) {
            $(".First").append('<div class="Second"></div>');
            $(".Second").append('<p class="p1">' + data[index].user.email +'</p>'+
            '<p class="p2">' + data[index].user.name +'</p>'+
            '<div class="clear"></div>');
        }
    }
    function funcForAjax(data){
        $(".First").empty();
        dataNewIn = data;
        for (var index in data) {
            $(".First").append('<div class="Second"></div>');
            $(".Second").append('<p class="p1">' + data[index].email +'</p>'+
            '<p class="p2">' + data[index].name +'</p>'+
            '<div class="clear"></div>');
        }

    }
</script>
</body>
</html>