/**
 * Created by Серый on 02.05.2016.
 */
$(document).ready(function() {



    $(document).on("click",".getModalStudent",function(){

        id  = parseInt(($(this).attr("candidate_id")));

        $.ajax({
            url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/admin/get_candidate",
            type: "GET",
            //   contentType : "application/json",
            //beforeSend: funcbefor,
            dataType: "json",
            data:{'id':id},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: funcForAnketOfStudents,
            error: function (data) {
                console.log(data);
            }
        });

    });


    $.ajax({
        url: "http://localhost:8080/hr_system-1.0-SNAPSHOT/getStudents",
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
        $("#TableStudents").append('<tr class="win getModalStudent" candidate_id="'+ studentIndex.id +'">' +
        '<td>'+studentIndex.user.name+'</td>' +
        '<td>'+studentIndex.user.surname+'</td>' +
        '<td>'+studentIndex.user.patronymic+'</td>' +
        '<td>'+studentIndex.user.email+'</td>' +
        '</tr>');
    }
    $(".win").mousedown(function(){return false});

    function toggleHider(){
        $("#hider").toggle();
    }

    $(".win").click(toggleHider);
    $("#hider").click(toggleHider);

    $("#hider").click(function(){
        $(".ModelViewStudent").css('display','none');
        $('#hider').css('display','none');
    });
    $(".getModalStudent").click(function(){
        if( $(".ModelViewStudent").css('display') == 'none' ){
            $(".ModelViewStudent").css('display','block');
        } else{
            $(".ModelViewStudent").css('display','none');
        }
    });
}

function funcForAnketOfStudents (data){
    dataInNewAll = data;
    alert("U get Anket");
    $(".ModelViewStudent").empty();
    $(".ModelViewStudent").append('<p>'+data.userId+'</p>');

}