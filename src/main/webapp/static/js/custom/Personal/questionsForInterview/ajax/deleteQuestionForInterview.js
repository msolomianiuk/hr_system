function initDeleteQuestion() {
    $("#delete-question .delete-question-button").on("click", function () {
        var questionId = $("#delete-question .question-id-div").attr('question-id');
        console.log(questionId);
        $.ajax({
            url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/deleteQuestion",
            type: "GET",
            dataType: "json",
            data: {
                'questionId': questionId
            },
            contentType: 'application/json',
            mimeType: 'application/json',
            success:  function(data) {
                removeQuestion(questionId);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });
}