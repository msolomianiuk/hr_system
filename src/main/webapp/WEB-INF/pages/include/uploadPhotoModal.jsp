<div class="modal fade" id="upload-image" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Upload image</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="img-container">
                            <img src="<c:url value="/static/images/user.png"/>" alt="Picture">
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="docs-preview clearfix">
                            <div class="img-preview preview-md"></div>
                        </div>
                        <div class="btn-group">
                            <label class="btn btn-primary btn-upload" for="inputImage"
                                   title="Upload im--age file">
                                <!--<form method="post" modelAttribute="spitter"
                                      enctype="multipart/form-data">
                                    input class="sr-only" id="inputImage" name="image" type="file" accept="image/*">-->
                                    <span class="docs-tooltip" data-toggle="tooltip"
                                          title="Import image with Blob URLs">
                                        <span class="fa fa-upload"></span>
                                        <span>Upload Photo</span>

                                    </span>

                                <!--</form>-->

                            </label>

                        </div>
                        <p>Size of image must be less then 2MB</p>
                    </div>
                </div>
            </div>
            <div class="modal-footer">

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <form method="post"
                          enctype="multipart/form-data"
                          action="<c:url value="/upload/photo" />?${_csrf.parameterName}=${_csrf.token}"
                          style="display: inline;">
                        <input class="sr-only" id="inputImage" name="inputImage" type="file" accept="image/jpeg">
                        <input id="x" name="x" type="hidden">
                        <input id="y" name="y" type="hidden">
                        <input id="width" name="width" type="hidden">
                        <input id="height" name="height" type="hidden">
                        <input type="submit" class="btn btn-primary" value="Save changes">

                    </form>
                </div>


                <!--<button id="photoUpload" type="button" class="btn btn-primary">Save changes</button>-->
            </div>
        </div>
    </div>
</div>
<!-- image cropping -->
<script src="<c:url value="/static/js/cropping/cropper.min.js"/>"></script>
<script src="<c:url value="/static/js/cropping/main2.js"/>"></script>