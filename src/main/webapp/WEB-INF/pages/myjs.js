$(document).ready(function () {

    $(".buttomsearch").bind("click", function () {


        var text_of_search = "Hello";

        $.ajax({
            url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/student/",
            type: "GET",
            beforeSend: funcbefor,
            data: {'text': text_of_search},
            dataType: "json",
            success: funcSuccess,
            error: function (data) {
                console.log(data);
            }
        });
    });
});

function funcbefor(){
   $("#NameTable").text("Загрузка данных");
}
function funcSuccess(data){
    dataNew = data;

    $(".First").append(data.user.email);
    $(".Second").append(data.user.name);
    //$(".Third").append();
}