/**
 * Created by Серый on 08.05.2016.
 */
$(document).ready(function () {

    location_origin = "http://localhost:8080/hr_system-1.0-SNAPSHOT"

    //var token = $("meta[name='_csrf']").attr("content");
    //var header = $("meta[name='_csrf_header']").attr("content");
    //$(document).ajaxSend(function (e, xhr, options) {
    //    xhr.setRequestHeader(header, token);
    //});


    $(document).on("click","#hider",function(){
        $("#TableAddress").empty();
        $.ajax({
            url: location_origin+"/admin/address_list",
            type: "GET",
            dataType: "json",
            contentType: 'application/json',
            mimeType: 'application/json',
            success: getInterviewAddress,
            error: function (data) {
                console.log(data);
            }
        });

    });



    $.ajax({
        url: location_origin+"/admin/address_list",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getInterviewAddress,
        error: function (data) {
            console.log(data);
        }
    });
    $(document).on("click",".getModalAddress",function(){

        var id  = parseInt(($(this).attr("daysDetails_id")));

        $.ajax({
            url: location_origin + "/admin/address_getAddress",
            type: "GET",
            dataType: "json",
            data:{'id':id},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: getAddressSolo,
            error: function (data) {
                console.log(data);
            }
        });

    });
    $("#AddAddress").bind("click", function () {
        var address = $("input[name='AddressAdd']").val();
        var roomCapacity = $("input[name='RoomCapacity']").val();

        $.ajax({
            url: location_origin + "/admin/address_insert",
            type: "GET",
            dataType: "json",
            data:{'address':address,'roomCapacity':roomCapacity},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: addAddress,
            error: function (data) {
                console.log(data);
            }
        });

    });
    $(document).on("click","#UpdateAddress",function(){

        var id  = parseInt(($("#AddressUpdate").attr("id_day")));
        var address = $("input[name='UpdateAddress']").val();
        var roomCapacity = $("input[name='UpdateRoomCapacity']").val();

        $.ajax({
            url: location_origin + "/admin/address_update",
            type: "GET",
            dataType: "json",
            data:{'id':id,'address':address,'roomCapacity':roomCapacity},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: updateAddress,
            error: function (data) {
                console.log(data);
            }
        });

    });


    $.ajax({
        url: location_origin+"/getCurseId",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getCourseID,
        error: function (data) {
            console.log(data);
        }
    });

    $('body').on('change', '#addressDays', function(){

        var date  = $(this).val();
        $.ajax({
            url: location_origin+"/admin/getInterviewDetailsByDate",
            type: "GET",
            dataType: "json",
            data:{'date':date},
            success: getSettingInterviewDays,
            error: function (data) {
                console.log(data);
            }
        });


    });
    $.ajax({
        url: location_origin+"/admin/getInterviewDetailsAddressList",
        type: "GET",
        dataType: "json",
        success: getTableWithDate,
        error: function (data) {
            console.log(data);
        }
    });
    $(document).on("click",".getModalInerviewDaysD",function(){

        var id  = parseInt(($(this).attr("daysInterview_id")));

        $.ajax({
            url: location_origin + "/admin/getInterviewDetailsById",
            type: "GET",
            dataType: "json",
            data:{'id':id},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: getInterviewsSolo,
            error: function (data) {
                console.log(data);
            }
        });

    });
    $(document).on("click","#UpdateInerviw",function(){





        var id  = parseInt(($("#id_interview").attr("id_day")));
        var address_id = $("#SelectorAddress").val();
        var start_time = $("input[name='StartInterview']").val();
        var end_time = $("input[name='EndInterview']").val();

        $.ajax({
            url: location_origin + "/admin/interview_details_update",
            type: "GET",
            dataType: "json",
            data:{'id':id,'address_id':address_id,'start_time':start_time,"end_time":end_time},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: updateAddress,
            error: function (data) {
                console.log(data);
            }
        });

    });
});


