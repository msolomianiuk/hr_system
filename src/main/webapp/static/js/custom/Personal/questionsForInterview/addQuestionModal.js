function initAddQuestionLink() {
    $("a.add-question-link").on("click", function () {
        var sublectId = $(this).parents('.subject-body').attr('id').replace('subject-', '');
        $('#add-question .subject-list option[value="' + sublectId + '"]').attr('selected', 'selected');
        $('#add-question input[name="questionValue"]').val('');
        console.log(sublectId);
    });
    initSaveQuestion();
}