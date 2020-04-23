$("#addbuttoninput").click(function () {
    if ($("#dropz6").css("display") != 'none') {
        $("#dropz7").show();
        return;
    }
    if ($("#dropz5").css("display") != 'none') {
        $("#dropz6").show();
        return;
    }
    if ($("#dropz4").css("display") != 'none') {
        $("#dropz5").show();
        return;
    }
    if ($("#drop3").css("display") != 'none') {
        $("#dropz4").show();
        return;
    }
});


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
    window.location.href = "/publish/index?token=" + token + "&assignmentId=" + assignmentId1;
}

var map1 = new Array();
var map2 = new Array();
// 从cookie中获取assignmentId
var assignmentId = getUrlParam("assignmentId");
if (assignmentId == null || assignmentId == "") {
    assignmentId = getCookie("assignmentId");
}

// 从cookie中获取值
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

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

// 判断数组是否重复
function isRepeat(arr) {
    if (!arr || arr.length < 1) {
        return false;
    }
    var hash = {};
    for (var i in arr) {
        if (hash[arr[i]]) {
            return true;
        }
        hash[arr[i]] = true;
    }
    return false;
}

// 发包方留言的逻辑实现
$("#fabaofangbtn").click(function () {
    var liuyan = $("#txt").val();
    if (liuyan == null || liuyan == "") {
        alert("请输入留言内容,您只有一次留言机会");
    } else {
        // 存入数据库并改变样式
        $.post("/publish/saveRemark", {remark: liuyan}, function (result) {
            if (result == 5) {
                alert("您已经留言过，请勿再次留言");
            }
            if (result == 1) {
                alert("保存留言成功")
                $("#fabaofangdetails").text(liuyan);
                $("#remarkshow").show();
                $("#fabaofanginput").hide();
            }
            if (result == -3) {
                alert("界面异常");
            }
        });
    }
});

// 倒计时定时器
function ShowCountDown(jztime, divname) {
    var now = new Date();
    var endDate = new Date(Date.parse(jztime.replace(/-/g, "/")));
    var leftTime = endDate.getTime() - now.getTime();
    var leftsecond = parseInt(leftTime / 1000);
    var day1 = Math.floor(leftsecond / (60 * 60 * 24));
    var hour = Math.floor((leftsecond - day1 * 24 * 60 * 60) / 3600);
    var minute = Math.floor((leftsecond - day1 * 24 * 60 * 60 - hour * 3600) / 60);
    var second = Math.floor(leftsecond - day1 * 24 * 60 * 60 - hour * 3600 - minute * 60);
    if (day1 < 10) {
        day1 = "0" + day1;
    }
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minute < 10) {
        minute = "0" + minute;
    }
    if (second < 10) {
        second = "0" + second;
    }
    var cc = document.getElementById(divname);
    cc.innerHTML = "剩余：<em class='wxmr4'>" + day1 + "</em>天<em class='wxml4'>" + hour + "</em>小时<em>" + minute + "</em>分钟<em>" + second + "</em>秒后投稿截止";
}

