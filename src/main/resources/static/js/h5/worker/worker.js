// postMessage("work.js传回的消息"+m);

onmessage = function (ev) {
  var a = ev.data.a;
  var b = ev.data.b;
  var c = ev.data.c;
  // postMessage("a:" +a + "/b:" + b + "/c:" + c);
  postMessage({
      a: a,
      b: b
  });
};
// var n = 0;
// search: while (true){
//     postMessage(n);
//     n++;
// }