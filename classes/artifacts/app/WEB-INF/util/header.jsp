<%@ page language="java" import="control.action.Action" %>

<%
    String shortcutIconURL = "images/favicon.ico";
    String sidebarCssURL = "css/sidebar.css";
    String navbarCssURL = "css/navbar.css";
    String generalCssURL = "css/general.css";
    String sidebarJsURL = "js/sidebar.js";

    if (response.getHeader(Action.HEADER_NAME) != null)
        if (response.getHeader(Action.HEADER_NAME).equals(Action.FORWARD_RESPONSE)) {
            shortcutIconURL = "../" + shortcutIconURL;
            sidebarCssURL = "../" + sidebarCssURL;
            navbarCssURL = "../" + navbarCssURL;
            sidebarJsURL = "../" + sidebarJsURL;
            generalCssURL = "../" + generalCssURL;
        }
%>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&display=swap" rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/c1ff589a0c.js" crossorigin="anonymous"></script>


<link rel="shortcut icon" href="<%=shortcutIconURL%>">
<link rel="stylesheet" type="text/css" href="<%=sidebarCssURL%>"/>
<link rel="stylesheet" type="text/css" href="<%=navbarCssURL%>"/>
<link rel="stylesheet" type="text/css" href="<%=generalCssURL%>"/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="<%=sidebarJsURL%>"></script>