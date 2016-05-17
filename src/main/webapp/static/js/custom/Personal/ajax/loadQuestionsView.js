$(document).ready(function () {
    getAllStatus();
    $.ajax({
        url: baseUrl + "/hr/service/getQuestionViewList",
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