window.onload = function () {
    // 查询是否收藏该任务
    $.post("/shejishi/getTaskCollect",{assignmentId:assignmentId},function (res) {
        if (res == 1) {
            // 收藏
            $("#clickcollect").hide();
            $("#collected").show();
        }
        if (res == 0) {
            // 没有收藏
            $("#clickcollect").show();
            $("#collected").hide();
        }
    })


    if ($("#shejigaojiandiv").length > 0) {
        // $("#tijiaoyuanwenjiandiv").hide();
        $("#baomingcanjia").css("background", "grey");
        $("#nowjifen").hide();
        $("#xiaohaojifen").hide();
        $("#baomingcanjia").attr("disabled", "true");
        $("#baomingcanjia").text("已提交");
    }
    if ($("#shejigaojiandiv1").length > 0) {
        // $("#tijiaoyuanwenjiandiv1").hide();
        $("#baomingcanjia").css("background", "grey");
        $("#nowjifen").hide();
        $("#xiaohaojifen").hide();
        $("#baomingcanjia").attr("disabled", "true");
        $("#baomingcanjia").text("已提交");
    }
    if ($("#shejigaojiandiv2").length > 0) {
        // $("#tijiaoyuanwenjiandiv2").hide();
        $("#baomingcanjia").css("background", "grey");
        $("#nowjifen").hide();
        $("#xiaohaojifen").hide();
        $("#baomingcanjia").attr("disabled", "true");
        $("#baomingcanjia").text("已提交");
    }

    let isUploadHidden = $("#isUploadHidden").val();
    if (isUploadHidden == 1) {
        $("#baomingcanjia").css("background", "grey");
        $("#nowjifen").hide();
        $("#xiaohaojifen").hide();
        $("#baomingcanjia").attr("disabled", "true");
    }
    if (isUploadHidden == 2) {
        $("#baomingcanjia").css("background", "grey");
        $("#nowjifen").hide();
        $("#xiaohaojifen").hide();
        $("#baomingcanjia").attr("disabled", "true");
        $("#baomingcanjia").text("请登录");
    }


    var isvip = $("#isvip").val();
    if (isvip == "0") {
        //非会员状态
        if ($("#taskhidenum").text() == "0") {
            //置顶道具使用完毕 扣钱
            $("#gaojianhidetr1").show();
            $("#gaojianhidetr2").hide();
            $("#gaojianhidetr3").hide();

            $("#gaojianhidetr4").show();
            $("#gaojianhidetr5").hide();
            $("#gaojianhidetr6").hide();
        } else {
            //有道具剩余次数 扣次数
            $("#gaojianhidetr1").hide();
            $("#gaojianhidetr2").show();
            $("#gaojianhidetr3").hide();

            $("#gaojianhidetr4").hide();
            $("#gaojianhidetr5").show();
            $("#gaojianhidetr6").hide();
        }
    }
    if (isvip == "1") {
        //会员状态
        $("#gaojianhidetr1").hide();
        $("#gaojianhidetr2").hide();
        $("#gaojianhidetr3").show();

        $("#gaojianhidetr4").hide();
        $("#gaojianhidetr5").hide();
        $("#gaojianhidetr6").show();
    }


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
                // 展示附件
                $("#logintocat1").hide();
                $("#ceshitupianjihe").show();
                $("#logintocat2").hide();
                $("#zongtifujian").show();
                // 拿头像和昵称
                $.post("/index/getNickname", {token: token}, function (result) {
                    if (result != "failed") {
                        $("#nickname1").text(result);
                    }

                });
                $.post("/index/getPic", {token: token}, function (result) {
                    if (result != "failed") {
                        $("#headPic").attr("src", result);
                        $("#headPicBottom").attr("src", result);
                        $("#headPicCeBian").attr("src", result);
                    }
                });
            }
        });

    } else {
        // 展示登录
        $("#logintocat1").show();
        $("#ceshitupianjihe").hide();
        $("#logintocat2").show();
        $("#zongtifujian").hide();
    }

    // 点亮进度条
    // 将assignmentId存入cookie
    $.cookie("assignmentId", assignmentId, {path: '/'})
    var scheduleStatuVal = $("#scheduleStatushidden").val();
    if (scheduleStatuVal == 1) {
        $("#jindu211").css("background", "red");
        $("#jindu212").css("background", "red");
        $("#jindu111").css("background", "red");
        $("#jindu112").css("background", "red");
    }
    if (scheduleStatuVal == 2) {
        $("#jindu211").css('background', 'red');
        $("#jindu212").css('background', 'red');
        $("#jindu221").css('background', 'red');
        $("#jindu222").css('background', 'red');
        $("#jindu111").css('background', 'red');
        $("#jindu112").css('background', 'red');
        $("#jindu121").css('background', 'red');
        $("#jindu122").css('background', 'red');
    }
    if (scheduleStatuVal == 3) {
        $("#jindu211").css('background', 'red');
        $("#jindu212").css('background', 'red');
        $("#jindu221").css('background', 'red');
        $("#jindu222").css('background', 'red');
        $("#jindu231").css('background', 'red');
        $("#jindu232").css('background', 'red');

        $("#jindu111").css('background', 'red');
        $("#jindu112").css('background', 'red');
        $("#jindu121").css('background', 'red');
        $("#jindu122").css('background', 'red');
        $("#jindu131").css('background', 'red');
        $("#jindu132").css('background', 'red');
    }
    if (scheduleStatuVal == 4) {
        $("#jindu111").css('background', 'red');
        $("#jindu112").css('background', 'red');
        $("#jindu121").css('background', 'red');
        $("#jindu122").css('background', 'red');
        $("#jindu131").css('background', 'red');
        $("#jindu132").css('background', 'red');
        $("#jindu141").css('background', 'red');
        $("#jindu142").css('background', 'red');
    }
    if (scheduleStatuVal == 5) {
        $("#jindu211").css('background', 'red');
        $("#jindu212").css('background', 'red');
        $("#jindu221").css('background', 'red');
        $("#jindu222").css('background', 'red');
        $("#jindu231").css('background', 'red');
        $("#jindu232").css('background', 'red');
        $("#jindu241").css('background', 'red');
        $("#jindu242").css('background', 'red');
        $("#jindu243").css('background', 'red');
        $("#jindu111").css('background', 'red');
        $("#jindu112").css('background', 'red');
        $("#jindu121").css('background', 'red');
        $("#jindu122").css('background', 'red');
        $("#jindu131").css('background', 'red');
        $("#jindu132").css('background', 'red');
        $("#jindu141").css('background', 'red');
        $("#jindu142").css('background', 'red');
        $("#jindu151").css('background', 'red');
        $("#jindu152").css('background', 'red');
        $("#jindu153").css('background', 'red');
    }


    // 拿到截稿日期
    var endtimehidden = $("#endtimehidden").val();
    //设置倒计时定时器
    window.setInterval(function () {
        ShowCountDown(endtimehidden, 'time_b');
    }, 1000);
    // 判断remark是否存在 从而改变页面
    var remarkVal = $("#remarkhidden").val();
    if (remarkVal != 0 && remarkVal != null && remarkVal != "") {
        // 说明remark已经标注
        $("#fabaofangdetails").text(remarkVal);
        $("#remarkshow").show();
        $("#fabaofanginput").hide();
    } else {
        $("#remarkshow").hide();
        $("#fabaofanginput").show();
    }
    // 判断测试类型
    var taskTypeVal = $("#taskTypeValhidden").val();
    if (taskTypeVal == 0) {
        // 投标模式哦
        $("#jindutiaohava").show();
        $("#jindutiaonothava").hide();
        $("#jinemoney1").text("￥ " + $("#toubiaoPricehidden").val());
        $("#renwujinfenpei").text("由中标者所得");
        $("#xuangaoceshitidiv").hide();
        $("#fenpeibiaozhu").text("");
        $("#leixing1").hide();
        $("#leixing2").show();
        $("#leixing3").hide();
        $("#leixing4").hide();
        $("#leixing5").hide();


    }
    if (taskTypeVal == 1) {
        // 测试模式
        $("#jindutiaohava").show();
        $("#jindutiaonothava").hide();
        $("#jinemoney1").text("￥ " + $("#toubiaoPricehidden").val());
        $("#xuangaoceshitidiv").show();
        $("#leixing1").show();
        $("#leixing2").hide();
        $("#leixing3").hide();
        $("#leixing4").hide();
        $("#leixing5").hide();
        // 拿到测试金
        var moneynum = $("#ceshiMoneyNumhidden").val();
        $("#renwujinfenpei").text("含测试金¥" + moneynum);
        $("#fenpeibiaozhu").text("（由中标者所得，若无中标者，则由提供测试题的小鱼们平分）");
    }
    if (taskTypeVal == 2) {
        //单人
        $("#jindutiaohava").hide();
        $("#jindutiaonothava").show();
        $("#jinemoney1").text("￥ " + $("#xuanshangPricehidden").val());
        $("#renwujinfenpei").text("一人独享赏金");
        $("#fenpeibiaozhu").text("");
        $("#xuangaoceshitidiv").hide();
        $("#leixing1").hide();
        $("#leixing2").hide();
        $("#leixing3").show();
        $("#leixing4").hide();
        $("#leixing5").hide();

        $("#dropz2").show();
        $("#gaojianMoreDiv").hide();
    }
    if (taskTypeVal == 3) {
        //多人
        $("#jindutiaohava").hide();
        $("#jindutiaonothava").show();
        $("#jinemoney1").text("￥ " + $("#xuanshangPricehidden").val());
        $("#leixing1").hide();
        $("#leixing2").hide();
        $("#xuangaoceshitidiv").hide();
        $("#leixing3").hide();
        $("#leixing4").show();
        $("#leixing5").hide();

        $("#dropz2").show();
        $("#gaojianMoreDiv").hide();

        // 拿到人数从而判断
        var peoplenum = $("#peopleNumhidden").val();
        if (peoplenum == 2) {
            //拿到金额
            var firstprizemoney = $("#firstPrizehidden").val();
            var secondprizemoney = $("#secondPrizehidden").val();
            $("#renwujinfenpei").text("一等奖1人:" + firstprizemoney + "元,二等奖1人:" + secondprizemoney + "元");
            $("#fenpeibiaozhu").text("");
        } else {
            var secondprizenum = $("#secondPrizeNumhidden").val();
            var firstprizemoney = $("#firstPrizehidden").val();
            var secondprizemoney = $("#secondPrizehidden").val();
            var thirdprizenum = $("#thirdPrizeNumhidden").val();
            var thirdprizemoney = $("#thirdPrizehidden").val();
            $("#renwujinfenpei").text("一等奖1人:" + firstprizemoney + "元,二等奖" + secondprizenum + "人:" + secondprizemoney + "元,三等奖" + thirdprizenum + "人:" + thirdprizemoney + "元");
            $("#fenpeibiaozhu").text("");
        }
    }
    if (taskTypeVal == 4) {
        // 稿件
        $("#diveditor3").hide();
        $("#gaojianshuomingbiaoqian").hide();
        $("#dropz2").hide();
        $("#gaojianMoreDiv").show();

        $("#jindutiaohava").hide();
        $("#jindutiaonothava").show();
        $("#jinemoney1").text("￥ " + $("#xuanshangPricehidden").val());
        $("#leixing1").hide();
        $("#leixing2").hide();
        $("#xuangaoceshitidiv").hide();
        $("#leixing3").hide();
        $("#leixing4").hide();
        $("#leixing5").show();
        $("#renwujinfenpei").text("结算时,合格一稿,支付一稿");
        $("#fenpeibiaozhu").text("");
    }
    // 更改编号
    $("#bianhaonum").text(assignmentId);
    // 展示道具
    var tophiddenVal = $("#tophidden").val();
    var allhiddenhiddenVal = $("#allhiddenhidden").val();
    var urgenthiddenVal = $("#urgenthidden").val();

    if (tophiddenVal == 0) {
        // 没有使用道具
        $("#zhidingdaoju").hide();
    } else {
        $("#zhidingdaoju").show();
    }
    if (allhiddenhiddenVal == 0) {
        $("#yincangdaoju").hide();
    } else {
        $("#yincangdaoju").show();
    }
    if (urgenthiddenVal == 0) {
        $("#jiajidaoju").hide();
    } else {
        $("#jiajidaoju").show();
    }


}


