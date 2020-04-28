var workId = getCookie("workId");



window.onload = function () {
    fontnum("#wordCount");
    fontnum("#wordCount1");
}


function fontnum(str) {
    //先选出 textarea 和 统计字数 dom 节点
    var wordCount = $(str),
        textArea = wordCount.find("textarea"),
        word = wordCount.find(".word");
    //调用
    statInputNum(textArea, word);
}

/*
* 剩余字数统计
* 注意 最大字数只需要在放数字的节点哪里直接写好即可 如：<var class="word">200</var>
*/
function statInputNum(textArea, numItem) {
    var max = numItem.text(),
        curLength;
    textArea[0].setAttribute("maxlength", max);
    curLength = textArea.val().length;
    numItem.text(max - curLength);
    textArea.on('input propertychange', function () {
        numItem.text(max - $(this).val().length);
    });
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



var uploader3 = new easyUploader({
    id: "uploaderZip",
    accept: '.zip,.rar',
    action: 'http://121.36.85.218:9999/uploadworks/uploaderZip',
    dataFormat: 'formData',
    maxCount: 1,
    maxSize: 50,
    multiple: true,
    data: null,
    beforeUpload: function (file, data, args) {
        /* dataFormat为formData时配置发送数据的方式 */
        // data.append('token', '387126b0-7b3e-4a2a-86ad-ae5c5edd0ae6TT');
        // data.append('otherKey', 'otherValue');
        /* dataFormat为base64时配置发送数据的方式 */
        // data.base = file.base;
        // data.token = '387126b0-7b3e-4a2a-86ad-ae5c5edd0ae6TT';
        // data.otherKey = 'otherValue';
    },
    onChange: function (fileList) {
        /* input选中时触发 */
    },
    onRemove: function (removedFiles, files) {
        console.log('onRemove', removedFiles);
    },
    onSuccess: function (res) {
        console.log('onSuccess', res);
    },
    onError: function (err) {
        console.log('onError', err);
    },
});


$("#sureToSub").click(function () {
    let worksTitle = $("#worksTitle").val();

    let workTypeV = $("#workType option:selected").val();
    let industryV = $("#industry option:selected").val();

    let workDetail = $("#workDetail").val();

    var radioV = $('input:radio[name="sell"]:checked').val();

    let price = $("#price").val();

    let time = $("#test1").val();

    let xhyV = $("#xiaohongyu").prop("checked");

    if (xhyV == false) {
        alert("请阅读小红鱼协议并勾选")
    } else {
        if (worksTitle == null || worksTitle == "") {
            alert("请填写作品名称");
        } else {
            if (workTypeV == 0 || industryV == 0) {
                alert("请勾选类型");
            } else {
                if (radioV == null) {
                    alert("请选择是否出售");
                } else {
                    if (price == "") {
                        alert("请输入作品价格");
                    } else {
                        if (time == "") {
                            alert("请选择创作时间");
                        } else {

                            $.post("/uploadworks/saveWork", {
                                time: time,
                                price: price,
                                worksTitle: worksTitle,
                                workTypeV: workTypeV,
                                industryV: industryV,
                                workDetail: workDetail,
                                radioV: radioV
                            }, function (res) {
                                if (res == 1) {
                                    window.location.href = "http://121.36.85.218:9999/worksdetails/index?workId=" + workId;
                                }
                            });
                        }
                    }
                }
            }
        }
    }
});

