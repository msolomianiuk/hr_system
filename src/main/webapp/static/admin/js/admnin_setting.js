/**
 * Created by Серый on 03.05.2016.
 */

$(document).ready(function () {


    location_origin = "http://31.131.25.206:8080/hr_system-1.0-SNAPSHOT"


    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });





    $.ajax({
        url: location_origin+"/getCurseId",
        type: "POST",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getCourseID,
        error: function (data) {
            console.log(data);
        }
    });


    $.ajax({
        url: location_origin +"/getQuantityQuestions",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getQuantityQuestions,
        error: function (data) {
            console.log(data);
        }
    });

    $(document).on("click","#ButtonQuestion",function(){

        var caption = $("input[name='Caption']").val();
        var curse_id = CurseID;
        var typeValue = $("#TypeOfQuestion").val();
        var additionValue = null;
        var isMandatory = true;//
        var orderNumber = QuantityQuestions+1;
        var isView = true;


        $.ajax({
            url: location_origin+"/setQuestion",
            type: "POST",
            data: {
                'caption': caption,
                'curse_id': curse_id,
                'typeValue': typeValue,
                'additionValue': additionValue,
                'isMandatory': isMandatory,
                'orderNumber': orderNumber,
                'isView':isView
            },
            dataType: "json",
            success: setQuestionWithAdd,
            error: function (data) {
                console.log(data);
            }
        });

    });
    $(document).on("click","#ButtonQuestionWithAdd",function(){

        var caption = $("input[name='Caption']").val();
        var curse_id = CurseID;
        var typeValue = $("#TypeOfQuestion").val();
        var additionValueNoAjax = [];
        $( ".VariantQuestion" ).each(function(index) {
            additionValueNoAjax[index]= $( this ).val();
        });
        var additionValue = JSON.stringify(additionValueNoAjax);
        var isMandatory = true;
        var orderNumber = QuantityQuestions+1;
        var isView = true;
        $.ajax({
            url: location_origin+"/setQuestion",
            type: "POST",
            data: {
                'caption': caption,
                'curse_id': curse_id,
                'typeValue': typeValue,
                'additionValue': additionValue,
                'isMandatory': isMandatory,
                'orderNumber': orderNumber,
                'isView': isView
            },
            dataType: "json",
            success: setQuestionWithAdd,
            error: function (data) {
                console.log(data);
            }
        });

    });


    $.ajax({
        url: location_origin+"/admin/up_setting",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getSetting,
        error: function (data) {
            console.log(data);
        }
    });
    $.ajax({
        url: location_origin+"/getTypeOfQuestions",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getTypeOfQuestions,
        error: function (data) {
            console.log(data);
        }
    });
    $("#ButtonIn").bind("click", function () {
        var registration_start_date = $("input[name='registration_start_date']").val();
        var registration_end_date = $("input[name='registration_end_date']").val();
        var interview_start_date = $("input[name='interview_start_date']").val();
        var interview_end_date = $("input[name='interview_end_date']").val();
        var course_start_date = $("input[name='course_start_date']").val();
        var interview_time_for_student = $("input[name='interview_time_for_student']").val();
        var student_for_interview_count = $("input[name='student_for_interview_count']").val();
        var student_for_course_count = $("input[name='student_for_course_count']").val();


        $.ajax({
            url: location_origin+"/admin/admin_setting",
            type: "GET",
            dataType: "json",
            data: {
                'registrationStartDate': registration_start_date,
                'registrationEndDate': registration_end_date,
                'interviewStartDate': interview_start_date,
                'interviewEndDate': interview_end_date,
                'courseStartDate': course_start_date,
                'interviewTimeForStudent': interview_time_for_student,
                'studentForInterviewCount': student_for_interview_count,
                'studentForCourseCount': student_for_course_count

            },
            contentType: 'application/json',
            mimeType: 'application/json',
            success: setSetting,
            error: function (data) {
                console.log(data);
            }
        });
    });
    $("#ButtonPersonal").bind("click", function () {

        var name_peronal = $("input[name='name_peronal']").val();
        var sername_peronal = $("input[name='sername_peronal']").val();
        var patronymic_peronal = $("input[name='patronymic_peronal']").val();
        var email_peronal = $("input[name='email_peronal']").val();
        var role_personal = $("#PersonalRole").val();
        var password_peronal = $("input[name='password_peronal']").val();

        if (role_personal == "Admin") {
            Role_Id = "ROLE_ADMIN";
        }
        if (role_personal == "BA") {
            Role_Id = "ROLE_BA";
        }
        if (role_personal == "HR") {
            Role_Id = "ROLE_HR";
        }
        if (role_personal == "DEV") {
            Role_Id = "ROLE_DEV";
        }
        if (role_personal == "Student") {
            Role_Id = "ROLE_STUDENT";
        }

        $.ajax({
            url: location_origin+"/new_personal",
            type: "POST",
            dataType: "json",
            data: {
                'name_peronal': name_peronal,
                'sername_peronal': sername_peronal,
                'patronymic_peronal': patronymic_peronal,
                'email_peronal': email_peronal,
                'Role_Id': Role_Id,
                'password_peronal': password_peronal
            },
            success: setPersonal,
            error: function (data) {
                console.log(data);
            }
        });


    });

    $('body').on("click",".plus",function(){
        $("#HowToAdd").append('<div>'+
        '<div class="form-group NewVariant">'+
        '<span>Possible answers to the question:</span>'+
        '<input type="text" name="VariantQuestion" class="VariantQuestion form-control">'+
        '</div>'+
        '<span class="glyphicon glyphicon-plus-sign plus"></span>'+
        '<span class="glyphicon glyphicon-minus-sign minus newminus"></span>'+
        '</div>');
    });
    $('body').on("click",".newminus",function(){
        $(this).parent().remove();
    });

    $('body').on("click",".plusUpdate",function(){
        $("#HowToAddUpdate").append('<div>'+
        '<div class="form-group NewVariant">'+
        '<span>Possible answers to the question:</span>'+
        '<input type="text" name="VariantQuestion" class="VariantQuestion VariantQuestionUpdate form-control">'+
        '</div>'+
        '<span class="glyphicon glyphicon-plus-sign plusUpdate"></span>'+
        '<span class="glyphicon glyphicon-minus-sign minus newminus"></span>'+
        '</div>');
    });



    $(document).on("click",".getModalAddress",function(){

        var id  = parseInt(($(this).attr("question_id")));

        $.ajax({
            url: location_origin + "/admin/get_question",
            type: "GET",
            dataType: "json",
            data:{'id':id},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: getQuestion,
            error: function (data) {
                console.log(data);
            }
        });

    });

    $(document).on("click","#ButtonQuestionUpdate",function(){

        var caption = $("input[name='CaptionUpdate']").val();
        var curse_id = CurseID;
        var typeValue = $("#TypeOfQuestionSolo").val();
        var additionValue = null;
        var isMandatory = $("#IsMandatorySolo").val();
        var orderNumber = parseInt(($("#CaptionUpdate").attr("order_number")));
        var id = parseInt(($("#CaptionUpdate").attr("question_id")));
        var isView = true;


        $.ajax({
            url: location_origin+"/update_question",
            type: "POST",
            data: {
                'caption': caption,
                'curse_id': curse_id,
                'typeValue': typeValue,
                'additionValue': additionValue,
                'isMandatory': isMandatory,
                'orderNumber': orderNumber,
                'id':id,
                'isView': isView
            },
            dataType: "json",
            success: updateQuestionWithAdd,
            error: function (data) {
                console.log(data);
            }
        });

    });
    $(document).on("click","#ButtonQuestionUpdateWithAdd",function(){

        var caption = $("input[name='CaptionUpdate']").val();
        var curse_id = CurseID;
        var typeValue = $("#TypeOfQuestionSolo").val();
        var additionValueNoAjax = [];
        $( ".VariantQuestionUpdate" ).each(function(index) {
            additionValueNoAjax[index]= $( this ).val();
        });
        var additionValue = JSON.stringify(additionValueNoAjax);
        var isMandatory = $("#IsMandatorySolo").val();
        var orderNumber = parseInt(($("#CaptionUpdate").attr("order_number")));
        var id = parseInt(($("#CaptionUpdate").attr("question_id")));
        var isView = true;
        $.ajax({
            url: location_origin+"/update_question",
            type: "POST",
            data: {
                'caption': caption,
                'curse_id': curse_id,
                'typeValue': typeValue,
                'additionValue': additionValue,
                'isMandatory': isMandatory,
                'orderNumber': orderNumber,
                'id':id,
                'isView':isView
            },
            dataType: "json",
            success: updateQuestionWithAdd,
            error: function (data) {
                console.log(data);
            }
        });

    });


});

