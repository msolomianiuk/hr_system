
function loadCandidatesList() {
    $.ajax({
        url: baseUrl + "/hr/service/getCandidatsList",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {

            getAllStatus();
            addInTableCandidates(data);
            initTrigger();
        },
        error: function (data) {
            console.log(data);
        }
    });


}

function getAllStatus() {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/hr/service/getAllStatus",
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

function drawSatus(statusList) {
    for (var index in statusList) {
        $('div.statusSelector select').append("<options value='" + index + "'>" + statusList[index] + "</options>");
    }
}