// 从数据库中查询参考文件的相关信息
$.post("/publish/getReferenceFileWithoutTestId", {assignmentId: assignmentId}, function (result) {
    // 拿到此时的附件信息 List 类型
    if (result != null) {
        for (var i = 0; i < result.length; i++) {
            // download
            var url = result[i].url;
            var fileName = result[i].fileName;
            var extendName = result[i].extendName;
            var fileSize = result[i].fileSize;
            var str = "";
            map1.push(fileName);
            var b = isRepeat(map1);
            if (b == true) {
                map1.pop();
            } else {
                if (extendName == "doc" || extendName == "docx") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                if (extendName == "pdf") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                if (extendName == "rar" || extendName == "zip") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                if (extendName == "jpg" || extendName == "png") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                $("#zongtifujian").append(str);
            }
        }
    } else {
        // 显示为暂无
        $("#zongtifujian").text("暂无");
    }
});
// 从数据库中查询该测试题的文件相关信息
$.post("/publish/getReferenceFileWithTestId", {assignmentId: assignmentId}, function (result) {
    // 拿到此时的附件信息 List 类型
    if (result != null) {
        for (var i = 0; i < result.length; i++) {
            var url = result[i].url;
            var fileName = result[i].fileName;
            var extendName = result[i].extendName;
            var fileSize = result[i].fileSize;
            var str = "";
            map2.push(fileName);
            var b = isRepeat(map2);
            if (b == true) {
                map2.pop();
            } else {
                if (extendName == "doc" || extendName == "docx") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                if (extendName == "jpg" || extendName == "png") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                if (extendName == "pdf") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                if (extendName == "rar" || extendName == "zip") {
                    str = "<p style=\"font-size:14px;font-weight:500;color:rgba(0,102,204,1);line-height:20px;\">\n" +
                        "                            <img src=\"/img/docmix.png\">\n" +
                        "                            " + fileName + "\n" +
                        "                            <font style=\"width:55px;height:20px;font-size:14px;font-weight:600;color:rgba(153,153,153,1);line-height:20px;\">(" + fileSize + "kb)</font>\n" +
                        "                            <a download=\"" + fileName + "\" href=\"" + url + "\">下载</a>\n" +
                        "                        </p>";
                }
                $("#ceshitupianjihe").append(str);
            }
        }
    } else {
        // 显示为暂无
        $("#ceshitupianjihe").text("暂无");
    }
    // 将页面内容设置到模态框
    // 需求标题设置
    $("#xuqiutitle").val($("#titlepage").text());
    // 需求详情设置
    $("#xuqiudetails").val($("#xuqiudetailspage").text());
    // 需求类型设置
    $("#loho").text($("#cata2page").text());
});

