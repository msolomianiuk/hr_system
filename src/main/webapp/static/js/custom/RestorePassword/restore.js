$("button#restore-p-email").on("click", function () {
    $('.loading').attr('style', 'display: flex');
    $('.message-error').remove();

    $.ajax({
        url: baseUrl + "/password/reset",
        type: "POST",
        data: {'email': $("#email").val()},
        dataType: "json",
        success: function (data) {
            setTimeout(function () {
                $('.loading').hide();
            }, 1000);
            if (data == true) {
                $('.form-bottom').empty();
                $('#enter-email').remove();
                showSuccessMessage();
            }
            else showErrorMessage();
        },
        error: function (data) {
            setTimeout(function () {
                $('.loading').hide();
            }, 1000);
            showErrorMessage();
        }
    });
});

function showErrorMessage() {
    $("<div class='message-error'>Error! Invalid email!</div>").insertBefore('#email-restore');
}

function showSuccessMessage() {
    $('.form-bottom').append(
        "<div class='message-success'>Check your e-mail!</div>"
    );
}


$("button#reset-password").on("click", function () {
    $('.loading').attr('style', 'display: flex');
    $('.message-error').remove();

    $.ajax({
        url: baseUrl + "/password/updatePassword",
        type: "POST",
        data: {
            'email': $("#email").val(),
            'token': $("#token").val(),
            'password': $("#password").val()
        },
        dataType: "json",
        success: function (data) {
            setTimeout(function () {
                $('.loading').hide();
            }, 1000);
            if (data == true) {
                $('.form-bottom').empty();
                $('#enter-pass').remove();
                showSuccessUpdateMessage();
            }
            else showErrorUpdateMessage();
        },
        error: function (data) {
            setTimeout(function () {
                $('.loading').hide();
            }, 1000);
            showErrorUpdateMessage();
        }
    });
});

function showErrorUpdateMessage() {
    $("<div class='message-error'>Incorrect password!</div>").insertBefore('#email-restore');
}

function showSuccessUpdateMessage() {
    $('.form-bottom').append(
        "<div class='message-success'>Success!</div>"
    );
}