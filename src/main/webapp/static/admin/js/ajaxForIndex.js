/**
 * Created by Серый on 11.05.2016.
 */
$(document).ready(function () {


    location_origin = "http://localhost:8080/hr_system-1.0-SNAPSHOT";
    /*  location_origin = "http://31.131.25.206:8080/hr_system-1.0-SNAPSHOT"*/

    $.ajax({
        url: location_origin + "/registration_period",
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