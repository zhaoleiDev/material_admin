var totalPage;
var pageSize=13;
var dateList;
var loginPath="/web/login.html";
$.ajax({
    type:"GET",
    url:"/material/getOrgMaterial",
    success:function(data){
        if(data.code === 200){
            dateList = data.data;
            totalPage = Math.floor(data.page.total/pageSize)+1;
            showDate(data.data);
            showPage(totalPage);
        }else if(data.code === 411){
            window.location.href = loginPath;
        }else{
            alert(data.msg);
        }
    },
    error:function(xhr){
        console.log(xhr);
        alert("程序错误");
    }
});
function showDate(list){
    $(".tr-data").hide();
    for(var i=0;i<list.length;i++){
        $("#orgMaterial").prepend('<tr class="tr-data">'+
            '<td>'+list[i].materialName+'</td>'+
            '<td>'+list[i].totalNum+'</td>'+
            '<td>'+list[i].lendNum+'</td>'+
            '<td>'+list[i].principalName+'</td>'+
            '<td>'+list[i].principalPhoneNum+'</td>'+
            '<td>'+'<a href='+list[i].photoPath+'>详情</a>'+'</td>'+
            '<td>'+'<a id="del-material" onclick="lend('+list[i].id+')">借出</a>'+'&nbsp&nbsp'+'<a href="updateMaterial.html?id='+list[i].id+'">修改</a>'+'&nbsp&nbsp'+'<a id="del-material" onclick="deleteMaterial('+list[i].id+')">删除</a>'+'</td>'+
            '</tr>');
    }
}

function deleteMaterial(id){
    alert("确认删除!!!!!!");
    $.ajax({
        type:"POST",
        url:"/material/delete",
        data:"id="+id,
        success:function(data){
            if(data.code === 200){
                alert("删除成功");
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

function showPage(pageTotal){
    for(var i=pageTotal;i>=1;i--){
        $("#div-page").prepend('<li><a href="#" onclick="getMaterialByPage('+i+')">'+i+'</a></li>');
    }
}

function getMaterialByPage(pageNum){
    $.ajax({
        type:"GET",
        url:"/material/getOrgMaterial",
        data:"pageNum="+pageNum+"&pageSize=13",
        success:function(data){
            if(data.code === 200){
                dateList = data.data;
                totalPage = Math.floor(data.page.total/pageSize)+1;
                showDate(data.data);
            }else if(data.code === 411){
                window.location.href = loginPath;
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

function lend(id){
    window.location.href = "lend.html?materialId="+id;
}
