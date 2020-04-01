getUserInfo();
var loginUro = "/web/login.html";

//获取用户信息
function getUserInfo(){
    $.ajax({
        type:"GET",
        url:"/user/getInfo",
        success:function(data){
            if(data.code === 200){
                var res = data.data;
                $("#orgName").val(res.organization);
                $("#orgToken").val(res.organizationToken);
                $("#stNum").val(res.studentNum);
                $("#userName").val(res.userName);
                $("#userPassword").val(res.userPassword);
                $("#phoneNum").val(res.phoneNum);
                $("#userPassword2").val(res.userPassword);
            }else if(data.code === 411){
                window.location.href = loginUro;
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

//更新用户信息
function updateUser(){
    var updateInfo ={
        phoneNum:$("#phoneNum").val(),
        userName:$("#userName").val(),
        userPassword:$("#userPassword").val()
    };
    if(updateInfo.userPassword === "" || updateInfo.userName === "" ||  updateInfo.phoneNum === ""){
        alert("请将修改信息填写完整");
        return ;
    }
    $.ajax({
        type:"POST",
        url:"/user/update",
        data:JSON.stringify(updateInfo),
        success:function(data){
            if(data.code === 200){
                alert("修改成功");
            }else if(data.code === 411){
                window.location.href = loginUro;
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
//核对密码是否一致
function checkPassword(){
    var password = $("#userPassword").val();
    var password2 = $("#userPassword2").val();
    if(password !== password2){
        alert("两次输入的密码不一致");
    }
}