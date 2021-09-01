$(document).ready(() => {
    const hoverables = $(".hoverable");
    const signupDiv = $("#signup-div");
    const backButton = $("#back-button");

    const form = signupDiv.find("form");
    const passwordMatch = $(form).find("#repeatPassword");
    const passwordInput = $(form).find("#inputPassword");
    
    passwordMatch.on('input', onPasswordInput);
    passwordInput.on('input', onPasswordInput);

    form.submit(onFormSubmit);
    hoverables.click(onHoverableClick);
    backButton.click(onBackButtonClick);
    
    instituteLazyLoading();
});

const onFormSubmit = event => {
    event.preventDefault();
	
    const form = event.target;
    const alert = $(form).find(".alert");
    alert.addClass("d-none");
    alert.removeClass("show");

    form.classList.remove("was-validated");
	
    if(form.checkValidity() === false) {
        event.stopPropagation();
        form.classList.add("was-validated");
        return;
    }
	
    const button = $("#btnControl");
    button.attr("disabled", true);
    const originalValue = button.html();
    button.html("<span class='spinner-border spinner-border-sm text-light' role='status' aria-hidden='true'></span>");

    let action = "pages/user/signup";

    const name = $(form).find("#name").val();
    const surname = $(form).find("#surname").val();
    const email = $(form).find("#inputEmail").val();
    const password = $(form).find("#inputPassword").val();
    const institute = $(form).find("#institutes option:selected").val();
    let isTeacher;
    
    if($(form).attr("data-is-teacher"))
        isTeacher = true;
    else
        isTeacher = false;
    
    if(isTeacher)
        action = "pages/teacher/signup";
    
    const data = {
        name: name,
        surname: surname,
        email: email,
        password: password,
        institute: institute
    };
    
    $.ajax({
        method: "POST",
        url: action,
        data: data
    }).done(result => {
        const response = JSON.parse(result);
        
        if(!response.ok) {
            alert.text(response.message);
            alert.removeClass("d-none");
            alert.addClass("show");
            button.attr("disabled", false);
            button.html(originalValue);
            return;
        }
        
        const signupDiv = $("#signup-div");
        const confirmDiv = $("#confirm-div");
        const nameText = confirmDiv.find("#account-name");
        
        nameText.text(name);
        
        signupDiv.fadeOut(200, () => {
            confirmDiv.addClass("fadeInUp");
            confirmDiv.removeClass("d-none");
            //Catch the end of animation
            confirmDiv.bind('oanimationend animationend webkitAnimationEnd', function() { 
                signupDiv.addClass("d-none");
                signupDiv.css("display", "initial");
                confirmDiv.removeClass("fadeInUp");
            });
        });
        
    }).fail(() => {
        alert.text("Impossibile completare la registrazione. Riprova piu' tardi.");
        alert.removeClass("d-none");
        alert.addClass("show");
        button.attr("disabled", false);
        button.html(originalValue);
    });
};

const onHoverableClick = event => {
    const target = $(event.target);
    const selector = $("#selector");
    const signupDiv = $("#signup-div");
    const emailInput = signupDiv.find("#inputEmail");
    const emailFormGroup = emailInput.parent();
    const emailLabel = emailFormGroup.find("label");

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

    const type = target.attr("data-type");
    const toShow = signupDiv.find("form #toHide");

    if(type) {
        //Type teacher
        toShow.removeClass("d-none");
        toShow.find("#institutes").attr("required", true);
        signupDiv.find("form").attr("data-is-teacher", "true");
        emailInput.attr("placeholder", "sample@istruzione.it");
        emailLabel.html(emailLabel.html() + `<span class="text-secondary"> - Istituzionale</span>`);
    }
    else {
        //Type student
        toShow.addClass("d-none");
        toShow.find("#institutes").attr("required", false);
        signupDiv.find("form").removeAttr("data-is-teacher");
        emailInput.attr("placeholder", "sample@email.com");
        emailLabel.html("Indirizzo email");
    }
};

const instituteLazyLoading = () => {
    const loadingElement = $(".custom-loading#institute");
    const element = $("form #institutes");
    loadingElement.css("display", "initial");
    element.attr("disabled", true);
    
    $.ajax({
        method: "POST",
        url: "pages/instituteLazyLoading"
    }).done(populateInstitute)
    .always(() => {
        loadingElement.css("display", "none");
        element.attr("disabled", false);
    });
};

const populateInstitute = result => {
    const response = JSON.parse(result);
    
    if(!response.ok) {
        console.log(response.message);
        return;
    }
    
    const instituteArray = response.content.institutes;
    const instituteSelect = $("form #institutes");
    
    instituteArray.forEach(element => {
        const toAppend = `<option value='${element.id}'>${element.name}</option>`;
        instituteSelect.append(toAppend);
    });
};

const onBackButtonClick = event => {
    const selector = $("#selector");
    const signupDiv = $("#signup-div");
    const signupForm = signupDiv.find("#formControl");
    const alert = signupForm.find(".alert");

    signupDiv.fadeOut(200, () => {
        selector.addClass("fadeInUp");
        selector.removeClass("d-none");
        //Catch the end of animation
        selector.bind('oanimationend animationend webkitAnimationEnd', function() { 
            signupDiv.addClass("d-none");
            signupDiv.css("display", "initial");
            selector.removeClass("fadeInUp");
            signupForm.trigger("reset");
            alert.addClass("d-none");
            alert.removeClass("show");
        });
    });
};

const onPasswordInput = event => {
    const password = $("#signup-div form #inputPassword");
    const passwordMatch = $("#signup-div form #repeatPassword");

    if(password.val() != passwordMatch.val())
        passwordMatch[0].setCustomValidity("Password does not match");
    else
        passwordMatch[0].setCustomValidity("");
};