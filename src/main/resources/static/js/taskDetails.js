var cata3val;
// 生成的测试题id
var test_id = 1;
var assignmentId = getUrlParam("assignmentId");
if (assignmentId == null || assignmentId == "") {
    assignmentId = getCookie("assignmentId");
}

var map1 = new Array();
var map2 = new Array();
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

document.getElementById("Demandtype").onclick=function () {
    $('#myModal_Demandtype').show()
}

$("#address").css('display', 'none');
$(".biddingModuleContainer > .m2 > .r2 a").on('click', function () {
    $(this).addClass('toggle').siblings().removeClass('toggle');
    var flug = ($(".biddingModuleContainer > .m2 > .r2 a:nth-of-type(2)").attr('class')).indexOf("toggle") >= 0;
    flug ? $("#address").css('display', 'inline-block') : $("#address").css('display', 'none');
});

getHtmlToEle('./city.html', $("#address"));
getHtmlToEle('./test_title.html', $(".test_title"));

var onesix = document.getElementsByClassName("one-six")[0];
var number = document.getElementsByClassName("number")[0];


function onmouse() {
    if (onesix.value.length <= 500) {
        number.innerText = 500 - onesix.value.length;
    } else {
        onesix.value = onesix.value.substring(0, 500);
    }
}

function getHtmlToEle(url, ele) {
    $.ajax({
        url: url,
        cache: true,
        async: false,
        dataType: "html",
        success: function (html) {
            $(ele).html('');
            $(ele).append(html);
        }
    });
}

$(function () {
    $(".biddingModuleContainer .c2 .r1 [name='r1']").on('change', function () {
        $(".biddingModuleContainer .c2 .flag").css('display', 'none');
        $(this).parent().next().css('display', 'inline-block');
        var checked = $(".biddingModuleContainer > .m1 > .c2 > .r1>#r1")[0].checked;
        if (checked) {
            $(".biddingModuleContainer .c2 .r3 li").eq(0).trigger('click');
        } else {
            $(".biddingModuleContainer > .m1 > .c2 > .r2 input").val('').trigger('change');

        }
    });

    $(".biddingModuleContainer > .m1 > .c2 > .r2 input").on('change', function () {
        var money = parseInt($(this).val());
        if (money >= 2000) {
            $(".test_title").css('display', 'block')
        } else {
            $(".test_title").css('display', 'none')
        }
    });

    var index_toggle = 0;
    $(".biddingModuleContainer .c2 .r3").mouseout(function () {
        $($(".biddingModuleContainer .c2 .r3 li")[index_toggle]).addClass('toggle').siblings().removeClass('toggle');
    });

    $(".biddingModuleContainer .c2 .r3 li").hover(function () {
        $(this).addClass('toggle').siblings().removeClass('toggle');
    });
    $(".biddingModuleContainer .c2 .r3 li").on('click', function () {
        var money = $(".biddingModuleContainer .c2 .r3 .text_show").text($(this).text()).attr({
            'min_money': $(this).attr('min_money'),
            'max_money': $(this).attr('max_money')
        }).attr('min_money');
        if (money >= 2000) {
            $(".test_title").css('display', 'block')
        } else {
            $(".test_title").css('display', 'none')
        }
        index_toggle = $(this).index();
    });


})

function getTotalPrice() {
    var tuoguanmoney = 0;
    var topmoney = 0;
    var urgentmoney = 0;
    var hidemoney = 0;
    if ($("#tuoguan").css('display') != 'none') {
        tuoguanmoney = $("#tuoguanmoney").text();
    }
    if ($("#topservice").css('display') != 'none') {
        topmoney = $("#topmoney").text();
    }
    if ($("#urgentservice").css('display') != 'none') {
        urgentmoney = $("#urgentmoney").text();
    }
    if ($("#hideservice").css('display') != 'none') {
        hidemoney = $("#hidemoney").text();
    }
    var number1 = parseInt(tuoguanmoney) + parseInt(topmoney) + parseInt(urgentmoney) + parseInt(hidemoney);
    $("#totalmoney1").text(number1);
}

window.onload = function () {
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



    // 将assignmentId存入cookie
    $.cookie("assignmentId",assignmentId,{path: '/'})
    // 使用雪花算法生成id 作为url和测试题的统一id保存

    $.post('/publish/getIdByIdWorker', {}, function (result) {
        // 返回的result即为生成的雪花id
        // 把这个id 赋值给全局变量  test_id
        test_id = result;
        // 将测试题id存入cookie
        $.cookie('testId', test_id, {path: '/'});
    });


    // 设置定时器，检查总价
    setInterval("getTotalPrice()", 10);


    $('#myModal1').modal('show');
    var isvip = $("#isvip").val();
    var assignmentId = getUrlParam("assignmentId");

    if (isvip == "0") {
        // 非会员
        if ($("#topnumfont").text() == "0") {
            //置顶道具使用完毕
            $("#daojup11").show();
            $("#daojup12").hide();
            $("#daojup13").hide();
        } else {
            //有道具剩余次数
            $("#daojup11").hide();
            $("#daojup12").show();
            $("#daojup13").hide();
            $("#topservice").hide();

        }
        if ($("#urgentnumfont").text() == "0") {
            //加急道具使用完毕
            $("#daojup21").show();
            $("#daojup22").hide();
            $("#daojup23").hide();
        } else {
            //有道具剩余次数
            $("#daojup21").hide();
            $("#daojup22").show();
            $("#daojup23").hide();

        }
        if ($("#allhidenumfont").text() == "0") {
            //全部隐藏道具使用完毕
            $("#daojup31").show();
            $("#daojup33").hide();
        } else {
            //有道具剩余次数
            $("#daojup31").show();
            $("#daojup33").hide();
        }
    }
    if (isvip == "1") {
        //会员，不多比比，随便用
        $("#daojup11").hide();
        $("#daojup21").hide();
        $("#daojup31").hide();
        $("#daojup12").hide();
        $("#daojup22").hide();
        $("#daojup13").show();
        $("#daojup23").show();
        $("#daojup33").show();
        $("#topmoney").text(0.0);
        $("#urgentmoney").text(0.0);
        $("#hidemoney").text(0.0);
    }


    $.cookie('assignmentId', assignmentId, {path: '/'});

    // 三级分类
    $.post("/index/getCatalog1", {}, function (result) {
        $(".u1>li").remove();
        $.each(result, function (key, val) {
            var key1 = key + 1;
            var temp = "<li value=\"" + val.id + "\"><a>" + val.name + "</a></li>";
            $(".u1").append(temp);
        });
    });

    $(".u1").on('click', 'li', function () {
        var idval = $(this).val();
        var cata1name = $(this).text();
        $("#cata1").text(cata1name);
        $(".u2>li").remove();
        $(".u3>li").remove();
        $.post("/index/getCatalog2", {catalog1Id: idval}, function (result) {
            $.each(result, function (key, val) {
                var key1 = key + 1;
                var temp = "<li value=\"" + val.id + "\"><a>" + val.name + "</a></li>";
                $(".u2").append(temp);
            })
        });
    });
    $(".u2").on('click', 'li', function () {
        var idval = $(this).val();
        cata3val = $(this).val();
        var hehe = $(this);
        var val = hehe.text();
        $("#loho").text(val);
        $('#myModal_Demandtype').modal('hide');
        var cata1name = $(this).text();
        $("#cata2").text(cata1name);
        $("#leixingtype").text(cata1name);
        $(".u3>li").remove();
    });


}
document.getElementById("savethispage").onclick = function () {
    // 加入非空判断
    if ($("#xuqiutitle").val() == "" || $("#xuqiutitle").val() == null) {
        alert("请输入需求标题");
    } else {
        if ($("#xuqiudetails").val() == "" || $("#xuqiudetails").val() == null) {
            alert("请输入需求描述");
        } else {
            if ($("#loho").text() == null || $("#loho").text() == "") {
                alert("请选择需求类型");
            } else {
                $('#myModal1').modal('hide')
                $('#myModal2').modal('show')
                var xuqiutitle = $("#xuqiutitle").val();
                var xuqiudetails = $("#xuqiudetails").val();
                $.post("/publish/saveFirstPage", {
                    title: xuqiutitle,
                    details: xuqiudetails,
                    cata3val: cata3val
                }, function (result) {
                    console.log(result);
                });
            }
        }
    }
}
document.getElementById("bid").onclick = function () {
    $('#tbmessage').show()
    $('#xsmessage').hide()
}

