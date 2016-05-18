
function loadCandidatesList() {
    $.ajax({
        url: baseUrl + "/hr/service/getCandidatsList",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            addInTableCandidates(data);
            initTrigger();
            $('.loading').attr('style', 'display: none');
        },
        error: function (data) {
            console.log(data);
        }
    });
}
var statusAllList;
function getAllStatus() {
    $.ajax({
        url: baseUrl + "/hr/service/getAllStatus",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            drawSatus(data);
        },
        error: function (data) {
            console.log(data);
        }
    });

}

var statusSelectOptionsString = ''
function drawSatus(statusList) {
    for (var index in statusList) {
        statusSelectOptionsString += "<option value='" + index + "'>" + statusList[index] + "</option>";
    }
}