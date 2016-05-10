function loadCandidatesList() {
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/hr/service/getCandidatsList",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success:  function(data) {
            addInTableCandidates(data);
            initTrigger();
        },
        error: function (data) {
            console.log(data);
        }
    });
}
