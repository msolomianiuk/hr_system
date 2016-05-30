<!-- Button trigger modal -->
<button type="button" class="btn btn-success question-interview-button"
        data-toggle="modal" data-target="#questions-for-interview">
    Questions
</button>

<!-- Modal -->
<div class="modal fade" id="questions-for-interview" tabindex="-1" role="dialog"
     aria-labelledby="questions-for-interviewLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="questions-for-interviewLabel">Questions for Interview</h4>
            </div>
            <div class="modal-body">

                <div class="hidden subject-template">
                    <div class="subject">
                        <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse"
                                data-target="#subject-1" aria-expanded="false"
                                aria-controls="collapseExample">
                            Subject 1
                        </button>
                        <div class="collapse subject-body" id="subject-0">
                            <div class="well ">
                                <ul class="question-list">
                                </ul>
                                <a class="add-question-link" data-toggle="modal" data-target="#add-question"><i
                                        class="fa fa-plus-circle"></i>Add Question</a>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        </br>
                    </div>
                </div>
                <div class="hidden questions-template">
                    <li>
                        <span class="question-text"></span>
                        <div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"
                                                                 class="edit-question-link"><i
                                class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"
                                                                class="delete-question-link"><i
                                class="fa fa-remove"></i></a></div>
                    </li>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<!-- Other Modals -->

<div class="modal fade" id="add-subject" tabindex="-1" role="dialog" aria-labelledby="add-subjectLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="add-subjectLabel">Add Subject</h4>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Add</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="add-question" tabindex="-1" role="dialog" aria-labelledby="add-questionLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="add-questionLabel">Add Question</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="item form-group">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                            Question:
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
                            <textarea type="text" name="questionValue" required="required" class="form-control col-md-7
                            col-xs-12"></textarea>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                            Subject:
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12 control-label subject-list">

                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary add-question-button">Add</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="edit-question" tabindex="-1" role="dialog" aria-labelledby="edit-questionLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="edit-questionLabel">Edit Question</h4>
            </div>
            <div class="modal-body">
                <form question-id="">
                    <div class="item form-group">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                            Question:
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
                            <textarea type="text" name="questionValue" required="required" class="form-control col-md-7
                            col-xs-12"></textarea>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
                            Subject:
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12 control-label subject-list">

                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary edit-question-button">Edit</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="delete-question" tabindex="-1" role="dialog" aria-labelledby="delete-questionLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content panel-danger">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="delete-questionLabel">Delete Question</h4>
            </div>
            <div class="modal-body">
                <div class="hidden question-id-div" question-id=""></div>
                Are you sure you want to DELETE question "
                <span class="question-text"></span>
                "?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-danger delete-question-button" data-dismiss="modal">Yes</button>
            </div>
        </div>
    </div>
</div>