/**
 * Created by Серый on 02.05.2016.
 */
currPage = 1;
elementPage = 10;
$(document).ready(function() {

    location_origin = "http://localhost:8080/hr_system-1.0-SNAPSHOT"

    $(document).on("click", ".back", function () {
        location.reload();
    });
    $(document).on("click", ".tip", function () {
        $("#hider").css('display','block');
        $(".ModalHelp").css('display','block');
    });
    $(document).on("click", ".closeModal", function () {
        $("#hider").css('display','none');
        $(".ModalHelp").css('display','none');
    });
    $(document).on("click", "#hider", function () {
        $("#hider").css('display','none');
        $(".ModalHelp").css('display','none');
    });
    $(document).on("click", "#EmailGo", function () {
        var candidateStatus = $("#Status_Email").val();

        $.ajax({
            url: location_origin + "/admin/service/sendEmailToStatus",
            type: "GET",
            dataType: "json",
            data: {'candidateStatus': candidateStatus},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: functionForEmail,
            error: function (data) {
                console.log(data);
            }
        });

    });

    $(document).on("click", ".getModalStudent", function () {

        var id = parseInt(($(this).attr("candidate_id")));

        $.ajax({
            url: location_origin + "/admin/answer_candidate",
            type: "GET",
            dataType: "json",
            data: {'id': id},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: funcForAnketOfStudents,
            error: function (data) {
                console.log(data);
            }
        });

    });


    $(document).on("click", ".getModalStatus", function () {

        var id = parseInt(($(this).attr("candidate_id")));

        $.ajax({
            url: location_origin + "/admin/candidate_id",
            type: "GET",
            dataType: "json",
            data: {'id': id},
            contentType: 'application/json',
            mimeType: 'application/json',
            success: getCandidateId,
            error: function (data) {
                console.log(data);
            }
        });

    });


    $(document).on("click", "#UpdateStatus", function () {

        var candidateID = parseInt(($(this).attr("candidate_id")));

        var status = $("#Status").val();

        $.ajax({
            url: location_origin + "/admin/candidate/update_status",
            type: "GET",
            dataType: "json",
            data: {'candidateID': candidateID, 'status': status},
            success: functionUpdate,
            error: function (data) {
                console.log(data);
            }
        });

    });
    $(document).on("click",".page-link",function(){

       var new_in = $('#fieldSearch').val().replace(/\s+/g, '');


    if(new_in.length) {

            var find = $('#fieldSearch').val().replace(/\s+/g, '');
            elementPage = $("#Rows").val();
            fromElement = $(this).text();
            currPage = parseInt(fromElement);



            $.ajax({
                url: location_origin + "/admin/rowsFind",
                type: "GET",
                dataType: "json",
                data: {'find': find},
                success: rowsAfterFind,
                error: function (data) {
                    console.log(data);
                }
            });


            $.ajax({
                url: location_origin + "/admin/findCandidate",
                type: "GET",
                dataType: "json",
                data: {'find': find, 'elementPage': elementPage, "fromElement": currPage},
                success: funcForStudents,
                error: function (data) {
                    console.log(data);
                }
            });
        }

        else {

                var fromElement = $(this).text();
                elementPage = $("#Rows").val();

                currPage = parseInt(fromElement);
                $.ajax({
                    url: location_origin + "/admin/getRows",
                    type: "GET",
                    dataType: "json",
                    data:{'answersJsonString': JSON.stringify($('.candidate-profile form').serializeObject())},
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: fistPaggination,
                    error: function (data) {
                        console.log(data);
                    }
                });

                $.ajax({
                    url: location_origin + "/admin/paginationCandidate",
                    type: "GET",
                    dataType: "json",
                    data: {'elementPage': elementPage, 'fromElement': fromElement,'answersJsonString': JSON.stringify($('.candidate-profile form').serializeObject())},
                    success: funcForStudents,
                    error: function (data) {
                        console.log(data);
                    }
                });

        }
});
    $(document).on("click", "#buttonSearch", function findClock() {


        var find = $('#fieldSearch').val();
        elementPage = $("#Rows").val();


        if (findClock.count == 0) {
            fromElement = 1;
            currPage = parseInt(fromElement);
            findClock.count = 1;
        }


        if (findClock.count > 1) {
            fromElement = $(this).text();
            currPage = parseInt(fromElement);
        }


        $.ajax({
            url: location_origin + "/admin/rowsFind",
            type: "GET",
            dataType: "json",
            data: {'find': find},
            success: rowsAfterFind,
            error: function (data) {
                console.log(data);
            }
        });


        $.ajax({
            url: location_origin + "/admin/findCandidate",
            type: "GET",
            dataType: "json",
            data: {'find': find, 'elementPage': elementPage, "fromElement": currPage},
            success: funcForStudents,
            error: function (data) {
                console.log(data);
            }
        });

    });

    $.ajax({
    
        url: location_origin + "/admin/getRows",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        data:{'answersJsonString': JSON.stringify($('.candidate-profile form').serializeObject())},
        mimeType: 'application/json',
        success: fistPaggination,
        error: function (data) {
            console.log(data);
        }
    });
    
    $.ajax({
        url: location_origin + "/admin/getFirst",
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',
        success: funcForStudents,
        error: function (data) {
            console.log(data);
        }
    });




});

