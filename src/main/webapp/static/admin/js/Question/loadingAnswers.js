function loadAnswers() {
    $.ajax({
        url: baseUrl + "/service/getAnswers",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: insertAnswersInCandidateForm,
        error: function (data) {
            console.log(data);
        }
    });
}