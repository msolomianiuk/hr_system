$("button#save").on("click", function () {
    $('.loading').attr('style', 'display: flex');
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/saveAnswers",
        type: "GET",
        dataType: "json",
        data: {'answersJsonString': JSON.stringify($('.candidate-profile form').serializeObject())},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            // Modal "Your Answers Saved"
            location.reload();
        },
        error: function (data) {
        //  Modal "Some Problems"
        }
    });
});