$("#baomingcanjia").click(function () {
    // 参加任务的逻辑
    //查看是否登录、
    var token = getUrlParam("token");
    var cookie = getCookie("oldToken");
    if (token == null || token == "") {
        if (cookie != null && cookie != "") {
            token = cookie;
        }
    }
    if (token != null && token != "") {
        let points = $("#pointshidden").val();
        let isOpenStore = $("#isOpenStorehidden").val();
        if (points != null && isOpenStore != null) {
            if (isOpenStore == 0) {
                if (points < 50) {
                    //积分不足且未开通店铺
                    $("#renwudiv3").show();
                    $("#renwudiv1").hide();
                    $("#renwudiv2").hide();
                    $("#renwudiv4").hide();
                } else {
                    //积分足但未开通店铺
                    $("#renwudiv3").hide();
                    $("#renwudiv1").show();
                    $("#renwudiv2").hide();
                    $("#renwudiv4").hide();
                }
            }
            if (isOpenStore == 1) {
                if (points < 50) {
                    //积分不足
                    $("#renwudiv3").hide();
                    $("#renwudiv1").hide();
                    $("#renwudiv2").show();
                    $("#renwudiv4").hide();
                } else {
                    // 满足条件
                    $("#renwudiv3").hide();
                    $("#renwudiv1").hide();
                    $("#renwudiv2").hide();
                    $("#renwudiv4").show();

                    $("#baomingcanjia").hide();
                    $("#toubiaobutton").show();
                    $("#nowjifen").hide();
                    $("#xiaohaojifen").hide();
                    $("#baomingcanjiasub").show();
                    $("#havebaomingsub").hide();
                }
            }
        }
        var taskTypeVal = $("#taskTypeValhidden").val();
        if (taskTypeVal == 0) {
            // 投标
            // 投标输入显示
            // 测试题隐藏
            // 悬赏输入隐藏
            $("#baomingcanjiasub").show();
            $("#toubiaotestinput").hide();
            $("#xuanshanginputdiv").hide();
        }
        if (taskTypeVal == 1) {
            //测试
            $("#baomingcanjiasub").show();
            $("#toubiaotestinput").show();
            $("#xuanshanginputdiv").hide();
        }
        if (taskTypeVal == 2) {
            //单人
            $("#baomingcanjiasub").hide();
            $("#toubiaotestinput").hide();
            $("#xuanshanginputdiv").show();
        }
        if (taskTypeVal == 3) {
            //多人
            $("#baomingcanjiasub").hide();
            $("#toubiaotestinput").hide();
            $("#xuanshanginputdiv").show();
        }
        if (taskTypeVal == 4) {
            // 稿件
            $("#baomingcanjiasub").hide();
            $("#toubiaotestinput").hide();
            $("#xuanshanginputdiv").show();
        }


        // 创建稿件记录
        $.post("/shejishi/getManuScriptId", {assignmentId: assignmentId}, function (manuscriptId) {
            // 返回该稿件id
            // 将稿件id 存入cookie
            $.cookie("manuscriptId", manuscriptId, {path: '/'})
        });


    } else {
        // 未登录
        // T回到登录页面
        window.location.href = "http://121.36.85.218:9999/passport/index?returnUrl=http://121.36.85.218:9999/shejishi/index";
    }

});

// 四个点击模块框消失的时间
function clicktohide1() {
    $("#renwudiv1").hide();
}

