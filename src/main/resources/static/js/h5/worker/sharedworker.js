
//标识当共享子线程与主线程建立连时出发的事件
onconnect = function (ev) {
    var port = ev.ports[0];
    port.postMessage("work");
}