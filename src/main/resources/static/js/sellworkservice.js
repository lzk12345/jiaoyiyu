
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
    statInputNum(textArea,word);
}

/*
* 剩余字数统计
* 注意 最大字数只需要在放数字的节点哪里直接写好即可 如：<var class="word">200</var>
*/
function statInputNum(textArea,numItem) {
    var max = numItem.text(),
        curLength;
    textArea[0].setAttribute("maxlength", max);
    curLength = textArea.val().length;
    numItem.text(max - curLength);
    textArea.on('input propertychange', function () {
        numItem.text(max - $(this).val().length);
    });
}
$("#sellServiceSub").click(function () {

    let serviceTitle = $("#serviceTitle").val();

    let serviceType = $("#serviceType option:selected").val();

    let price = $("#price").val();

    let serviceDetail = $("#serviceDetail").val();

    let xhyV = $("#xiaohongyu").prop("checked");

    if (xhyV == false) {
        alert("请阅读小红鱼协议并勾选")
    } else {
        if (serviceTitle == null || serviceTitle == "") {
            alert("请填写服务名称");
        } else {
            if (serviceType == 0 || serviceType == 0) {
                alert("请勾选类型");
            } else {
                    if (price == "") {
                        alert("请输入作品价格");
                    }  else {

                            $.post("/sellworkservice/saveSellService",{
                                serviceTitle:serviceTitle,
                                serviceType:serviceType,
                                price:price,
                                serviceDetail:serviceDetail

                            }, function (res) {
                                if (res == 1) {
                                    alert("上传成功")
                                    window.location.href = "http://121.36.85.218:9999/worksdetails/index";
                                }
                            });
                        }
                    }
                }
            }

});
