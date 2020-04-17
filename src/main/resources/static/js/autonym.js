var access_token = "24.3476b69567d3debf40b33db36bd3274a.2592000.1580118368.282335-18130111";
var app = new Vue({
    el: '#app',
    data: {
        imgTempList: [], //图片临时路径列表
        isUploading: false, //是否正在上传
        successPath: [], //上传成功后的路径(没必要)
    },
    mounted: function () {
        var that = this;
    },
    watch: {},
    methods: {
        //选择图片
        onChooseImage: function (event) {
            var that = this;

            //判断图片数量是否已上限
            var currentImgTempArray = that.imgTempList;
            if (currentImgTempArray.length >= 1) {
                alert("最多上传1张图片");
                return false;
            }

            //使用FileReader对文件对象进行操作
            var reader = new FileReader();
            reader.readAsDataURL(event.target.files[0]); //将读取到的文件编码成Data URL
            reader.onload = function () { //读取完成时
                imageBase = reader.result.substring(23)
                $.ajax({
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    type: "post",
                    url: "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard",
                    async: true,
                    data: {
                        access_token: access_token,
                        id_card_side: "front",
                        image: imageBase,
                        detect_direction: true
                    },
                    dataType: "json",
                    timeout: 30000,
                    success: function (data) {
                        // console.log("解析成功");
                        // console.log(data);
                        if (data.image_status == "normal") {
                            $("#appspan1").text("经检测：识别正常");
                            $("#username1").val(data.words_result.姓名.words);
                            $("#idcardnum1").val(data.words_result.公民身份号码.words);
                        }
                        if (data.image_status == "reversed_side") {
                            $("#appspan1").text("经检测：未摆正身份证");
                        }
                        if (data.image_status == "non_idcard") {
                            $("#appspan1").text("经检测：上传的图⽚中不包含身份证");
                        }
                        if (data.image_status == "blurred") {
                            $("#appspan1").text("经检测：身份证模糊");
                        }
                        if (data.image_status == "over_exposure") {
                            $("#appspan1").text("经检测：身份证关键字段反光或过曝");
                        }
                        if (data.image_status == "unknown") {
                            $("#appspan1").text("经检测：未知状态");
                        }
                    },
                    error: function (xhr) {
                        console.log("请求解析失败");
                        alert("图片解析失败");
                    }
                });

                var replaceSrc = reader.result; //文件输出的内容
                //调用图片压缩处理方法
                that.compressedImage({
                    src: replaceSrc,
                    quality: 0.8,
                    success: function (src) {
                        //将压缩后的路径 追加到临时路径数组中
                        // $.post("/autonym/BaiDuYunCheck",{file:replaceSrc},function (res) {
                        //     console.log(res);
                        // });
                        var totalList = [];
                        if (currentImgTempArray.length > 0) {
                            totalList = currentImgTempArray.concat(src);
                        } else {
                            totalList[0] = src;
                        }
                        that.imgTempList = totalList;
                    }
                });
            };

        },

        //删除某张图片
        deleteImg: function (idx) {
            var that = this;
            that.imgTempList.splice(idx, 1);
        },


        //提交上传图片
        onUploadImg: function () {
            if ($("#appspan1").text() == "经检测：识别正常") {
                var that = this;
                var imgTempList = that.imgTempList;
                if (imgTempList.length > 0) {

                    that.isUploading = true; //正在上传 显示遮罩层 防止连续点击

                    var countNum = 0; //计算数量用的 判断上传到第几张图片了

                    //map循环遍历上传图片
                    imgTempList.map(function (imgItem, imgIndex) {
                        var files = that.dataURLtoFile(imgItem, 'pj' + Date.now() + '.jpg'); //DataURL转File

                        //创FormData对象
                        var formdata = new FormData();
                        //append(key,value)在数据末尾追加数据。 这儿的key值需要和后台定义保持一致
                        formdata.append('img', files);

                        //用axios上传，
                        axios({
                            method: "POST",
                            url: "/publish/upload111", //请将图片上传路径换成自己的。这是测试用的，会不定期删除图片
                            // url: "http://图片上传路径",
                            data: formdata,
                            headers: {
                                "Content-Type": "multipart/form-data"
                            }
                        }).then(function (res) {
                            countNum++;
                            //图片全部上传完后去掉遮罩层
                            if (countNum >= imgTempList.length) {
                                that.isUploading = false;
                            }
                            console.log(res);
                        }).catch(function (error) {
                            console.error(error);
                        });
                    });
                }

                if ($("#appspan3").text() == "经检测：识别正常" && $("#username1").val() != "" && $("#idcardnum1").val() != "" && $("#shixiaodate").val() != "" && $("#username1").val() != null && $("#idcardnum1").val() != null && $("#shixiaodate").val() != null) {
                    alert("实名认证成功");
                }

            } else {
                alert("请正确传入照片！！");
            }
        },

        /**
         * 压缩图片处理
         * @src 需要压缩的图片base64路径
         * @quality 图片质量 0-1，默认1
         * @success()  成功后的回调
         * */
        compressedImage: function (params) {
            var that = this;

            var initParams = {
                src: params.src || "",
                quality: params.quality || 1,
            };

            var image = new Image();
            image.src = initParams.src;
            image.onload = function () {
                //获取图片初始宽高
                var width = image.width;
                var height = image.height;
                //判断图片宽度，再按比例设置宽度和高度的值
                if (width > 1024) {
                    width = 1024;
                    height = Math.ceil(1024 * (image.height / image.width));
                }

                //将图片重新画入canvas中
                var canvas = document.getElementById("compressCanvas");
                if (!canvas) { //如果没有压缩用的canvas 就创建一个canvas画布
                    var body = document.body;
                    canvas = document.createElement("canvas"); //创建canvas标签
                    canvas.id = "compressCanvas"; //给外层容器添加一个id
                    canvas.style.position = "fixed";
                    canvas.style.zIndex = "-1";
                    canvas.style.opacity = "0";
                    canvas.style.top = "-100%";
                    canvas.style.left = "-100%";
                    body.append(canvas);
                }

                var context = canvas.getContext("2d");
                canvas.width = width;
                canvas.height = height;
                context.beginPath();
                context.fillStyle = "#ffffff";
                context.fillRect(0, 0, width, height);
                context.fill();
                context.closePath();
                context.drawImage(image, 0, 0, width, height);
                var replaceSrc = canvas.toDataURL("image/jpeg", initParams.quality); //canvas转DataURL(base64格式)

                params.success && params.success(replaceSrc);
            };
        },

        /**
         * 将base64转换为文件
         * @dataUrl base64路径地址
         * @fileName 自定义文件名字
         * */
        dataURLtoFile: function (dataUrl, fileName) {
            var arr = dataUrl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new File([u8arr], fileName, {type: mime});
        },
    }
});
var app1 = new Vue({
    el: '#app1',
    data: {
        imgTempList: [], //图片临时路径列表
        isUploading: false, //是否正在上传
        successPath: [], //上传成功后的路径(没必要)
    },
    mounted: function () {
        var that = this;
    },
    watch: {},
    methods: {
        //选择图片
        onChooseImage: function (event) {
            var that = this;
            if ($("#username1").val() == "" && $("#idcardnum1").val() == "") {
                alert("请先上传身份证正面照片");
                return false;
            }
            //判断图片数量是否已上限
            var currentImgTempArray = that.imgTempList;
            if (currentImgTempArray.length >= 1) {
                alert("最多上传1张图片");
                return false;
            }

            //使用FileReader对文件对象进行操作
            var reader = new FileReader();
            reader.readAsDataURL(event.target.files[0]); //将读取到的文件编码成Data URL
            reader.onload = function () { //读取完成时
                imageBase = reader.result.substring(23)
                $.ajax({
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    type: "post",
                    url: "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard",
                    async: true,
                    data: {
                        access_token: access_token,
                        id_card_side: "back",
                        image: imageBase,
                        detect_direction: true
                    },
                    dataType: "json",
                    timeout: 30000,
                    success: function (data) {
                        // console.log("解析成功");
                        // console.log(data);
                        if (data.image_status == "normal") {
                            $("#appspan2").text("经检测：识别正常");
                            let str2 = data.words_result.失效日期.words;
                            $("#shixiaodate").val(str2.slice(0, 4) + '-' + str2.slice(4, 6) + '-' + str2.slice(6, 8));
                        }
                        if (data.image_status == "reversed_side") {
                            $("#appspan2").text("经检测：未摆正身份证");
                        }
                        if (data.image_status == "non_idcard") {
                            $("#appspan2").text("经检测：上传的图⽚中不包含身份证");
                        }
                        if (data.image_status == "blurred") {
                            $("#appspan2").text("经检测：身份证模糊");
                        }
                        if (data.image_status == "over_exposure") {
                            $("#appspan2").text("经检测：身份证关键字段反光或过曝");
                        }
                        if (data.image_status == "unknown") {
                            $("#appspan2").text("经检测：未知状态");
                        }
                    },
                    error: function (xhr) {
                        console.log("请求解析失败");
                        alert("图片解析失败");
                    }
                });

                var replaceSrc = reader.result; //文件输出的内容
                //调用图片压缩处理方法
                that.compressedImage({
                    src: replaceSrc,
                    quality: 0.8,
                    success: function (src) {
                        //将压缩后的路径 追加到临时路径数组中
                        // $.post("/autonym/BaiDuYunCheck",{file:replaceSrc},function (res) {
                        //     console.log(res);
                        // });
                        var totalList = [];
                        if (currentImgTempArray.length > 0) {
                            totalList = currentImgTempArray.concat(src);
                        } else {
                            totalList[0] = src;
                        }
                        that.imgTempList = totalList;
                    }
                });
            };

        },

        //删除某张图片
        deleteImg: function (idx) {
            var that = this;
            that.imgTempList.splice(idx, 1);
        },


        //提交上传图片
        onUploadImg: function () {
            if ($("#appspan2").text() == "经检测：识别正常") {
                var that = this;
                var imgTempList = that.imgTempList;
                if (imgTempList.length > 0) {

                    that.isUploading = true; //正在上传 显示遮罩层 防止连续点击

                    var countNum = 0; //计算数量用的 判断上传到第几张图片了

                    //map循环遍历上传图片
                    imgTempList.map(function (imgItem, imgIndex) {
                        var files = that.dataURLtoFile(imgItem, 'pj' + Date.now() + '.jpg'); //DataURL转File

                        //创FormData对象
                        var formdata = new FormData();
                        //append(key,value)在数据末尾追加数据。 这儿的key值需要和后台定义保持一致
                        formdata.append('img', files);
                        //用axios上传，
                        axios({
                            method: "POST",
                            url: "/publish/upload111", //请将图片上传路径换成自己的。这是测试用的，会不定期删除图片
                            // url: "http://图片上传路径",
                            data: formdata,
                            headers: {
                                "Content-Type": "multipart/form-data"
                            }
                        }).then(function (res) {
                            countNum++;
                            //图片全部上传完后去掉遮罩层
                            if (countNum >= imgTempList.length) {
                                that.isUploading = false;
                            }

                        }).catch(function (error) {
                            console.error(error);
                        });
                    });
                }
                if ($("#appspan3").text() == "经检测：识别正常" && $("#username1").val() != "" && $("#idcardnum1").val() != "" && $("#shixiaodate").val() != "" && $("#username1").val() != null && $("#idcardnum1").val() != null && $("#shixiaodate").val() != null) {
                    alert("实名认证成功");
                }
            } else {
                alert("请正确传入照片！！");
            }

        },

        /**
         * 压缩图片处理
         * @src 需要压缩的图片base64路径
         * @quality 图片质量 0-1，默认1
         * @success()  成功后的回调
         * */
        compressedImage: function (params) {
            var that = this;
            var initParams = {
                src: params.src || "",
                quality: params.quality || 1,
            };

            var image = new Image();
            image.src = initParams.src;
            image.onload = function () {
                //获取图片初始宽高
                var width = image.width;
                var height = image.height;
                //判断图片宽度，再按比例设置宽度和高度的值
                if (width > 1024) {
                    width = 1024;
                    height = Math.ceil(1024 * (image.height / image.width));
                }

                //将图片重新画入canvas中
                var canvas = document.getElementById("compressCanvas");
                if (!canvas) { //如果没有压缩用的canvas 就创建一个canvas画布
                    var body = document.body;
                    canvas = document.createElement("canvas"); //创建canvas标签
                    canvas.id = "compressCanvas"; //给外层容器添加一个id
                    canvas.style.position = "fixed";
                    canvas.style.zIndex = "-1";
                    canvas.style.opacity = "0";
                    canvas.style.top = "-100%";
                    canvas.style.left = "-100%";
                    body.append(canvas);
                }

                var context = canvas.getContext("2d");
                canvas.width = width;
                canvas.height = height;
                context.beginPath();
                context.fillStyle = "#ffffff";
                context.fillRect(0, 0, width, height);
                context.fill();
                context.closePath();
                context.drawImage(image, 0, 0, width, height);
                var replaceSrc = canvas.toDataURL("image/jpeg", initParams.quality); //canvas转DataURL(base64格式)

                params.success && params.success(replaceSrc);
            };
        },

        /**
         * 将base64转换为文件
         * @dataUrl base64路径地址
         * @fileName 自定义文件名字
         * */
        dataURLtoFile: function (dataUrl, fileName) {
            var arr = dataUrl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new File([u8arr], fileName, {type: mime});
        },
    }
});
var app2 = new Vue({
    el: '#app2',
    data: {
        imgTempList: [], //图片临时路径列表
        isUploading: false, //是否正在上传
        successPath: [], //上传成功后的路径(没必要)
    },
    mounted: function () {
        var that = this;
    },
    watch: {},
    methods: {
        //选择图片
        onChooseImage: function (event) {
            var that = this;
            if ($("#username1").val() == "" && $("#idcardnum1").val() == "") {
                alert("请先上传身份证正面照片");
                return false;
            }




            //判断图片数量是否已上限
            var currentImgTempArray = that.imgTempList;
            if (currentImgTempArray.length >= 1) {
                alert("最多上传1张图片");
                return false;
            }

            //使用FileReader对文件对象进行操作
            var reader = new FileReader();
            reader.readAsDataURL(event.target.files[0]); //将读取到的文件编码成Data URL
            reader.onload = function () { //读取完成时
                var replaceSrc = reader.result; //文件输出的内容
                //调用图片压缩处理方法
                that.compressedImage({
                    src: replaceSrc,
                    quality: 0.8,
                    success: function (src) {
                        let username = $("#username1").val();
                        let idcard = $("#idcardnum1").val();
                        if (username != null && idcard != null) {
                            $.post("/autonym/checkIdCardAndHead",{idcard:idcard,name:username,image:src},function (result) {
                                if (JSON.parse(result).data.msg == "系统判断为同一人") {
                                    $("#appspan3").text("经检测：识别正常");
                                }
                            });
                        }
                        //将压缩后的路径 追加到临时路径数组中
                        var totalList = [];
                        if (currentImgTempArray.length > 0) {
                            totalList = currentImgTempArray.concat(src);
                        } else {
                            totalList[0] = src;
                        }
                        that.imgTempList = totalList;
                    }
                });
            };

        },

        //删除某张图片
        deleteImg: function (idx) {
            var that = this;
            that.imgTempList.splice(idx, 1);
        },


        //提交上传图片
        onUploadImg: function () {
            if ($("#appspan3").text() == "经检测：识别正常") {
                var that = this;
                var imgTempList = that.imgTempList;
                if (imgTempList.length > 0) {

                    that.isUploading = true; //正在上传 显示遮罩层 防止连续点击

                    var countNum = 0; //计算数量用的 判断上传到第几张图片了

                    //map循环遍历上传图片
                    imgTempList.map(function (imgItem, imgIndex) {
                        var files = that.dataURLtoFile(imgItem, 'pj' + Date.now() + '.jpg'); //DataURL转File

                        //创FormData对象
                        var formdata = new FormData();
                        //append(key,value)在数据末尾追加数据。 这儿的key值需要和后台定义保持一致
                        formdata.append('img', files);

                        //用axios上传，
                        axios({
                            method: "POST",
                            url: "/publish/upload111", //请将图片上传路径换成自己的。这是测试用的，会不定期删除图片
                            // url: "http://图片上传路径",
                            data: formdata,
                            headers: {
                                "Content-Type": "multipart/form-data"
                            }
                        }).then(function (res) {
                            countNum++;
                            //图片全部上传完后去掉遮罩层
                            if (countNum >= imgTempList.length) {
                                that.isUploading = false;
                            }
                            console.log(res);
                        }).catch(function (error) {
                            console.error(error);
                        });
                    });
                }

                if ($("#appspan3").text() == "经检测：识别正常" && $("#username1").val() != "" && $("#idcardnum1").val() != "" && $("#shixiaodate").val() != "" && $("#username1").val() != null && $("#idcardnum1").val() != null && $("#shixiaodate").val() != null) {
                    alert("实名认证成功");
                }

            } else {
                alert("请正确传入照片！！");
            }
        },

        /**
         * 压缩图片处理
         * @src 需要压缩的图片base64路径
         * @quality 图片质量 0-1，默认1
         * @success()  成功后的回调
         * */
        compressedImage: function (params) {
            var that = this;

            var initParams = {
                src: params.src || "",
                quality: params.quality || 1,
            };

            var image = new Image();
            image.src = initParams.src;
            image.onload = function () {
                //获取图片初始宽高
                var width = image.width;
                var height = image.height;
                //判断图片宽度，再按比例设置宽度和高度的值
                if (width > 1024) {
                    width = 1024;
                    height = Math.ceil(1024 * (image.height / image.width));
                }

                //将图片重新画入canvas中
                var canvas = document.getElementById("compressCanvas");
                if(!canvas){ //如果没有压缩用的canvas 就创建一个canvas画布
                    var body = document.body;
                    canvas = document.createElement("canvas"); //创建canvas标签
                    canvas.id = "compressCanvas"; //给外层容器添加一个id
                    canvas.style.position = "fixed";
                    canvas.style.zIndex = "-1";
                    canvas.style.opacity = "0";
                    canvas.style.top = "-100%";
                    canvas.style.left = "-100%";
                    body.append(canvas);
                }

                var context = canvas.getContext("2d");
                canvas.width = width;
                canvas.height = height;
                context.beginPath();
                context.fillStyle = "#ffffff";
                context.fillRect(0, 0, width, height);
                context.fill();
                context.closePath();
                context.drawImage(image, 0, 0, width, height);
                var replaceSrc = canvas.toDataURL("image/jpeg", initParams.quality); //canvas转DataURL(base64格式)

                params.success && params.success(replaceSrc);
            };
        },

        /**
         * 将base64转换为文件
         * @dataUrl base64路径地址
         * @fileName 自定义文件名字
         * */
        dataURLtoFile: function (dataUrl, fileName) {
            var arr = dataUrl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new File([u8arr], fileName, {type: mime});
        },
    }
});