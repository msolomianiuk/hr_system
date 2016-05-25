/**
 * Created by Владимир on 22.05.2016.
 */
var reports;
var reportIndex;
$(document).ready(function () {
    init();
    $.validator.addMethod('checkSql', function (value, element) {
        value = value.toLowerCase();
        return this.optional(element) || (value.indexOf("create") === -1 && value.indexOf("drop") === -1 && value.indexOf("delete") === -1 && value.indexOf("update") === -1 && value.indexOf("alter") === -1)
    }, "You can not use words - create, drop, delete, update, alter");
    var validateForm = $(".validate").validate({
        rules: {
            description: "required",
            query: {
                required: true,
                checkSql: true
            }
        }
    });
    $(".btn-delete").on("click", function () {
        reports[reportIndex].status = 'delete';
        ajax("service/setReportQuery", function () {
        }, function () {
        }, reports[reportIndex]);
        reports.splice(reportIndex, 1);
        generateReportsList(reports);
        generateDeveloperReportsList(reports);
    });
    $(".btn-update").on("click", function () {
        var textQuery = $(".dev_panel_query");
        textQuery.empty();
        textQuery.append('<h4> Query:</h4> <textarea class = "form-control col-md-7 col-xs-12 query" row="3" name="query">' + reports[reportIndex].query + '</textarea>');
        var textDescription = $(".dev_panel_description");
        textDescription.empty();
        textDescription.append('<h4> Brief Description:</h4><input type="text" class = "form-control col-md-7 col-xs-12 description" name = "description" value="' + reports[reportIndex].description + '">');
        $(".btn-update").css({'display': 'none'});
        $(".btn-delete").css({'display': 'none'});
        $(".btn-save").css({'display': 'inline-block'});
        $(".btn-cancel").css({'display': 'inline-block'});
    });
    $(".btn-save").on("click", function () {
        if (validateForm.form()) {
            $(".btn-update").css({'display': 'inline-block'});
            $(".btn-delete").css({'display': 'inline-block'});
            $(".btn-save").css({'display': 'none'});
            $(".btn-cancel").css({'display': 'none'});
            if (reportIndex !== -1) {
                reports[reportIndex].status = 'update';
                reports[reportIndex].description = $(".description").val();
                reports[reportIndex].query = $(".query").val();
                ajax("service/setReportQuery", function () {
                }, function () {
                }, reports[reportIndex]);
            } else {
                var report = {};
                report.status = 'insert';
                report.description = $(".description").val();
                report.query = $(".query").val();
                report.show = true;
                report.id = -1;
                ajax("service/setReportQuery", function () {
                    getAllReports();
                }, function () {
                }, report);
                reportIndex = reports.push(report) - 1;
            }
            showDevModal();
            generateReportsList(reports);
            generateDeveloperReportsList(reports);
        }
    });
    $(".btn-cancel").on("click", function () {
        $(".btn-update").css({'display': 'inline-block'});
        $(".btn-delete").css({'display': 'inline-block'});
        $(".btn-save").css({'display': 'none'});
        $(".btn-cancel").css({'display': 'none'});
        showDevModal();
    });
    $(".btn-create").on("click", function () {
        var textQuery = $(".dev_panel_query");
        textQuery.empty();
        textQuery.append('<label class="control-label col-md-3 col-sm-3 col-xs-12"> Query</label> <textarea class = "form-control col-md-7 col-xs-12 query" row="3" name="query"></textarea>');
        autosize($('textarea'));
        var textDescription = $(".dev_panel_description");
        textDescription.empty();
        textDescription.append('<label class="control-label col-md-3 col-sm-3 col-xs-12"> Brief Description</label><input type="text" class = "form-control col-md-7 col-xs-12 description" name = "description" value="">');
        $(".btn-update").css({'display': 'none'});
        $(".btn-delete").css({'display': 'none'});
        $(".btn-save").css({'display': 'inline-block'});
        $(".btn-cancel").css({'display': 'inline-block'});
        reportIndex = -1;
    });
});

