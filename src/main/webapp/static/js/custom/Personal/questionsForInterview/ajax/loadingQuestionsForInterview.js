$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/getAllQuestionForInterview",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function(data) {
            drawQuestionsForInterview(data);
        },
        error: function (data) {
            console.log(data);
        }
    });
});