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
            setTimeout(function () { $('.loading').hide(); }, 1000);
            new PNotify({
                title: 'Saved answer',
                text: 'Your answers was successfully saved!',
                type: 'success'
            });
            setTimeout(function () {location.reload(); }, 3000);
        },
        error: function (data) {
            setTimeout(function () { $('.loading').hide(); }, 1000);
            new PNotify({
                title: 'Some Problem',
                text: 'There have been some problems!',
                type: 'error'
            });
        }
    });
});