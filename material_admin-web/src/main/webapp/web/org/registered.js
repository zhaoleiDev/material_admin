function orgRegistered(){
    var orgName = $("#orgName").val();
    var principalName = $("#principalName").val();
    var principalStNum = $("#principalStNum").val();
    var principalPhoneNum = $("#principalPhoneNum").val();
    var principalCollege = $("#principalCollege").val();
    if(orgName === null || principalName === null || principalStNum === null || principalPhoneNum === null || principalCollege === null ||
        orgName.trim() === "" || principalName.trim() === "" || principalStNum.trim() === "" || principalPhoneNum.trim() === "" || principalCollege.trim() === ""){
        alert("参数错误");
        return;
    }
    var json = {
        orgName : orgName,
        principalName : principalName,
        principalStNum : principalStNum,
        principalPhoneNum : principalPhoneNum,
        principalCollege : principalCollege
    };
    $.ajax({
        type:"POST",
        url:"/org/registered",
        contentType:"application/json",
        data:JSON.stringify(json),
        success:function(data){
            if(data.code === 411){
                alert("用户未登录");
                window.location.href = "/web/login.html";
            }else if(data.code === 200){
                alert("组织令牌:"+data.data);
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