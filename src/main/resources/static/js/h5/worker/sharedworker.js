
//标识当共享子线程与主线程建立连时出发的事件
onconnect = function (ev) {
    var port = ev.ports[0];
    port.postMessage("work");
};

onmessage = function (ev) {
    var a = ev.data.a;
    var b = ev.data.b;
    var c = ev.data.c;
    // postMessage("a:" +a + "/b:" + b + "/c:" + c);
    console.log("send.js");
    postMessage({
        a: a,
        b: b
    });
};