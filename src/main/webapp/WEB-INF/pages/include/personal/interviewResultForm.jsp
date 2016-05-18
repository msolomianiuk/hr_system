<form class="form-interview-result form-horizontal form-label-left col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12" novalidate>

    <!-- comment input -->
    <div class="item form-group text-input-type">
        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
            Recomendation:
        </label>
        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
            <select class="form-control col-md-7 col-xs-12 select-and-text" name="recomendation">
                <option value="TOWORK">TOWORK</option>
                <option value="TOCOURSE">TOCOURSE</option>
                <option value="REFUSE">REFUSE</option>
            </select>
        </div>
    </div>

    <!-- mark input -->
    <div class="item form-group text-input-type">
        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
            Mark:
        </label>
        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
            <input type="number" name="mark" required="required" class="form-control col-md-7 col-xs-12" data-validate-minmax="1,5">
        </div>
    </div>

    <!-- comment input -->
    <div class="item form-group text-input-type">
        <label class="control-label col-md-4 col-sm-4 col-xs-12 caption">
            Comment:
        </label>
        <div class="col-md-8 col-sm-8 col-xs-12 control-label">
            <textarea type="text" name="comment" required="required" class="form-control col-md-7 col-xs-12"></textarea>
        </div>
    </div>

    <div class="ln_solid"></div>
    <div class="form-group">
        <div class="col-md-6 col-md-offset-3">
            <button id="interview-submit" type="button" class="btn btn-primary">Submit</button>
            <button id="interview-cancel" type="button" class="btn btn-danger">Cancel</button>
        </div>
    </div>
</form>