document.getElementById("reward").onclick = function () {
    $('#tbmessage').hide()
    $('#xsmessage').show()


}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

$(function () {
    // 测试题的上传框
    // var typeArrays = ".png, .jpg, .jpeg, .ico, .bmp, .gif";
    var maxFileCount = 4;
    Dropzone.options.dropz1 = {
        url: '/publish/uploadFile1',
        paramName: "file",
        maxFiles: maxFileCount,
        maxFilesize: 50, // 上传图片大小，单位：MB
        // acceptedFiles: typeArrays,
        addRemoveLinks: true,
        parallelUploads: maxFileCount,
        dictDefaultMessage: '点击或将单个/多个附件拖放到此处,最多可添加五个附件 每个大小不超过50M',
        dictFileTooBig: "File is too big ({{filesize}}MiB). Max filesize: {{maxFilesize}}MiB.",
        // dictInvalidFileType: "文件类型只能是" + typeArrays,
        dictRemoveFile: "移除附件",
        dictMaxFilesExceeded: "您一次最多只能上传" + maxFileCount + "个附件！",
        dictResponseError: "上传附件失败！",
        init: function () {
            this.on("success", function (file, data) {
            });
            this.on('error', function (files, data) {
            });
        },
        removedfile: function (a) {
            $.post("/publish/removeFile1",{filename:a.name,assignmentId:assignmentId},function (res) {

            });
            var b;
            return a.previewElement && null != (b = a.previewElement) && b.parentNode.removeChild(a.previewElement), this._updateMaxFilesReachedClass()

        }
    };
    // 发布任务上传文件框
    // var typeArrays = ".png, .jpg, .jpeg, .ico, .bmp, .gif";
    var maxFileCount = 5;
    Dropzone.options.dropz = {
        url: '/publish/uploadFile',
        paramName: "file",
        maxFiles: maxFileCount,
        maxFilesize: 50, // 上传图片大小，单位：MB
        // acceptedFiles: typeArrays,
        addRemoveLinks: true,
        parallelUploads: maxFileCount,
        dictDefaultMessage: '点击或将单个/多个附件拖放到此处,最多可添加五个附件 每个大小不超过50M',
        dictFileTooBig: "File is too big ({{filesize}}MiB). Max filesize: {{maxFilesize}}MiB.",
        // dictInvalidFileType: "文件类型只能是" + typeArrays,
        dictRemoveFile: "移除附件",
        dictMaxFilesExceeded: "您一次最多只能上传" + maxFileCount + "个附件！",
        dictResponseError: "上传附件失败！",
        init: function () {
            this.on("success", function (file, data) {
            });
            this.on('error', function (files, data) {
            });
        },
        removedfile: function (a) {
            $.post("/publish/removeFile",{filename:a.name,assignmentId:assignmentId},function (res) {

            });
            var b;
            return a.previewElement && null != (b = a.previewElement) && b.parentNode.removeChild(a.previewElement), this._updateMaxFilesReachedClass()
        }
    };


    $(".test_title>.m4").on('click', function (e) {
        $(".test_title>input").eq(0).trigger('click');
    });

    var checked = $(".test_title > .m1 div>input");
    $(checked).on('change', function () {
        var checked = $(this)[0].checked;
        var label = $(".test_title > .m1 div>label");
        checked ? $(label).css('color', 'white') : $(label).css('color', 'rgba(255,82,81,1)');
    })
});


// 界面迁移的js
$("#ulyl>.ulyl1").click(function () {
    $("#ceshiti").show();
    var radiochecked = $(':radio[name="open"]:checked').val();
    if (radiochecked == 0) {
        $(".test_title1").show();
    }
});
$("#open3").click(function () {
    $(".test_title1").show();
})
$("#open4").click(function () {
    $("#ceshiti").hide();
    $(".test_title1").hide();
})
$("#ulyl>.ulyl2").click(function () {
    $("#ceshiti").hide();
    $(".test_title1").hide();
});
$("#r1").click(function () {
    $(".test_title1").hide();
});

$("#inputyusuan").on("input", function () {
    var v = $(this).val();
    if (v >= 2000) {
        $("#ceshiti").show();
        var radiochecked = $(':radio[name="open"]:checked').val();
        if (radiochecked == 0) {
            $(".test_title1").show();
        }
    }
    if (v < 2000) {
        $(".test_title1").hide();
        $("#ceshiti").hide();
    }
});

