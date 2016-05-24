/**
 * Created by Серый on 30.04.2016.
 */
$(document).ready(function () {

    location_origin = "http://localhost:8080/hr_system-1.0-SNAPSHOT"

    $.ajax({
        url: location_origin + "/personal_list",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcForAjax,
        error: function (data) {
            console.log(data);
        }
    });


    $('body').on('click', '.getModalStatus', function(){

        var id  = parseInt(($(this).attr("user_id")));

        $.ajax({
            url: location_origin + "/admin/user_id",
            type: "GET",
            dataType: "json",
            data:{'id':id},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: getCandidateId,
            error: function (data) {
                console.log(data);
            }
        });

    });

    $(document).on("click","#UpdateStatus",function (){

        var userID  = parseInt(($(this).attr("user_id")));

        var role =  $("#Status").val();

        $.ajax({
            url: location_origin + "/admin/add_role",
            type: "GET",
            dataType: "json",
            data:{'userID':userID,'role':role},
            success: functionForUpdateRole,
            error: function (data) {
                console.log(data);
            }
        });

    });

});

function funcForAjax(data) {
    dataNewIn = data;
    for (var indexba in data["BA"]) {
        baIndex = data["BA"][indexba];
        $("#TableBA").append('<tr class="getModalStatus" user_id="'+ baIndex.id +'">' +
        '<td>' + baIndex.name + '</td>' +
        '<td>' + baIndex.surname + '</td>' +
        '<td>' + baIndex.patronymic + '</td>' +
        '<td>' + baIndex.email + '</td>' +
        '</tr>');
    }
    for (var indexhr in data["HR"]) {
        hrIndex = data["HR"][indexhr];
        $("#TableHR").append('<tr class="getModalStatus" user_id="'+ hrIndex.id +'">' +
        '<td>' + hrIndex.name + '</td>' +
        '<td>' + hrIndex.surname + '</td>' +
        '<td>' + hrIndex.patronymic + '</td>' +
        '<td>' + hrIndex.email + '</td>' +
        '</tr>');
    }
    for (var indexdev in data["DEV"]) {
        devIndex = data["DEV"][indexdev];
        $("#TableDEV").append('<tr class="getModalStatus" user_id="'+ devIndex.id +'">' +
        '<td>' + devIndex.name + '</td>' +
        '<td>' + devIndex.surname + '</td>' +
        '<td>' + devIndex.patronymic + '</td>' +
        '<td>' + devIndex.email + '</td>' +
        '</tr>');
    }

    $("#hider").click(function(){
        $(".ModalPersonal").css('display','none');
        $('#hider').css('display','none');
    });
    $(".getModalStudent").click(function(){
        if( $(".ModalPersonal").css('display') == 'none' ){
            $(".ModalPersonal").css('display','block');
            $('#hider').css('display','block');
        } else{
            $(".ModalPersonal").css('display','none');
            $('#hider').css('display','none');
        }
    });

    $(".getModalStatus").click(function(){
        if( $(".ModalPersonal").css('display') == 'none' ){
            $(".ModalPersonal").css('display','block');
            $('#hider').css('display','block');
        } else{
            $(".ModalPersonal").css('display','none');
            $('#hider').css('display','none');
        }
    });
    $('#TableBA').dataTable({});
    $('#TableHR').dataTable({});
    $('#TableDEV').dataTable({});
    }


function getCandidateId(data){
    $("#UpdateStatus").attr('user_id','');

    id_us = data;

    $("#UpdateStatus").attr('user_id',id_us);
}

function functionForUpdateRole(data){
    console.log("Update Role");
}