$(document).ready(function () {
    loadQuestions();
    $("button#search").on("click", function () {
        loadCandidateById($(this).parents('.input-group').find('input').val());
    });
});
