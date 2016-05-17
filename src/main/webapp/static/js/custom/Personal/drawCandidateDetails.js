function cleanCandidateDetails() {
    $('#candidate-details h4').html("Candidate #"); //modal only
    $('.candidate-details').html($('.candidate-details-template').html());
}

function drawCandidateDetails(candidate) {
    $('.candidate-details').show();
    $('.interview-comments').show();
    cleanCandidateDetails();
    $('#candidate-details h4').append(candidate.id); // for modal only
    $('div.candidate-details h3').html(candidate.user.name+' '+candidate.user.surname+' '+candidate.user.patronymic);
    $('div.candidate-details img').attr('src', $('div.candidate-details img').attr('src')+candidate.user.image);
    questionsList.forEach(function (question) {
        $('div.candidate-details ul').append("<li data-question="+question.id+">" +
        "<div class='question-caption'><strong>" +
            "<span>"+question.caption+"</span>" +
            "</strong></div>" +
            "<div class='question-answers'>" +
            "</div>"+
        "<div class='ln_solid'></div>"+
            "</li>");
    });
    var answersList = candidate.answers;
    answersList.forEach(function (answer) {
        var li = $("div.candidate-details ul li[data-question='"+answer.questionId+"'] div.question-answers");
        if (li.html() !== ""){
            li.append(', ');
        }
        li.append(answer.value);
    });
    $('.interview-comments>div').html('');
    var interviewResultsList = candidate.interviewResults;
    var currentInterviewerIndicator = false;
    if (interviewResultsList.length > 0 ){
        interviewResultsList.forEach( function (interviewResult){
            var interviewResultBlock = $('.interview-comments-template .interview-comment').clone();
            var interviewerRoles ='';
            var interviewerId = interviewResult.interviewer.id;
            if (interviewerId == parseInt( $('.container.body').attr('user-id') )){
                currentInterviewerIndicator = true;
            }
            interviewResult.interviewer.roles.forEach( function(role){
                if(interviewerRoles !== ''){
                    interviewerRoles += "&";
                }
                interviewerRoles += role;
            });
            interviewResultBlock.find('.interviewer-detail').html(interviewResult.interviewer.name + " "+
                interviewResult.interviewer.name+" - "+interviewerRoles);
            interviewResultBlock.find('.recomendation span').html(interviewResult.recommendation);
            interviewResultBlock.find('.comment_text span').html( interviewResult.comment);
            interviewResultBlock.find('.inteview-mark span').html(interviewResult.mark);

            $('.interview-comments>div').append(interviewResultBlock);
        });
        
    } else {
        $('.interview-comments>div').append('<h5 class=" text-center">No comments</h5>');
        $('.interview-comments>div').append('<div class="ln_solid"></div>');
    }
    if(!currentInterviewerIndicator){
        $('.form-interview-result').show();
    }
}

