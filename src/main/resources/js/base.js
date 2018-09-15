
//字符串前自动补零  ly 2018/06/21
String.prototype.lyPadStart = function (size, pad){
    if(typeof(pad) != "string"){
        pad += "";
    }
    if(typeof(size) != "number"){
        size *= 1;
    }
    var pads ="";
    for(var i = 0; i < (size-this.length); i++){
        pads += pad;
    }
    return pads + this;
}

//获得项目path路径--wkf
function getPath(){

    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;

}

//深度克隆方法 -- 递归
function deepCopy(obj){
    if(typeof obj != 'object'){
        return obj;
    }
    var newobj;
    if (isArrayFn(obj)){
        newobj = [];
        for (var i = 0, length = obj.length; i < length; i++) {
            newobj.push(deepCopy(obj[i]));
        }
    } else {
        newobj = {};
        for (var attr in obj) {
            newobj[attr] = deepCopy(obj[attr]);
        }
    }
    return newobj;
}

//判断是否是数组
function isArrayFn(value){
    if (typeof Array.isArray === "function") {
        return Array.isArray(value); //ECMAScript5将Array.isArray()正式引入JavaScript 存在兼容性问题
    }else{
        return Object.prototype.toString.call(value) === "[object Array]"; //兼容性高
    }
}


//数字转换成汉字
function numberToChinese(number) {

    var numberMap = [];
    numberMap[1] = "一";
    numberMap[2] = "二";
    numberMap[3] = "三";
    numberMap[4] = "四";
    numberMap[5] = "五";
    numberMap[6] = "六";
    numberMap[7] = "七";
    numberMap[8] = "八";
    numberMap[9] = "九";
    numberMap[0] = "";

    var numDw = [];
    numDw[1] = "";
    numDw[2] = "十";
    numDw[3] = "百";
    numDw[4] = "千";
    numDw[5] = "万";
    numDw[6] = "十万";
    numDw[7] = "百万";
    numDw[8] = "千万";
    numDw[9] = "亿";

    if (number > 9 && number < 20)
        return "十" + numberMap[number%10];

    var chinese = "";
    var headChinese = "";
    if (number > 99999 && number < 200000) {
        var headNum = Math.floor(number/10000);
        headChinese = "十" + numberMap[headNum % 10] + "万";
        number = number%10000;
        if (number/1000 < 1){
            headChinese += "零";
        }
    }

    for (var i = 1; number >= 1; i++) {

        var singleNum = number % 10;
        if (0 == singleNum) {

            if (("" != chinese) && ("零" != chinese.split("")[0])) {
                chinese = "零" + chinese;
            }
        } else {
            chinese = numberMap[singleNum] + numDw[i] + chinese;
        }
        number = Math.floor(number / 10);
    }
    return headChinese + chinese;
}

//获得文件扩展名 ly
function getFileType(fileDomId){

    var file = document.getElementById(fileDomId).files[0];
    var fileName = file.name;
    var resultArray = fileName.split(".");
    return resultArray[resultArray.length-1];
}

//获得文件名
function getFileName(fileDomId) {
    var name = document.getElementById(fileDomId).files[0].name;
    var last = name.lastIndexOf(".");
    return name.substring(0, last);
}

//获得文件反显路径 即时反显
function getObjectURL(fileId) {
    var file;
    if ("string" == typeof (fileId)) { //domId
        file = document.getElementById(fileId).files[0];
    } else {
        file = fileId; //file对象
    }
    var url = null;
    if (undefined != window.createObjectURL){
        url = window.createObjectURL(file);
    } else if (undefined != window.URL){
        url = window.URL.createObjectURL(file);
    } else if (undefined != window.webkitURL){
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

// 获得 当前时间 param: boolean isTime
function getNowDate(isTime){
    var date = new Date();
    var dateTime = date.getFullYear();
//		dateTime += "-" + ((date.getMonth() + 1).toString().padStart(2, '0'));
    dateTime += "-" + ((date.getMonth() + 1).toString().lyPadStart(2, '0'));
    dateTime += "-" + (date.getDate().toString().lyPadStart(2, '0'));

    if(true == isTime){  //获得时分秒

        dateTime += " " + (date.getHours().toString().lyPadStart(2, '0'));
        dateTime += ":" + (date.getMinutes().toString().lyPadStart(2, '0'));
        dateTime += ":" + (date.getSeconds().toString().lyPadStart(2, '0'));
    }
    return dateTime;
}

//延时加载 fun：要延时的方法 time:时间／毫秒
function timeOut(fun, time) {
    setTimeout(function () {
        fun();
    }, time*1);
}