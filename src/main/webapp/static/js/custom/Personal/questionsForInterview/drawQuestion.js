function drawQuestion(questionId, subjectId, questionValue) {
    var questionsObject = $('#questions-for-interview .questions-template li').clone();
    questionsObject.attr('question-id', questionId);
    questionsObject.find('.question-text').html(questionValue);
    $('#questions-for-interview #subject-'+ subjectId +' ul.question-list').append(questionsObject);
    $('#add-question').modal('hide');
}

function drawQuestionEdit(questionId, subjectId, questionValue, object) {
    var questionObject = $('#questions-for-interview ul.question-list li[question-id="'+questionId+'"]');
    if( subjectId == questionObject.parents('.subject-body').attr('id').replace('subject-','') ) {
        object.html(questionValue);
    } else {
        questionObject.remove();
        drawQuestion(questionId, subjectId, questionValue);
    }
    $('#edit-question').modal('hide');
}