$(document).ready(() => {
    const laureaSelect = $("#laureaPaziente");
    const forms = $(".needs-validation");
    const button = $("#startQuiz");
    const checkPatientButton = $("#checkPatient");
    
    const isGroupMode = button.attr('data-isgroupmode');
    if(isGroupMode === "true") {
        $(".to-show").css("display", "none");
    }
    else
        precompileForm();

    checkPatientButton.click(onCheckPatientButtonClick);
    laureaSelect.change(onLaureaSelectChange);
    forms.submit(validateForm);

    quizLazyLoading();
    instituteLazyLoading();
});

const onLaureaSelectChange = event => {
    const value = event.target.value;
    const negativeValue = "No";
    const elementToHideOrShow = $("#istruzionePaziente").parent();

    if (value === negativeValue) {
        elementToHideOrShow.fadeOut();
        elementToHideOrShow.find("select").prop("required", false);
    } else {
        elementToHideOrShow.fadeIn();
        elementToHideOrShow.find("select").prop("required", true);
    }
};

const quizLazyLoading = () => {
    const loadingElement = $(".custom-loading#quizs");
    const element = $("#quiz");
    loadingElement.css("display", "initial");
    element.attr("disabled", true);
    
    $.ajax({
        method: "POST",
        url: "pages/quizLazyLoading",
        data: {
            askFor: "trustedQuizzes"
        }
    }).done(populateQuiz)
    .always(() => {
        loadingElement.css("display", "none");
        element.attr("disabled", false);
    });
};

