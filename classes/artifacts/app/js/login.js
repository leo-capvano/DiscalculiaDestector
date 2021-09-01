$(document).ready(() => {
    const form = $("#formControl");

    form.submit(onFormSubmit);
});

const onFormSubmit = event => {
    event.preventDefault();

    const form = event.target;
    const alert = $("form .alert.alert-danger.alert-dismissible");
    alert.removeClass("show");
    alert.addClass("d-none");

    if (form.checkValidity() === false) {
        event.stopPropagation();
        form.classList.add("was-validated");
        return;
    }

    const button = $("#btnControl");
    button.attr("disabled", true);
    const originalValue = button.html();
    button.html("<span class='spinner-border spinner-border-sm text-light' role='status' aria-hidden='true'></span>");

    const email = $("#inputEmail").val();
    const password = $("#inputPassword").val();

    $.ajax({
        method: "POST",
        url: "pages/login",
        data: {
            email: email,
            password: password
        }
    }).done(data => {
        const response = JSON.parse(data);

        if (response.ok)
            window.location.href = response.content.redirect;
        else {
            alert.text(response.message);
            alert.removeClass("d-none");
            alert.addClass("show");
        }
    }).always(() => {
        form.classList.remove("was-validated");
        button.html("");
        button.text(originalValue);
        button.attr("disabled", false);
    });

};
