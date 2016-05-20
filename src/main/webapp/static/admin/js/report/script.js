/**
 * Created by Владимир on 04.05.2016.
 */
$(document).ready(function () {
    init();
    var editField = $('.report_edit');
    var mainForm = $('.main_form');
    var textDescription = $('.text_description');
    var textQuery = $('.text_query');
    var showTable = $('.show_table');
    var saveListButtons = $('.save_buttons');
    var report;

    var createButton = $('.create_button');
    createButton.click(function () {
        showElement(editField);
        hideElement(mainForm);
        hideElement(showTable);
        textDescription.val("");
        textQuery.val("");
        report = null;
    });
    var updateButton = $('.update_button');
    updateButton.click(function () {
        showElement(editField);
        hideElement(mainForm);
        hideElement(showTable);
        report = getReportByRadio();
        textDescription.val(report.description);
        textQuery.val(report.query);
    });
    var deleteButton = $('.delete_button');
    deleteButton.click(function () {
        hideElement(showTable);
        var index = getIndexByRadio();
        if (curData[index].status !== 'insert') {
            curData[index].status = 'delete';
            curData[index].show = false;
        } else {
            curData.splice(index, 1);
        }
        generateDescriptionList(curData);
    });

    $('.show_button').click(function () {
        var index = getIndexByRadio();
        var query = curData[index].query.toLowerCase();
        if (query.indexOf('drop') === -1 && query.indexOf('insert') === -1 && query.indexOf('update') === -1) {
            ajax("/service/createReport", function (data) {
                showReport(data);
                showElement(showTable);
            }, function (data) {
                alert("ERROR: bad request");
                console.log(data);
            }, {"query": curData[index].query, "description": curData[index].description});
        } else {
            alert("ban words: drop, insert, update");
        }
    });
    var showAllButton = $(".show_all_button");
    showAllButton.click(function () {
        hideElement(showTable);
        showElement(saveListButtons);
        disableButton(showAllButton);
        disableButton(createButton);
        disableButton(deleteButton);
        isShowAll = true;
        sendAjax();
        ajax("/service/getAllReportQuery", function (data) {
            curData = data;
            for (var index in curData) {
                curData[index].status = "new";
            }
            generateDescriptionList(curData);
        }, function (data) {
            console.log(data);
        })
    });

    $(".save_button").click(function () {
        if (textQuery.val() && textDescription.val()) {
            hideElement(editField);
            showElement(mainForm);
            if (report !== null) {
                var index = getIndexById(report.id);
                curData[index].description = textDescription.val();
                curData[index].query = textQuery.val();
                if (curData[index].status !== 'insert') {
                    curData[index].status = 'update';
                }
            } else {
                maxID++;
                var temp = {
                    id: maxID,
                    description: textDescription.val(),
                    query: textQuery.val(),
                    status: 'insert',
                    show: true
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
        hideElement(editField);
        showElement(mainForm);
    });

    $(".save_list_button").click(function () {
        hideElement(saveListButtons);
        enableButton(showAllButton);
        enableButton(createButton);
        enableButton(deleteButton);
        checkChanges();
        isShowAll = false;
        sendAjax();
        setTimeout(init, 500);
        //setTimeout(init, 500);
    });

    $(".cancel_list_button").click(function () {
        hideElement(saveListButtons);
        enableButton(showAllButton);
        enableButton(createButton);
        enableButton(deleteButton);
        isShowAll = false;
        init();
    });
    //$(".export_button").click(function () {
    //    var index = getIndexByRadio();
    //    var now = new Date();
    //    $("#table2excel").table2excel({
    //        filename: curData[index].description + now.getFullYear()+':'+(now.getMonth()+1)+':'+now.getDate()
    //    });
    //
    //});
    $(window).on('beforeunload', function () {
        sendAjax();
    });
});

var curData = [];
var maxID = 0;
var isShowAll = false;

function init() {
    ajax("/service/getReportQuery", ajaxSuccess, function (data) {
        console.log(data);
    });
}

function ajaxSuccess(data) {
    curData = data;
    for (var index in curData) {
        curData[index].status = "new";
    }
    generateDescriptionList(curData);
}

function generateDescriptionList(data) {
    var reportForm = $(".report_form");
    reportForm.empty();
    var isExist = false;
    for (var index in data) {
        if (data[index].status !== 'delete') {
            reportForm.append(createRow(data[index].id, data[index].description, data[index].show));
            isExist = true;
        }
    }
    $(".radio_button:first").prop("checked", true);
    if (!isExist || data.length == 0) {
        hideElement($(".not_empty_form"));
        $(".immut_text").empty();
    } else {
        radioButtonsChange();
        showElement($(".not_empty_form"));
    }
}
function createRow(id, description, isShow) {
    if (isShowAll)
        return '<label><input type="radio" name = "description" class = "radio_button" value='
            + id + ' hidden>' + description + '<input type="checkbox" name = "isShow" class = "checkbox" value='
            + id + (isShow ? ' checked' : '') + '></label> <br>';
    return '<label><input type="radio" name = "description" class = "radio_button" value='
        + id + ' hidden>' + description + '</label> <br>';
}
function radioButtonsChange() {
    var radioButtons = $(".radio_button");
    showQuery(radioButtons);
    radioButtons.change(function () {
        showQuery(radioButtons);
    });
}
function showReport(data) {
    var report = $(".report");
    report.empty();
    var table = '';
    for (var i in data) {
        table = table + '<tr>';
        for (var j in data[i]) {
            table = table + '<td>' + data[i][j] + '</td>';
        }
        table = table + '</tr>';
    }
    report.append(table);
}
function showQuery(radioButtons) {
    hideElement($('.show_table'));
    var text = $(".immut_text");
    var radio = $("input:radio:checked");
    radioButtons.parent().css({
        'background': '#ccc'
    });
    radio.parent().css({
        'background': '#d6e9c6'
    });
    text.empty();
    text.append(curData[getIndexById(radio.val())].query);
}

function sendAjax() {
    for (var index in curData) {
        if (curData[index] !== "new") {
            ajax("/service/setReportQuery", function () {
                },
                function (data) {
                    console.log(data);
                },
                curData[index]
            );
        }
    }
}

function checkChanges() {
    var checkboxes = $('input[type=checkbox]');
    $(checkboxes).each(function () {
        var index = getIndexById($(this).val());
        if ($(this).prop('checked') !== curData[index].show) {
            curData[index].status = "update";
            curData[index].show = !curData[index].show;
        }
    });
}

function getReportByRadio() {
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

function showElement(el) {
    el.css({
        'display': 'block'
    });
}

function hideElement(el) {
    el.css({
        'display': 'none'
    });
}

function disableButton(but) {
    but.prop("disabled", true);
}

function enableButton(but) {
    but.prop("disabled", false);
}

function ajax(url, success, error, data) {
    $.ajax({
        url: "http://31.131.25.206:8080/hr_system-1.0-SNAPSHOT/admin" + url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        data: data,
        success: success,
        error: error
    });
}
