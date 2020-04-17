$("#clickToChangeColor   button").click(function () {
    $("#clickToChangeColor  button").css("background", "white");
    $("#clickToChangeColor button").css("border", "solid 1px #999999");
    $("#clickToChangeColor button").css("color", "#333333");

    $(this).css("border", "solid 1px #ff5251")
    $(this).css("color", "#ff5251")


});

$(".showpath>div>p>a").click(function () {
    var pathname = $(this).text();
    // 拿到当前对象的id，然后进行字符串拼接即可
    $("#path3").text(pathname);
    var v_id = $(this).attr("id");
})
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

window.onload = function () {
    var token = $("#tokenVal").val();
    var oldToken = getCookie("oldToken");
    if (token == null || token == "") {
        token = oldToken;
    }
    // 拿头像和昵称
    if (token != null) {
        $.post("/index/checkToken", {token: token}, function (result) {
            if (result != "failed") {
                // 删除登录按钮
                $(".registerlogin").css("display", "none");
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

// 点击按钮跳转页面（个人中心）
//我的积分
$("#myIntegral").click(function () {
    window.location.href = "/myIntegral";
})
//我的道具
$("#MyProps").click(function () {
    window.location.href = "/myProps";
})
//我的优惠券
$("#coupon").click(function () {
    window.location.href = "/coupon";
})
//我发布的任务
$("#publish").click(function () {
    window.location.href = "/publishperson";
})
// 购买的设计
$("#buyProduction").click(function () {
    window.location.href = "/buyProduction";
})
//发包方主页
$("#fbfpage").click(function () {
    window.location.href = "/fbfpage";
})
//信誉评价
$("#sellreputation").click(function () {
    window.location.href = "/sellReputation";
})
//我的发票
$("#invoice").click(function () {
    window.location.href = "/invoice";
})
// 我参与的任务
$("#partTask").click(function () {
    window.location.href = "/partTask";
})
//我的作品
$("#myWork").click(function () {
    window.location.href = "/myWork";
})
// 已出售作品
$("#sellWork").click(function () {
    window.location.href = "/sellWork";
})
//我的主页店铺
$("#HomeStore").click(function () {
    window.location.href = "/homeStore";
})
//店铺服务
$("#shopsServiceEmpty").click(function () {
    window.location.href = "/shopsServiceEmpty";
})
// 技能标签设置
$("#skillslable").click(function () {
    window.location.href = "/skillslable";
})
//能力评价
$("#AbilityEvaluation").click(function () {
    window.location.href = "/abilityEvaluation";
})
//诚信卫士
$("#GuardContainer").click(function () {
    window.location.href = "/GuardContainer";
})
// 任务收藏
$("#taskcollection").click(function () {
    window.location.href = "/taskCollection";
})
//用户收藏
$("#userCollect").click(function () {
    window.location.href = "/userCollect";
})
//帖子收藏
$("#CollectionofPosts").click(function () {
    window.location.href = "/collectionofPosts";
})
// 我的退款
$("#myrefund").click(function () {
    window.location.href = "/myRefund";
})
//我的举报
$("#myreport").click(function () {
    window.location.href = "/myReport";
})
// 我的维权
$("#Myrightsdefense").click(function () {
    window.location.href = "/myrightsDefense";
})
$("#accountManagement").click(function () {
    window.location.href = "/accountManagemet";
})


