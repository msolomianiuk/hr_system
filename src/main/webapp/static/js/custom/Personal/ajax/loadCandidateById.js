function loadCandidateById(candidateId) {
    $.ajax({
        url: baseUrl + "/service/getStudent",
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
