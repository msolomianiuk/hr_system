var questionsIdArray = [];

function addInTableQuestions(questions) {
    if (questions !== undefined)
        questions.forEach(
            function (value) {
                $('table.candidate-list-table thead tr').append("<th data-question='" + value.id + "' class='question'>" + value.caption + "</th>");
                questionsIdArray.push(value.id);
            }
        );
}