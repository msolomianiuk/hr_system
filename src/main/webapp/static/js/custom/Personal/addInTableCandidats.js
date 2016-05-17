function addInTableCandidates(candidates) {
    if (candidates !== undefined) {
        candidates.forEach(
            function (candidate) {
                if (candidate !== undefined) {
                    var answers;
                    var newTd = "<tr data-toggle='modal' data-target='#candidate-details' candidate-id=" + candidate.id + ">" +
                        "<td>" + candidate.id + "</td>" +
                        "<td>" + candidate.user.name + " " + candidate.user.surname + " " + candidate.user.patronymic + "</td>" +
                        "<td class='candidate-status-td'><select class=''>"+statusSelectOptionsString+"</select></td>"+
                        "<td class='status-text hidden'>"+candidate.status+"</td>";
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
                    
                    $('tr[candidate-id="'+ candidate.id +'"]').css('color', getStatusColor(candidate.statusId));
                    $('tr[candidate-id="'+ candidate.id +'"] td select').val(candidate.statusId);
                }
            }
        );
    }

    var dataTableColumnsArray = [null, null, {"bSearchable": false, "orderDataType": "dom-select" },
        null ];
    for (i = 0; i < questionsIdArray.length; i++) {
        dataTableColumnsArray.push(null);
    }
    
    /* Create an array with the values of all the select options in a column */
    $.fn.dataTable.ext.order['dom-select'] = function  ( settings, col )
    {
        return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
            return $('select', td).val();
        } );
    }

    /* Create an array with the values of all the input boxes in a column */
    $.fn.dataTable.ext.order['dom-text'] = function  ( settings, col )
    {
        return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
            return $('input', td).val();
        } );
    }

    var candidatesTable = $('#candidates-list').dataTable({
        "oLanguage": {
            "sSearch": "_INPUT_" //search
        },
        "aoColumns": dataTableColumnsArray
    });

    $('td select').change( function () {
        setCandidateStatus($(this).parents('tr').attr('candidate-id'), $(this).val(), candidatesTable,
            $(this).find('option:selected').text());
    } );



    $('#candidates-list_filter label input').attr('placeholder', 'Search for...');
    $('table.candidate-list-table').on('click', 'td.candidate-status-td select', function(e) {
        e.stopPropagation();
    });
}