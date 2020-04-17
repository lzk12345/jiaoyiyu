
$(function(){
    //jQuery代码;
    $("#listh21").click(function () {
        $(".listdiva1").toggle(500);
        $("#listh21>i").toggleClass("glyphicon glyphicon-triangle-right");
        $("#listh21>i").toggleClass("glyphicon glyphicon-triangle-bottom");
    });

    $("#listh22").click(function () {
        $(".listdiva2").toggle(500);
        $("#listh22>i").toggleClass("glyphicon glyphicon-triangle-right");
        $("#listh22>i").toggleClass("glyphicon glyphicon-triangle-bottom");
    });
    $("#listh23").click(function () {
        $(".listdiva3").toggle(500);
        $("#listh23>i").toggleClass("glyphicon glyphicon-triangle-right");
        $("#listh23>i").toggleClass("glyphicon glyphicon-triangle-bottom");
    });
    $("#listh24").click(function () {
        $(".listdiva4").toggle(500);
        $("#listh24>i").toggleClass("glyphicon glyphicon-triangle-right");
        $("#listh24>i").toggleClass("glyphicon glyphicon-triangle-bottom");
    });
});
