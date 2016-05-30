$("button#save").on("click", function (e) {
    $('.loading').attr('style', 'display: flex');

    var submit = true;

    if (!validator.checkAll($('form'))) {
        submit = false;
    }

    if (submit){
        $.ajax({
            url: baseUrl + "/service/saveAnswers",
            type: "POST",
            data: {'answersJsonString': JSON.stringify($('.candidate-profile form').serializeObject())},
            dataType: "json",
            success: function (data) {
                setTimeout(function () { $('.loading').hide(); }, 1000);
                new PNotify({
                    title: 'Saved answer',
                    text: 'Your answers was successfully saved!</br>Page will update in several seconds.',
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
    } else {
        setTimeout(function () { $('.loading').hide(); }, 1000);
        new PNotify({
            title: 'Сheck your answers!',
            text: 'You input not correct answer! Please, check your answers!',
            type: 'error'
        });
    }
});