function getSetting(data) {
    dataNewInto = data;


    $("#reg_start_date").attr("placeholder", data.registrationStartDate);
    $("#reg_end_date").attr("placeholder", data.registrationEndDate);
    $("#int_start_date").attr("placeholder", data.interviewStartDate);
    $("#interview_end_date").attr("placeholder", data.interviewEndDate);
    $("#course_start_date").attr("placeholder", data.courseStartDate);
    $("#interview_time_for_student").attr("placeholder", data.interviewTime);
    $("#student_for_interview_count").attr("placeholder", data.studentInterviewCount);
    $("#student_for_course_count").attr("placeholder", data.studentCourseCount);


}

function setSetting(data) {
    alert("Course Setting UpDate. Reload Page");
}

function setPersonal(data) {
    alert("HelloNewPersonal");
    personalData = data;
}

function getTypeOfQuestions(data){
    newDateType = data;
    for (var index in data){
        $("#TypeOfQuestion").append('<option>'+data[index].type+'</option>');
    }

    $('body').on('change', '#TypeOfQuestion', function(){

        var a  = $(this).val();


        if(a == "Select" || a == "Checkboxes" || a == "Select or text" ){
            $('#ComboBox').css('display','block');
            $("#ButtonQuestion").attr('id','ButtonQuestionWithAdd');

        }else if (a == "Text" || a == "Number") {
            $("#ButtonQuestion").attr('id','ButtonQuestion');
            $('#ComboBox').css('display','none');
        }

    });


}

