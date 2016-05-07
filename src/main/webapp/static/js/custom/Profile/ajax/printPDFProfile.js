$("a#get-pdf").on("click", function(){
    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/service/getPDF",
        type: "GET",
        contentType: 'application/json',
        mimeType: 'application/json',
        error: function (data) {
            console.log(data);
        }
    });
});