$("#renwujine").on('input', function () {
    if ($(this).val() != null || $(this).val() != "" || $(this).val() != NaN) {
        $("#tuoguanmoney").text($(this).val());
        $("#tuoguan").show();
        $("#jinecontext").text($(this).val());
    }
});
$("#cehsijinpanduan").on('input', function () {
    // 如果有具体预算金额  就把测试金控制在具体预算金额的百分之10
    if ($("#r1").get(0).checked) {
        // 拿到具体预算金额
        var yusuanval = $("#inputyusuan").val();
        if ($(this).val() < yusuanval * 0.1) {
            // 测试金额小于百分十
            $("#cuowuhao1").show();
            $("#tuoguanmoney").text(0);
            $("#tuoguan").hide();
        } else {

            if ($(this).val() - yusuanval > 0) {
                $(this).val("");
                $("#tuoguanmoney").text(0);
                $("#tuoguan").hide();
            } else {
                $("#cuowuhao1").hide();
                $("#tuoguanmoney").text($(this).val());
                $("#tuoguan").show();
            }
        }
    }
    // 如果为区间 就把测试金控制为区间最小的百分之十
    if ($("#r2").get(0).checked) {
        var yusuanval = $("#xialakuangval").attr('min_money');
        if ($(this).val() < yusuanval * 0.1) {
            // 测试金额小于百分十
            $("#cuowuhao1").show();
            $("#tuoguanmoney").text(0);
            $("#tuoguan").hide();
        } else {
            if ($(this).val() - yusuanval > 0) {
                $(this).val("");
                $("#tuoguanmoney").text(0);
                $("#tuoguan").hide();
            } else {
                $("#cuowuhao1").hide();
                $("#tuoguanmoney").text($(this).val());
                $("#tuoguan").show();
            }
        }
    }
})


$("#reward").click(function () {
    $(".test_title1").hide();
});
$(function () {
    $(".biddingModuleContainer-tb/*最大div*/ .c22/*任务金额选项div*/ .r11/*有没有金额预算div*/ [name='r11'/*有没有预算选项*/]").on('change'/*当用户改变input输入框内容时执行一段Javascript代码：*/, function () {
        $(".biddingModuleContainer-tb .c22 .flag/*选项下边的内容*/").css('display'/*属性规定元素应该生成的框的类型。*/, 'none'/**/);
        $(this).parent().next().css('display', 'inline-block');
    });

    var index_toggle = 0;
    $(".biddingModuleContainer-tb .c22 .r33").mouseout(function () {
        $($(".biddingModuleContainer-tb .c22 .r33 li")[index_toggle]).addClass('toggle').siblings().removeClass('toggle');
    });

    $(".biddingModuleContainer-tb .c22 .r33 li").hover(function () {
        $(this).addClass('toggle').siblings().removeClass('toggle');
    });
    $(".biddingModuleContainer-tb .c22 .r33 li").on('click', function () {
        $(".biddingModuleContainer-tb .c22 .r33 .text_show").text($(this).text());
        index_toggle = $(this).index();
    });
})

$("#renwujine").on('input', function () {
    var v = $(this).val();
    calc(v);
});

function calc(v) {
    var val = $("select[name='duorennum'] option:selected").val();
    var val1 = $("select[name='zhanbiselect'] option:selected").val();
    if (val == 2) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor((v - (v * val1)) * 100) / 100);
    }
    if (val == 3) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor(((v - (v * val1)) * 0.7) * 100) / 100);
        $("#thirdmoney").val(Math.floor((((v - (v * val1)) * 0.3)) * 100) / 100);
    }
    if (val == 4) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor(((v - (v * val1)) * 0.7) * 100) / 100);
        $("#thirdmoney").val(Math.floor((((v - (v * val1)) * 0.3) / 2) * 100) / 100);
    }
    if (val == 5) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor((((v - (v * val1)) * 0.7) / 2) * 100) / 100);
        $("#thirdmoney").val(Math.floor((((v - (v * val1)) * 0.3) / 2) * 100) / 100);
    }
}

function calc1(v, val1) {
    var val = $("select[name='duorennum'] option:selected").val();
    if (val == 2) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor((v - (v * val1)) * 100) / 100);
    }
    if (val == 3) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor(((v - (v * val1)) * 0.7) * 100) / 100);
        $("#thirdmoney").val(Math.floor((((v - (v * val1)) * 0.3)) * 100) / 100);
    }
    if (val == 4) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor(((v - (v * val1)) * 0.7) * 100) / 100);
        $("#thirdmoney").val(Math.floor((((v - (v * val1)) * 0.3) / 2) * 100) / 100);
    }
    if (val == 5) {
        $("#firstmoney").val(Math.floor((v * val1) * 100) / 100);
        $("#secondmoney").val(Math.floor((((v - (v * val1)) * 0.7) / 2) * 100) / 100);
        $("#thirdmoney").val(Math.floor((((v - (v * val1)) * 0.3) / 2) * 100) / 100);
    }
}

function checkboxOnclick1(checkbox) {
    if (checkbox.checked == true) {
        $("#topservice").css('display', 'block');
        var isvip = $("#isvip").val();
        if (isvip == "0") {
            if ($("#topnumfont").text() != "0") {
                $("#topservice").hide();
            }
        }
        $("#topnumfont").text(parseInt($("#topnumfont").text()) - 1);
    } else {
        $("#topservice").css('display', 'none');
        $("#topnumfont").text(parseInt($("#topnumfont").text()) + 1);
    }
}

function checkboxOnclick2(checkbox) {
    if (checkbox.checked == true) {
        $("#urgentservice").css('display', 'block');
        var isvip = $("#isvip").val();
        if (isvip == "0") {
            if ($("#urgentnumfont").text() != "0") {
                $("#urgentservice").hide();
            }
        }

        $("#urgentnumfont").text(parseInt($("#urgentnumfont").text()) - 1);
    } else {
        $("#urgentservice").css('display', 'none');
        $("#urgentnumfont").text(parseInt($("#urgentnumfont").text()) + 1);
    }
}

function checkboxOnclick3(checkbox) {
    if (checkbox.checked == true) {
        $("#hideservice").css('display', 'block');
    } else {
        $("#hideservice").css('display', 'none');
    }
}

$("#huhu").change(function () {
    var val = $("select[name='duorennum'] option:selected").val();
    var v = $("#renwujine").val();
    if (val == 2) {
        $("#firstp").show();
        $("#secondp").show();
        $("#thirdp").hide();
        $("#firstprize").text(1);
        $("#secondprize").text(1);
        calc(v);
    }
    if (val == 3) {
        $("#firstp").show();
        $("#secondp").show();
        $("#thirdp").show();
        $("#firstprize").text(1);
        $("#secondprize").text(1);
        $("#thirdprize").text(1);
        calc(v);
    }
    if (val == 4) {
        $("#firstp").show();
        $("#secondp").show();
        $("#thirdp").show();
        $("#firstprize").text(1);
        $("#secondprize").text(1);
        $("#thirdprize").text(2);
        calc(v);
    }
    if (val == 5) {
        $("#firstp").show();
        $("#secondp").show();
        $("#thirdp").show();
        $("#firstprize").text(1);
        $("#secondprize").text(2);
        $("#thirdprize").text(2);
        calc(v);
    }
})

$("select[name='zhanbiselect']").change(function () {
    var val1 = $("select[name='zhanbiselect'] option:selected").val();
    var v = $("#renwujine").val();
    calc1(v, val1);
});

