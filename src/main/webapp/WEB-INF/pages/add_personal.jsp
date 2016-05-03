<%--
  Created by IntelliJ IDEA.
  User: Legion
  Date: 27.04.2016
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form>
  <input type="text" name="roleId" placeholder="roleId">
  <input type="text" name="name" placeholder="name">
  <input type="text" name="surname" placeholder="surname">
  <input type="text" name="email" placeholder="email">
  <input type="text" name="password" placeholder="password">
  <input type="text" name="patronymic" placeholder="patronymic">
  <input type="button" id="SetUser">Зарегестрировать
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
  $(document).ready(function () {

    $("#ButtonIn").bind("click", function () {
      var idRole = parseInt($("input[name='idRole']").val());
      var name = $("input[name='name']").val();
      var email = $("input[name='email']").val();
      var patronymic = $("input[name='patronymic']").val();
      var password = $("input[name='password']").val();


      $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/new_personal",
        type: "GET",
        dataType: "json",
        data: {'roleId': roleId, 'name': name, 'email': email, 'password': password, 'patronymic': patronymic},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcReg,
        error: function (data) {
          console.log(data);
        }
      });
    });

  });
  function funcReg(data) {
    alert("Зарегано");
  }
</script>


</body>
</html>
