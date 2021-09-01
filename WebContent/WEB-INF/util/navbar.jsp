<%@page import="control.action.Action"%>
<%@ page language="java"%>

<% 
    String homepageURI = "./";
    
    if(response.getHeader(Action.HEADER_NAME) != null)
        if(response.getHeader(Action.HEADER_NAME).equals(Action.FORWARD_RESPONSE))
            homepageURI = "../";
%>

<nav class="navbar navbar-expand navbar-dark bg-dark static-top">

    <div class="navbar-toggle mr-4" id="sidebarToggle">
        <div class="animated-times"><span></span><span></span><span></span></div>
    </div>
    <a class="navbar-brand mr-1" href="<%= homepageURI %>">Home</a>
</nav>