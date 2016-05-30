/**
 * Created by Владимир on 22.05.2016.
 */
var reports;
var reportIndex;
var delReports;
var isValidQuery = true;
$(document).ready(function () {
    init();
    $.validator.addMethod('checkQuery', function (value, element) {
        return this.optional(element) || isValidQuery;
    }, "This is bad query calls DB ERROR. Change it and click Save!!!");
    $.validator.addMethod('checkSql', function (value, element) {
        value = value.toLowerCase();
        return this.optional(element) || (value.indexOf("create") === -1 && value.indexOf("drop") === -1 && value.indexOf("delete") === -1 && value.indexOf("update") === -1 && value.indexOf("alter") === -1)
    }, "You can not use words - create, drop, delete, update, alter");
    var validateForm = $(".validate").validate({
        rules: {
            description: "required",
            query: {
                required: true,
                checkSql: true,
                checkQuery: true
            }
        }
    });
    $(".btn-delete").on("click", function () {
        reports[reportIndex].show = false;
        ajax("service/updateReportQuery", function () {
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
    });
    $(".btn-save").on("click", function () {
        isValidQuery = true;
        validateForm.form();
        ajax("service/checkQuery", function (data){
            isValidQuery = data;
            saveHandler();
        }, function (data) {
            saveHandler();
        }, {sql: $(".query").val()});
    });
    function saveHandler() {
        if (validateForm.form()) {
            $(".btn-update").css({'display': 'inline-block'});
            $(".btn-delete").css({'display': 'inline-block'});
            $(".btn-save").css({'display': 'none'});
            if (reportIndex !== -1) {
                reports[reportIndex].description = $(".description").val();
                reports[reportIndex].query = $(".query").val();
                ajax("service/updateReportQuery", function () {
                }, function () {
                }, reports[reportIndex]);
            } else {
                var report = {};
                report.description = $(".description").val();
                report.query = $(".query").val();
                report.show = true;
                reportIndex = reports.push(report) - 1;
                ajax("service/insertReportQuery", function () {
                    getAllReports();
                }, function () {
                }, report);
            }
            showDevModal();
            generateReportsList(reports);
            generateDeveloperReportsList(reports);
        }
        isValidQuery = true;
    }

    $(".btn-cancel").on("click", function () {
        $(".btn-update").css({'display': 'inline-block'});
        $(".btn-delete").css({'display': 'inline-block'});
        $(".btn-save").css({'display': 'none'});
        showDevModal();
    });
    $(".btn-create").on("click", function () {
        $('#myModalLabel1').text("Yo can create report");
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
        reportIndex = -1;
    });
    $(".btn-show-deleted-reports").on("click", function () {
        ajax('service/getDeletedReports', function (data) {
            delReports = data;
            generateDelReportList(delReports)
        }, function () {
            $('deleted_reports').html('Empty');
        });
    });

})
;

function generateDelReportList(data) {
    var reportList = $('.deleted_reports');
    reportList.empty();
    if (data === undefined || data.length === 0) {
        reportList.append('Empty');
        return;
    }
    var table = '<table class = "table-striped table-bordered" style="width:95%;"><tr><th>Report Name</th><th>Report Body</th><th></th></tr>';
    for (var index in data) {
        table += '<tr><td>' + data[index].description + "</td><td>" + data[index].query + '</td><td><button value =' + index + ' class = "btn btn-success btn-return">Return</button></td></tr>';
    }
    table += '</table>';
    reportList.append(table);
    $('.btn-return').on('click', function () {
        var index = $(this).val();
        delReports[index].show = true;
        ajax("service/updateReportQuery", function () {
        }, function () {
        }, delReports[index]);
        if (reports === undefined) {
            reports = [];
        }
        reports.push(delReports[index]);
        delReports.splice(index, 1);
        generateDelReportList(delReports);
        generateDeveloperReportsList(reports);
        generateReportsList(reports);
    });
}

function getAllReports() {
    ajax("service/getAllShowReportQuery", function (data) {
        reports = data;
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
    if (data === undefined || data.length === 0) {
        report.append('Empty');
        return;
    }
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
            var status = $(".status").val();
            if (status === "ALL") {
                ajax("service/createReportByCourse", showReport, getReportError, {
                    courseId: $(".course_setting").val()
                });
            } else {
                ajax("service/createReportByCourseAndStatus", showReport, getReportError, {
                    courseId: $(".course_setting").val(),
                    status: status
                });
            }
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
    if (data === undefined || data.length === 0) {
        report.append('Empty');
        return;
    }
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
        $('#myModalLabel1').text("You can update or delete this report");
    });
}

function showDevModal() {
    var textQuery = $(".dev_panel_query");
    textQuery.empty();
    textQuery.append('<h4> Query: ' + reports[reportIndex].query + '</h4>');
    var textDescription = $(".dev_panel_description");
    textDescription.empty();
    textDescription.append('<h4> Brief Description: ' + reports[reportIndex].description + '</h4>');
    //$(".btn-cancel").css({'display': 'none'});
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
    if (data.length > 1) {
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
    } else {
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
/*<<<<<<< HEAD
        url: "http://31.131.25.206:8080/hr_system-1.0-SNAPSHOT/admin/" + url,
=======*/
        url: baseUrl + "/admin/" + url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        data: data,
        success: success,
        error: error
    });
}