$("#manyreward").on('click', function () {
    $("#manyselect").show();
    $("#countselect").hide();
})
$("#countreward").on('click', function () {
    $("#manyselect").hide();
    $("#countselect").show();
})
$("#personalreward").on('click', function () {
    $("#manyselect").hide();
    $("#countselect").hide();
})

$(".biddingModuleContainer-tb > .m22 > .r22 a").on('click', function () {
    $(this).addClass('toggle').siblings().removeClass('toggle');
})

$("#returnpre").click(function () {
    $('#myModal1').modal('show')
    $('#myModal2').modal('hide')
})
$("#tongyi").click(function () {
    if ($("#tongyi").is(":checked")) {
        // 勾选 则让发布任务和保存按钮可用
        $("#faburenwu").attr("disabled", false);
        $("#savebutton").attr("disabled", false);
        $("#faburenwu").css('background', '');
        $("#savebutton").css('background', '');
    } else {
        // 未勾选 让发布任务和保存按钮失效
        $("#faburenwu").attr("disabled", true);
        $("#savebutton").attr("disabled", true);
        $("#faburenwu").css('background', '#ccc');
        $("#savebutton").css('background', '#ccc');

    }
})
// 发布任务就是在保存任务的逻辑上多了一个跳转页面的逻辑  所以将以下方法提取出来。

// 第二个模态框数据的存入
// 1 首先判断他的模式  投标模式还是悬赏模式

// 1.1  如果时投标模式   看有无具体的预算金额
//      1.1.1 有预算金额 判断输入金额是否大于2000 大于2000有测试题
//             1.1.1.1 如果没有测试题  只存入任务金额以及各服务道具清单和需要付费金额，日期
//                 1.1.1.2 如果有测试题
//                       存入测试题标题 测试题描述 测试题金额
//                             测试题存到哪里呢？url表的testid写什么
//             如果使用了道具 道具开始计时，过期进行提醒，并再user表中将道具数目更新
//     1.2 如果是悬赏模式
//        1.2.1   是单人悬赏还是多人悬赏  还是计件悬赏
//             多人悬赏
//                     记录占比，一等奖二等奖三等奖的数值  以及需要几人参加悬赏
//                 计件悬赏
//                     记录稿件数 并自动算得每个稿件多少钱 存入数据库 稿件数不能超过价钱数目
// ***************************************************发布任务第二页内容的保存**********************************************************
// 保存按钮的点击

