$(document).ready(function () {
    $("form").submit(function (event) {
        let url ='FrontController?command=' + $("#command").val()
            + '&' + 'userName' + '='+ $("#userName").val()
            + '&' + 'password' + '='+ $("#password").val();

        $.ajax({
            type: "POST",
            url: url,
            encode: true,
        }).done(function (data) {
            if(data != ""){
                jsonData = JSON.parse(data);
                alert(jsonData['error']);
            }else{
                window.location.href = "FrontController";
            }
        });

        event.preventDefault();
    });
});
