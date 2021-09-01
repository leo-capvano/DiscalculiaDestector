/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.action.shared;

import com.google.gson.Gson;
import control.action.Action;
import entity.Institute;
import entity.account.*;
import entity.enums.*;
import entity.question.Question;
import entity.quiz.DyscalculiaQuiz;
import entity.quiz.QuestionSection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DyscalculiaPatientModel;
import model.DyscalculiaQuizModel;
import model.InstituteModel;
import utility.GsonProducer;
import utility.JsonResponse;

/**
 * @author Francesco Capriglione
 * @version 1.0
 */
public class StartQuizAction extends Action {

    private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request";
    private static final String INVALID_INSTITUTE_MESSAGE = "Invalid institute";
    private static final String NOT_LOGGED_IN_MESSAGE = "You are not allowed to be here";
    private static final String INVALID_QUIZ_MESSAGE = "Invalid quiz";

    private Gson gson;

    private DyscalculiaPatientModel dyscalculiaPatientModel;
    private DyscalculiaQuizModel dyscalculiaQuizModel;
    private InstituteModel instituteModel;

    public StartQuizAction() {
        dyscalculiaPatientModel = new DyscalculiaPatientModel();
        dyscalculiaQuizModel = new DyscalculiaQuizModel();
        instituteModel = new InstituteModel();

        gson = GsonProducer.getGson();
    }

    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        PrintWriter writer = response.getWriter();
        JsonResponse jsonResponse;
        Account loggedAccount = (Account) request.getSession().getAttribute("account");

        int quizID = Integer.parseInt(request.getParameter("quiz"));
        DyscalculiaQuiz quiz = dyscalculiaQuizModel.doRetrieveById(quizID);
        if (quiz == null) {
            jsonResponse = new JsonResponse(false, INVALID_QUIZ_MESSAGE);
            writer.println(gson.toJson(jsonResponse));
            return null;
        }

        List<QuestionSection> sections = quiz.getSections();
        List<Question> questions = new ArrayList<Question>();

        for (QuestionSection questionSection : sections)
            questions.addAll(questionSection.getQuestions());

        //Dispose the questions in a random order
        Collections.shuffle(questions);

        if (loggedAccount instanceof Teacher || loggedAccount instanceof Administrator) {
            HttpSession session = request.getSession();
            session.setAttribute("debugMode", true);
            session.setAttribute("questions", questions);
            session.setAttribute("started", true);
            session.setAttribute("accountInQuiz", loggedAccount);

            jsonResponse = new JsonResponse(true);
            jsonResponse.addContent("redirect", "doQuizDyscalculia.jsp");
            writer.println(gson.toJson(jsonResponse));

            return null;
        } else {
            boolean isGroupMode = Boolean.valueOf(request.getParameter("isGroupMode"));
            String patientName = request.getParameter("patientName");
            Date dateOfBirth = Date.valueOf((String) request.getParameter("dateOfBirth"));
            String patientSurname = request.getParameter("patientSurname");
            String patientGender = request.getParameter("patientGender");
            String patientGraduation = request.getParameter("patientGraduation");
            String patientRegion = request.getParameter("patientRegion");
            String patientAttendedSchool = request.getParameter("patientAttendedSchool");
            String classRoomCode = request.getParameter("classRoomCode");
            String schoolRegister = request.getParameter("schoolRegister");
            String fatherQualification = request.getParameter("fatherQualification");
            String motherQualification = request.getParameter("motherQualification");
            String familyPsychiatricDisorder = request.getParameter("familyPsychiatricDisorder");
            String familyLearningDisorder = request.getParameter("familyLearningDisorder");

            //The user is not logged in
            if (!isGroupMode && loggedAccount == null) {
                jsonResponse = new JsonResponse(false, NOT_LOGGED_IN_MESSAGE);
                writer.println(gson.toJson(jsonResponse));
                return null;
            }

            DyscalculiaPatient patient = null;

            if (!isGroupMode && loggedAccount instanceof DyscalculiaPatient) {
                patient = (DyscalculiaPatient) loggedAccount;
                boolean isUpdated = true;

                if (patient.getName() == null) {
                    isUpdated = false;
                    patient.setName(patientName);
                }

                if (patient.getSurname() == null && patientSurname != null && !patientSurname.equals("")) {
                    isUpdated = false;
                    patient.setSurname(patientSurname);
                }

                if (patient.getDateOfBirth() == null) {
                    isUpdated = false;
                    patient.setDateOfBirth(dateOfBirth);
                }

                if (patient.getGender() == null) {
                    isUpdated = false;
                    patient.setGender(Gender.valueOf(patientGender));
                }

                if (patient.getUniversityDegree() == null && !patientGraduation.equalsIgnoreCase("Nessuna")) {
                    isUpdated = false;
                    patient.setUniversityDegree(UniversityDegree.valueOf(patientGraduation));
                }

                if (patient.getRegion() == null) {
                    isUpdated = false;
                    patient.setRegion(Region.valueOf(patientRegion));
                }

                if (patient.getAttendedSchool() == null) {
                    isUpdated = false;
                    patient.setAttendedSchool(School.valueOf(patientAttendedSchool));
                }

                if (patient.getFatherQualification() == null) {
                    isUpdated = false;
                    patient.setFatherQualification(Qualification.valueOf(fatherQualification));
                }

                if (patient.getMotherQualification() == null) {
                    isUpdated = false;
                    patient.setMotherQualification(Qualification.valueOf(motherQualification));
                }

                if (patient.getFamilyLearningDisorder() == null) {
                    isUpdated = false;
                    patient.setFamilyLearningDisorder(Disorder.valueOf(familyLearningDisorder));
                }

                if (patient.getFamilyPsychiatricDisorder() == null) {
                    isUpdated = false;
                    patient.setFamilyPsychiatricDisorder(Disorder.valueOf(familyPsychiatricDisorder));
                }

                if (!isUpdated)
                    patient = dyscalculiaPatientModel.doUpdate(patient);
            } else if (isGroupMode) {
                int instituteID = Integer.parseInt(request.getParameter("institute"));
                PatientType patientType = PatientType.MULTI_PATIENT;
                Institute institute = instituteModel.doRetrieveById(instituteID);

                if (institute == null) {
                    jsonResponse = new JsonResponse(false, INVALID_INSTITUTE_MESSAGE);
                    writer.println(gson.toJson(jsonResponse));
                    return null;
                }

                patient = dyscalculiaPatientModel.doRetrieveBySchool(classRoomCode, institute, schoolRegister);
                if (patient == null) {
                    patient = new DyscalculiaPatient(patientType, patientName, patientSurname, null, null, Gender.valueOf(patientGender), dateOfBirth, Region.valueOf(patientRegion), null, School.valueOf(patientAttendedSchool), Qualification.valueOf(fatherQualification), Qualification.valueOf(motherQualification), Disorder.valueOf(familyLearningDisorder), Disorder.valueOf(familyPsychiatricDisorder), classRoomCode, institute, schoolRegister);
                    dyscalculiaPatientModel.doSave(patient);
                }
            }
            HttpSession session = request.getSession();
            session.setAttribute("accountInQuiz", patient);
            session.setAttribute("questions", questions);
            session.setAttribute("started", true);

            jsonResponse = new JsonResponse(true);
            jsonResponse.addContent("redirect", "doQuizDyscalculia.jsp");
            writer.println(gson.toJson(jsonResponse));

            return null;
        }

    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        String quizID = request.getParameter("quiz");

