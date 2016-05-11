function initEditQuestion() {
    $("#edit-question .edit-question-button").on("click", function () {
        var questionValue = $("#edit-question textarea").val();
        var subjectId = $('#edit-question select').val();
        var questionId = $('#edit-question form').attr('question-id');
        var object = $('#questions-for-interview ul.question-list li[question-id="'+questionId+'"] .question-text');
        console.log("ololololo");
        $.ajax({
            url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/editQuestion",
            type: "GET",
            dataType: "json",
            data: {
                'subjectId': subjectId,
                'questionValue': questionValue,
                'questionId': questionId
            },
            contentType: 'application/json',
            mimeType: 'application/json',
            success:  function(data) {
                drawQuestionEdit(questionId, subjectId, questionValue, object);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });
}