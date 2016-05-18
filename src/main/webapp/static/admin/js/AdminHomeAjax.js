/**
 * Created by Серый on 18.05.2016.
 */




$(document).ready(function () {

    location_origin = "http://localhost:8080/hr_system-1.0-SNAPSHOT"

    $.ajax({
        url: location_origin+"/admin/candidateCount",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: getCountCandidate,
        error: function (data) {
            console.log(data);
        }
    });

});

function getCountCandidate(data){
    $(".CountView").empty();
    $(".CountView").append(data);
}