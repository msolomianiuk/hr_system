$("button#interview-submit").on("click", function () {
    var candidateId =  $('.candidate-details').attr('candidate-id');
    var interviewerId = $('.container.body').attr('user-id');
    var mark = $('.form-interview-result input[name="mark"]').val();
    var recomendation = $('.form-interview-result select[name="recomendation"]').val();
    var comment = $('.form-interview-result textarea[name="comment"]').val();
    setInterviewResult(candidateId, interviewerId, mark, recomendation, comment);
});