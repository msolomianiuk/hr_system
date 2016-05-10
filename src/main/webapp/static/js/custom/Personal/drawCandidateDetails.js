function cleanCandidateDetails() {
    $('#candidate-details h4').html("Candidate #"); //modal only
    $('.candidate-details').html($('.candidate-details-template').html());
}

function drawCandidateDetails(candidate) {
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
}

