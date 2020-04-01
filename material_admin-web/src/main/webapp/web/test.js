//获取a标签中的值
var page=document.elementFromPoint(event.clientX,event.clientY).text;
//alert(page);
//设置input的值inputvalue
$("#inputvalue").val(page);
//alert($("#inputvalue").val());
}
/*
  获取input的值
  p>=1;  隐藏当前页，显示上一页
 p==0；提示是第一页，并显示第一页
 
 * */
function back(){
    var p=$("#inputvalue").val()
//alert("ddd"+p);
    if(p>=1){
        $("#p"+p).css("display","none");
        /*清除并设置input的值inputvalue*/
        $("#inputvalue").val(p-1);
//alert("当前页为："+$("#inputvalue").val());
        var p=$("#inputvalue").val()
        $("#p"+p).css("display","block");
    }
//alert(p);
    if(p==0){
        alert("This is page first");
        $("#p1").css("display","block");
    }
}
function xiayiye(){
    var p=$("#inputvalue").val()
//alert("ddd"+p);
    if(p<=4){
        $("#p"+p).css("display","none");
        $("#inputvalue").val("");
        $("#inputvalue").val(++p);
//alert("当前页为："+$("#inputvalue").val());
        var p=$("#inputvalue").val()
        $("#p"+p).css("display","block");
    }
//alert(p);
    if(p==5){
        alert("This is page last");
        $("#p4").css("display","block");
    }
}


function page1(){
    $("#p2").css("display","none");
    $("#p3").css("display","none");
    $("#p4").css("display","none");
    $("#p1").css("display","block");
}
function page2(){
    $("#p1").css("display","none");
    $("#p3").css("display","none");
    $("#p4").css("display","none");
    $("#p2").css("display","block");
}
function page3(){
    $("#p2").css("display","none");
    $("#p1").css("display","none");
    $("#p4").css("display","none");
    $("#p3").css("display","block");
}
function page4(){
    $("#p2").css("display","none");
    $("#p3").css("display","none");
    $("#p1").css("display","none");
    $("#p4").css("display","block");
}