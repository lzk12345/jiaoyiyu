function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

// 点击搜索后出发的函数
function sousuo() {
    var keyword = $("#souzuoneirong").val();
    if(keyword == null || keyword == "") {
        alert("请输入要搜索的内容");
    }else {
        window.location.href="/taskhall/index?keyword="+keyword;
    }
}
