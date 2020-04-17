var token = getUrlParam("token");
var cookie = getCookie("oldToken");
if (token == null || token == "") {
    if (cookie != null && cookie != "") {
        token = cookie;
    }
}
if (token != null) {
    $.post("/index/checkToken", {token: token}, function (result) {
        if (result != "failed") {
            // 删除登录按钮
            $(".codediv").css("display", "none");
            $(".registerlogin").css("display", "none");
            $(".exit").show();
            if (result != "success") {
                $("#phonenum").val(result);
                $("#phonenum").attr("disabled", "disabled");
            }
            // 展示 你好
            $("#headp").css("display", "block");

            // 拿头像和昵称
            $.post("/index/getNickname", {token: token}, function (result) {
                if (result != "failed") {
                    $("#nickname1").text(result);
                }

            });
            $.post("/index/getPic", {token: token}, function (result) {
                if (result != "failed") {
                    $("#headPic").attr("src", result);
                }

            });
        }
    });
}

function getCookie(c_name) {
//判断document.cookie对象里面是否存有cookie
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=")
        //如果document.cookie对象里面有cookie则查找是否有指定的cookie，如果有则返回指定的cookie值，如果没有则返回空字符串
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1
            c_end = document.cookie.indexOf(";", c_start)
            if (c_end == -1) c_end = document.cookie.length
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return ""
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}