var questionsList;
function loadQuestions() {
    $.ajax({
        url: baseUrl + "/service/getAllMandatoryQuestions",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            questionsList = data;
        },
        error: function (data) {
            console.log(data);
        }
    });
}