function getAllReports(){
    ajax("service/getReportQuery", function (data) {
        reports = data;
        for (var index in reports) {
            reports[index].status = "new";
        }
        generateReportsList(reports);
        generateDeveloperReportsList(reports);
    });
}

function init() {
    getAllReports();
    ajax("service/getCourses", function (data) {
        generateCourseList(data);
    });
    ajax("service/getStatuses", function (data) {
        generateStatusList(data);
    });
}

function generateReportsList(data) {
    var report = $(".report_form");
    report.empty();
    for (var index in data) {
        report.append('<div class="col-md-4 col-sm-4 col-xs-12 animated fadeInDown">' +
        '<button type="button" class="btn btn-dark btn-lg show_button" style="white-space:normal"' +
        'data-toggle = "modal" data-target = ".bs-example-modal-lg"' +
        'value = "' + index + '">' +
        data[index].description + '</button> </div>');
    }
    $(".show_button").on("click", function () {
        $('.export_button').css({'display': 'inline-block'});
        if ($(this).val() === "main") {
            ajax("service/createMainReport", showReport, getReportError, {
                courseId: $(".course_setting").val(),
                status: $(".status").val()
            });
        }
        else {
            ajax("service/createReport", showReport, getReportError, {
                query: reports[($(this).val())].query,
                description: reports[$(this).val()].description
            });
        }
    });
}
function generateDeveloperReportsList(data) {
    var report = $(".report_developer_form");
    report.empty();
    for (var index in data) {
        report.append('<div class="col-md-4 col-sm-4 col-xs-12 animated fadeInDown">' +
        '<button type="button" class="btn btn-dark btn-lg dev_show_button" style="white-space:normal" ' +
        'data-toggle = "modal" data-target = ".bs-example-modal-lg-dev" ' +
        'value = "' + index + '">' +
        data[index].description + '</button> </div>');
    }
    $(".dev_show_button").on("click", function () {
        reportIndex = $(this).val();
        showDevModal();
    });
}

function showDevModal() {
    var textQuery = $(".dev_panel_query");
    textQuery.empty();
    textQuery.append('<h4> Query: ' + reports[reportIndex].query + '</h4>');
    var textDescription = $(".dev_panel_description");
    textDescription.empty();
    textDescription.append('<h4> Brief Description: ' + reports[reportIndex].description + '</h4>');
    $(".btn-cancel").css({'display': 'none'});
    $(".btn-save").css({'display': 'none'});
    $(".btn-update").css({'display': 'inline-block'});
    $(".btn-delete").css({'display': 'inline-block'});
}

function getReportError() {
    var report = $(".report");
    report.empty();
    report.append("DB Error");
    $('.export_button').css({'display': 'none'});
}
function showReport(data) {
    var report = $(".report");
    report.empty();
    if(data.length>1) {
        var table = '';
        for (var i in data) {
            table += (i === 0 ? '<thead>' : '') + '<tr>';
            for (var j in data[i]) {
                if (i != 0) {
                    table += '<td>' + (data[i][j] === null ? '' : data[i][j]) + '</td>';
                } else {
                    table += '<th>' + data[i][j] + '</th>';
                }
            }
            table += (i === 0 ? '<thead>' : '') + '</tr>';
        }
        report.append(table);
    }else{
        report.html("Empty");
        $('.export_button').css({'display': 'none'});
    }
}

function generateCourseList(data) {
    var courseList = $(".course_setting");
    courseList.empty();
    for (var index in data) {
        courseList.append("<option>" + data[index] + "</option>");
    }
}

function generateStatusList(data) {
    var statusList = $(".status");
    statusList.empty();
    statusList.append("<option>" + "ALL" + "</option>");
    for (var index in data) {
        statusList.append("<option>" + data[index] + "</option>");
    }
}

function ajax(url, success, error, data) {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/" + url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        data: data,
        success: success,
        error: error
    });
}