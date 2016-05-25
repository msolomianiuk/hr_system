
function loadCandidatesList() {
    var listSize = 10;
    $.ajax({
        url: baseUrl + "/hr/service/getCandidateCount",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            for (var i=0; i<((parseInt(data/listSize))+1); i++){
                getCandidatsListWithTo((i*listSize), listSize);
            }
            $('.loading').attr('style', 'display: none');
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function getCandidatsListWithTo(offset, limit){
    $.ajax({
        url: baseUrl + "/hr/service/getCandidatsListWithTo",
        type: "GET",
        data: {
            'offset' : offset,
            'limit' : limit
        },
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            console.log(data);
            addInTableCandidates(data);
            initTrigger();
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