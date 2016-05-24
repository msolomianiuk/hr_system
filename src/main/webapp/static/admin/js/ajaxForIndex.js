/**
 * Created by Серый on 11.05.2016.
 */
$(document).ready(function () {

    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/registration_period",
        type: "GET",
        dataType: "json",
        success: getPeriodRegistration,
        error: function (data) {
            console.log(data);
        }
    });





});


function getPeriodRegistration(data){


    MyReg = data;


    if(1 == data) {
        $("#Registration").css('display', 'block');
    }
    if(0 == data){
        $("#Registration").remove();
    }

}