function initEditQuestionLink() {
    $("a.edit-question-link").on("click", function () {
        var sublectId = $(this).parents('.subject-body').attr('id').replace('subject-', '');
        var questionValue = $(this).parents('li').find('.question-text').html();
        var questionId = $(this).parents('li').attr('question-id');
        $('#edit-question .subject-list option[value="' + sublectId + '"]').attr('selected', 'selected');
        $('#edit-question textarea[name="questionValue"]').val(questionValue);
        $('#edit-question form').attr( 'question-id',questionId);
    });
    initEditQuestion();
}