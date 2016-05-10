function addInTableCandidates(candidates){
    candidates.forEach(
        
        function(candidate) {
            if(candidate !== undefined ){
                var answers;
                var newTd = "<tr data-toggle='modal' data-target='#candidate-details' candidate-id=" + candidate.id + ">" +
                    "<td>" + candidate.id + "</td>" +
                    "<td>" + candidate.user.name + " " + candidate.user.surname + " " + candidate.user.patronymic + "</td>" +
                    "<td>" + candidate.status + "</td>";
                if (questionsIdArray == []) {
                    newTd += "</tr>";
                    $('table.candidate-list-table #students-table').append(newTd);
                } else {
                    for (i = 0; i < questionsIdArray.length; i++) {
                        newTd += "<td class='question' question-id='" + questionsIdArray[i] + "'></td>";
                    }
                    newTd += "</tr>";
                    $('table.candidate-list-table #students-table').append(newTd);

                    answers = candidate.answers;

                    answers.forEach(
                        function (answer) {
                            var td = $("table.candidate-list-table tr[candidate-id='" + candidate.id + "'] " +
                                "td.question[question-id='" + answer.questionId + "']");
                            if (td.html() !== "") {
                                td.append(", ");
                            }
                            td.append(answer.value);
                        }
                    );
                }
            }
        }
    );
    $('#candidates-list').dataTable({
        "oLanguage": {
            "sSearch": "_INPUT_" //search
        }
    });
    // $('#candidates-list_filter label').html($('#candidates-list_filter label').html().replace('Search:',''));
    $('#candidates-list_filter label input').attr('placeholder', 'Search for...');
}
