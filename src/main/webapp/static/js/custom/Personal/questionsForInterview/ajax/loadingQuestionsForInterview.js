$(document).ready(function () {
    $.ajax({
        url: baseUrl + "/service/getAllQuestionForInterview",
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