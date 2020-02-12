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
    $(function() {
        var testView = editormd.markdownToHTML("markdown-view", {
            // markdown : "[TOC]\n### Hello world!\n## Heading 2", // Also, you can dynamic set Markdown text
            // htmlDecode : true,  // Enable / disable HTML tag encode.
            // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
        });
    });
    $(".btn-reply").click(reply);
})