function insertAnswersInCandidateForm(answers) {
    // console.log(answers);
    for (var index in answers) {
        var questionType, questionInpute;
        questionInpute = $('.candidate-profile .questions .item [name="question-' + answers[index].questionId + '"]').parents('.item.form-group')[0];
        questionType = questionInpute.classList[2];
        switch (questionType) {
            case "text-input-type" :
                $('.candidate-profile .questions .item input[name="question-' + answers[index].questionId + '"]').val(answers[index].value);
                break
            case "int-input-type":
                $('.candidate-profile .questions .item input[name="question-' + answers[index].questionId + '"]').val(parseInt(answers[index].value));
                break
            case "select-input-type":
                $('.candidate-profile .questions .item [name="question-' + answers[index].questionId + '"] option[value="' + answers[index].value + '"]').attr('selected', 'selected');
                break
            case "check-input-type":
                $('input[name="question-' + answers[index].questionId + '"][value="' + answers[index].value + '"]').attr('checked','checked')
                break
            case 5:
                questionType = "textANDselect";
                break
        }
    }
}