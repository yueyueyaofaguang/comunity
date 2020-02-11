function reply() {
    let parentId = $(this).parent().find("[data-ipt='parentId']").val();
    let content = $(this).parent().find("[data-ipt='description']").val();
    let type = $(this).attr('data-type');
    if (!content) {
        alert("输入内容不能为空");
        return;
    }

    let data = {
        parentId: parentId,
        content: content,
        type: type
    };

    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: rep => {
            console.log(rep);
            alert(rep.message);
            if (rep.code == 200) {
                location.reload();
            }
        },
        error: (message) => {
            console.log(message);
        }
    })
}

$(document).ready(function() {
    $(".btn-reply").click(reply);
})