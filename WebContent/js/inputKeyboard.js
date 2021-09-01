function writeToDisplay(n, displayId) {
    let display = document.getElementById(displayId);
    display.value = display.value + n;
}

function clearDisplay(displayId) {
    let display = document.getElementById(displayId);
    display.value = "";
}