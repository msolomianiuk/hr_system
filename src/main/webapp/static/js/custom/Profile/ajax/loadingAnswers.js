$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/getAnswers",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: insertAnswersInCandidateForm,
        error: function (data) {
            console.log(data);
        }
    });
});