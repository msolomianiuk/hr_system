function loadCandidateById(candidateId) {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/hr/service/getCandidateById",
        type: "GET",
        dataType: "json",
        data: 'id='+candidateId,
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            drawCandidateDetails(data);
        },
        error: function (data) {
            console.log(data);
        }
    });
}
