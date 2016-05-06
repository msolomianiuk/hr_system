function drawQuestionForm(questionsList) {
    var questionType;
    var questionInput;
    var questionsAnswers;
    for (var index in questionsList) {
        switch (questionsList[index].type) {
            case "String":
                questionType = "text";
                break
            case "int":
                questionType = "int";
                break
            case "combobox":
                questionType = "select";
                break
            case "checkbox":
                questionType = "check";
                break
            case 5:
                questionType = "textANDselect";
                break

        }
        questionInput = $(".hidden ." + questionType + "-input-type").clone();
        questionInput.find("label.caption").html(questionsList[index].caption);
        questionInput.find("label.caption").attr("for", "question-" + questionsList[index].id);
        questionInput.find("input").attr("name", "question-" + questionsList[index].id);
        questionInput.find("select").attr("name", "question-" + questionsList[index].id);
        questionInput.find("input").attr("id", "question-" + questionsList[index].id);

        questionsAnswers = questionsList[index].answerVariants;

        if ((questionType == "select") || (questionType == "textANDselect")) {
            for (var indexAnswer in questionsAnswers) {
                questionInput.find("select").append("<option value='" + questionsAnswers[indexAnswer] + "'>"
                    + questionsAnswers[indexAnswer] + "</option>");
            }
        }
        if (questionType == "check") {
            for (var indexAnswer in questionsAnswers) {

                questionInput.find(".checkbox .column-" + (indexAnswer % 3 + 1)).append(
                    "<label class='' for='question-" + questionsList[index].id + "-" + indexAnswer + "'>" +
                    "<input id='question-" + questionsList[index].id + "-" + indexAnswer +
                    "' name='question-" + questionsList[index].id +
                    "' value = '" + questionsAnswers[indexAnswer] + "' type=\"checkbox\" class=\"flat\" >" +
                    questionsAnswers[indexAnswer] + "</label></br>");
            }
        }
        $(".candidate-profile form .questions").append(questionInput);
    }
    setTimeout(function () {
        $('.loading').css('display', 'none');
        $('.loading .questions_text').hide();
        $('.loading .answers_text').removeClass('hidden');
    }, 1000);

}