$(document).ready(() => {
    const form = $("form");
    
    form.submit(onFormSubmit);
});

const onFormSubmit = event => {
    event.preventDefault();
    
    const form = event.target;
    
    if (form.checkValidity() === false) {
        event.stopPropagation();
        form.classList.add("was-validated");
        return;
    }
    
    const selector = $("#toHide");
    const signupDiv = $("#toShow");

    selector.fadeOut(200, () => {
        signupDiv.addClass("fadeInUp");
        signupDiv.removeClass("d-none");
        //Catch the end of animation
        signupDiv.bind('oanimationend animationend webkitAnimationEnd', function() { 
            selector.addClass("d-none");
            selector.css("display", "initial");
            signupDiv.removeClass("fadeInUp");
        });
    });
    
    const email = $("#inputEmail").val();
    
    $.ajax({
        method: "POST",
        url: "pages/forgotPassword",
        data: {
            email: email
        }
    }).done(data => {
        console.log(JSON.parse(data));
    }).fail(err => {
        console.log(err);
    });
};