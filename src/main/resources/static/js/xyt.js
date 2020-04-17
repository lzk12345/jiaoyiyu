$(document).ready(function () {
    $(".xyt_pushForm .select ul li").on('click',function () {
        $(this).parent().siblings("span").text($(this).text());
    })
});