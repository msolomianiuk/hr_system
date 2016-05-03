/**
 * Created by Серый on 30.04.2016.
 */
$(document).ready(function() {

    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/student/test",
        type: "GET",
        //   contentType : "application/json",
        //beforeSend: funcbefor,
        dataType: "json",
        // data:{'id':id},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcForAjax,
        error: function (data) {
            console.log(data);
        }
    });


});

function funcForAjax(data){
    dataNewIn = data;
    for(var indexba in data["BA"])
    {
        baIndex = data["BA"][indexba];
          $("#TableBA").append('<tr>' +
                                  '<td>'+baIndex.name+'</td>' +
                                  '<td>'+baIndex.surname+'</td>' +
                                  '<td>'+baIndex.patronymic+'</td>' +
                                  '<td>'+baIndex.email+'</td>' +
                               '</tr>');
    }
    for(var indexhr in data["HR"])
    {
        hrIndex = data["HR"][indexhr];
        $("#TableHR").append('<tr>' +
                                '<td>'+hrIndex.name+'</td>' +
                                '<td>'+hrIndex.surname+'</td>' +
                                '<td>'+hrIndex.patronymic+'</td>' +
                                '<td>'+hrIndex.email+'</td>' +
                             '</tr>');
    }
    for(var indexdev in data["DEV"])
    {
        devIndex = data["DEV"][indexdev];
        $("#TableDEV").append('<tr>' +
        '<td>'+devIndex.name+'</td>' +
        '<td>'+devIndex.surname+'</td>' +
        '<td>'+devIndex.patronymic+'</td>' +
        '<td>'+devIndex.email+'</td>' +
        '</tr>');
    }

}


