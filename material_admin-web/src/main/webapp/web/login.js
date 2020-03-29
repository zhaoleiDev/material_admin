function login(){
    var stNum = $("#stNum").val();
    var pwd = $("#pwd").val();
    $.ajax({
        type:"POST",
        url:"/user/login",
        data:"stNum="+stNum+"&pwd="+pwd,
        success:function(data){
            if(data.code === 410){
                window.location.href = "registered.html";
            }else if(data.code === 200){
                window.location.href = "total.html";
            }else{
                alert(data.msg);
            }
        },
        error:function(xhr){
            console.log(JSON.stringify(xhr));
            alert("程序错误");
        }
    });
}
function registered(){
    window.location.href = "registered.html";
}