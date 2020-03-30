var array;
$.ajax({
    type:"GET",
    url:"/org/getAllOrg",
    success:function(data){
        if(data.code === 200){
            array = data.data;
            //将数据填充
            getOrgName();
        }else{
            alert(data.msg);
        }
    },
    error:function(xhr){
        console.log(xhr);
        alert("程序错误");
    }
});

//解决浏览器自动填充
setTimeout(function removeReadonly(){
    var username=document.getElementById("userName");
    var password=document.getElementById("userPassword");
    var password2=document.getElementById("userPassword2");
    username.removeAttribute("readonly");
    password.removeAttribute("readonly");
    password2.removeAttribute("readonly");
},1000);

function getOrgName(){
    for(var i=0;i<array.length;i++){
        $("#orgName").prepend('<option>'+array[i]+'</option>');
    }
}


 function registered(){
    var json ={
        organization:$("#orgName").val(),
        organizationToken:$("#orgToken").val(),
        phoneNum:$("#phoneNum").val(),
        studentNum:$("#stNum").val(),
        userName:$("#userName").val(),
        userPassword:$("#userPassword").val()
    };
    if(json.organization==="" || json.organizationToken === "" || json.phoneNum ==="" || json.studentNum === "" || json.userName === "" || json.userPassword === ""){
        alert("请完善所有信息");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/user/registered",
        data:JSON.stringify(json),
        contentType:"application/json",
        success:function(data){
            if(data.code === 200){
                window.location.href = "login.html";
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

