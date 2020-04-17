
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}
function getCookie(cookieName) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (cookieName == arr[0]) {
            return arr[1];
        }
    }
    return "";
}

function publishTask() {
    var ineed = $("#ineed").val();
    var phonenum = $("#phonenum").val();
    var token = getUrlParam("token");
    var cookie = getCookie("oldToken");
    if (token == "" || token == null) {
        token = cookie;
    }
    var assignmentId1 = 0;
    $.ajaxSetup({
        async: false
    });
    $.post("/publish/taskDetails", {
        ineed: ineed,
        phonenum: phonenum,
        token: token
    }, function (assignmentId) {
        assignmentId1 = assignmentId;
    });
    window.location.href="/publish/index?token="+token+"&assignmentId="+assignmentId1;
}
// 页面初始化加载

window.onload=function () {
    hideCata();
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
                $(".registerlogin").css("display", "none");
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
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

//定时去检测链接地址有无该属性
function hideCata() {
    var tasktype = getUrlParam("tasktype");
    var taskTimeTypeId = getUrlParam("taskTimeTypeId");
    var categoryid = getUrlParam("categoryid");
    if (categoryid != null && categoryid != "") {
        $(".u1").hide();
    }else {
        $(".u1").show();
    }
    if (tasktype != null && tasktype != "") {
        $(".u3").hide();
    }else {
        $(".u3").show();
    }
    if (taskTimeTypeId != null && taskTimeTypeId != "") {
        $(".u2").hide();
    }else {
        $(".u2").show();
    }
}



