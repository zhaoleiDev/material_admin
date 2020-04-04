var totalPage;
var pageSize=15;
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
    for(var i=0;i<list.length;i++){
        $("#orgMaterial").prepend('<tr>'+
            '<th>'+list[i].materialName+'</th>'+
            '<th>'+list[i].totalNum+'</th>'+
            '<th>'+list[i].lendNum+'</th>'+
            '<th>'+list[i].principalName+'</th>'+
            '<th>'+list[i].principalPhoneNum+'</th>'+
            '<th>'+'<a href='+list[i].photoPath+'>详情</a>'+'</th>'+
            '<th>'+'<a href="updateMaterial.html">修改</a>'+'&nbsp&nbsp'+'<a id="del-material" onclick="deleteMaterial('+list[i].id+')">删除</a>'+'</th>'+
            '</tr>');
    }
}

function deleteMaterial(id){
    alert("确认删除id为"+id+"的物资"+"\n"+"!!!!!!");
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
        data:"pageNum="+pageNum+"&pageSize=15",
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

}
