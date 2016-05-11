function initSaveQuestion() {
    $("#add-question .add-question-button").on("click", function () {
        var questionValue = $("#add-question textarea").val();
        var subjectId = $('#add-question select').val();
        $.ajax({
            url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/addQuestion",
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