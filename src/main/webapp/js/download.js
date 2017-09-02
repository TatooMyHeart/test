/**
 * Created by dell on 2017/8/28.
 */
function login() {
    var r="";
    var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;
    var json = {"name":name,"password":password};
    $.ajax({
        type:"post",
        url:"login",
        cache:false,
        async:false,
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify(json),
        success:function (d) {
            r=d;
        }
    });
    alert(r);
}

function download() {
    var task_id = document.getElementById("task_id").value;
    window.location.href = "export?task_id="+task_id;
}