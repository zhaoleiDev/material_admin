var orgMember;
$.ajax({
    type:"GET",
    url:"/user/getOrgMember",
    success:function(data){
        if(data.code === 200){
            orgMember = data.data;
            showOrgMember();
        }else if(data.code === 411){
            window.location.href = "/web/login.html"
        }else{
            alert(data.msg);
        }
    },
    error:function(xhr){
        console.log(xhr);
        alert("程序错误");
    }
});

function showOrgMember(){
    for(var i=0;i<orgMember.length;i++){
        $("#orgToken").prepend('<option>'+orgMember[i].studentNum+":"+orgMember[i].userName+'</option>');
    }
}

function submit(){
    //var form = $("#materialForm");
    var dataForm = new FormData();
    var materialName = $("#stNum").val();
    var totalNum = $("#userName").val();
    var lendNum = $("#orgName").val();
    var temp = $("#orgToken").val().split(":");
    var principalStNum = temp[0];
    //注意图片的接收方式
    var file = $("#photo")[0].files[0];
    if(totalNum < lendNum){
        alert("可借出数量不能大于物资总量");
        return;
    }
    if(materialName === null || totalNum === null || lendNum === null || principalStNum === null||
        materialName === '' || totalNum === '' || lendNum === '' || principalStNum === '' || file === null || file === undefined){
        alert("所有信息为必填项");
        return;
    }
    dataForm.append("materialName",materialName);
    dataForm.append("totalNum",totalNum);
    dataForm.append("lendNum",lendNum);
    dataForm.append("principalStNum",principalStNum);
    dataForm.append("file",file);
    $.ajax({
        type:"POST",
        url:"/material/entry",
        //不让jquery对参数进行处理,下面两项是必须设置的
        processData: false,
        contentType: false,
        data:dataForm,
        //data:"materialName="+materialName+"&totalNum="+totalNum+"&lendNum="+lendNum+"&principalStNum="+principalStNum,
        success:function(data){
            if(data.code === 200){
                alert("录入成功");
            }else if(data.code === 411){
                window.location.href = "/web/login.html"
            }else{
                alert(data.msg);
            }
        },
        error:function(xhr){
            console.log(xhr);
            alert("程序错误");
        }
    });

}