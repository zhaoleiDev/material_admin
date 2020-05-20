getOrgMember();

function getOrgMember(){
    $.ajax({
        type:"GET",
        url:"/user/getOrgMember",
        success:function(data){
            if(data.code === 200){
                showMember(data.data);
            }else if(data.code === 411){
                window.location.href = "../login.html";
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
function showMember(list){
    $(".tr-data").hide();
    for(var i=0;i<list.length;i++){
        var prepend = '<tr class="tr-data">'+
            '<td>'+list[i].userName+'</td>'+
            '<td>'+list[i].studentNum+'</td>'+
            '<td>'+list[i].phoneNum+'</td>'+
            '<td>'+'<a onclick="deleteMember('+list[i].id+')">删除</a>'+'</td>'+
            '</tr>';
        $("#orgMaterial").prepend(prepend);
    }
}
function deleteMember(memberId){
    alert("确认删除该成员！！！");
    $.ajax({
        type:"POST",
        url:"/user/deleteMember",
        data:"id="+memberId,
        success:function(data){
            if(data.code === 200){
                alert("删除成功,两分钟后生效");
            }else if(data.code === 411){
                window.location.href = "../login.html";
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