var subjectList = [];
function drawQuestionsForInterview(subjects) {
    var isAdmin = true;
    if (subjects !== undefined){
        subjects.forEach(
            function (subject) {
                var sublectId = subject.id;
                var sublectValue = subject.value;
                subjectList.push({sublectId : sublectId, sublectValue : sublectValue});
                if(isAdmin) {
                    var subjectObject = $('#questions-for-interview .hidden.subject-template .subject').clone();
                    subjectObject.find('button').attr('data-target', '#subject-' + sublectId);
                    subjectObject.find('.subject-body').attr('id', 'subject-' + sublectId);
                    subjectObject.find('button').html(sublectValue);
                    if (subject.questionForInterviews !== undefined){
                        var questions = subject.questionForInterviews;
                        questions.forEach( function (question) {
                            var questionsObject = $('#questions-for-interview .questions-template li').clone();
                            questionsObject.attr('question-id', question.id);
                            questionsObject.find('.question-text').html(question.value);
                            subjectObject.find('ul.question-list').append(questionsObject);
                        });
                    }
                    $('#questions-for-interview .modal-body').append(subjectObject);
                }
            }
        );
        subjectList.forEach(
            function (subjectFromList) {
                $('.subject-selection-block select').append("<option value = '" + subjectFromList.sublectId + "'>" +
                    subjectFromList.sublectValue + "</option>")
            }
        );
        $('.subject-list').append($('.subject-selection-block select'));

    }
    initAddQuestionLink();
    initEditQuestionLink();
}