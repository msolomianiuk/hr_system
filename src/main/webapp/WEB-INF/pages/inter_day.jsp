
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>

<form method="post">
  <table>

    <h1/>Interview Days Details

    <tr>
      <td>course_id</td>
      <td><input name="course_id" placeholder=""></td>
    </tr>
    <tr>
      <td>interviewDate</td>
      <td><input name="interviewDate" placeholder=""></td>
    </tr>
    <tr>
      <td>addressId</td>
      <td><input name="addressId" placeholder=""></td>
    </tr>
    <tr>
      <td>employeesMaxCount</td>
      <td><input name="employeesMaxCount" placeholder=""></td>
    </tr>
    <tr>
      <td>candidateMaxCount</td>
      <td><input name="candidateMaxCount" placeholder=""></td>
    </tr>
      <td><input type="button" id="ButtonIn" value="Add Interview Days Details"></td>
    </tr>
  </table>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
  $(document).ready(function () {

    $("#ButtonIn").bind("click", function (){
      var course_id = $("input[name='course_id']").val();
      var interviewDate = $("input[name='interviewDate']").val();
      var addressId = $("input[name='addressId']").val();
      var employeesMaxCount = $("input[name='employeesMaxCount']").val();
      var candidateMaxCount = $("input[name='candidateMaxCount']").val();

      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/inter_day_req",
        type: "GET",
        //   contentType : "application/json",
        //beforeSend: funcbefor,
        dataType: "json",
        data:{'course_id':course_id,'interviewDate':interviewDate,'addressId':addressId,'employeesMaxCount':employeesMaxCount,'candidateMaxCount':candidateMaxCount},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcForAjax,
        error: function (data) {
          console.log(data);
        }
      });
    });
  });
  function funcForAjax(data){
    newData = data;
    alert("Hello");
  }
</script>
</body>
</html>
