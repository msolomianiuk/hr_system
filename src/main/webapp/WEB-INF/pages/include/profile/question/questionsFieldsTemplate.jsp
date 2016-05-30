<form class="form-horizontal form-label-left" novalidate>
    <!-- text input -->
    <div class="item form-group text-input-type">
    <%@ include file="questionFieldsObvertkaTop.jsp" %>
        <input type="text" name="" required="required" class=" form-control col-md-7 col-xs-12" placeholder="">
    <%@ include file="questionFieldsObvertksFooter.jsp"%>

    <!-- number input -->
    <div class="item form-group int-input-type">
    <%@ include file="questionFieldsObvertkaTop.jsp" %>
        <input type="number" name="" required="required" data-validate-minmax="1,100000" class="form-control col-md-7 col-xs-12" placeholder="">
    <%@ include file="questionFieldsObvertksFooter.jsp"%>

    <!-- select input -->
    <div class="item form-group select-input-type">
    <%@ include file="questionFieldsObvertkaTop.jsp" %>
        <select class="form-control col-md-7 col-xs-12"></select>
    <%@ include file="questionFieldsObvertksFooter.jsp"%>

    <!-- Checkbox button -->
    <div class="item form-group check-input-type">
    <%@ include file="questionFieldsObvertkaTop.jsp" %>
        <div class="checkbox col-md-12 col-sm-12 col-xs-12">
            <div class="col-md-4 col-sm-4 col-xs-6 column-1 text-left"></div>
            <div class="col-md-4 col-sm-4 col-xs-6 column-2 text-left"></div>
            <div class="col-md-4 col-sm-4 col-xs-6 column-3 text-left"></div>
        </div>
    <%@ include file="questionFieldsObvertksFooter.jsp"%>

    <!-- select and text input -->
    <div class="item form-group textANDselect-input-type">
    <%@ include file="questionFieldsObvertkaTop.jsp" %>
        <select class="form-control col-md-7 col-xs-12 select-and-text">
            <option>Choose option</option>
            <option>Option one</option>
            <option>Option two</option>
            <option>Option three</option>
            <option class="other">other</option>
        </select>
        <input type="text" name="" required="required" class="form-control col-md-7 col-xs-12 select-other" placeholder="">
    <%@ include file="questionFieldsObvertksFooter.jsp"%>
</form>