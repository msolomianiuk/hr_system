function insertAnswersInCandidateForm(answers) {
    for (var index in answers) {
        switch (answers[index].type) {
            case "String":
                questionType = "text";
                break
            case "int":
                questionType = "int";
                break
            case "combobox":
                $('.candidate-profile .questions .item [name*="question-' + index + '"] option[value*="' + answers[index] + '"]').attr('selected', 'selected');
                break
            case "checkbox":
                questionType = "check";
                break
            case 5:
                questionType = "textANDselect";
                break

        }

    }
}