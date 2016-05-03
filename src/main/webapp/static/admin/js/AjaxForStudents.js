/**
 * Created by Серый on 02.05.2016.
 */
$(document).ready(function() {

    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/student/getStudents",
        type: "GET",
        //   contentType : "application/json",
        //beforeSend: funcbefor,
        dataType: "json",
        // data:{'id':id},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcForStudents,
        error: function (data) {
            console.log(data);
        }
    });

});

function funcForStudents (data){
    dataNewStudents = data;
    for(var index_student in data)
    {
        studentIndex = data[index_student];
        $("#TableStudents").append('<tr candidate_id="'+ studentIndex.id +'">' +
                                        '<td>'+studentIndex.user.name+'</td>' +
                                        '<td>'+studentIndex.user.surname+'</td>' +
                                        '<td>'+studentIndex.user.patronymic+'</td>' +
                                        '<td>'+studentIndex.user.email+'</td>' +
                                   '</tr>');
    }
}

