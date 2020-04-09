var totalPage;
var pageSize = 13;
getLendMaterial(1);

function getLendMaterial(pageNum){
   $.ajax({
       type:"GET",
       url:"/lendBorrow/getLendMaterial",
       data:"pageNum="+pageNum+"&pageSize="+pageSize,
       success:function(data){
           if(data.code === 200){
               showLendMaterial(data.data);
               totalPage = Math.floor(data.page.total/pageSize)+1;
               showPage(totalPage);
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

function showPage(totalPage){
    var page = totalPage >20? 20:totalPage;
    var div = $("#div-page");
    div.children().hide();
    for(var i=page;i>=1;i--){
        div.prepend('<li><a href="#" onclick="getLendMaterial('+i+')">'+i+'</a></li>');
    }
}

function showLendMaterial(list){
    $(".tr-data").hide();
    for(var i=0;i<list.length;i++){
        var status = list[i].ackRevertStNum === ''? "未归还":"已归还";
        var prepend = '<tr class="tr-data">'+
            '<td>'+list[i].materialName+'</td>'+
            '<td>'+list[i].materialOrg+'</td>'+
            '<td>'+list[i].number+'</td>'+
            '<td>'+list[i].borrowName+'</td>'+
            '<td>'+list[i].borrowPhoneNum+'</td>'+
            '<td>'+list[i].operationTime+'</td>'+
            '<td>'+list[i].promiseTime+'</td>'+
            '<td>'+list[i].ackRevertStNum+'</td>'+
            '<td>'+status+'</td>'+
            '<td>'+'<a onclick="ackLend('+list[i].id+","+list[i].materialId+","+list[i].number+')">归还确认</a>'+'</td>'+
            '</tr>';
        if(status === "已归还"){
            prepend = '<tr class="tr-data">'+
                '<td>'+list[i].materialName+'</td>'+
                '<td>'+list[i].materialOrg+'</td>'+
                '<td>'+list[i].number+'</td>'+
                '<td>'+list[i].borrowName+'</td>'+
                '<td>'+list[i].borrowPhoneNum+'</td>'+
                '<td>'+list[i].operationTime+'</td>'+
                '<td>'+list[i].promiseTime+'</td>'+
                '<td>'+list[i].ackRevertStNum+'</td>'+
                '<td>'+status+'</td>'+
                '<td></td>'+
                '</tr>';
        }

        $("#orgMaterial").prepend(prepend);
    }
}

function ackLend(id,materialId,number){
    alert("确认归还");
    $.ajax({
        type:"Post",
        url:"/lendBorrow/ackRevert",
        data:"id="+id+"&materialId="+materialId+"&number="+number,
        success:function(data){
            if(data.code === 200){
                alert("归还成功");
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