$("button#filter").on("click", function () {
    $('.loading').attr('style', 'display: flex');
    elementPage = $("#Rows").val();
    fromElement = 1;
    currPage = parseInt(fromElement);
    $.ajax({
        url: location_origin + "/admin/paginationCandidate",
        type: "GET",
        dataType: "json",
        data: {'elementPage': elementPage, 'fromElement': fromElement,'answersJsonString': JSON.stringify($('.candidate-profile form').serializeObject())},
        success: funcForStudents,
        error: function (data) {
            console.log(data);
        }
    });
    $.ajax({
        url: location_origin + "/admin/getRows",
        type: "GET",
        dataType: "json",
        data:{'answersJsonString': JSON.stringify($('.candidate-profile form').serializeObject())},
        contentType: 'application/json',
        mimeType: 'application/json',
        success: fistPaggination,
        error: function (data) {
            console.log(data);
        }
    });
});



function funcForStudents (data){
    dataNewStudents = data;


    $("#TableStudents").empty();

    for(var index_student in data)
    {
        studentIndex = data[index_student];

        status = studentIndex.statusId;

        if(status == 1 ){
            status = "Rejected";
        }
        if(status == 2 ){
            statusNew = "Ready";
        }
        if(status == 3 ){
            statusNew = "No_interview";
        }
        if(status == 4 ){
            statusNew = "Interview";
        }
        if(status == 5 ){
            statusNew = "Interview_dated";
        }
        if(status == 6 ){
            statusNew = "Interview_process";
        }
        if(status == 7 ){
            statusNew = "Interview_passed";
        }
        if(status == 8 ){
            statusNew = "Trainee_accepted";
        }
        if(status == 9 ){
            statusNew = "Job_accepted";
        }

        var ClassName = " ";

        switch(studentIndex.statusId) {
            case 1:
                ClassName = "REJECTED";
                break;
            case 2:
                ClassName = "READY";
                break;
            case 3:
                ClassName = "NO_INTERVIEW";
                break;
            case 4:
                ClassName = "INTERVIEW";
                break;
            case 5:
                ClassName = "INTERVIEW_DATED";
                break;
            case 6:
                ClassName = "INTERVIEW_IN_PROCESS";
                break;
            case 7:
                ClassName = "INTERVIEW_PASSED";
                break;
            case 8:
                ClassName = "TRAINEE_ACCEPTED";
                break;
            case 9:
                ClassName = "JOB_ACCEPTED";
                break;

        }
        var b = "";
        for (var interview_index in studentIndex.interviewResults) {
        b += '<div>'+
                '<p>' + studentIndex.interviewResults[interview_index].mark + '</p>' +
                '<p>' + studentIndex.interviewResults[interview_index].recommendation + '</p>'+
            '</div>';
        }

        $("#TableStudents").append('<tr>' +
        '<td class="Recomendations">'+b+'</td>'+
        '<td  class="'+ClassName+' TextColorBlack" status_id="'+ studentIndex.statusId +'">'+statusNew+'</td>' +
        '<td>'+studentIndex.user.name+'</td>' +
        '<td>'+studentIndex.user.surname+'</td>' +
        '<td>'+studentIndex.user.patronymic+'</td>' +
        '<td>'+studentIndex.user.email+'</td>' +
        '<td>'+
            '<button candidate_id="'+ studentIndex.id +'" data-toggle="modal" data-target = "#candidate-details" ' +
                'type="button" title="Anketa" class="btn btn-success candidateProfile">A</button>'+
            '<button title="Add Status" candidate_id="'+ studentIndex.id +'" type="button" class="getModalStatus btn btn-danger">R</button>'+
        '</td>'+
        '</tr>');




        }






    $('.candidateProfile').on("click", function () {
        var candidateId = $(this).attr('candidate_id');
        loadCandidateById(candidateId);
    });



    $("#hider").click(function(){
        $(".ModelViewStudent").css('display','none');
        $('#hider').css('display','none');
        $('.ModelStatus').css('display','none');
    });
    $(".getModalStudent").click(function(){
        if( $(".ModelViewStudent").css('display') == 'none' ){
            $(".ModelViewStudent").css('display','block');
            $('#hider').css('display','block');
            $('.ModelStatus').css('display','none');
        } else{
            $(".ModelViewStudent").css('display','none');
            $('#hider').css('display','none');
        }
    });

    $(".getModalStatus").click(function(){
        if( $(".ModelStatus").css('display') == 'none' ){
            $(".ModelStatus").css('display','block');
            $('#hider').css('display','block');
            $('#ModelViewStudent').css('display','none');
        } else{
            $(".ModelStatus").css('display','none');
            $('#hider').css('display','none');
        }
    });

}