        try {
            Integer.parseInt(quizID);
        } catch (NumberFormatException e) {
            return false;
        }

        Account loggedAccount = (Account) request.getSession().getAttribute("account");
        if (loggedAccount instanceof Teacher || loggedAccount instanceof Administrator) {
            return true;
        }

        String patientName = request.getParameter("patientName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String patientGender = request.getParameter("patientGender");
        String patientRegion = request.getParameter("patientRegion");
        String patientAttendedSchool = request.getParameter("patientAttendedSchool");
        String fatherQualification = request.getParameter("fatherQualification");
        String motherQualification = request.getParameter("motherQualification");
        String familyPsychiatricDisorder = request.getParameter("familyPsychiatricDisorder");
        String familyLearningDisorder = request.getParameter("familyLearningDisorder");
        String patientGraduation = request.getParameter("patientGraduation");
        String classRoomCode = request.getParameter("classRoomCode");
        String schoolRegister = request.getParameter("schoolRegister");
        String instituteID = request.getParameter("institute");

        if (patientName == null || dateOfBirth == null || patientGender == null || patientRegion == null || patientAttendedSchool == null || fatherQualification == null || motherQualification == null || familyPsychiatricDisorder == null || familyLearningDisorder == null)
            return false;

        try {
            Region.valueOf(patientRegion);
            Gender.valueOf(patientGender);
            School.valueOf(patientAttendedSchool);
            Qualification.valueOf(fatherQualification);
            Qualification.valueOf(motherQualification);
            Disorder.valueOf(familyLearningDisorder);
            Disorder.valueOf(familyPsychiatricDisorder);
            Date.valueOf(dateOfBirth);
        } catch (IllegalArgumentException e) {
            return false;
        }

        boolean isGroupMode = Boolean.valueOf(request.getParameter("isGroupMode"));
        if (isGroupMode) {
            if (classRoomCode == null || schoolRegister == null)
                return false;

            try {
                Integer.parseInt(instituteID);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            if (patientGraduation == null)
                return false;

            if (!patientGraduation.equalsIgnoreCase("Nessuna")) {
                try {
                    UniversityDegree.valueOf(patientGraduation);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    protected void catchInvalidRequestError(HttpServletResponse response) throws IOException {
        JsonResponse jsonResponse = new JsonResponse(false, MALFORMED_REQUEST_MESSAGE);
        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(jsonResponse));
    }

    @Override
    protected String getResponseType() {
        return Action.JSON_RESPONSE;
    }

}
