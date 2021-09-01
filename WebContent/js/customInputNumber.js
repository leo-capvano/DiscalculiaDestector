function makeNumber(inputString) {
    if (!inputString) {
        inputString = "0";
    }
    return parseInt(inputString, 10);
}

function addButtonClick(inputClassName) {
    var inputSpinner = document.getElementsByClassName(inputClassName)[0];
    var number = makeNumber(inputSpinner.value)
    inputSpinner.value = number + 1;
}

function subtractButtonClick(inputClassName) {
    var inputSpinner = document.getElementsByClassName(inputClassName)[0];
    var number = makeNumber(inputSpinner.value)
    inputSpinner.value = number - 1;
}