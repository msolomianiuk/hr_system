$(document).ready(function () {
    init();
    var editField = $('.template_edit');
    var mainForm = $('.main_form');
    var textDescription = $('.text_description');
    var textEmailTemplate = $('.text_email_template');
    var emailTemplate;

    $('.create_button').click(function () {
        switchBlock(editField);
        switchNone(mainForm);
        textDescription.val("");
        textEmailTemplate.val("");
        emailTemplate = null;
    });
    $('.update_button').click(function () {
        switchBlock(editField);
        switchNone(mainForm);
        emailTemplate = getTemplateByRadio();
        textDescription.val(emailTemplate.description);
        textEmailTemplate.val(emailTemplate.template);
    });

    $('.delete_button').click(function () {
        var index = getIndexByRadio();
        if (curData[index].status !== 'insert') {
            curData[index].status = 'delete';
        } else {
            curData.splice(index, 1);
        }
        generateDescriptionList(curData);
    });

    $(".save_button").click(function () {
        if (textEmailTemplate.val() && textDescription.val()) {
            switchNone(editField);
            switchBlock(mainForm);
            if (emailTemplate !== null) {
                var index = getIndexById(emailTemplate.id);
                curData[index].description = textDescription.val();
                curData[index].template = textEmailTemplate.val();
                if (curData[index].status !== 'insert') {
                    curData[index].status = 'update';
                }
            } else {
                maxID++;
                var temp = {
                    id: maxID,
                    description: textDescription.val(),
                    template: textEmailTemplate.val(),
                    status: 'insert'
                }
                if (curData == undefined) {
                    curData = [];
                }
                curData.push(temp);
            }
            generateDescriptionList(curData);
        } else {
            alert("Not all fields are filled");
        }
    });
    $(".cancel_button").click(function () {
        switchNone(editField);
        switchBlock(mainForm);
    });
    $(window).on('beforeunload', function () {
        sendAjax();
    });
});

var curData = [];
var maxID = 0;

function init() {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/getEmailTemplates",
        type: "GET",
        dataType: "json",
        success: function (data) {
            curData = data;
            for (var index in curData) {
                curData[index].status = "new";
            }
            generateDescriptionList(curData);
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function generateDescriptionList(data) {
    var templateForm = $(".template_form");
    templateForm.empty();
    var isExist = false;
    for (var index in data) {
        if (data[index].status !== 'delete') {
            templateForm.append('<label><input type="radio" name = "description" class = "radio_button" value='
            + data[index].id + ' hidden>' + data[index].description + '</label> <br>');
            isExist = true;
        }
        maxID = data[index].id > maxID ? data[index].id : maxID;
    }
    //maxID = data.length > maxID ? data.length : maxID;
    $(".radio_button:first").prop("checked", true);
    if (!isExist || data.length == 0) {
        switchNone($(".not_empty_form"));
        $(".immut_text").empty();
    } else {
        radioButtonsChange();
        switchBlock($(".not_empty_form"));
    }
}

function radioButtonsChange() {
    var radioButtons = $(".radio_button");
    showTemplate(radioButtons);
    radioButtons.change(function () {
        showTemplate(radioButtons);
    });
}

function showTemplate(radioButtons) {
    var text = $(".immut_text");
    var radio = $("input:radio:checked");
    radioButtons.parent().css({
        'background': 'rgba(0, 0, 0, 0.35)'
    });
    radio.parent().css({
        'background': 'rgba(100, 149, 237, 0.35)'
    });
    text.empty();
    text.append(curData[getIndexById(radio.val())].template);
}

function sendAjax() {
    for (var index in curData) {
        if (curData[index].status !== "new") {
            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/setEmailTemplates",
                type: "GET",
                dataType: "json",
                data: curData[index],
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    console.log(data);
                },
                error: function (data) {
                    console.log(data);
                }
            });
        }
    }
}
function getTemplateByRadio() {
    return curData[getIndexById($("input:radio:checked").val())];
}

function getIndexById(id) {
    for (var index in curData) {
        if (curData[index].id == id) {
            return index;
        }
    }
}

function getIndexByRadio() {
    var id = $("input:radio:checked").val();
    return getIndexById(id);
}

function switchBlock(el) {
    el.css({
        'display': 'block'
    });
}

function switchNone(el) {
    el.css({
        'display': 'none'
    });
}