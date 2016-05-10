<!-- Button trigger modal -->
<button type="button" class="btn btn-success question-interview-button"
        data-toggle="modal" data-target="#questions-for-interview">
    Questions
</button>

<!-- Modal -->
<div class="modal fade" id="questions-for-interview" tabindex="-1" role="dialog" aria-labelledby="questions-for-interviewLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="questions-for-interviewLabel">Questions for Interview</h4>
            </div>
            <div class="modal-body">
                <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse" data-target="#subject-1" aria-expanded="false"
                        aria-controls="collapseExample">
                    Subject 1
                </button>
                <div class="collapse" id="subject-1">
                    <div class="well ">
                        <ul class="question-list">
                            <li>
                                <span class="question-text">Question-1</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-2</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-3</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-4</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                        </ul>
                        <a class="add-question-link" data-toggle="modal" data-target="#add-question"><i class="fa fa-plus-circle"></i>Add Question</a>
                    </div>
                </div>
                <div class="clearfix"></div>
                </br>
                <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse" data-target="#subject-2" aria-expanded="false"
                        aria-controls="collapseExample">
                    Subject 2
                </button>
                <div class="collapse" id="subject-2">
                    <div class="well ">
                        <ul class="question-list">
                            <li>
                                <span class="question-text">Question-1</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-2</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-3</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-4</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                        </ul>
                        <a class="add-question-link" data-toggle="modal" data-target="#add-question"><i class="fa fa-plus-circle"></i>Add Question</a>
                    </div>
                </div>
                <div class="clearfix"></div>
                </br>
                <button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse" data-target="#subject-3" aria-expanded="false"
                        aria-controls="collapseExample">
                    Subject 3
                </button>
                <div class="collapse" id="subject-3">
                    <div class="well ">
                        <ul class="question-list">
                            <li>
                                <span class="question-text">Question-1</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-2</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-3</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                            <li>
                                <span class="question-text">Question-4</span><div class="question-edit pull-right"><a data-toggle="modal" data-target="#edit-question"><i class="fa fa-pencil"></i></a><a data-toggle="modal" data-target="#delete-question"><i class="fa fa-remove"></i></a></div>
                            </li>
                        </ul>
                        <a class="add-question-link" data-toggle="modal" data-target="#add-question"><i class="fa fa-plus-circle"></i>Add Question</a>
                    </div>
                </div>
                <div class="clearfix"></div>
                </br>
                <button type="button" data-toggle="modal" data-target="#add-subject" class="btn btn-success add-subject-link" ><i class="fa fa-plus-circle"></i><span>Add Subject</span></button>
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
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="add-questionLabel">Add Question</h4>
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


<div class="modal fade" id="edit-question" tabindex="-1" role="dialog" aria-labelledby="edit-questionLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="edit-questionLabel">Edit Question</h4>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Edit</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="delete-question" tabindex="-1" role="dialog" aria-labelledby="delete-questionLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content panel-danger">
            <div class="modal-header panel-heading">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="delete-questionLabel">Delete Question</h4>
            </div>
            <div class="modal-body">
                Are you sure you want to DELETE question "
                <span class="question-text"></span>
                "?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-primary">Yes</button>
            </div>
        </div>
    </div>
</div>