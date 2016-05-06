<%--
  Created by IntelliJ IDEA.
  User: Владимир
  Date: 29.04.2016
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <title></title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
  <script src='<c:url value="/static/admin/js/template/script.js"/>'></script>
  <link rel="stylesheet" href='<c:url value="/static/admin/css/template/style.css"/>'/>
  <link rel="stylesheet" href="<c:url value="/static/bootstrap/css/bootstrap.min.css"/>">
  <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.min.css'/>">
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

          </div>
        </div>
        <div class="col-md-4">
          <form class="template_form">
          </form>
        </div>
        <div class="col-md-4 immut_text">
        </div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <form class="template_edit">
            <h3>Brief description</h3>
            <input type="text" class="text_description" size="45">
            <h3>Email template</h3>
            <textarea class="text_email_template" cols="45">flfkfkfkfkf</textarea>
            <br>
            <button type="button" class=" btn save_button">Save</button>
            <button type="button" class="btn cancel_button">Cancel</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="include/footer.jsp" %>
</body>

</html>