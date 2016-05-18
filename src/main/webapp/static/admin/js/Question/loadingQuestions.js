$(document).ready(function () {
    $.ajax({
        url: baseUrl + "/service/getAllMandatoryQuestions",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: drawQuestionForm,
        error: function (data) {
            console.log(data);
        }
    });
});