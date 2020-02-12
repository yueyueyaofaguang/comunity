function addTagToIPt(){
    let tags = [];
    let val = $('#tagIpt').val().split(',');
    if(val!="")
        tags= $('#tagIpt').val().split(',');
    let select = $(this).attr('data-tagName');
    tags.push(select);
    $('#tagIpt').attr('value',tags.join(','));
}

$(document).ready(function() {
    var editor = editormd("test-editor", {
        height : "300px",
        path   : "js/lib/",
        delay:0,
        watch:false,
        placeholder:"请输入图片描述",
        imageUpload    : true,
        imageFormats   : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : "file/upload",
    });
    $(".questions-detail-tag").click(addTagToIPt);
    $(".tag-select-pancel").hide();
    $("#tagIpt").focus(_=>{
        $(".tag-select-pancel").show();
    })
})