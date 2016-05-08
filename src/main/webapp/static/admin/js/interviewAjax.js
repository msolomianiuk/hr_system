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
    //$(document).on("click","#AddAddress",function(){
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
}