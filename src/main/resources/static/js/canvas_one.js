    function writeAuthCode(optiona){
        let canvas = document.getElementById(optiona.canvasId);
        canvas.width=optiona.width||300
        canvas.height=optiona.height||150
        let ctx = canvas.getContext('2d');/**创建一个canvas对象*/
        ctx.textBaseline = "middle";
        ctx.fillStyle = randomColor(180, 255);/**这个范围的颜色作背景看起来清晰一些*/
        ctx.fillRect(0, 0, optiona.width, optiona.height);
        for (let i = 0; i < optiona.txt.length; i++) {
            let txt = optiona.txt.charAt(i);/**让每个字不一样*/
            ctx.font = '40px SimHei';
            ctx.fillStyle = randomColor(50, 160); /**随机生成字体颜色*/
            ctx.shadowOffsetY = randomNum(-3, 3);
            ctx.shadowBlur = randomNum(-3, 3);
            ctx.shadowColor = "rgba(0, 0, 0, 0.3)";
            let x = optiona.width / (optiona.txt.length+1) * (i+1);
            let y = optiona.height / 2;
            let deg = randomNum(-30, 30);
            /**设置旋转角度和坐标原点*/
            ctx.translate(x, y);
            ctx.rotate(deg * Math.PI / 180);
            ctx.fillText(txt, 0, 0);
            /**恢复旋转角度和坐标原点*/
            ctx.rotate(-deg * Math.PI / 180);
            ctx.translate(-x, -y);
        }
        /**1~4条随机干扰线随机出现*/
        for (let i = 0; i < randomNum(1,4); i++) {
            ctx.strokeStyle = randomColor(40, 180);
            ctx.beginPath();
            ctx.moveTo(randomNum(0, optiona.width), randomNum(0, optiona.height));
            ctx.lineTo(randomNum(0, optiona.width), randomNum(0, optiona.height));
            ctx.stroke();
        }
        /**绘制干扰点*/
        for (let i = 0; i < optiona.width / 6; i++) {
            ctx.fillStyle = randomColor(0, 255);
            ctx.beginPath();
            ctx.arc(randomNum(0, optiona.width), randomNum(0, optiona.height), 1, 0, 2 * Math.PI);
            ctx.fill();
        }
    };
    /**随机数字**/
    function randomNum(min, max) {
        return Math.floor(Math.random() * (max - min) + min);
    }
    /**随机颜色**/
    function randomColor(min, max) {
        let r = randomNum(min, max);
        let g = randomNum(min, max);
        let b = randomNum(min, max);
        return "rgb(" + r + "," + g + "," + b + ")";
    }

    var securityCodea=randomNum(0,9)+''+randomNum(0,9)+''+randomNum(0,9)+''+randomNum(0,9);

    var optiona = {
        canvasId: "auth-codea",
        txt: this.securityCodea,
        height: 50,
        width: 200
    };
    writeAuthCode(optiona);


    document.getElementById('auth-codea').addEventListener('click',function () {
        securityCodea=randomNum(0,9)+''+randomNum(0,9)+''+randomNum(0,9)+''+randomNum(0,9);
        optiona.txt=securityCodea;
        writeAuthCode(optiona);
    })