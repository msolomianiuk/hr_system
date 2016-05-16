function initDeleteQuestionLink() {
    $("a.delete-question-link").on("click", function () {
        var questionId = $(this).parents('li').attr('question-id');
        console.log(questionId);
        $('#delete-question .question-id-div').attr( 'question-id',questionId);
    });
    initDeleteQuestion();
}