var totalPage;
var pageSize=13;
var dateList;
//获取组织
$.ajax({
    type:"GET",
    url:"/org/getAllOrg",
    success:function(data){
        if(data.code === 200){
            array = data.data;
            //将数据填充
            showOrg();
        }else{
            alert(data.msg);
        }
    },
    error:function(xhr){
        console.log(xhr);
        alert("程序错误");
    }
});
var loginPath="/web/login.html";
$.ajax({
    type:"GET",
    url:"/material/searchMaterial",
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
            '<td>'+list[i].orgName+'</td>'+
            '<td>'+list[i].lendNum+'</td>'+
            '<td>'+list[i].principalName+'</td>'+
            '<td>'+list[i].principalPhoneNum+'</td>'+
            '<td>'+'<a href='+list[i].photoPath+'>详情</a>'+'</td>'+
            '</tr>');
    }
}

function showPage(pageTotal){
    var div = $("#div-page");
    div.children().hide();
    var page = pageTotal>20? 20:pageTotal;
    for(var i=page;i>=1;i--){
        div.prepend('<li><a href="#" onclick="getMaterialByPage('+i+')">'+i+'</a></li>');
    }
}

function showOrg(){
    for(var i=0;i<array.length;i++){
        $("#orgName").prepend('<option>'+array[i]+'</option>');
    }
}

function getMaterialByPage(pageNum){
    var materialName = $("#materialName").val().trim();
    var orgName = $("#orgName").val().trim();
    var param ;
    if(pageNum !== null && pageNum !== ''){
        param = "pageNum="+pageNum+"&pageSize="+pageSize;
    }
    if(materialName !== null && materialName !== ''){
        param = param + "&materialName="+materialName;
    }
    if(orgName !== null && orgName !== '请选择'){
        param = param + "&orgName="+orgName;
    }
    $.ajax({
        type:"POST",
        url:"/material/searchMaterial",
        data:param,
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

function search(){
    //默认查询第一页
    getMaterialByPage(1);
}