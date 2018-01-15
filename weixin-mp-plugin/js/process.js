window.name = "mp-assist-doc";

$("span.highlighter-opreate").click(function () {
    var btn = $(this);
    var state = btn.attr("state")?btn.attr("state"):"1";
    if (state == "1") {
        btn.html("-Demo");
        btn.next().slideDown(200);
        btn.attr("state","2");
    } else {
        btn.html("+Demo");
        btn.next().slideUp(200);
        btn.attr("state","1");
    }
});