//获取参数上的值
function getParams(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}
var id = getParams("id");
$.ajax({
    type:"GET",
    url:"/material/getMaterialById",
    data:"id="+id,
    success:function(data){
        if(data.code === 200){
            var material = data.data;
            $("#stNum").val(material.materialName);
            $("#userName").val(material.totalNum);
            $("#orgName").val(material.lendNum);
            $("#orgToken").val(material.principalStNum+":"+material.principalName);
        }else if(data.code === 411){
            window.location.href = "web/login.html"
        }else{
            alert(data.msg);
        }

    },
    error:function(xhr){
        console.log(xhr);
        alert("程序错误");
    }
});

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

function update(){
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

    dataForm.append("materialName",materialName);
    dataForm.append("totalNum",totalNum);
    dataForm.append("lendNum",lendNum);
    dataForm.append("principalStNum",principalStNum);
    dataForm.append("file",file);
    dataForm.append("id",id);
    $.ajax({
        type:"POST",
        url:"/material/update",
        //不让jquery对参数进行处理,下面两项是必须设置的
        processData: false,
        contentType: false,
        data:dataForm,
        //data:"materialName="+materialName+"&totalNum="+totalNum+"&lendNum="+lendNum+"&principalStNum="+principalStNum,
        success:function(data){
            if(data.code === 200){
                alert("更新成功");
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