function getQuantityQuestions(data){
    QuantityQuestions = data;

}

function getCourseID(data){
    CurseID = data;

    $.ajax({
        url: location_origin+"/admin/get_list",
        type: "GET",
        dataType: "json",
        data: {'id':CurseID},
        success: getQuestionTable,
        error: function (data) {
            console.log(data);
        }
    });
}

function setQuestionWithAdd (data){
    alert("Set with ADD Question");
}

function setQuestionWithoutAdd (data){
    alert("Set with ADD Question");
}



function getQuestionTable(data){

    Question = data;


    for(var index_student in data)
    {
        studentIndex = data[index_student];
        $("#TableQuestionFull").append('<tr class="win getModalAddress" question_id="'+ studentIndex.id +'">' +
        '<td>'+studentIndex.caption+'</td>' +
        '<td>'+studentIndex.type+'</td>' +
        '<td>'+studentIndex.mandatory+'</td>' +
        '</tr>');
    }

    $(".win").mousedown(function(){return false});

    function toggleHider(){
        $("#hider").toggle();
    }

    $(".win").click(toggleHider);
    $("#hider").click(toggleHider);

    $("#hider").click(function(){
        $(".ModelViewDays").css('display','none');
        $('#hider').css('display','none');
    });
    $(".getModalAddress").click(function(){
        if( $(".ModelViewDays").css('display') == 'none' ){
            $(".ModelViewDays").css('display','block');

        } else{
            $(".ModelViewDays").css('display','none');
        }
    });
}

function getQuestion (data){

    QuestionSolo = data;


    $(".ModelViewDays").empty();

    $(".ModelViewDays").css('width','59%');
    $(".ModelViewDays").css('left','22%');


    $(".ModelViewDays").append('<h3 style="text-align:center;">Change Question Parametres</h3>');
    $(".ModelViewDays").append('<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">'+
    '<div class="row">'+
    '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">'+
    '<div class="form-group">'+
    '<label for="CaptionUpdate">Question:</label>'+
    '<input type="text" name="CaptionUpdate" order_number="'+ data.orderNumber +'" question_id="'+ data.id +'"  class="form-control"id="CaptionUpdate">'+
    '</div>'+
    '</div>'+
    '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">'+
    '<div class="TypeOfQuestionNew">'+
    '<select  id="TypeOfQuestionSolo">'+
    '<option selected>Choose Type</option>'+
    '</select>'+
    '<select  id="IsMandatorySolo">'+
    '<option>true</option>'+
    '<option>false</option>'+
    '</select>'+
    '</div>'+
    '</div>'+
    '</div>'+
    '<div id="ComboBoxUpdate">'+
    '<div id="HowToAddUpdate">'+
    '<div>'+
    '<div class="form-group NewVariant">'+
    '<span>Possible answers to the question:</span>'+
    '<input type="text" name="VariantQuestion" class="VariantQuestion VariantQuestionUpdate  form-control">'+
    '</div>'+
    '<span class="glyphicon glyphicon-plus-sign plusUpdate"></span>'+
    '<span class="glyphicon glyphicon-minus-sign minus"></span>'+
    '</div>'+
    '</div>'+
    '</div>'+
    '<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">'+
    '<button type="button" id="ButtonQuestionUpdate" class="btn btn-success">Add Question</button>'+
    '</div>'+
    '</div>');
    $("#ComboBoxUpdate").css('display','none');
    $("#CaptionUpdate").attr("value", data.caption);


    $.ajax({
        url: location_origin+"/getTypeOfQuestions",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getTypeOfQuestionsForTable,
        error: function (data) {
            console.log(data);
        }
    });
}


function getTypeOfQuestionsForTable (data){


    for (var index in data){
        $("#TypeOfQuestionSolo").append('<option>'+data[index].type+'</option>');
    }

    $('body').on('change', '#TypeOfQuestionSolo', function(){

        var a  = $(this).val();


        if(a == "Select" || a == "Checkboxes" || a == "Select or text" ){
            $('#ComboBoxUpdate').css('display','block');
            $("#ButtonQuestionUpdate").attr('id','ButtonQuestionUpdateWithAdd');

        }else if (a == "Text" || a == "Number") {
            $("#ButtonQuestionUpdateWithAdd").attr('id','ButtonQuestionUpdate');
            $('#ComboBoxUpdate').css('display','none');
        }

    });

}

function updateQuestionWithAdd(data){
    alert("You update Question");
}