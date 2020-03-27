function login(){
    /*$.post()(
        "user/login",
        {
            stNum:$("#stNum").get(),
            psw:$("#pwd").get()
        },
        function(data,status){
            alert(data+"==="+status);
        }
    );*/
    //window.open("registered.html");

    $.get("/user/login?stNum=222016333210145&pwd=qqlove",function(data,status){
        window.alert(data+"=="+status);
    });

}