function publishTaskBaseFunc() {

    // 将目前时间与选择时间相比较
    // 获取当前时间
    var date = new Date();
    var mytime = date.toLocaleDateString(); //获取当前时间
    $("#nowtimeshow").text(mytime);


    // 刚开始使用assignmentId在数据库中插入一条空记录
    $.post("/publish/makeSettlementNull", {}, function (result) {
    });
    // 拿到StandardrewardVal的radio值 来判断为投标模式还是悬赏模式
    var StandardrewardVal = $('input:radio[name="Standardreward"]:checked').val();
    if (StandardrewardVal == null || StandardrewardVal == "") {
        // 如果为空说明没有选择  则需要给用户提示请选择交易模式
        //TODO
        // 简单的以弹窗方式进行提醒，后期改为标签提醒。
        alert("请选择交易模式");
        return false;
    } else {
        if (StandardrewardVal == 0) {
            // 如果为0 则说明选中了投标模式 进行投标模式的保存逻辑
            var yusuanmoneyStatus = $('input:radio[name="r1"]:checked').val();
            if (yusuanmoneyStatus == null || yusuanmoneyStatus == "") {
                // 说明没有选择预算金额按钮
                //TODO
                // 简单的以弹窗方式进行提醒，后期改为标签提醒。
                alert("您是否有具体的预算金额，请选择");
                return false;
            }
            var dateVal = $("#test1").val();

            if (dateVal == null || dateVal == "") {
                // 说明没有选择日期，前台需要提示用户选择
                // TODO
                alert("请选择截至投稿日期");
                return false;
            } else {
                var xuanzeshijian = new Date(dateVal.replace(/-/g, "/"));
                var nowtime = new Date(mytime.replace(/-/g, "/"));
                //js判断日期
                if (nowtime - xuanzeshijian >= 0) {
                    alert("截止日期不能在当前时间之前");
                    return false;
                }
            }

            if (yusuanmoneyStatus == 0) {
                // 如果为0 则说明该用户有具体的预算金额，执行第一个按钮的逻辑
                // 具体预算金额的获取
                var yusuanmoney = $("#inputyusuan").val();
                if (yusuanmoney == "" || yusuanmoney == null || yusuanmoney == 0 || yusuanmoney == 0.0 || yusuanmoney == 0.00) {
                    alert("请填入大于0元的预算金额");
                    return false;
                }
                if (parseInt(yusuanmoney) > 2000) {
                    // 可以选择测试题，但不强制
                    // 查看测试题是否在界面显示
                    if ($("#ceshiti").css('display') != 'none') {
                        var ceshiVal = $('input:radio[name="open"]:checked').val();
                        if (ceshiVal == 1) {
                            // 显示但为关闭状态，执行大于2000没有使用测试题的逻辑
                            if (dateVal == null || dateVal == "") {
                                // 说明没有选择日期，前台需要提示用户选择
                                // TODO
                                alert("请选择截至投稿日期");
                                return false;
                            } else {
                                // 说明存在dateVal
                                // 进行下一步逻辑 保存任务金额和投稿日期 以及道具使用情况和应付金额的逻辑
                                // 根据assignmentId来存
                                $("#renwujinfenpei").text("由中标者所得");
                                $("#xuangaoceshitidiv").hide();
                                $("#fenpeibiaozhu").text("");
                                $("#leixing1").hide();
                                $("#leixing2").show();
                                $("#leixing3").hide();
                                $("#leixing4").hide();
                                $("#leixing5").hide();
                                $.post("/publish/saveTouBiaoWithoutCeshiMoney", {
                                    toubiaoPrice: yusuanmoney,
                                    closingDate: dateVal
                                }, function (result) {
                                    // 返回值为插入数据的返回值 即-1 0 1
                                    if (result == 1) {
                                        // 说明存入成功
                                    }
                                });

                            }
                        }
                        if (ceshiVal == 0) {
                            // 显示并且为开启状态
                            // 测试题在界面显示了，说明使用了测试题功能，需要连同测试题中的字段一并保存起来
                            // 拿到截至投稿日期
                            if (dateVal == null || dateVal == "") {
                                // 说明没有选择日期，前台需要提示用户选择
                                // TODO
                                alert("请选择截至投稿日期");
                                return false;
                            } else {
                                // 说明存在dateVal
                                // 进行下一步逻辑 ，保存测试题标题 测试题描述 以及任务金额内的测试金

                                $("#xuangaoceshitidiv").show();
                                $("#leixing1").show();
                                $("#leixing2").hide();
                                $("#leixing3").hide();
                                $("#leixing4").hide();
                                $("#leixing5").hide();
                                $.post("/publish/saveTouBiaoWithCeshiMoney", {
                                    toubiaoPrice: yusuanmoney,
                                    closingDate: dateVal
                                }, function (result) {
                                    // 返回值为插入数据的返回值 即-1 0 1
                                    if (result == 1) {
                                        // 说明存入成功
                                        // alert("保存成功");
                                    }
                                });

                                // 存入測試題表
                                var ceshititle = $("#ceshititle").val();
                                var describe = $("#ceshidetails").val();
                                var moneynum = $("#cehsijinpanduan").val();
                                $("#renwujinfenpei").text("含测试金¥" + moneynum);
                                $("#fenpeibiaozhu").text("（由中标者所得，若无中标者，则由提供测试题的小鱼们平分）");
                                if (ceshititle == null || ceshititle == "") {
                                    alert("请填写测试题标题");
                                    return false;
                                } else {
                                    if (describe == null || describe == "") {
                                        alert("请填写测试题描述");
                                        return false;
                                    } else {
                                        if ($("#r1").get(0).checked) {
                                            // 拿到具体预算金额
                                            var yusuanval = $("#inputyusuan").val();
                                            if (moneynum < yusuanval * 0.1) {
                                                // 测试金额小于百分十
                                                alert("目前填写测试金额小于预算金额的百分之十,请重新填写");
                                                return false;
                                            }
                                        }
                                        // 如果为区间 就把测试金控制为区间最小的百分之十
                                        if ($("#r2").get(0).checked) {
                                            var yusuanval = $("#xialakuangval").attr('min_money');
                                            if (moneynum < yusuanval * 0.1) {
                                                // 测试金额小于百分十
                                                alert("目前填写测试金额小于预算金额的百分之十,请重新填写");
                                                return false;
                                            }
                                        }
                                    }
                                }

                                $.post("/publish/saveCeShiTi", {
                                    ceshititle: ceshititle,
                                    describe: describe,
                                    moneynum: moneynum
                                }, function (result) {
                                    if (result == 1) {
                                        // 说明存入成功
                                        // alert("保存成功");
                                    }
                                });
                            }
                        }
                    } else {
                        // 大于2000，但没有使用测试题
                        if (dateVal == null || dateVal == "") {
                            // 说明没有选择日期，前台需要提示用户选择
                            // TODO
                            alert("请选择截至投稿日期");
                            return false;
                        } else {
                            // 说明存在dateVal
                            // 进行下一步逻辑 保存任务金额和投稿日期 以及道具使用情况和应付金额的逻辑
                            // 根据assignmentId来存
                            $("#xuangaoceshitidiv").hide();
                            $("#leixing1").hide();
                            $("#leixing2").show();
                            $("#leixing3").hide();
                            $("#leixing4").hide();
                            $("#leixing5").hide();
                            $("#renwujinfenpei").text("由中标者所得");
                            $("#fenpeibiaozhu").text("");
                            $.post("/publish/saveTouBiaoWithoutCeshiMoney", {
                                toubiaoPrice: yusuanmoney,
                                closingDate: dateVal
                            }, function (result) {
                                // 返回值为插入数据的返回值 即-1 0 1
                                if (result == 1) {
                                    // 说明存入成功
                                }
                            });
                        }
                    }
                } else {
                    // 小于2000没有测试题
                    if (dateVal == null || dateVal == "") {
                        // 说明没有选择日期，前台需要提示用户选择
                        // TODO
                        alert("请选择截至投稿日期");
                        return false;
                    } else {
                        // 说明存在dateVal
                        // 进行下一步逻辑 保存任务金额和投稿日期 以及道具使用情况和应付金额的逻辑
                        // 根据assignmentId来存
                        $("#xuangaoceshitidiv").hide();
                        $("#renwujinfenpei").text("由中标者所得");
                        $("#fenpeibiaozhu").text("");
                        $("#leixing1").hide();
                        $("#leixing2").show();
                        $("#leixing3").hide();
                        $("#leixing4").hide();
                        $("#leixing5").hide();
                        $.post("/publish/saveTouBiaoWithoutCeshiMoney", {
                            toubiaoPrice: yusuanmoney,
                            closingDate: dateVal
                        }, function (result) {
                            // 返回值为插入数据的返回值 即-1 0 1
                            if (result == 1) {
                                // 说明存入成功
                            }
                        });
                    }
                }
            }
            var yusuanvalmin = $("#xialakuangval").attr('min_money');
            var yusuanvalmax = $("#xialakuangval").attr('max_money');
            if (yusuanmoneyStatus == 1) {
                //说明该用户没有具体的预算金额，执行价格区间的逻辑
                //保存此时的价格区间和 截至投稿日期
                if ($("#ceshiti").css('display') != 'none') {
                    var ceshiVal = $('input:radio[name="open"]:checked').val();
                    if (ceshiVal == 1) {
                        // 显示但为关闭状态，没有使用测试题的逻辑
                        if (dateVal == null || dateVal == "") {
                            // 说明没有选择日期，前台需要提示用户选择
                            // TODO
                            alert("请选择截至投稿日期");
                            return false;
                        } else {
                            // 说明存在dateVal
                            // 进行下一步逻辑，保存价格区间和投稿日期 以及道具使用情况和应付金额的逻辑
                            $("#renwujinfenpei").text("由中标者所得");
                            $("#fenpeibiaozhu").text("");
                            $("#xuangaoceshitidiv").hide();
                            $("#leixing1").hide();
                            $("#leixing2").show();
                            $("#leixing3").hide();
                            $("#leixing4").hide();
                            $("#leixing5").hide();
                            $.post("/publish/savePriceRangeWithoutCeShiTi", {
                                yusuanvalmin: yusuanvalmin,
                                yusuanvalmax: yusuanvalmax,
                                closingDate: dateVal
                            }, function (result) {
                                if (result == 1) {
                                    // 说明存入成功
                                    // alert("存入成功");
                                }
                            })
                        }
                    }
                    if (ceshiVal == 0) {
                        // 显示并且为开启状态
                        // 测试题在界面显示了，说明使用了测试题功能，需要连同测试题中的字段一并保存起来
                        // 拿到截至投稿日期
                        if (dateVal == null || dateVal == "") {
                            // 说明没有选择日期，前台需要提示用户选择
                            // TODO
                            alert("请选择截至投稿日期");
                            return false;
                        } else {
                            // 说明存在dateVal
                            // 进行下一步逻辑，保存价格区间和截至投稿日期以及测试题的相关字段

                            $.post("/publish/savePriceRangeWithCeShiTi", {
                                yusuanvalmin: yusuanvalmin,
                                yusuanvalmax: yusuanvalmax,
                                closingDate: dateVal
                            }, function (result) {
                                if (result == 1) {
                                    // 说明存入成功
                                    // alert("存入成功");
                                }
                            })

                            // 存入測試題表
                            var ceshititle = $("#ceshititle").val();
                            var describe = $("#ceshidetails").val();
                            var moneynum = $("#cehsijinpanduan").val();
                            if (ceshititle == null || ceshititle == "") {
                                alert("请填写测试题标题");
                                return false;
                            } else {
                                if (describe == null || describe == "") {
                                    alert("请填写测试题描述");
                                    return false;
                                } else {
                                    if ($("#r1").get(0).checked) {
                                        // 拿到具体预算金额
                                        var yusuanval = $("#inputyusuan").val();
                                        if (moneynum < yusuanval * 0.1) {
                                            // 测试金额小于百分十
                                            alert("目前填写测试金额小于预算金额的百分之十,请重新填写");
                                            return false;
                                        }
                                    }
                                    // 如果为区间 就把测试金控制为区间最小的百分之十
                                    if ($("#r2").get(0).checked) {
                                        var yusuanval = $("#xialakuangval").attr('min_money');
                                        if (moneynum < yusuanval * 0.1) {
                                            // 测试金额小于百分十
                                            alert("目前填写测试金额小于预算金额的百分之十,请重新填写");
                                            return false;
                                        }
                                    }
                                }
                            }
                            $("#ceshitibiaoti").text(ceshititle);
                            $("#ceshitineirong").text(describe);
                            $.post("/publish/saveCeShiTi", {
                                ceshititle: ceshititle,
                                describe: describe,
                                moneynum: moneynum
                            }, function (result) {
                                if (result == 1) {
                                    // 说明存入成功
                                    // alert("保存成功");
                                }
                            });
                            $("#renwujinfenpei").text("含测试金¥" + moneynum);
                            $("#fenpeibiaozhu").text("（由中标者所得，若无中标者，则由提供测试题的小鱼们平分）");
                            $("#xuangaoceshitidiv").show();
                            $("#leixing1").show();
                            $("#leixing2").hide();
                            $("#leixing3").hide();
                            $("#leixing4").hide();
                            $("#leixing5").hide();
                        }
                    }
                } else {
                    // 没有使用测试题
                    if (dateVal == null || dateVal == "") {
                        // 说明没有选择日期，前台需要提示用户选择
                        // TODO
                        alert("请选择截至投稿日期");
                        return false;
                    } else {
                        // 说明存在dateVal
                        // 进行下一步逻辑 保存价格区间和投稿日期 以及道具使用情况和应付金额的逻辑
                        $("#xuangaoceshitidiv").hide();
                        $("#renwujinfenpei").text("由中标者所得");
                        $("#fenpeibiaozhu").text("");
                        $("#leixing1").hide();
                        $("#leixing2").show();
                        $("#leixing3").hide();
                        $("#leixing4").hide();
                        $("#leixing5").hide();
                        $.post("/publish/savePriceRangeWithoutCeShiTi", {
                            yusuanvalmin: yusuanvalmin,
                            yusuanvalmax: yusuanvalmax,
                            closingDate: dateVal
                        }, function (result) {
                            if (result == 1) {
                                // 说明存入成功
                                // alert("存入成功");
                            }
                        })
                    }
                }

            }
        }
        // 拿到悬赏模式的截至投稿时间
        var dateVal1 = $("#test11").val();

        if (StandardrewardVal == 1) {
            // 如果为1 则说明选中了悬赏模式 进行悬赏模式的保存逻辑
            // 悬赏任务金额
            if (dateVal1 == null || dateVal1 == "") {
                // 说明没有选择日期，前台需要提示用户选择
                // TODO
                alert("请选择截至投稿日期");
                return false;
            } else {
                var xuanzeshijian = new Date(dateVal1.replace(/-/g, "/"));
                var nowtime = new Date(mytime.replace(/-/g, "/"));
                //js判断日期
                if (nowtime - xuanzeshijian >= 0) {
                    alert("截止日期不能在当前时间之前");
                    return false;
                }
            }
            var renwumoney = $("#renwujine").val();
            if (renwumoney == "" || renwumoney == null || renwumoney == 0 || renwumoney == 0.0 || renwumoney == 0.00) {
                alert("请填写大于0元的任务金额");
                return false;
            }
            // 首先拿到悬赏模式中的悬赏方式
            var rewardselectStatus = $('input:radio[name="rewardselect"]:checked').val();
            if (rewardselectStatus == null || rewardselectStatus == "") {
                //说明还未选择悬赏方式 进行alert
                alert("请选择悬赏方式。");
                return false;
            }
            if (rewardselectStatus == 0) {
                if (dateVal1 == null || dateVal1 == "") {
                    // 说明没有选择日期，前台需要提示用户选择
                    // TODO
                    alert("请选择截至投稿日期");
                    return false;
                } else {
                    // 单人悬赏
                    // 状态需要存入数据库，并将还未选择中标者状态设置为0
                    // 待选择中标者后改为1
                    $("#renwujinfenpei").text("一人独享赏金");
                    $("#fenpeibiaozhu").text("");
                    $("#leixing1").hide();
                    $("#leixing2").hide();
                    $("#leixing3").show();
                    $("#leixing4").hide();
                    $("#leixing5").hide();
                    $.post("/publish/saveSingleReward", {
                        renwumoney: renwumoney,
                        closingDate: dateVal1
                    }, function (result) {
                        if (result == 1) {
                            // 说明存入成功
                            // alert("存入成功");
                        }
                    });
                }
            }
            if (rewardselectStatus == 1) {
                // 多人悬赏
                $("#leixing1").hide();
                $("#leixing2").hide();
                $("#leixing3").hide();
                $("#leixing4").show();
                $("#leixing5").hide();
                if (dateVal1 == null || dateVal1 == "") {
                    // 说明没有选择日期，前台需要提示用户选择
                    // TODO
                    alert("请选择截至投稿日期");
                    return false;
                } else {
                    // 拿到几人  拿到一等奖的赏金 二等奖的赏金  三等奖的赏金 （为显示状态下拿） 占比
                    // 存入数据库时使用
                    // 多少人分悬赏金额 peoplenum

                    var peoplenum = $("select[name='duorennum'] option:selected").val();
                    // 一等奖金额
                    var firstprizemoney = $("#firstmoney").val();
                    // 占比
                    var zhanbi = $("select[name='zhanbiselect'] option:selected").val();
                    // 二等奖人数
                    var secondprizenum = $("#secondprize").text();
                    // 二等奖金额
                    var secondprizemoney = $("#secondmoney").val();


                    // 保存金额和时间
                    $.post("/publish/saveMoreRewardDateAndMoney", {
                        renwumoney: renwumoney,
                        closingDate: dateVal1
                    }, function (result) {
                        if (result == 1) {
                            // 说明存入成功
                            // alert("存入成功");
                        }
                    });
                    // 保存悬赏金额和人数占比，都在rewardMore表中，
                    if (peoplenum == 2) {
                        //  不存三等奖
                        $("#renwujinfenpei").text("一等奖1人:" + firstprizemoney + "元,二等奖1人:" + secondprizemoney + "元");
                        $("#fenpeibiaozhu").text("");
                        $.post("/publish/saveMoreRewardWhenNumEqualsTwo", {
                            firstprizemoney: firstprizemoney,
                            zhanbi: zhanbi,
                            secondprizemoney: secondprizemoney
                        }, function (result) {
                            if (result == 1) {
                                // 说明存入成功
                                // alert("存入成功");
                            }
                        });
                    } else {
                        // 存三等奖
                        if ($("#thirdp").css('display') != 'none') {
                            // 说明有三等奖
                            // 三等奖人数
                            var thirdprizenum = $("#thirdprize").text();
                            // 三等奖金额
                            var thirdprizemoney = $("#thirdmoney").val();
                            $("#renwujinfenpei").text("一等奖1人:" + firstprizemoney + "元,二等奖" + secondprizenum + "人:" + secondprizemoney + "元,三等奖" + thirdprizenum + "人:" + thirdprizemoney + "元");
                            $("#fenpeibiaozhu").text("");

                            $.post("/publish/saveMoreRewardWhenNumGreaterThanTwo", {
                                thirdprizenum: thirdprizenum,
                                secondprizenum: secondprizenum,
                                peoplenum: peoplenum,
                                firstprizemoney: firstprizemoney,
                                zhanbi: zhanbi,
                                secondprizemoney: secondprizemoney,
                                thirdprizemoney: thirdprizemoney
                            }, function (result) {
                                if (result == 1) {
                                    // 说明存入成功
                                    // alert("存入成功");
                                }
                            });
                        }
                    }
                }
            }
            if (rewardselectStatus == 2) {
                // 计件悬赏
                $("#leixing1").hide();
                $("#leixing2").hide();
                $("#leixing3").hide();
                $("#leixing4").hide();
                $("#leixing5").show();
                if (dateVal1 == null || dateVal1 == "") {
                    // 说明没有选择日期，前台需要提示用户选择
                    // TODO
                    alert("请选择截至投稿日期");
                    return false;
                } else {
                    // 拿到稿件数目即可，数据库设置已经使用过的中标数，中一人自减1
                    var gaojiannum = $("#gaojiannum").val();
                    if (gaojiannum == "" || gaojiannum == null || gaojiannum == 0 || gaojiannum == 0.0 || gaojiannum == 0.00) {
                        alert("请填写大于0元的任务金额");
                        return false;
                    }
                    // 保存金额和时间
                    $("#renwujinfenpei").text("结算时,合格一稿,支付一稿");
                    $("#fenpeibiaozhu").text("");
                    $.post("/publish/saveGaoJianDateAndMoney", {
                        renwumoney: renwumoney,
                        closingDate: dateVal1
                    }, function (result) {
                        if (result == 1) {
                            // 说明存入成功
                            // alert("存入成功");
                        }
                    });
                    // 存入稿件数目 稿件剩余 稿件单价即可
                    var gaojianSinglePrice = Math.floor((renwumoney / gaojiannum) * 100) / 100;
                    $.post("/publish/saveGaoJian", {
                        gaojianSinglePrice: gaojianSinglePrice,
                        gaojiannum: gaojiannum
                    }, function (result) {
                        if (result == 1) {
                            // 说明存入成功
                            // alert("存入成功");
                        }
                    });
                }
            }
        }
        // 保存完模式数据后，对选择服务道具进行数据库的增删查改
        // 如果有剩余次数，则需要在数据库中对道具的剩余次数减一
        // 注意：不能在此处进行减一，因为此处只保存用户的发布任务逻辑，并不真正的执行，
        // 需要等到用户真正付款完成并发布任务后 才进行数据库的更改。
        // 勾选状态下。置顶服务和加急服务需要查看是否存在剩余次数   是扣次数还是扣钱
        // 全部隐藏服务不用管，因为全部服务必须为扣钱逻辑
        // 还得判断当前用户是否是会员状态  如果是会员状态，只需要给任务中加入三种道具 而不需要执行道具的增删查改 较为简单。
        // 数据库字段解释：
        // top置顶字段 分为以下几个状态：
        // 0 ：未使用置顶道具
        // 1 ：使用了置顶道具（扣剩余次数（未扣））
        // 2 ：使用了置顶道具（扣钱（未扣））
        // 3 ：使用了置顶道具（扣剩余次数（已扣））
        // 4 ：使用了置顶道具（扣钱（已扣））
        // 5 ：使用了道具且为会员
        var isvip = $("#isvip").val();
        if (isvip == "0") {
            //非会员状态
            if ($("#checkbox1").is(":checked")) {
                // 置顶服务被勾选
                // 需要判断目前有无剩余次数
                $("#zhidingimg").show();
                if ($("#topnumfont").text() == "0") {
                    //置顶道具使用完毕 扣钱
                    $.post("/publish/updateTop", {top: 2}, function (result) {
                        if (result == 1) {
                            // alert("使用道具成功");
                        }
                    })
                } else {
                    //有道具剩余次数 扣次数
                    $.post("/publish/updateTop", {top: 1}, function (result) {
                        if (result == 1) {
                            // alert("使用道具成功");
                        }
                    })
                }
            } else {
                // 置顶服务未被勾选
                // 直接在任务表中加入未使用置顶服务
                // 什么都不用干  数据库中默认为0 即未使用服务
                $("#zhidingimg").hide();
            }
            if ($("#checkbox2").is(":checked")) {
                // 加急服务被勾选
                // 需要判断目前有无剩余次数
                $("#jiajiimg").show();
                if ($("#daojup21").css('display') != 'none') {
                    //加急道具使用完毕  扣钱
                    $.post("/publish/updateUrgent", {urgent: 2}, function (result) {
                        if (result == 1) {
                            // alert("使用道具成功");
                        }
                    })
                }
                if ($("#daojup22").css('display') != 'none') {
                    //有道具剩余次数  扣次数
                    $.post("/publish/updateUrgent", {urgent: 1}, function (result) {
                        if (result == 1) {
                            // alert("使用道具成功");
                        }
                    })
                }
            } else {
                // 加急服务未被勾选
                // 直接在任务表中加入未使用加急服务
                // 什么都不用干  数据库中默认为0 即未使用服务
                $("#jiajiimg").hide();
            }
            if ($("#checkbox3").is(":checked")) {
                // 全部隐藏服务被勾选  扣钱
                $("#yingaoimg").show();
                $.post("/publish/updateAllHide", {allHide: 2}, function (result) {
                    if (result == 1) {
                        // alert("使用道具成功");
                    }
                })
            } else {
                // 全部隐藏服务未被勾选
                // 直接在任务表中加入未使用全部隐藏服务
                // 什么都不用干  数据库中默认为0 即未使用服务
                $("#yingaoimg").hide();
            }
        }
        if (isvip == "1") {
            //会员状态
            if ($("#checkbox1").is(":checked")) {
                // 置顶服务被勾选
                // 直接在任务表中加入使用了置顶服务
                // 即为使用了置顶服务且为会员。
                $("#zhidingimg").show();
                $.post("/publish/updateTop", {top: 5}, function (result) {
                    if (result == 1) {
                        // alert("使用道具成功");
                    }
                })
            } else {
                // 置顶服务未被勾选
                // 直接在任务表中加入未使用置顶服务
                $("#zhidingimg").hide();
            }
            if ($("#checkbox2").is(":checked")) {
                // 加急服务被勾选
                // 直接在任务表中加入使用了加急服务
                $("#jiajiimg").show();
                $.post("/publish/updateUrgent", {urgent: 5}, function (result) {
                    if (result == 1) {
                        // alert("使用道具成功");
                    }
                })
            } else {
                // 加急服务未被勾选
                // 直接在任务表中加入未使用加急服务
                $("#jiajiimg").hide();
            }
            if ($("#checkbox3").is(":checked")) {
                // 全部隐藏服务被勾选
                // 直接在任务表中加入使用了全部隐藏服务
                $("#yingaoimg").show();
                $.post("/publish/updateAllHide", {allHide: 5}, function (result) {
                    if (result == 1) {
                        // alert("使用道具成功");
                    }
                })
            } else {
                // 全部隐藏服务未被勾选
                // 直接在任务表中加入未使用全部隐藏服务
                $("#yingaoimg").hide();
            }
        }
        // 最后将结算清单的数据也存入数据库，将各项服务扣除金额明细也存入方便用户显示查看。
        // 前台使用的p标签   如果显示就存其金额 不论会员与否 数据库默认值为0 托管金额如果没有就提醒选择 属于前台校验
        // 特殊情况：投标模式没有测试题则没有托管资金
        // 如果服务不显示说明没有用到这些服务   数据库默认为null；   如果用到就存入。
        // 根据assignmentId来存 可是如果第一次是没有这个assignmentId记录的  没法更新
        // 需要在上面服务中创建一个只有该assignmentId字段的空记录。
        if ($("#tuoguan").css('display') != 'none') {
            var tuoguanmoney = $("#tuoguanmoney").text();
            $.post("/publish/saveTuoGuanMoney", {tuoguanmoney: tuoguanmoney}, function (result) {

            });
        } else {
            // 不显示的话把数据库设置为null
            $.post("/publish/saveTuoGuanMoney", {tuoguanmoney: -1}, function (result) {

            });
        }
        if ($("#topservice").css('display') != 'none') {
            var topmoney = $("#topmoney").text();
            $.post("/publish/saveTopMoney", {topmoney: topmoney}, function (result) {

            });
        } else {
            // 不显示的话把数据库设置为null
            $.post("/publish/saveTopMoney", {topmoney: -1}, function (result) {

            });
        }
        if ($("#urgentservice").css('display') != 'none') {
            var urgentmoney = $("#urgentmoney").text();
            $.post("/publish/saveUrgentMoney", {urgentmoney: urgentmoney}, function (result) {

            });
        } else {
            // 不显示的话把数据库设置为null
            $.post("/publish/saveUrgentMoney", {urgentmoney: -1}, function (result) {

            });
        }
        if ($("#hideservice").css('display') != 'none') {
            var hidemoney = $("#hidemoney").text();
            $.post("/publish/saveHideMoney", {hidemoney: hidemoney}, function (result) {

            });
        } else {
            // 不显示的话把数据库设置为null
            $.post("/publish/saveHideMoney", {hidemoney: -1}, function (result) {

            });
        }
        // 最后存入总金额totalmoney1
        var totalmoney = $("#totalmoney1").text();
        $.post("/publish/saveTotalMoney", {totalmoney: totalmoney}, function (result) {

        });
        // 点击完保存按钮后隐藏模态框
        $('#myModal1').modal('hide');
        $('#myModal2').modal('hide');
        // ***************************************点击保存按钮后使用前台js将模态框中的内容显示到界面中，第一次不通过数据库的查询**********************************
        //拿到需求标题并设置页面
        var xuqiutitle = $("#xuqiutitle").val();
        if (xuqiutitle != null && xuqiutitle != "" && xuqiutitle != 'NaN') {
            $("#needtitle").text(xuqiutitle);
        }
        //拿到需求内容并设置页面
        var xuqiudetails = $("#xuqiudetails").val();
        if (xuqiudetails != null && xuqiudetails != "" && xuqiudetails != 'NaN') {
            $("#xuqiudetailstext").text(xuqiudetails);
        }

        var assignmentId = getUrlParam("assignmentId");
        // 任务id的设置
        $("#bianhaonum").text(assignmentId);
        // 创建时间
        $("#chuangjiantime").text(mytime);
        // 金额
        $("#jinemoney1").text("￥" + totalmoney);


        // 从数据库中查询参考文件的相关信息
        $.post("/publish/getReferenceFileWithoutTestId", {assignmentId:assignmentId}, function (result) {
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
        $.post("/publish/getReferenceFileWithTestId", {assignmentId:assignmentId}, function (result) {
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
        });
    }
    return true;
}


$("#savebutton").click(function () {
    if (!publishTaskBaseFunc()) return;

});

//*****************************************发布任务*********************************************//'
//跳转到选择支付方式的页面
$("#faburenwu").click(function () {
    if (!publishTaskBaseFunc()) {
        return;
    }
    $.post("/publish/catAndUpdateTaskStatus",{},function (result) {
        if (result == 1) {
            //让其发布 不做任何处理
                // 查询该任务的状态  如果不是0 说明已经发布，不让重新发布
                // 如果是0 则更改其状态 并且让其发布。
                // 发布前 存入剩余时间
                $.post("/publish/saveDifferTime",{},function (result) {
                    if (result == 1) {
                    }
                });
                window.location.href = "/publish/submitOrder?assignmentId=" + assignmentId;
        }
        if (result == 2) {
            // 不让发布
            alert("该任务已经发布，请前往个人中心查看");
        }
    });
});


// 发包方留言的逻辑实现
$("#fabaofangbtn").click(function () {
    var liuyan = $("#fabaofangliuyan").val();
    if (liuyan == null || liuyan == "") {
        alert("请输入留言内容,您只有一次留言机会");
    }else{
        // 存入数据库并改变样式
        $.post("/publish/saveRemark",{remark:liuyan},function (result) {
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