menu = $(".menu");
menu.children().hide();
var clickNum=0;

function totalSearch(){
    window.open("../web/material/searchMaterial.html");
}
function totalOrgMaterial(){
    window.open("../web/material/orgMaterial.html");
}
function totalLend(){
    window.open("../web/lendBorrowMaterial/personalLend.html");
}
function totalBorrow(){
    window.open("../web/lendBorrowMaterial/personalBorrow.html");
}
function totalOrgLend(){
    window.open("../web/lendBorrowMaterial/orgLend.html");
}
function totalOrgBorrow(){
    window.open("../web/lendBorrowMaterial/orgBorrow.html");
}
function totalUserInfo(){
    clickNum++;
    if(clickNum === 1){
        menu.children().show();
    }else{
        menu.children().hide();
        clickNum = 0 ;
    }
}
function totalOrgMember(){
    window.open("../web/user/orgMember.html");
}
function totalOperationGuid(){
    window.open("../web/operationGuid.html");
}
function totalUpdateUser(){
    window.open("../web/user/updateUser.html");
}
function totalLogout(){
    alert("确认注销用户！！！！");
    $.ajax({
        type:"GET",
        url:"/user/logout",
        success:function(data){
            if(data.code === 200){
                alert("注销成功  感谢您的使用");
                window.location.href = "../web/registered.html";
            }else if(data.code === 411){
                window.location.href = "../web/login.html";
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

function signOut(){
    alert("确认退出登录");
    $.ajax({
        type:"GET",
        url:"/user/signOut",
        success:function(data){
            if(data.code === 200){
                window.location.href = "../web/login.html";
            }else if(data.code === 411){
                window.location.href = "../web/login.html";
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