<!DOCTYPE HTML>

<%@ page import="model.MedicineModel" %>
<%@ page import="servlet.DocListServlet" %>
<%@ page import="servlet.InfoServlet" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <title><%=DocListServlet.PAGE_TITLE%>
    </title>

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.teal-deep_purple.min.css"/>
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
    <script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
</head>
<body>

<%LinkedList<MedicineModel> documents = (LinkedList<MedicineModel>) request.getAttribute(DocListServlet.ATTR_DOC_LIST);%>

<style>
    div.center {
        width: 70%;
        display: block;
        margin-left: auto;
        margin-right: auto;
        padding: 16px;
    }

    .mdl-layout__content {
        width: 100%;
    }

    .mdl-button--fab {
        position: absolute;
        right: 0;
        bottom: 0;
        margin: 36px;
    }
</style>

<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <!-- Title -->
            <span class="mdl-layout-title"><%=InfoServlet.PAGE_TITLE%></span>
            <!-- Add spacer, to align navigation to the right -->
            <div class="mdl-layout-spacer"></div>
            <!-- Navigation. We hide it in small screens. -->
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable
                  mdl-textfield--floating-label mdl-textfield--align-right">
                <label class="mdl-button mdl-js-button mdl-button--icon"
                       for="fixed-header-drawer-exp">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <form action="${pageContext.request.contextPath}/" method="post">
                        <input type="hidden"
                               name="<%=DocListServlet.PARAM_ACTION%>"
                               value="<%=DocListServlet.ACTION_SEARCH%>">
                        <input class="mdl-textfield__input"
                               type="text"
                               value="<%=request.getParameter(DocListServlet.PARAM_ACTION) == null?
                               "":request.getParameter(DocListServlet.PARAM_ACTION).equals(DocListServlet.ACTION_SEARCH)?
                               request.getParameter(DocListServlet.PARAM_SEARCH_QUERY):""%>"
                               name="<%=DocListServlet.PARAM_SEARCH_QUERY%>"
                               id="fixed-header-drawer-exp">
                    </form>
                </div>
            </div>
        </div>
    </header>
    <div class="mdl-layout__drawer">
        <span class="mdl-layout-title">JavaEE ♥ MongoDB</span>
        <nav class="mdl-navigation">
            <a class="mdl-navigation__link" href="${pageContext.request.contextPath}/"><%=DocListServlet.PAGE_TITLE%>
            </a>
            <a class="mdl-navigation__link"
               href="${pageContext.request.contextPath}/info"><%=InfoServlet.PAGE_TITLE%>
            </a>
            <%--<a class="mdl-navigation__link" href="">Link</a>
            <a class="mdl-navigation__link" href="">Link</a>
            <a class="mdl-navigation__link" href="">Link</a>--%>
        </nav>
    </div>
    <main class="mdl-layout__content">
        <div class="page-content">

        </div>
    </main>
</div>

</body>
</html>
