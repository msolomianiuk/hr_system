$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/hr/service/getQuestionViewList",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            addInTableQuestions(data);
            loadCandidatesList();
        },
        error: function (data) {
            console.log(data);
        }
    });
});