function funcForAnketOfStudents (data){


    $("#hider").css('display','block');
    $(".ModelViewStudent").css('display','block');

    dataInNewAll = data;

    $(".ModelViewStudent").empty();


    $(".ModelViewStudent").append('<h2 style="text-align: center!important;">Anketa</h2>');

    for (var index in data) {

        index_first = data[[index]];


        $(".ModelViewStudent").css('text-align','left');

        for(var index_in in index_first)
        {
            index_second = index_first[index_in];



            $(".ModelViewStudent").append('<div class="form-group">'+
            '<span class="QuestionInModal">'+ index_second[2]+'</span>'+
            '<span class="AnswerInModal">' +index_second[1] +'</span>'+
            '<div style="margin-top:20px;"></div>'+
            '</div>');

        }


    }

}

function fistPaggination(data){
    pag = data;
    $(function() {
        $("#dark-pagination").pagination({
            items: data,
            itemsOnPage: elementPage,
            cssStyle: 'light-theme',
            currentPage: currPage
        });
    });
}

function rowsAfterFind(data){

    var elements  = parseInt(data);

    $(function() {
        $("#dark-pagination").pagination({
            items: elements,
            itemsOnPage: elementPage,
            cssStyle: 'light-theme',
            currentPage: currPage
        });
    });
}

function functionUpdate(data){
    console.log("Update Status");
    location.reload();
}

function getCandidateId(data){
    $("#UpdateStatus").attr('candidate_id','');

    id_can = data;

    $("#UpdateStatus").attr('candidate_id',id_can);
}

function functionForEmail(data){
    console.log("EmailGo");
}