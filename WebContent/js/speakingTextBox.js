let imgHTML = "<img src=\"images/click.png\" alt=\"click\" style=\"width: 35px;\">"


function readPage(questionNumber) {
    let questionContainer = document.getElementById("question-" + questionNumber)
    let speakables = questionContainer.getElementsByClassName("speakable" + questionNumber)
    for (let i = 0; i < speakables.length; i++) {
        if (getComputedStyle(speakables[i], null).display !== "none") {
            let cleared = speakables[i].innerHTML.replace(imgHTML, "");
            responsiveVoice.speak(cleared, "Italian Female");
        }
    }
}