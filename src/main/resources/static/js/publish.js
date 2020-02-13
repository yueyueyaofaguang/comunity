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
        toolbarIconsClass : {
            qiniu : "fa-cloud-upload"
        },
        toolbarHandlers : {
            qiniu : function(cm, icon, cursor, selection) {
                this.imageDialogQiniu();
            }
        },
        qiniuTokenUrl : "/getQiniuToken",                        //本地服务器获取七牛token的url
        qiniuPublishUrl : "https://q5l5ljewq.bkt.clouddn.com"    //远程七牛服务器个人发布地址
    });
    $(".questions-detail-tag").click(addTagToIPt);
    $(".tag-select-pancel").hide();
    $("#tagIpt").focus(_=>{
        $(".tag-select-pancel").show();
    })
})