function getInterviewAddress(data){

    dataNewStudents = data;
    for(var index_student in data)
    {
        studentIndex = data[index_student];
        $("#TableAddress").append('<tr class="win getModalAddress" daysDetails_id="'+ studentIndex.id +'">' +
        '<td>'+studentIndex.address+'</td>' +
        '<td>'+studentIndex.roomCapacity+'</td>' +
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


function getAddressSolo(data){
    dataInNewAll = data;
    alert("U get Address");
    $(".ModelViewDays").empty();
    $(".ModelViewDays").append('<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">'+
    '<div class="form-group">'+
    '<label for="AddressUpdate">Address interview</label>'+
    '<input type="text" name="UpdateAddress" class="form-control" id="AddressUpdate" placeholder="Nothing detected">'+
    '</div>'+
    '</div>'+
    '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">'+
    '<div class="form-group">'+
    '<label for="RoomCapacityUpdate">Room Capacity:</label>'+
    '<input type="text" name="UpdateRoomCapacity" class="form-control" id="RoomCapacityUpdate" placeholder="Nothing detected">'+
    '</div>'+
    '</div>'+
    '<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">'+
    '<button type="button" id="UpdateAddress" class="btn btn-success">Add Address Interview days details</button>'+
    '</div>');
    $("#AddressUpdate").attr("id_day", data.id);
    $("#AddressUpdate").attr("value", data.address);
    $("#RoomCapacityUpdate").attr("value", data.roomCapacity);

}

function addAddress(data){
    alert("U add address");
    $("#AddressAdd").val("");
    $("#RoomCapacity").val("");
    $("#TableAddress").empty();
    $.ajax({
        url: location_origin+"/admin/address_list",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getInterviewAddress,
        error: function (data) {
            console.log(data);
        }
    });
}

function updateAddress(data){
    alert("U update address")

    $("#TableAddressFull").empty();
    $.ajax({
        url: location_origin+"/admin/getInterviewDetailsAddressList",
        type: "GET",
        dataType: "json",
        success: getTableWithDate,
        error: function (data) {
            console.log(data);
        }
    });


}

function getCourseID(data){
    CurseID = data;
}

function getSettingInterviewDays(data){


}

function getTableWithDate(data){

    for(var index_student in data)
    {
        var  dayIndex = data[index_student];
        $("#TableAddressFull").append('<tr class="winOne getModalInerviewDaysD" daysInterview_id="'+ dayIndex.id +'">' +
        '<td>'+dayIndex.date+'</td>' +
        '<td>'+dayIndex.start_time+'</td>' +
        '<td>'+dayIndex.end_time+'</td>' +
        '<td>'+dayIndex.address+'</td>' +
        '<td>'+dayIndex.room_capacity+'</td>' +
        '<td>'+dayIndex.count_students+'</td>' +
        '<td>'+dayIndex.count_personal+'</td>' +
        '</tr>');
    }

    $(".winOne").mousedown(function(){return false});

    function toggleHider(){
        $("#hider").toggle();
    }

    $(".winOne").click(toggleHider);
    $("#hider").click(toggleHider);

    $("#hider").click(function(){
        $(".ModelViewDays").css('display','none');
        $('#hider').css('display','none');
    });
    $(".getModalInerviewDaysD").click(function(){
        if( $(".ModelViewDays").css('display') == 'none' ){
            $(".ModelViewDays").css('display','block');

        } else{
            $(".ModelViewDays").css('display','none');
        }
    });

}


function getInterviewsSolo (data){

    day_data = data;

    $(".ModelViewDays").empty();


    $(".ModelViewDays").append('<h3 style="text-align:center;">'+data.interviewDate+'</h3>');
    $(".ModelViewDays").append('<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">'+
    '<select id="SelectorAddress">'+
    '<option selected>Choose day</selected>'+
    '<select>'+
    '</div>'+
    '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">'+
    '<div class="form-group" id="id_interview" id_day="'+data.id+'" >'+
    '<label for="StartInterview">Start interview:</label>'+
    '<input type="text" name="StartInterview" class="form-control" id="StartInterview" placeholder="Nothing detected">'+
    '</div>'+
    '</div>'+
    '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">'+
    '<div class="form-group">'+
    '<label for="EndInterview:">End interview:</label>'+
    '<input type="text" name="EndInterview" class="form-control" id="EndInterview" placeholder="Nothing detected">'+
    '</div>'+
    '</div>'+
    '<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">'+
    '<button type="button" id="UpdateInerviw" class="btn btn-success">Add Address Interview days details</button>'+
    '</div>');
    $("#StartInterview").attr("value", data.startTime);
    $("#EndInterview").attr("value", data.endTime);

    $.ajax({
        url: location_origin + "/admin/address_list",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getAddressWithID,
        error: function (data) {
            console.log(data);
        }
    });

    function getAddressWithID(data){
        data_id = data;
        for(var index_date in data) {
            $("#SelectorAddress").append('<option address_id="' + data[index_date].id + '">' + data[index_date].address + '</option>');
        }

    }

}