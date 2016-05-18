function setCandidateStatus(candidateId, statusId, dataTableObject, statusText) {
    $.ajax({
        url: baseUrl + "/hr/service/setCandidateStatus",
        type: "GET",
        dataType: "json",
        data: {
            'candidateId' : candidateId,
            'statusId' : statusId
        },
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            if ( data ){
                new PNotify({
                    title: 'Changed candidate status',
                    text: 'You change candidate with ID="'+candidateId+'" status on "'+statusText+'"',
                    type: 'success'
                });
                $('tr[candidate-id="'+ candidateId +'"]').css('color', getStatusColor(statusId));
                dataTableObject.fnUpdate(statusText, $('tr[candidate-id="'+ candidateId +'"] td.status-text'), 3);
            } else {
                new PNotify({
                    title: 'Some Problem',
                    text: 'There have been some problems!',
                    type: 'error'
                });
            }
        },
        error: function () {
            new PNotify({
                title: 'Some Problem',
                text: 'There have been some problems!',
                type: 'error'
            });
        }
    });
}