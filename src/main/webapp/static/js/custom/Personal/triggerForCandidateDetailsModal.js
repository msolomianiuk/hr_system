function initTrigger() {
    loadQuestions();
    $("#students-table tr").on("click", function () {
        loadCandidateById($(this).attr('candidate-id'));
    }); 
}
