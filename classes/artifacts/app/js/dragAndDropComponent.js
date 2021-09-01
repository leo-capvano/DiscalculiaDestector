let element = document.getElementById("dragDropPosition");

let initialParent = document.getElementById("dragAndDropPositionContainer");
let draggableRow = document.getElementById("draggableRow");
let initialLeft = element.style.left;
let initialTop = element.style.top;
let initialHTMLElementBelow = document.getElementsByClassName("droppableTarget")[0].innerHTML;
let clone;

function cloneElementToFillSpace() {
    clone = element.cloneNode(true);
    clone.style.visibility = "hidden";
    draggableRow.append(clone);
}

element.onmousedown = function (event) {

    cloneElementToFillSpace();

    element.style.position = 'absolute';
    element.style.zIndex = 1000;

    document.body.append(element);

    function moveAt(pageX, pageY) {
        element.style.left = pageX - element.offsetWidth / 2 + 'px';
        element.style.top = pageY - element.offsetHeight / 2 + 'px';
    }

    moveAt(event.pageX, event.pageY);

    function onMouseMove(event) {
        moveAt(event.pageX, event.pageY);
        element.hidden = true;
        let elementBelow = document.elementFromPoint(event.clientX, event.clientY).closest(".droppableTarget");

        if (elementBelow) {
            elementBelow.innerHTML = element.innerHTML;
            elementBelow.style.backgroundColor = "#feaf20";
            elementBelow.style.transform = "scale(1,1)"
        } else {
            clearAllDroppableTarget()
        }

        function clearAllDroppableTarget() {
            let targets = document.getElementsByClassName("droppableTarget");
            for (let j = 0; j < targets.length; j++) {
                if (targets[j].innerHTML !== initialHTMLElementBelow) {
                    targets[j].innerHTML = initialHTMLElementBelow;
                }
                targets[j].style.backgroundColor = "rgba(86, 130, 204, 0.85)";
            }

        }

        element.hidden = false;
    }

    document.addEventListener('mousemove', onMouseMove);

    function returnBackToInitialPosition() {
        element.style.position = "Relative";
        element.style.left = initialLeft;
        element.style.top = initialTop;
        initialParent.append(element);
    }

    element.onmouseup = function (event) {
        document.removeEventListener('mousemove', onMouseMove);
        element.hidden = true;
        let elementBelow = document.elementFromPoint(event.clientX, event.clientY).closest(".droppableTarget");
        if (elementBelow) {
            //click elementBelow to go next question
            elementBelow.click();
            returnBackToInitialPosition();
        } else {
            returnBackToInitialPosition();
        }
        element.hidden = false;
        clone.remove();
    };

};

element.ondragstart = function () {
    return false;
};
