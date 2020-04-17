
window.onload = function () {
    var token = $("#tokenVal").val();
    var oldToken = getCookie("oldToken");
    if (token == null || token == "") {
        token = oldToken;
    }
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




function getCookie(c_name){
//判断document.cookie对象里面是否存有cookie
    if (document.cookie.length>0){
        c_start=document.cookie.indexOf(c_name + "=")
        //如果document.cookie对象里面有cookie则查找是否有指定的cookie，如果有则返回指定的cookie值，如果没有则返回空字符串
        if (c_start!=-1){
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}



// 点击按钮跳转页面（账号管理）
// 账号管理页面
$("#accountManagement").click(function () {
    window.location.href = "/accountManagemet";
})
// 返回个人中心
$("#personalHome2").click(function () {
    window.location.href = "/personal/index";
})
// 基本资料
$("#information").click(function () {
    window.location.href = "/accountManagemet";
})
// 实名认证
$("#autonym").click(function () {
    window.location.href = "/autonym";
})
// 银行卡认证
// $("#bankcard").click(function () {
//     window.location.href = "/autonym";
// })
// 登录密码修改
$("#loginpwdmodify").click(function () {
    window.location.href = "/loginpwdmodify";
})

//支付密码修改
$("#pwdmodify").click(function () {
    window.location.href = "/pwdmodify";
})
//邮箱认证
$("#emailauthentication").click(function () {
    window.location.href = "/emailAuthentication";
})
//手机认证
$("#phonecertification").click(function () {
    window.location.href = "/phonecertification";
})
// 第三方账号绑定
$("#QQbinding").click(function () {
    window.location.href = "/xhyAccountBinding";
})