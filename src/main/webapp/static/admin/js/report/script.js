/**
 * Created by Владимир on 04.05.2016.
 */
$(document).ready(function () {
    init();
    var editField = $('.report_edit');
    var mainForm = $('.main_form');
    var textDescription = $('.text_description');
    var textQuery = $('.text_query');
    var report;

    $('.create_button').click(function () {
        switchBlock(editField);
        switchNone(mainForm);
        textDescription.val("");
        textQuery.val("");
        report = null;
    });
    $('.update_button').click(function () {
        switchBlock(editField);
        switchNone(mainForm);
        report = getReportByRadio();
        textDescription.val(report.description);
        textQuery.val(report.query);
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

    $('.show_button').click(function () {
        var index = getIndexByRadio();

        $.ajax({
            url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/createReport",
            type: "GET",
            dataType: "json",
            data:{"query":curData[index].query},
            contentType: 'application/json',
            mimeType: 'application/json',
            success:function (data){
                console.log(data);
                showReport(data);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });

    $(".save_button").click(function () {
        if (textQuery.val() && textDescription.val()) {
            switchNone(editField);
            switchBlock(mainForm);
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
                    status: 'insert'
                }
                if(curData == undefined){
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
    $(window).on('beforeunload', function(){
        sendAjax();
    });
});

var curData = [];
var maxID = 0;

function init() {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/getReportQuery",
        type: "GET",
        dataType: "json",
        success:function (data){
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
    var reportForm = $(".report_form");
    reportForm.empty();
    var isExist = false;
    for (var index in data) {
        if (data[index].status !== 'delete') {
            reportForm.append('<label><input type="radio" name = "description" class = "radio_button" value='
            + data[index].id + ' hidden>' + data[index].description + '</label> <br>');
            isExist = true;
        }
       // maxID = data[index].id > maxID ? data[index].id : maxID;
    }
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
    showQuery(radioButtons);
    radioButtons.change(function () {
        showQuery(radioButtons);
    });
}
function showReport(data){
    var report = $(".report");
    report.empty();
    var table = '';
    for(var i in data){
        table = table + '<tr>';
        for(var j in data[i]){
            //report.append('<td>'+data[i][j]+'</td>');
            table = table + '<td>'+data[i][j]+'</td>';
        }
        table = table + '</tr>';
    }
    report.append(table);
}
function showQuery(radioButtons){
    var text = $(".immut_text");
    var radio = $("input:radio:checked");
    radioButtons.parent().css({
        'background': 'rgba(0, 0, 0, 0.35)'
    });
    radio.parent().css({
        'background': 'rgba(100, 149, 237, 0.35)'
    });
    text.empty();
    text.append(curData[getIndexById(radio.val())].query);
}

function sendAjax(){
    for(var index in curData){
        if(curData[index].status!=="new"){
            $.ajax({
                url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/setReportQuery",
                type: "GET",
                dataType: "json",
                data:curData[index],
                contentType: 'application/json',
                mimeType: 'application/json',
                success:function (data){
                    console.log(data);
                },
                error: function (data) {
                    console.log(data);
                }
            });
        }
    }
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