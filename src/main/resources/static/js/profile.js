function changeState(){
    if(!$(this).attr('data-status')) return;
    let id = $(this).attr('data-id');
    $.ajax({
        type: "get",
        url: "/notification/"+id,
        success: rep => {
            console.log(rep);
        },
        error: (message) => {
            console.log(message);
        }
    })
}

$(document).ready(function() {
    $(".changeStateBtn").click(changeState);
})