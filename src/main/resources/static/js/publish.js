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
    $(".questions-detail-tag").click(addTagToIPt);
    $(".tag-select-pancel").hide();
    $("#tagIpt").focus(_=>{
        $(".tag-select-pancel").show();
    })
})