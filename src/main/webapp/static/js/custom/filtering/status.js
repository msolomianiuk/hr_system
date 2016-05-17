/**
 * Created by michael on 29/07/16.
 */
function drawSatus(statusList) {
    for (var index in statusList) {
        $('.status').append("<option value='" + index + "'>" + statusList[index] + "</option>");
    }
}

$('.status').ready(function () {
    $.ajax({
        url: baseUrl + "/getStatuses",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            drawSatus(data);
        },
        error: function (data) {
            console.log(data);
        }
    });
});