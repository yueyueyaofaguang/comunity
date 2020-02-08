function replyQuestion() {
    let data = {
        parentId: $("#parentId").val(),
        content: $("#description").val(),
        type: 1
    }
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
                $("#description").val("");
            }
        },
        error: (message) => {
            console.log(message);
        }
    })
}