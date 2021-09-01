<%@ page language="java" import="entity.account.*, control.action.Action"%>

<%
    final Account account = (Account) session.getAttribute("account");
    
    boolean isInWebInf = false;
    
    if(response.getHeader(Action.HEADER_NAME) != null)
        isInWebInf = response.getHeader(Action.HEADER_NAME).equals(Action.FORWARD_RESPONSE);
        
    
    String dyscalculiaURL = "dyscalculia.jsp";
    String dyscalculiaMultiURL = "dyscalculia.jsp?mode=group";
    String userListURL = "pages/userlist";
    String quizManagerURL = "pages/quizSections";
    String loginURL = "login.jsp";
    String signupURL = "register.jsp";
    String logoutURL = "pages/logout";
    String statsURL = "pages/quizstatistics?id=";
    String userManangerURL = "pages/manageusers";
    String instituteManagerURL = "pages/manageinstitutes";
    String databaseURL = "pages/database";
    
    if(isInWebInf) {
        dyscalculiaURL = "../" + dyscalculiaURL;
        dyscalculiaMultiURL = "../" + dyscalculiaMultiURL;
        userListURL = "../" + userListURL;
        quizManagerURL = "../" + quizManagerURL;
        loginURL = "../" + loginURL;
        signupURL = "../" + signupURL;
        logoutURL = "../" + logoutURL;
        statsURL = "../" + statsURL;
        userManangerURL = "../" + userManangerURL;
        instituteManagerURL = "../" + instituteManagerURL;
        databaseURL = "../" + databaseURL;
    }
%>

<ul class="sidebar list-unstyled components">
    <li class="nav-item">
        <a href="#dyscalculiaSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link"><i class="pr-2 fas fa-fw fa-sort-numeric-up"></i>Discalculia</a>
        <ul class="collapse list-unstyled" id="dyscalculiaSubmenu">
            <li class="nav-item">
                <a href='<%=dyscalculiaURL%>'>Test discalculia (singolo)</a>
            </li>
            <% if(account == null || !(account instanceof DyscalculiaPatient)) { %>
                <li class="nav-item">
                    <a href='<%=dyscalculiaMultiURL%>'>Test discalculia (gruppo)</a>
                </li>
            <%}%>
            <% if(account != null && !(account instanceof DyscalculiaPatient)) { %>
                <li class="nav-item">
                    <a href='<%=userListURL%>'>Lista utenti</a>
                </li>
                <li class="nav-item">
                    <a href='<%=quizManagerURL%>'>Gestisci quiz</a>
                </li>
            <%} %>
        </ul>
    </li>
    <% if(account instanceof DyscalculiaPatient) { 
        statsURL += account.getId(); %>
        <li class="nav-item">
            <a href="#statisticheSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link"><i class="pr-2 fas fa-chart-pie"></i>Statistiche</a>
            <ul class="collapse list-unstyled" id="statisticheSubmenu">
                <li class="nav-item">
                    <a href='<%=statsURL%>'>Statistiche discalculia</a>
                </li>
            </ul>
        </li>
    <%}%>
    <li class="nav-item">
        <a href="#userSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link"><i class="pr-2 fas fa-user-shield"></i>Area Utente</a>
        <ul class="collapse list-unstyled" id="userSubmenu">
        <%
        if(account == null) { %>
            <li class="nav-item">
                <a href='<%=loginURL%>'>Accedi</a>
            </li>
            <li class="nav-item">
                <a href='<%=signupURL%>'>Registrati</a>
            </li>
        <%} 
        else {
            if(account instanceof Administrator) { %>
                <li class="nav-item">
                    <a href="<%=userManangerURL%>">Gestione utenti</a>
                </li>
                <li class="">
                    <a href="<%=instituteManagerURL%>">Gestione istituti</a>
                </li>
                <li class="nav-item">
                    <a href="<%=databaseURL%>">Database</a>
                </li>
            <%} %>
            <li class="nav-item">
                <a href='<%=logoutURL%>'>Logout</a>
            </li>
        <% } %>
        </ul>
    </li>
</ul>