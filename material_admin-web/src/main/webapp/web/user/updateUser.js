getUserInfo();
var loginUrl = "/web/login.html";

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
                window.location.href = loginUrl;
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
    var phoneNum = $("#phoneNum").val();
    var userName = $("#userName").val();
    var userPassword = $("#userPassword").val();
    if(userPassword === "" || userName === "" ||  phoneNum === ""){
        alert("请将修改信息填写完整");
        return ;
    }
    $.ajax({
        type:"POST",
        url:"/user/update",
        data:"phoneNum="+phoneNum+"&userName="+userName+"&userPassword="+userPassword,
        success:function(data){
            if(data.code === 200){
                alert("修改成功");
                window.location.href = "../total.html"
            }else if(data.code === 411){
                window.location.href = loginUrl;
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