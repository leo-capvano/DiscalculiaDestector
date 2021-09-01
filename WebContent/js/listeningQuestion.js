let tooltipcontent = document.getElementById("tooltiptextListen");

function endCallback() {
    fadeIn(tooltipcontent);
    setTimeout(function () {
        fadeOut(tooltipcontent);
    }, 3000)
}

function readNumber(toBeRead) {
    responsiveVoice.speak(toBeRead, "Italian Female", {onend: endCallback});
}

function fadeOut(element) {
    let op = 1;  // initial opacity
    let timer = setInterval(function () {
        if (op <= 0.1) {
            clearInterval(timer);
            element.style.display = 'none';
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op -= op * 0.1;
    }, 10);
}

function fadeIn(element) {
    let op = 0.1;  // initial opacity
    element.style.display = 'inline-block';
    let timer = setInterval(function () {
        if (op >= 1) {
            clearInterval(timer);
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op += op * 0.1;
    }, 10);
}