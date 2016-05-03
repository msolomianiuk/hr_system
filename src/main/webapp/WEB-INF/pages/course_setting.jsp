<%--
  Created by IntelliJ IDEA.
  User: Legion
  Date: 27.04.2016
  Time: 1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>

<form method="post">
  <table>

    <h1/>Course_Setting



    <tr>
      <td>registration_start_date (date-format(yyyy-mm-dd))</td>
      <td><input name="registration_start_date" placeholder="yyyy-mm-dd"></td>
    </tr>
    <tr>
      <td>registration_end_date(date-format(yyyy-mm-dd))</td>
      <td><input name="registration_end_date" placeholder="yyyy-mm-dd"></td>
    </tr>
    <tr>
      <td>interview_start_date(date-format(yyyy-mm-dd))</td>
      <td><input name="interview_start_date" placeholder="yyyy-mm-dd"></td>
    </tr>
    <tr>
      <td>interview_end_date(date-format(yyyy-mm-dd))</td>
      <td><input name="interview_end_date" placeholder="yyyy-mm-dd"></td>
    </tr>
    <tr>
      <td>course_start_date(date-format(yyyy-mm-dd))</td>
      <td><input name="course_start_date" placeholder="yyyy-mm-dd"></td>
    </tr>
    <tr>
      <td>interview_time_for_student(min.)</td>
      <td><input name="interview_time_for_student" placeholder="interview_time_for_student"></td>
    </tr>
    <tr>
      <td>student_for_interview_count</td>
      <td><input name="student_for_interview_count" placeholder="Enter student_for_interview_count"></td>
    </tr>
    <tr>
      <td>student_for_course_count</td>
      <td><input name="student_for_course_count" placeholder="Enter student_for_course_count"></td>
    </tr>
    <tr>
      <td><input type="button" id="ButtonIn" value="Add Course Setting"></td>
    </tr>
  </table>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
  $(document).ready(function () {

    $("#ButtonIn").bind("click", function (){
      var registration_start_date = $("input[name='registration_start_date']").val();
      var registration_end_date = $("input[name='registration_end_date']").val();
      var interview_start_date = $("input[name='interview_start_date']").val();
      var interview_end_date = $("input[name='interview_end_date']").val();
      var course_start_date = $("input[name='course_start_date']").val();
      var interview_time_for_student = $("input[name='interview_time_for_student']").val();
      var student_for_interview_count = $("input[name='student_for_interview_count']").val();
      var student_for_course_count = $("input[name='student_for_course_count']").val();



      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/course_setting",
        type: "GET",
        //   contentType : "application/json",
        //beforeSend: funcbefor,
        dataType: "json",
        data:{'registrationStartDate':registration_start_date,
          'registrationEndDate':registration_end_date,
          'interviewStartDate':interview_start_date,
          'interviewEndDate':interview_end_date,
          'courseStartDate':course_start_date,
          'interviewTimeForStudent':interview_time_for_student,
          'studentForInterviewCount':student_for_interview_count,
          'studentForCourseCount':student_for_course_count},
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