function clicktohide2() {
    $("#renwudiv2").hide();
}

function clicktohide3() {
    $("#renwudiv3").hide();
}

function clicktohide4() {
    $("#renwudiv4").hide();
}

// 投标模式：点击投标以后 存入数据到数据库
$("#toubiaobtn").click(function () {
    var taskTypeVal = $("#taskTypeValhidden").val();
    if (taskTypeVal == 0) {
        // 投标
        // 工作周期
        let workPeriod = $("#workPeriod").val();
        // 项目报价
        let projectQuotation = $("#projectQuotation").val();
        // 拿到editor的内容
        // 看是否使用了稿件隐藏
        // 看是否有稿件隐藏次数
        // 是否是会员
        //y以及与下面的保证金进行非空判断，是否需要付费
        var checkStatus = -1;
        if ($("#gaojianhidetr1").css("display") != 'none') {
            // 付钱
            if ($("#gaojianhidetrbox1").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr2").css("display") != 'none') {
            // 次数
            if ($("#gaojianhidetrbox2").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr3").css("display") != 'none') {
            // 会员
            if ($("#gaojianhidetrbox3").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        let ensuremoneyinput = $("#ensuremoneyinput").val();
        if (ensuremoneyinput != null && ensuremoneyinput != "" && ensuremoneyinput != 0) {

        } else {
            // 说明没有填写保证金
            // 将保证金额设置未0
            ensuremoneyinput = 0;
        }

        //存入数据库
        var editor2html = editor2.txt.html();
        $.post("/shejishi/saveTouBiaoModeDetails", {
            workPeriod: workPeriod,
            projectQuotation: projectQuotation,
            editor2html: editor2html,
            checkStatus: checkStatus,
            ensuremoneyinput: ensuremoneyinput,
            assignmentId: assignmentId
        }, function (res) {
            if (res == 1) {
                alert("投标成功");
                // 如果选稿时间不为空则更新选稿时间
                $.post("/publish/updateXuangaoTime",{assignmentId: assignmentId},function (res) {

                });
                // 任务进度
                $.post("/publish/setAssignmentScheduleStatus1",{},function (res) {

                });
                $("#havebaomingsub").show();
                $("#baomingcanjiasub").hide();
                $("#toubiaoModeCeShiDetailsView").hide();
                $("#toubiaoModeDetailsView").show();
                window.location.href = window.location.href;
            }
        });
        if (ensuremoneyinput != 0) {
            $("#toubiaoensuremoney").text(ensuremoneyinput);
            $("#ensurediv").show();
        } else {
            // 隐藏保证金
            $("#ensurediv").hide();
        }

        $("#toubiaoworkperiod").text(workPeriod);
        $("#toubiaomoney1").text(projectQuotation);
        $("#toubiaobaojiashuoming").html(editor2html);
        let ddddid = getCookie("manuscriptId");
        $("#toubiaobianhao").text(ddddid);
        let currentTimeObj = new Date();
        let year = currentTimeObj.getFullYear();
        let month = (currentTimeObj.getMonth() + 1);
        month = month >= 10 ? month : '0' + month;
        let day = currentTimeObj.getDate();
        day = day >= 10 ? day : '0' + day;
        let hour = currentTimeObj.getHours();
        hour = hour >= 10 ? hour : '0' + hour;
        let minute = currentTimeObj.getMinutes();
        minute = minute >= 10 ? minute : '0' + minute;
        $("#touibiaocurrtime").text(year + "/" + month + "/" + day + " " + hour + ":" + minute);
    }


    if (taskTypeVal == 1) {
        //测试
        // 工作周期
        let workPeriod = $("#workPeriod").val();
        // 项目报价
        let projectQuotation = $("#projectQuotation").val();
        // 拿到editor的内容
        // 看是否使用了稿件隐藏
        // 看是否有稿件隐藏次数
        // 是否是会员
        //y以及与下面的保证金进行非空判断，是否需要付费
        var checkStatus = -1;
        if ($("#gaojianhidetr1").css("display") != 'none') {
            // 付钱
            if ($("#gaojianhidetrbox1").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr2").css("display") != 'none') {
            // 次数
            if ($("#gaojianhidetrbox2").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr3").css("display") != 'none') {
            // 会员
            if ($("#gaojianhidetrbox3").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        let ensuremoneyinput = $("#ensuremoneyinput").val();
        if (ensuremoneyinput != null && ensuremoneyinput != "" && ensuremoneyinput != 0) {
        } else {
            // 说明没有填写保证金
            // 将保证金额设置未0
            ensuremoneyinput = 0;
        }

        //存入数据库
        var editor2html = editor2.txt.html();
        var editor3html = editor3.txt.html();
        $.post("/shejishi/saveCeShiTouBiaoModeDetails", {
            workPeriod: workPeriod,
            projectQuotation: projectQuotation,
            editor2html: editor2html,
            checkStatus: checkStatus,
            ensuremoneyinput: ensuremoneyinput,
            assignmentId: assignmentId,
            editor3html: editor3html
        }, function (res) {
            if (res == 1) {
                alert("测试投标成功");
                $.post("/publish/updateXuangaoTime",{assignmentId: assignmentId},function (res) {

                });
                // 任务进度
                $.post("/publish/setAssignmentScheduleStatus1",{},function (res) {

                });
                $("#havebaomingsub").show();
                $("#baomingcanjiasub").hide();
                $("#toubiaoModeCeShiDetailsView").show();
                $("#toubiaoModeDetailsView").show();
                window.location.href = window.location.href;
            }
        });

        if (ensuremoneyinput != 0) {
            $("#toubiaoensuremoney").text(ensuremoneyinput);
            $("#ensurediv").show();
        } else {
            // 隐藏保证金
            $("#ensurediv").hide();
        }

        $("#toubiaoworkperiod").text(workPeriod);
        $("#toubiaomoney1").text(projectQuotation);
        $("#toubiaobaojiashuoming").html(editor2html);
        let ddddid = getCookie("manuscriptId");
        $("#toubiaobianhao").text(ddddid);
        let currentTimeObj = new Date();
        let year = currentTimeObj.getFullYear();
        let month = (currentTimeObj.getMonth() + 1);
        month = month >= 10 ? month : '0' + month;
        let day = currentTimeObj.getDate();
        day = day >= 10 ? day : '0' + day;
        let hour = currentTimeObj.getHours();
        hour = hour >= 10 ? hour : '0' + hour;
        let minute = currentTimeObj.getMinutes();
        minute = minute >= 10 ? minute : '0' + minute;
        $("#touibiaocurrtime").text(year + "/" + month + "/" + day + " " + hour + ":" + minute);

        $("#ceshitoubiaoceshishuoming").html(editor3html);

        // 从后台获取附件
        $.post("/publish/getUrlByManuscriptId", {}, function (result) {
            if (result != null) {
                result.forEach((item, index, array) => {
                    //执行代码
                    if (item.extendName == "jpg" || item.extendName == "png" || item.extendName == "jpeg") {
                        var sty = "<img src=\"" + item.url + "\" width=\"260\" height=\"150\" alt=\"\">"
                        $("#ceshitupian").append(sty);
                    }
                    if (item.extendName == "doc" || item.extendName == "docx" || item.extendName == "pdf" || item.extendName == "rar" || item.extendName == "zip") {
                        var sty = " <img src=\"" + item.url + "\" width=\"20\" height=\"20\" alt=\"\">\n" +
                            "                            <span>" + item.fileName + "</span>\n" +
                            "                            <span style=\"margin-left: 10px;font-size:14px;font-family:PingFangSC-Semibold,PingFang SC;font-weight:600;color:rgba(153,153,153,1);\">(" + item.fileSize + "k)</span>\n" +
                            "                            <a download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                            <br/>";
                        $("#ceshifujiandiv").append(sty);
                    }
                })
            }
        });
    }
});


$("#xuanshangbtn").click(function () {
    var taskTypeVal = $("#taskTypeValhidden").val();
    if (taskTypeVal == 2) {
        //单人
        var checkStatus = -1;
        if ($("#gaojianhidetr4").css("display") != 'none') {
            // 付钱
            if ($("#gaojianhidetrbox4").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr5").css("display") != 'none') {
            // 次数
            if ($("#gaojianhidetrbox5").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr6").css("display") != 'none') {
            // 会员
            if ($("#gaojianhidetrbox6").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        var editor4html = editor4.txt.html();

        $.post("/shejishi/saveSingleXuanShangModeDetails", {
            assignmentId: assignmentId,
            editor4html: editor4html,
            checkStatus: checkStatus
        }, function (res) {
            if (res == 1) {
                alert("单人悬赏投标成功");

                $.post("/publish/updateXuangaoTime",{assignmentId: assignmentId},function (res) {

                });
                // 任务进度
                $.post("/publish/setAssignmentScheduleStatus1",{},function (res) {

                });


                $("#havebaomingsub").show();
                $("#baomingcanjiasub").hide();
                $("#toubiaoModeCeShiDetailsView").hide();
                $("#toubiaoModeDetailsView").hide();
                $("#xuanshangModeView").show();
                $("#xuanshanginputdiv").hide();
                $("#gaojianxuanshangView").hide();
                window.location.href = window.location.href;
            }
        });


        let ddddid = getCookie("manuscriptId");
        $("#xuanshangbianhao").text(ddddid);
        let currentTimeObj = new Date();
        let year = currentTimeObj.getFullYear();
        let month = (currentTimeObj.getMonth() + 1);
        month = month >= 10 ? month : '0' + month;
        let day = currentTimeObj.getDate();
        day = day >= 10 ? day : '0' + day;
        let hour = currentTimeObj.getHours();
        hour = hour >= 10 ? hour : '0' + hour;
        let minute = currentTimeObj.getMinutes();
        minute = minute >= 10 ? minute : '0' + minute;
        $("#xuanshangriqi").text(year + "/" + month + "/" + day + " " + hour + ":" + minute);

        // 从后台获取附件
        $.post("/publish/getUrlByManuscriptId", {}, function (result) {
            if (result != null) {
                result.forEach((item, index, array) => {
                    //执行代码
                    if (item.extendName == "jpg" || item.extendName == "png" || item.extendName == "jpeg") {
                        var sty = "<img src=\"" + item.url + "\" width=\"260\" height=\"150\" alt=\"\">"
                        $("#xuanshangimhg").append(sty);
                    }
                    if (item.extendName == "doc" || item.extendName == "docx" || item.extendName == "pdf" || item.extendName == "rar" || item.extendName == "zip") {
                        var sty = " <img src=\"" + item.url + "\" width=\"20\" height=\"20\" alt=\"\">\n" +
                            "                            <span>" + item.fileName + "</span>\n" +
                            "                            <span style=\"margin-left: 10px;font-size:14px;font-family:PingFangSC-Semibold,PingFang SC;font-weight:600;color:rgba(153,153,153,1);\">(" + item.fileSize + "k)</span>\n" +
                            "                            <a download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                            <br/>";
                        $("#xuanshangfujiandiv").append(sty);
                    }
                })
            }
        });
        $("#danrenxuanshangshuoming1").html(editor4html);
    }
    if (taskTypeVal == 3) {
        //多人
        var checkStatus = -1;
        if ($("#gaojianhidetr4").css("display") != 'none') {
            // 付钱
            if ($("#gaojianhidetrbox4").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr5").css("display") != 'none') {
            // 次数
            if ($("#gaojianhidetrbox5").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr6").css("display") != 'none') {
            // 会员
            if ($("#gaojianhidetrbox6").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        var editor4html = editor4.txt.html();
        $.post("/shejishi/saveMoreXuanShangModeDetails", {
            assignmentId: assignmentId,
            editor4html: editor4html,
            checkStatus: checkStatus
        }, function (res) {
            if (res == 1) {
                alert("多人悬赏投标成功");
                $.post("/publish/updateXuangaoTime",{assignmentId: assignmentId},function (res) {

                });
                // 任务进度
                $.post("/publish/setAssignmentScheduleStatus1",{},function (res) {

                });
                $("#havebaomingsub").show();
                $("#baomingcanjiasub").hide();
                $("#toubiaoModeCeShiDetailsView").hide();
                $("#toubiaoModeDetailsView").hide();
                $("#xuanshangModeView").show();
                $("#xuanshanginputdiv").hide();
                $("#gaojianxuanshangView").hide();
                window.location.href = window.location.href;
            }
        });

        let ddddid = getCookie("manuscriptId");
        $("#xuanshangbianhao").text(ddddid);
        let currentTimeObj = new Date();
        let year = currentTimeObj.getFullYear();
        let month = (currentTimeObj.getMonth() + 1);
        month = month >= 10 ? month : '0' + month;
        let day = currentTimeObj.getDate();
        day = day >= 10 ? day : '0' + day;
        let hour = currentTimeObj.getHours();
        hour = hour >= 10 ? hour : '0' + hour;
        let minute = currentTimeObj.getMinutes();
        minute = minute >= 10 ? minute : '0' + minute;
        $("#xuanshangriqi").text(year + "/" + month + "/" + day + " " + hour + ":" + minute);

        // 从后台获取附件
        $.post("/publish/getUrlByManuscriptId", {}, function (result) {
            if (result != null) {
                result.forEach((item, index, array) => {
                    //执行代码
                    if (item.extendName == "jpg" || item.extendName == "png" || item.extendName == "jpeg") {
                        var sty = "<img src=\"" + item.url + "\" width=\"260\" height=\"150\" alt=\"\">"
                        $("#xuanshangimhg").append(sty);
                    }
                    if (item.extendName == "doc" || item.extendName == "docx" || item.extendName == "pdf" || item.extendName == "rar" || item.extendName == "zip") {
                        var sty = " <img src=\"" + item.url + "\" width=\"20\" height=\"20\" alt=\"\">\n" +
                            "                            <span>" + item.fileName + "</span>\n" +
                            "                            <span style=\"margin-left: 10px;font-size:14px;font-family:PingFangSC-Semibold,PingFang SC;font-weight:600;color:rgba(153,153,153,1);\">(" + item.fileSize + "k)</span>\n" +
                            "                            <a download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                            <br/>";
                        $("#xuanshangfujiandiv").append(sty);
                    }
                })
            }
        });
        $("#danrenxuanshangshuoming1").html(editor4html);
    }
    if (taskTypeVal == 4) {
        // 稿件
        var checkStatus = -1;
        if ($("#gaojianhidetr4").css("display") != 'none') {
            // 付钱
            if ($("#gaojianhidetrbox4").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr5").css("display") != 'none') {
            // 次数
            if ($("#gaojianhidetrbox5").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        if ($("#gaojianhidetr6").css("display") != 'none') {
            // 会员
            if ($("#gaojianhidetrbox6").prop("checked") == true) {
                //使用隐藏
                checkStatus = 1;
            } else {
                // 未使用隐藏
                checkStatus = 0;
            }
        }
        var editor4html = editor4.txt.html();
        $.post("/shejishi/saveGaoJianXuanShangModeDetails", {
            assignmentId: assignmentId,
            checkStatus: checkStatus
        }, function (res) {
            if (res == 1) {
                alert("稿件悬赏投标成功");
                $.post("/publish/updateXuangaoTime",{assignmentId: assignmentId},function (res) {

                });
                // 任务进度
                $.post("/publish/setAssignmentScheduleStatus1",{},function (res) {

                });
                $("#havebaomingsub").show();
                $("#baomingcanjiasub").hide();
                $("#toubiaoModeCeShiDetailsView").hide();
                $("#toubiaoModeDetailsView").hide();
                $("#xuanshangModeView").hide();
                $("#xuanshanginputdiv").hide();
                $("#gaojianxuanshangView").show();
                window.location.href = window.location.href;
            }
        });


        let ddddid = getCookie("manuscriptId");
        $("#jijianbianhao").text(ddddid);
        let currentTimeObj = new Date();
        let year = currentTimeObj.getFullYear();
        let month = (currentTimeObj.getMonth() + 1);
        month = month >= 10 ? month : '0' + month;
        let day = currentTimeObj.getDate();
        day = day >= 10 ? day : '0' + day;
        let hour = currentTimeObj.getHours();
        hour = hour >= 10 ? hour : '0' + hour;
        let minute = currentTimeObj.getMinutes();
        minute = minute >= 10 ? minute : '0' + minute;
        $("#jijianriqi").text(year + "/" + month + "/" + day + " " + hour + ":" + minute);

        // 从后台获取附件
        $.post("/publish/getUrlByManuscriptId", {}, function (result) {
            if (result != null) {
                result.forEach((item, index, array) => {
                    //执行代码
                    if (item.gaojianSign == 1) {
                        $("#jijianxuanshangfujian11").show();
                        var str = " <img src=\"" + item.url + "\" width=\"21\" height=\"21\" alt=\"\">\n" +
                            "                                <font color=\"#0066cc\" weight=\"500\">" + item.fileName + "</font>\n" +
                            "                                <font color=\"#999999\" weight=\"600\">(" + item.fileSize + "k)</font>\n" +
                            "                                <a color=\"#0066cc\" weight=\"500\" download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                                <font color=\"#999999\" weight=\"600\" style=\"margin-left: 20px;\">删除</font>\n" +
                            "                                <br><br>";
                        $("#jijianxuanshangfujian1").append(str);

                    }
                    if (item.gaojianSign == 2) {
                        $("#jijianxuanshangfujian12").show();
                        var str = " <img src=\"" + item.url + "\" width=\"21\" height=\"21\" alt=\"\">\n" +
                            "                                <font color=\"#0066cc\" weight=\"500\">" + item.fileName + "</font>\n" +
                            "                                <font color=\"#999999\" weight=\"600\">(" + item.fileSize + "k)</font>\n" +
                            "                                <a color=\"#0066cc\" weight=\"500\" download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                                <font color=\"#999999\" weight=\"600\" style=\"margin-left: 20px;\">删除</font>\n" +
                            "                                <br><br>";
                        $("#jijianxuanshangfujian2").append(str);
                    }

                    if (item.gaojianSign == 3) {
                        $("#jijianxuanshangfujian13").show();
                        var str = " <img src=\"" + item.url + "\" width=\"21\" height=\"21\" alt=\"\">\n" +
                            "                                <font color=\"#0066cc\" weight=\"500\">" + item.fileName + "</font>\n" +
                            "                                <font color=\"#999999\" weight=\"600\">(" + item.fileSize + "k)</font>\n" +
                            "                                <a color=\"#0066cc\" weight=\"500\" download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                                <font color=\"#999999\" weight=\"600\" style=\"margin-left: 20px;\">删除</font>\n" +
                            "                                <br><br>";
                        $("#jijianxuanshangfujian3").append(str);
                    }

                    if (item.gaojianSign == 4) {
                        $("#jijianxuanshangfujian14").show();
                        var str = " <img src=\"" + item.url + "\" width=\"21\" height=\"21\" alt=\"\">\n" +
                            "                                <font color=\"#0066cc\" weight=\"500\">" + item.fileName + "</font>\n" +
                            "                                <font color=\"#999999\" weight=\"600\">(" + item.fileSize + "k)</font>\n" +
                            "                                <a color=\"#0066cc\" weight=\"500\" download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                                <font color=\"#999999\" weight=\"600\" style=\"margin-left: 20px;\">删除</font>\n" +
                            "                                <br><br>";
                        $("#jijianxuanshangfujian4").append(str);
                    }

                    if (item.gaojianSign == 5) {
                        $("#jijianxuanshangfujian15").show();
                        var str = " <img src=\"" + item.url + "\" width=\"21\" height=\"21\" alt=\"\">\n" +
                            "                                <font color=\"#0066cc\" weight=\"500\">" + item.fileName + "</font>\n" +
                            "                                <font color=\"#999999\" weight=\"600\">(" + item.fileSize + "k)</font>\n" +
                            "                                <a color=\"#0066cc\" weight=\"500\" download=\"" + item.fileName + "\" href=\"" + item.url + "\">下载</a>\n" +
                            "                                <font color=\"#999999\" weight=\"600\" style=\"margin-left: 20px;\">删除</font>\n" +
                            "                                <br><br>";
                        $("#jijianxuanshangfujian5").append(str);
                    }
                })
            }
        });
    }
});


$("#submitOrigin").click(function () {
    $("#dropz9").show();
    let val = $("#dropz9").attr("value");
    $.cookie("manuscriptId", val, {path: '/'})
    $("#submitOrigin").hide();
    $("#wanchengbtn").show();
});

$("#wanchengbtn").click(function () {
    window.location.href=window.location.href;
});

$("#submitOrigin1").click(function () {
    $("#dropz10").show();
    let val = $("#dropz10").attr("value");
    $.cookie("manuscriptId", val, {path: '/'})
    $("#submitOrigin1").hide();
    $("#wanchengbtn1").show();
});

$("#wanchengbtn1").click(function () {
    window.location.href=window.location.href;
});

$("#submitOrigin2").click(function () {
    $("#dropz11").show();
    let val = $("#dropz11").attr("value");
    $.cookie("manuscriptId", val, {path: '/'})
    $("#submitOrigin2").hide();
    $("#wanchengbtn2").show();
});

$("#wanchengbtn2").click(function () {
    window.location.href=window.location.href;
});