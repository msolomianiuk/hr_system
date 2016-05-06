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

<form method="post">
  <table>
    <h1/>Interview Days Details
    <tr>
      <td>Date</td>
      <td><input name="date" placeholder=""></td>
    </tr>
    <tr>
      <td>Start_time</td>
      <td><input name="start_time" placeholder=""></td>
    </tr>
    <tr>
      <td>End_time</td>
      <td><input name="end_time" placeholder=""></td>
    </tr>
    <tr>
      <td>Address_id</td>
      <td><input name="address_id" placeholder=""></td>
    </tr>
    <tr>
      <td>
        <input type="button" id="ButtonInsert" value="Add">
        <input type="button" id="ButtonShow" value="Show all">
      </td>
    </tr>
    <tr>
      <td>id</td>
      <td><input name="id" placeholder=""></td>
    </tr>
    <tr>
      <td>
        <input type="button" id="ButtonDeleteInt" value="Delete">
        <input type="button" id="ButtonUpdateInt" value="Edit">
      </td>
    </tr>
    <tr>
      <td>
        <input type="button" id="ButtonAddressPage" value="Add new address">
      </td>
    </tr>
    <tr>
      <td><input name="query" placeholder=""></td>
      <td><input type="button" id="ButtonQuery" value="Query"></td>
    </tr>
  </table>
</form>

<div class="First">

</div>
<%@ include file="include/footer.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
  $(document).ready(function () {

    $("#ButtonQuery").bind("click", function () {
      var query = $("input[name='query']").val();

      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/inter_day_query",
        type: "GET",
        dataType: "json",
        data: {'query': query},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcForAjax,
        error: function (data) {
          console.log(data);
        }
      });
    });

    $("#ButtonInsert").bind("click", function () {
      var date = $("input[name='date']").val();
      var start_time = $("input[name='start_time']").val();
      var end_time = $("input[name='end_time']").val();
      var address_id = $("input[name='address_id']").val();

      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/inter_day_insert",
        type: "GET",
        dataType: "json",
        data: {'date': date, 'start_time': start_time, 'end_time': end_time, 'address_id': address_id},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcForAjax,
        error: function (data) {
          console.log(data);
        }
      });
    });

    $("#ButtonDeleteInt").bind("click", function () {
      var id = $("input[name='id']").val();

      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/inter_day_deleteInt",
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

    $("#ButtonUpdateInt").bind("click", function () {
      var id = $("input[name='id']").val();
      var date = $("input[name='date']").val();
      var start_time = $("input[name='start_time']").val();
      var end_time = $("input[name='end_time']").val();
      var address_id = $("input[name='address_id']").val();

      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/inter_day_updateInt",
        type: "GET",
        dataType: "json",
        data: {
          'id': id,
          'date': date,
          'start_time': start_time,
          'end_time': end_time,
          'address_id': address_id
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
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/inter_day_list",
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

    $("#ButtonAddressPage").bind("click", function () {
      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/service/inter/address",
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
//        alert("Hello");
  }

  function funcSuccess(data) {
//        alert("Hello");
    $(".First").empty();
    dataNew = data;
    for (index in data) {
      $(".First").append(
              '<p class="p1">' + data[index].id + '</p>' +
              '<p class="p2">' + data[index].courseId + '</p>' +
              '<p class="p3">' + data[index].interviewDate + '</p>' +
              '<p class="p4">' + data[index].startTime + '</p>' +
              '<p class="p5">' + data[index].endTime + '</p>' +
              '<p class="p6">' + data[index].addressId + '</p>' +
              '<div class="clear"></div>');
    }
  }

</script>
</body>
</html>
