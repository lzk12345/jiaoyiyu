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

    let paysign = getUrlParam("sign");
    if (paysign == 1) {
        $("#MSTModal").show();
    }


    let gaojianStatushidden = $("#gaojianStatushidden").val();
    let gaojianNumhidden = $("#gaojianNumhidden").val();

    $("#jijianhege").text(parseInt(gaojianNumhidden) - parseInt(gaojianStatushidden));
    $("#jijiankexuanze").text(gaojianStatushidden);

    let firstStatus = $("#firstStatushidden").val();
    let secondStatus = $("#secondStatushidden").val();
    let thirdStatus = $("#thirdStatushidden").val();
    let senum = $("#secondPrizeNumhidden").val();
    let thnum = $("#thirdPrizeNumhidden").val();
    if (firstStatus == 1) {
        $("#hasUsed").text(0);
        $("#userdTo").text(1);
    }else {
        $("#hasUsed").text(1);
        $("#userdTo").text(0);
    }

    if (secondStatus == 0 && senum == 1) {
        $("#hasUsed2").text(1);
        $("#userdTo2").text(0);
    }
    if (secondStatus == 0 && senum == 2) {
        $("#hasUsed2").text(2);
        $("#userdTo2").text(0);
    }
    if (secondStatus == 1 && senum == 1) {
        $("#hasUsed2").text(0);
        $("#userdTo2").text(1);
    }
    if (secondStatus == 1 && senum == 2) {
        $("#hasUsed2").text(1);
        $("#userdTo2").text(1);
    }
    if (secondStatus == 2 && senum == 2) {
        $("#hasUsed2").text(0);
        $("#userdTo2").text(2);
    }


    if (thirdStatus == 0 && thnum == 1) {
        $("#hasUsed3").text(1);
        $("#userdTo3").text(0);
    }
    if (thirdStatus == 0 && thnum == 2) {
        $("#hasUsed3").text(2);
        $("#userdTo3").text(0);
    }
    if (thirdStatus == 1 && thnum == 1) {
        $("#hasUsed3").text(0);
        $("#userdTo3").text(1);
    }
    if (thirdStatus == 1 && thnum == 2) {
        $("#hasUsed3").text(1);
        $("#userdTo3").text(1);
    }
    if (thirdStatus == 2 && thnum == 2) {
        $("#hasUsed3").text(0);
        $("#userdTo3").text(2);
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
        $("#toubiaoModeDetailsView").show();
        $("#toubiaoModeCeShiDetailsView").hide();
        $("#xuanshangModeView").hide();
        $("#gaojianxuanshangView").hide();


        var currentGaoJianNum = $("#currentGaoJian").text();
        var io = new IntersectionObserver(
            entries => {
                for (i = 1; i <= currentGaoJianNum; i++) {
                    if (entries[0].target.getAttribute("id") == (i + "isliulan")) {
                        if (entries[0].isIntersecting == true) {
                            $("#" + i + "isliulan").text("雇主已浏览");
                            //修改数据库 iscat字段改为1
                            let bianhao = $("#" + i + "bianhao").text();
                            // 更改后台
                            $.post("/shejishi/updateBianHao", {bianhao: bianhao}, function (res) {
                                if (res == 1) {
                                }
                            });
                        }
                    }
                }
            },
        );
        // 开始观测某个元素
        for (i = 1; i <= currentGaoJianNum; i++) {
            var aa = document.getElementById(i + "isliulan");
            if (aa != null) {
                io.observe(aa);
            }
        }
    }
    if (taskTypeVal == 1) {

        var currentGaoJianNum = $("#currentGaoJian").text();
        var io = new IntersectionObserver(
            entries => {
                for (i = 1; i <= currentGaoJianNum; i++) {
                    if (entries[0].target.getAttribute("id") == (i + "isliulan")) {
                        if (entries[0].isIntersecting == true) {
                            $("#" + i + "isliulan").text("雇主已浏览");
                            //修改数据库 iscat字段改为1
                            let bianhao = $("#" + i + "bianhao").text();
                            // 更改后台
                            $.post("/shejishi/updateBianHao", {bianhao: bianhao}, function (res) {
                                if (res == 1) {
                                }
                            });
                        }
                    }
                }
            },
        );
        // 开始观测某个元素
        for (i = 1; i <= currentGaoJianNum; i++) {
            var aa = document.getElementById(i + "isliulan");
            if (aa != null) {
                io.observe(aa);
            }

        }


        $("#toubiaoModeDetailsView").show();
        $("#toubiaoModeCeShiDetailsView").show();
        $("#xuanshangModeView").hide();
        $("#gaojianxuanshangView").hide();
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

        var currentGaoJianNum = $("#currentGaoJian").text();
        var io = new IntersectionObserver(
            entries => {
                for (i = 1; i <= currentGaoJianNum; i++) {
                    if (entries[0].target.getAttribute("id") == (i + "isliulan1")) {
                        if (entries[0].isIntersecting == true) {
                            $("#" + i + "isliulan1").text("雇主已浏览");
                            //修改数据库 iscat字段改为1
                            let bianhao = $("#" + i + "bianhao1").text();
                            // 更改后台
                            $.post("/shejishi/updateBianHao", {bianhao: bianhao}, function (res) {
                                if (res == 1) {
                                }
                            });
                        }
                    }
                }
            },
        );
        // 开始观测某个元素
        for (i = 1; i <= currentGaoJianNum; i++) {
            var aa = document.getElementById(i + "isliulan1");
            if (aa != null && aa != "") {
                io.observe(aa);
            }
        }


        $("#toubiaoModeDetailsView").hide();
        $("#toubiaoModeCeShiDetailsView").hide();
        $("#xuanshangModeView").show();
        $("#gaojianxuanshangView").hide();
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
    }
    if (taskTypeVal == 3) {

        var currentGaoJianNum = $("#currentGaoJian").text();
        var io = new IntersectionObserver(
            entries => {
                for (i = 1; i <= currentGaoJianNum; i++) {
                    if (entries[0].target.getAttribute("id") == (i + "isliulan1")) {
                        if (entries[0].isIntersecting == true) {
                            $("#" + i + "isliulan1").text("雇主已浏览");
                            //修改数据库 iscat字段改为1
                            let bianhao = $("#" + i + "bianhao1").text();
                            // 更改后台
                            $.post("/shejishi/updateBianHao", {bianhao: bianhao}, function (res) {
                                if (res == 1) {
                                }
                            });
                        }
                    }
                }
            },
        );
        // 开始观测某个元素
        for (i = 1; i <= currentGaoJianNum; i++) {
            var aa = document.getElementById(i + "isliulan1");
            if (aa != null) {
                io.observe(aa);
            }
        }


        $("#toubiaoModeDetailsView").hide();
        $("#toubiaoModeCeShiDetailsView").hide();
        $("#xuanshangModeView").show();
        $("#gaojianxuanshangView").hide();
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

        var currentGaoJianNum = $("#currentGaoJian").text();
        var io = new IntersectionObserver(
            entries => {
                for (i = 1; i <= currentGaoJianNum; i++) {
                    if (entries[0].target.getAttribute("id") == (i + "isliulan2")) {
                        if (entries[0].isIntersecting == true) {
                            $("#" + i + "isliulan2").text("雇主已浏览");
                            //修改数据库 iscat字段改为1
                            let bianhao = $("#" + i + "bianhao2").text();
                            // 更改后台
                            $.post("/shejishi/updateBianHao", {bianhao: bianhao}, function (res) {
                                if (res == 1) {
                                }
                            });
                        }
                    }
                }
            },
        );
        // 开始观测某个元素
        for (i = 1; i <= currentGaoJianNum; i++) {
            var aa = document.getElementById(i + "isliulan2");
            if (aa != null) {
                io.observe(aa);
            }
        }


        $("#toubiaoModeDetailsView").hide();
        $("#toubiaoModeCeShiDetailsView").hide();
        $("#xuanshangModeView").hide();
        $("#gaojianxuanshangView").show();
        // 稿件
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


$("#toubiaobutton").click(function () {

});

$("#offerplayerimg").click(function () {
    $("#offerplayerimgid").hide();
});
$("#offerplayercancle").click(function () {
    $("#offerplayerimgid").hide();
});
$("#offerplayerimg1").click(function () {
    $("#offerplayerimgid1").hide();
});
$("#offerplayercancle1").click(function () {
    $("#offerplayerimgid1").hide();
});
// 点击中标  弹出支付框
let zhongbiaoshijianClass = document.getElementsByClassName("zhongbiaoshijian");
for (var i = 0; i<zhongbiaoshijianClass.length;i++) {
    zhongbiaoshijianClass[i].onclick = function () {
        $("#offerplayerimgid").show();
        let attr = $(this).attr("id");
        let bianhao = $("#" + attr.substring(0,8)).text();
        $.cookie("manuscriptId", bianhao, {path: '/'})
    }
}


//单人
let hegeshijianClass = document.getElementsByClassName("hegeshijian");
for (var i = 0; i<hegeshijianClass.length;i++) {
    hegeshijianClass[i].onclick = function () {
        $("#offerplayerimgid1").show();
        let attr = $(this).attr("id");
        let bianhao = $("#" + attr.substring(0,9)).text();
        $.cookie("manuscriptId", bianhao, {path: '/'})
    }
}
$("#toubiaoPayButton1").click(function () {

    $.post("/publish/IsBiddingByManuScriptIdAndScheduleStatus",{},function () {
        window.location.href=window.location.href;
    });
});

// 点击确认并支付
$("#toubiaoPayButton").click(function () {
    let val = $("#toubiaoPricehidden").val();
    var moneynum = $("#ceshiMoneyNumhidden").val();
    $.post("/publish/saveorder",{totalMoney:val-moneynum},function (orderSn) {
        window.location.href="/payment/TouBiaoindex?orderSn=" + orderSn;
    });
});


let yidengjiangClass = document.getElementsByClassName("yidengjiang");
for (var i = 0; i<yidengjiangClass.length;i++) {
    yidengjiangClass[i].onclick = function () {
        $("#duorenxuanshangdiv").show();
        let attr = $(this).attr("id");
        let bianhao = $("#" + attr.substring(0,9)).text();
        $.cookie("manuscriptId", bianhao, {path: '/'})
    }
}
let erdengjiangClass = document.getElementsByClassName("erdengjiang");
for (var i = 0; i<erdengjiangClass.length;i++) {
    erdengjiangClass[i].onclick = function () {
        $("#duorenxuanshangdiv2").show();
        let attr = $(this).attr("id");
        let bianhao = $("#" + attr.substring(0,9)).text();
        $.cookie("manuscriptId", bianhao, {path: '/'})
    }
}
let sandengjiangClass = document.getElementsByClassName("sandengjiang");
for (var i = 0; i<sandengjiangClass.length;i++) {
    sandengjiangClass[i].onclick = function () {
        $("#duorenxuanshangdiv3").show();
        let attr = $(this).attr("id");
        let bianhao = $("#" + attr.substring(0,9)).text();
        $.cookie("manuscriptId", bianhao, {path: '/'})
    }
}



$("#duorenxuanshangimg").click(function () {
    $("#duorenxuanshangdiv").hide();
});
$("#duorenxuanshangcancle").click(function () {
    $("#duorenxuanshangdiv").hide();
});

$("#duorenxuanshangimg2").click(function () {
    $("#duorenxuanshangdiv2").hide();
});
$("#duorenxuanshangcancle2").click(function () {
    $("#duorenxuanshangdiv2").hide();
});

$("#duorenxuanshangimg3").click(function () {
    $("#duorenxuanshangdiv3").hide();
});
$("#duorenxuanshangcancle3").click(function () {
    $("#duorenxuanshangdiv3").hide();
});

$("#xuanshangclickok1").click(function () {
    // 一等奖确认
    // rewardmore表中的一等奖减一
    $.post("/publish/updateFirstStatus",{},function (res) {

    });
    // 该任务状态变化
    $.post("/publish/setTaskStatus",{},function (res) {

    });
    //展示奖杯

    $("#duorenxuanshangdiv").hide();
    window.location.href=window.location.href;
});

$("#xuanshangclickok2").click(function () {
    // 二等奖确认
    // rewardmore表中的二等奖减一
    $.post("/publish/updateFirstStatus2",{},function (res) {

    });
    // 该任务状态变化
    $.post("/publish/setTaskStatus2",{},function (res) {

    });
    // 该任务状态变化
    //展示奖杯
    window.location.href=window.location.href;
});

$("#xuanshangclickok3").click(function () {
    // 三等奖确认
    // rewardmore表中的三等奖减一
    $.post("/publish/updateFirstStatus3",{},function (res) {

    });
    // 该任务状态变化
    $.post("/publish/setTaskStatus3",{},function (res) {

    });
    // 展示奖杯
    window.location.href=window.location.href;
});

let hegejijianbtnClass = document.getElementsByClassName("hegejijianbtn");
for (var i = 0; i<hegejijianbtnClass.length;i++) {
    hegejijianbtnClass[i].onclick = function () {
        $("#jijianmodaldiv").show();
        let attr = $(this).attr("id");
        if (attr.substring(8,10) == '21') {
            $.cookie("gaojianSign", 1, {path: '/'})
        }
        if (attr.substring(8,10) == '22') {
            $.cookie("gaojianSign", 2, {path: '/'})
        }
        if (attr.substring(8,10) == '23') {
            $.cookie("gaojianSign", 3, {path: '/'})
        }
        if (attr.substring(8,10) == '24') {
            $.cookie("gaojianSign", 4, {path: '/'})
        }
        if (attr.substring(8,10) == '25') {
            $.cookie("gaojianSign", 5, {path: '/'})
        }

        let bianhao = $("#" + attr.substring(0,9)).text();
        $.cookie("manuscriptId", bianhao, {path: '/'})
    }
}

$("#jijianmodalcancle").click(function () {
    $("#jijianmodaldiv").hide();
});
$("#jijianmodalimg").click(function () {
    $("#jijianmodaldiv").hide();
});

$("#jijianmodaldivok").click(function () {
    // 更新assignment表中的 稿件status
    $.post("/publish/updateGaoJianStatus",{},function () {

    });
    // // 将status改为1
    // $.post("/publish/updateUrlStatus",{},function () {
    //
    // });
    // 更该taskjoindetaills表中的五个字段
    $.post("/publish/updateJijianHegeNum",{},function () {

    });

    $.post("/publish/IsBiddingByManuScriptIdAndScheduleStatus2",{},function () {
        window.location.href=window.location.href;
    });
});