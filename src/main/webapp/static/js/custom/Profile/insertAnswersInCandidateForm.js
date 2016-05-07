function insertAnswersInCandidateForm(answers) {
    // console.log(answers);
    for (var index in answers) {
        var questionType, questionInpute;
        questionInpute = $(".candidate-profile .questions .item [name='question-" + answers[index].questionId + "']").parents('.item.form-group');
        questionType = questionInpute.attr('q-type');
        switch (questionType) {
            case "text-input-type" :
                $('.candidate-profile .questions .item input[name="question-' + answers[index].questionId + '"]').val(answers[index].value);
                break
            case "int-input-type":
                $('.candidate-profile .questions .item input[name="question-' + answers[index].questionId + '"]').val(parseInt(answers[index].value));
                break
            case "select-input-type":
                $(".candidate-profile .questions .item [name='question-" + answers[index].questionId + "'] option[value='" + escapeHtml(answers[index].value) + "']").attr('selected', 'selected');
                break
            case "check-input-type":
                $("input[name='question-" + answers[index].questionId + "'][value='" + answers[index].value + "']").attr('checked','checked')
                break
            case 5:
                questionType = "textANDselect";
                break
        }
    }
}

function escapeHtml(text) {
    return text
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}