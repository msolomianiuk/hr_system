function setInterviewResult(candidateId, interviewerId, mark, recomendation, comment) {
    $.ajax({
        url: baseUrl + "/service/setStudentInterviewResult",
        type: "GET",
        dataType: "json",
        data: {
            'candidateId' : candidateId,
            'interviewerId' : interviewerId,
            'mark' : mark,
            'recomendation' : recomendation,
            'comment' : comment
        },
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {
            if ( data ){
                new PNotify({
                    title: 'You save interview result',
                    text: 'You save interview result for candidate with ID="'+candidateId+'"',
                    type: 'success'
                });
                var interviewResultBlock = $('.interview-comments-template .interview-comment').clone();
                interviewResultBlock.find('.interviewer-detail').html('Your comment');
                interviewResultBlock.find('.recomendation span').html(recomendation);
                interviewResultBlock.find('.comment_text span').html( comment);
                interviewResultBlock.find('.inteview-mark span').html( mark );
                $('.interview-comments>div').append(interviewResultBlock);
                $('.form-interview-result').hide();

            } else {
                new PNotify({
                    title: 'Some Problem',
                    text: 'There have been some problems!',
                    type: 'error'
                });
            }
        },
        error: function () {
            new PNotify({
                title: 'Some Problem',
                text: 'There have been some problems!',
                type: 'error'
            });
        }
    });
}