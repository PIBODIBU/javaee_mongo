<!DOCTYPE HTML>

<%@ page import="helper.Config" %>
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
    <link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.deep_purple-amber.min.css"/>
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
    <script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
</head>
<body>

<%LinkedList<MedicineModel> documents = (LinkedList<MedicineModel>) request.getAttribute(DocListServlet.ATTR_DOC_LIST);%>

<style>
    table {
        width: 90%;
    }

    .table-td {
        word-wrap: break-word; /* All browsers since IE 5.5+ */
        overflow-wrap: break-word; /* Renamed property in CSS3 draft spec */
        white-space: normal;
    }

    .table-td-controls {
    }

    .td-wrapper {
        height: 100px;
        overflow: auto;
    }

    .mdl-layout__content {
        width: 100%;
        max-width: 100%;
        margin-top: 16px;
    }

    .mdl-data-table {
        margin-left: auto;
        margin-right: auto;
    }

    .mdl-button--fab {
        position: fixed;
        right: 0;
        bottom: 0;
        margin: 36px;
    }

    .dialog-info {
        width: 80%;
    }
</style>

<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <!-- Title -->
            <span class="mdl-layout-title"><%=DocListServlet.PAGE_TITLE%></span>
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
                               new String(request.getParameter(DocListServlet.PARAM_SEARCH_QUERY).getBytes("iso-8859-1"), "UTF-8"):""%>"
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

        <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
            <thead>
            <tr>
                <th class="mdl-data-table__cell--non-numeric"> Дії
                </th>
                <th class="mdl-data-table__cell--non-numeric"><%=Config.DB.COLUMN_ID_HUMAN%>
                </th>
                <th class="mdl-data-table__cell--non-numeric"><%=Config.DB.COLUMN_NAME%>
                </th>
                <th class="mdl-data-table__cell--non-numeric"><%=Config.DB.COLUMN_INDICATION%>
                </th>
                <th class="mdl-data-table__cell--non-numeric"><%=Config.DB.COLUMN_CONTRAINDICATION%>
                </th>
                <th class="mdl-data-table__cell--non-numeric"><%=Config.DB.COLUMN_SALES_FORM%>
                </th>
            </tr>
            </thead>
            <tbody>

            <%
                for (MedicineModel model : documents) {
                    if (model == null) {
                        continue;
                    }
            %>
            <tr>
                <td class="mdl-data-table__cell--non-numeric table-td-controls">
                    <form action="${pageContext.request.contextPath}/" method="post">
                        <input type="hidden" name="<%=DocListServlet.PARAM_ACTION%>"
                               value="<%=DocListServlet.ACTION_DELETE%>">
                        <input type="hidden" name="<%=DocListServlet.PARAM_DOCUMENT_ID%>"
                               value="<%=model.getId().toString()%>">
                        <button class="mdl-button mdl-js-button mdl-button--icon mdl-color-text--grey-500"
                                style="float: left">
                            <i class="material-icons">delete</i>
                        </button>
                    </form>
                    <button class="mdl-button mdl-js-button mdl-button--icon mdl-color-text--grey-500 show-modal-dialog-edit"
                            onclick="showDialogEditDocument(
                                    '<%=model.getId().toString()%>',
                                    '<%=model.getMedicineName().replaceAll("'", "\\\\'")%>',
                                    '<%=model.getIndication().replaceAll("'", "\\\\'")%>',
                                    '<%=model.getContraindication().replaceAll("'", "\\\\'")%>',
                                    '<%=model.getSalesForm().replaceAll("'", "\\\\'")%>')">
                        <i class="material-icons">edit</i>
                    </button>
                    <button class="mdl-button mdl-js-button mdl-button--icon mdl-color-text--grey-500 show-modal-dialog-edit"
                            onclick="showDialogInfoDocument(
                                    '<%=model.getId().toString()%>',
                                    '<%=model.getMedicineName().replaceAll("'", "\\\\'")%>',
                                    '<%=model.getIndication().replaceAll("'", "\\\\'")%>',
                                    '<%=model.getContraindication().replaceAll("'", "\\\\'")%>',
                                    '<%=model.getSalesForm().replaceAll("'", "\\\\'")%>')">
                        <i class="material-icons">info</i>
                    </button>
                </td>
                <td class="mdl-data-table__cell--non-numeric table-td">
                    <%=model.getId()%>
                </td>
                <td class="mdl-data-table__cell--non-numeric table-td">
                    <%=model.getMedicineName()%>
                </td>
                <td class="mdl-data-table__cell--non-numeric table-td">
                    <div class="td-wrapper">
                        <%=model.getIndication()%>
                    </div>
                </td>
                <td class="mdl-data-table__cell--non-numeric table-td">
                    <div class="td-wrapper">
                        <%=model.getContraindication()%>
                    </div>
                </td>
                <td class="mdl-data-table__cell--non-numeric table-td">
                    <div class="td-wrapper">
                        <%=model.getSalesForm()%>
                    </div>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <button class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored show-modal">
            <i class="material-icons">add</i>
        </button>

        <dialog class="mdl-dialog" id="dialog-add">
            <form action="${pageContext.request.contextPath}/" method="post" accept-charset="UTF-8">
                <div class="mdl-dialog__content">
                    <p class="mdl-typography--headline">
                        Новий документ
                    </p>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input"
                               type="text"
                               name="<%=DocListServlet.PARAM_DOCUMENT_NAME%>"
                               id="sample1">
                        <label class="mdl-textfield__label" for="sample1">Назва</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input name="<%=DocListServlet.PARAM_DOCUMENT_INDICATION%>"
                               class="mdl-textfield__input"
                               type="text"
                               id="sample2">
                        <label class="mdl-textfield__label" for="sample2">Показання</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input name="<%=DocListServlet.PARAM_DOCUMENT_CONTRAINDICATION%>"
                               class="mdl-textfield__input"
                               type="text"
                               id="sample3">
                        <label class="mdl-textfield__label" for="sample3">Протипоказання</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input name="<%=DocListServlet.PARAM_DOCUMENT_SALES_FORM%>"
                               class="mdl-textfield__input"
                               type="text"
                               id="sample4">
                        <label class="mdl-textfield__label" for="sample4">Форма продажу</label>
                    </div>
                </div>

                <input type="hidden" name="<%=DocListServlet.PARAM_ACTION%>" value="<%=DocListServlet.ACTION_ADD%>">
                <input type="hidden" name="<%=DocListServlet.PARAM_ACTION%>" value="<%=DocListServlet.ACTION_ADD%>">
                <div class="mdl-dialog__actions" style="float: right">
                    <button type="submit" class="mdl-button">Додати</button>
                </div>
                <div class="mdl-dialog__actions">
                    <button type="button" class="mdl-button close">Відміна</button>
                </div>
            </form>
        </dialog>
        <script>
            var dialog = document.getElementById('dialog-add');
            var showModalButton = document.querySelector('.show-modal');
            if (!dialog.showModal) {
                dialogPolyfill.registerDialog(dialog);
            }
            showModalButton.addEventListener('click', function () {
                dialog.showModal();
            });
            dialog.querySelector('.close').addEventListener('click', function () {
                dialog.close();
            });
        </script>

        <dialog class="mdl-dialog dialog-info" id="dialog-info">
            <div class="mdl-dialog__content">
                <h3 id="dialog_info_model_name">Update</h3>

                <h5><%=Config.DB.COLUMN_INDICATION%>
                </h5>
                <h7 id="dialog_info_model_indication">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Aenan convallis.
                </h7>

                <h5><%=Config.DB.COLUMN_CONTRAINDICATION%>
                </h5>
                <h7 id="dialog_info_model_contraindication">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Aenan convallis.
                </h7>

                <h5><%=Config.DB.COLUMN_SALES_FORM%>
                </h5>
                <h7 id="dialog_info_model_sales_form">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Aenan convallis.
                </h7>

                <div class="mdl-dialog__actions">
                    <button type="button" class="mdl-button close">OK</button>
                </div>
            </div>
        </dialog>
        <script>
            function showDialogInfoDocument(id, name, indication, contraindication, salesForm) {
                var dialog = document.getElementById('dialog-info');
                if (!dialog.showModal) {
                    dialogPolyfill.registerDialog(dialog);
                }

                dialog.querySelector('.close').addEventListener('click', function () {
                    dialog.close();
                });

                document.getElementById('dialog_info_model_name').innerHTML = name;
                document.getElementById('dialog_info_model_indication').innerHTML = indication;
                document.getElementById('dialog_info_model_contraindication').innerHTML = contraindication;
                document.getElementById('dialog_info_model_sales_form').innerHTML = salesForm;

                dialog.showModal();
            }
        </script>

        <dialog class="mdl-dialog" id="dialog-edit">
            <form action="${pageContext.request.contextPath}/" method="post">
                <div class="mdl-dialog__content">
                    <p class="mdl-typography--headline">
                        Редагувати документ
                    </p>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input"
                               id="input_name"
                               type="text"
                               name="<%=DocListServlet.PARAM_DOCUMENT_NAME%>">
                        <label class="mdl-textfield__label" for="sample3">Назва</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input name="<%=DocListServlet.PARAM_DOCUMENT_INDICATION%>"
                               class="mdl-textfield__input"
                               id="input_indication"
                               type="text">
                        <label class="mdl-textfield__label" for="sample3">Показання</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input name="<%=DocListServlet.PARAM_DOCUMENT_CONTRAINDICATION%>"
                               class="mdl-textfield__input"
                               id="input_contraindication"
                               type="text">
                        <label class="mdl-textfield__label" for="sample3">Протипоказання</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input name="<%=DocListServlet.PARAM_DOCUMENT_SALES_FORM%>"
                               class="mdl-textfield__input"
                               id="input_sales_form"
                               type="text">
                        <label class="mdl-textfield__label" for="sample3">Форма продажу</label>
                    </div>
                </div>

                <input type="hidden" name="<%=DocListServlet.PARAM_ACTION%>"
                       value="<%=DocListServlet.ACTION_EDIT%>">
                <input type="hidden" name="<%=DocListServlet.PARAM_DOCUMENT_ID%>"
                       id="input_doc_id">
                <div class="mdl-dialog__actions" style="float: right">
                    <button type="submit" class="mdl-button">Готово</button>
                </div>
                <div class="mdl-dialog__actions">
                    <button type="button" class="mdl-button close">Відміна</button>
                </div>
            </form>
        </dialog>
        <script>
            function showDialogEditDocument(id, name, indication, contraindication, salesForm) {
                var dialog = document.getElementById('dialog-edit');
                if (!dialog.showModal) {
                    dialogPolyfill.registerDialog(dialog);
                }
                dialog.querySelector('.close').addEventListener('click', function () {
                    dialog.close();
                });

                document.getElementById('input_name').setAttribute('value', name);
                document.getElementById('input_indication').setAttribute('value', indication);
                document.getElementById('input_contraindication').setAttribute('value', contraindication);
                document.getElementById('input_sales_form').setAttribute('value', salesForm);
                document.getElementById('input_doc_id').setAttribute('value', id);

                dialog.showModal();
            }
        </script>
    </main>
</div>

</body>
</html>
