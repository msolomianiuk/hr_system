function initSaveQuestion() {
    $("#add-question .add-question-button").on("click", function () {
        var questionValue = $("#add-question textarea").val();
        var subjectId = $('#add-question select').val();
        $.ajax({
            url: baseUrl + "/service/addQuestion",
            type: "GET",
            dataType: "json",
            data: {
                'subjectId': subjectId,
                'questionValue': questionValue
            },
            contentType: 'application/json',
            mimeType: 'application/json',
            success:  function(data) {
                drawQuestion(data, subjectId, questionValue);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });
}