const instituteLazyLoading = () => {
    const loadingElement = $(".custom-loading#institute");
    const element = $("#codicePlesso");
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

const populateQuiz = result => {

    const response = JSON.parse(result);

    if (!response.ok) {
        console.log(response.message);
        return;
    }

    const quizArray = response.content.quizList;

    const quizSelect = $("#quiz");
    
    quizArray.forEach(element => {
        const toAppend = `<option value='${element.id}'>${element.name}</option>`;
        quizSelect.append(toAppend);
    });

};

const populateInstitute = result => {
    const response = JSON.parse(result);
    
    if(!response.ok) {
        console.log(response.message);
        return;
    }
    
    const instituteArray = response.content.institutes;
    const instituteSelect = $("#codicePlesso");
    
    instituteArray.forEach(element => {
        const toAppend = `<option value='${element.id}'>${element.name}</option>`;
        instituteSelect.append(toAppend);
    });
};

const validateForm = event => {
    event.preventDefault();
    const form = event.target;

    if (form.checkValidity() === false) {
        event.stopPropagation();
        form.classList.add("was-validated");
        $(".form-control:invalid").first().focus();
        return;
    }

    const nomePaziente = $('#nomePaziente').val();
    let cognomePaziente = $('#cognomePaziente').val();
    const annoPaziente = $('#annoPaziente option:selected').text();
    const mesePaziente = $('#mesePaziente option:selected').text();
    const giornoPaziente = $('#giornoPaziente option:selected').text();
    const agePaziente = getAge(giornoPaziente, mesePaziente, annoPaziente);
    const genderPaziente = $('#genderPaziente option:selected').val();
    const regionePaziente = $("#regionePaziente option:selected").val();
    let tipoLaurea = $("#istruzionePaziente option:selected").val();
    const scuolaFreq = $('#scuolaFrequentata option:selected').val();
    const codicePlesso = $('#codicePlesso option:selected').val();
    const codiceClasse = $('#codiceClasse').val();
    const codiceRegistro = $('#codiceRegistro').val();
    const titoloPadre = $('#titoloPadre option:selected').val();
    const titoloMadre = $('#titoloMadre option:selected').val();
    const disturbiAppr = $('#disturbi option:selected').val();
    const disturbiFamiglia = $('#disturbiFamiglia option:selected').val();
    const quiz = $('#quiz').find(":selected").val();

    const isGroupMode = $(form).find("#startQuiz").attr('data-isgroupmode');

    if (!cognomePaziente)
        cognome = "";

    if (!tipoLaurea)
        tipoLaurea = "Nessuna";

    const dataPaziente = annoPaziente + "-" + mesePaziente + "-" + giornoPaziente;

    $.ajax({
        method: "POST",
        url: "pages/startQuiz",
        data: {
            quiz: quiz,
            isGroupMode: isGroupMode,
            patientName: nomePaziente,
            patientSurname: cognomePaziente,
            dateOfBirth: dataPaziente,
            patientAge: agePaziente,
            patientGender: genderPaziente,
            patientRegion: regionePaziente,
            patientGraduation: tipoLaurea,
            patientAttendedSchool: scuolaFreq,
            classRoomCode: codiceClasse,
            institute: codicePlesso,
            schoolRegister: codiceRegistro,
            fatherQualification: titoloPadre,
            motherQualification: titoloMadre,
            familyLearningDisorder: disturbiAppr,
            familyPsychiatricDisorder: disturbiFamiglia
        }
    }).done(data => {
        console.log("Waiting for quiz...");
        
        const response = JSON.parse(data);
        if(!response.ok) {
            console.error(response.message);
        }
        else
            window.location.href = response.content.redirect;
        
    }).fail((response, errorText) => {
        console.error(response.status, errorText);
    });

};

const precompileForm = () => {
    const form = $("form");
    const quizField = $("#quiz");
    
    quizField.attr("required", false);
    
    if(form[0].checkValidity()) {
        const inputs = form.find("input");
        const selects = form.find("select");
        
        inputs.attr("disabled", true);
        selects.attr("disabled", true);
    }
    
    quizField.attr("disabled", false);
    quizField.attr("required", true);
};

const onCheckPatientButtonClick = event => {
    const button = $(event.target);
    const elementsToHide = $(".to-hide");
    const elementsToShow = $(".to-show");
    const subForm = $("#sub-form");
    const form = $("form");
    subForm.removeClass("was-validated");
    
    const classRoomCode = $('#codiceClasse').val();
    const institute = $('#codicePlesso option:selected').val();;
    const schoolRegister = $('#codiceRegistro').val();
    
    if(!institute || !schoolRegister || !classRoomCode) {
        subForm.addClass("was-validated");
        return;
    }
    
    button.attr("disabled", true);
    button.addClass("disabled");
    
    $.ajax({
        method: "POST",
        url: "pages/retrieveMultiPatient",
        data: {
            classRoomCode: classRoomCode,
            institute: institute,
            schoolRegister: schoolRegister
        }
    }).done(data => {
        const response = JSON.parse(data);
        if(!response.ok) {
            console.error(response.message);
            return;
        }
        
        const patient = response.content.patient;
        
        if(patient) {
            const date = new Date(patient.dateOfBirth);
            
            const inputs = form.find("input");
            const selects = form.find("select");
            
            inputs.attr("disabled", true);
            selects.attr("disabled", true);
            
            //Show stored info into inputs
            $('#nomePaziente').val(patient.name);
            $('#cognomePaziente').val(patient.surname);
            $('#annoPaziente').val(date.getFullYear());
            $('#mesePaziente').val(date.getMonth() + 1);
            $('#giornoPaziente').val(date.getDate());
            $('#genderPaziente').val(patient.gender);
            $("#regionePaziente").val(patient.region);
            $('#scuolaFrequentata').val(patient.attendedSchool);
            $('#titoloPadre').val(patient.fatherQualification);
            $('#titoloMadre').val(patient.motherQualification);
            $('#disturbi').val(patient.familyLearningDisorder);
            $('#disturbiFamiglia').val(patient.familyPsychiatricDisorder);
            
            retrieveTeacherQuiz();
            
            $("#quiz").attr("disabled", false);
        }
        
        elementsToHide.slideUp();
        elementsToShow.slideDown();
    });
    
};

const retrieveTeacherQuiz = () => {
    const loadingElement = $(".custom-loading#quizs");
    const element = $("#quiz");
    loadingElement.css("display", "initial");
    element.attr("disabled", true);
    
    const instituteID = $('#codicePlesso option:selected').val();
    
    $.ajax({
        method: "POST",
        url: "pages/quizLazyLoading",
        data: {
            askFor: "instituteQuizzes",
            instituteID: instituteID
        }
    }).done(populateQuiz)
    .always(() => {
        loadingElement.css("display", "none");
        element.attr("disabled", false);
    });
};

const getAge = (day, month, year) => {
    const currDate = new Date();
    const birthDate = new Date(year + "/" + month + "/" + day);
    let age = currDate.getFullYear() - birthDate.getFullYear();
    const m = currDate.getMonth() - birthDate.getMonth();

    if (m < 0 || (m === 0 && currDate.getDate() < birthDate.getDate()))
        age--;

    return age;
};