//获取参数上的值
function getParams(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}
var materialId = getParams("materialId");

function lendMaterial(){
    var borrowStNum = $("#userName").val();
    var number = $("#stNum").val();
    var promiseTime = $("#time").val() ;
    if(borrowStNum === null || number === null || promiseTime === null ||
        borrowStNum === "" || number === "" || promiseTime === ""){
        alert("请输入正确的参数");
        return;
    }
    if(number <= 0){
        alert("数量不能小于或等于0");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/lendBorrow/lend",
        data:"materialId="+materialId+"&borrowStNum="+borrowStNum+"&number="+number+"&promiseTime="+promiseTime,
        success:function(data){
            if(data.code === 200){
               alert("借出成功");
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


}