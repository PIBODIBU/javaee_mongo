package servlet;

import db.DBHelper;
import db.implementation.DBHelperImpl;
import model.MedicineModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "DocListServlet", urlPatterns = {"/"})
public class DocListServlet extends HttpServlet {
    public static final String PAGE_TITLE = "Список документів";
    public static final String ATTR_DOC_LIST = "doc_list";
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_DOCUMENT_ID = "doc_id";
    public static final String PARAM_DOCUMENT_NAME = "doc_name";
    public static final String PARAM_DOCUMENT_INDICATION = "doc_indication";
    public static final String PARAM_DOCUMENT_CONTRAINDICATION = "doc_contraindication";
    public static final String PARAM_DOCUMENT_SALES_FORM = "doc_sales_form";
    public static final String PARAM_SEARCH_QUERY = "search_query";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_SEARCH = "search";

    private DBHelper dbHelper;

    @Override
    public void init() throws ServletException {
        super.init();

        dbHelper = new DBHelperImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter(PARAM_ACTION).equals(ACTION_DELETE)) {
            dbHelper.deleteDocument(request.getParameter(PARAM_DOCUMENT_ID));

            LinkedList<MedicineModel> documents = dbHelper.getAllDocuments();
            request.setAttribute(ATTR_DOC_LIST, documents);
        } else if (request.getParameter(PARAM_ACTION).equals(ACTION_ADD)) {
            dbHelper.addDocument(new MedicineModel(null,
                    new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_NAME).getBytes("iso-8859-1"), "UTF-8"),
                    new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_INDICATION).getBytes("iso-8859-1"), "UTF-8"),
                    new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_CONTRAINDICATION).getBytes("iso-8859-1"), "UTF-8"),
                    new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_SALES_FORM).getBytes("iso-8859-1"), "UTF-8")
            ));

            LinkedList<MedicineModel> documents = dbHelper.getAllDocuments();
            request.setAttribute(ATTR_DOC_LIST, documents);
        } else if (request.getParameter(PARAM_ACTION).equals(ACTION_EDIT)) {
            dbHelper.updateDocument(
                    new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_ID).getBytes("iso-8859-1"), "UTF-8"), new MedicineModel(null,
                            new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_NAME).getBytes("iso-8859-1"), "UTF-8"),
                            new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_INDICATION).getBytes("iso-8859-1"), "UTF-8"),
                            new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_CONTRAINDICATION).getBytes("iso-8859-1"), "UTF-8"),
                            new String(request.getParameter(DocListServlet.PARAM_DOCUMENT_SALES_FORM).getBytes("iso-8859-1"), "UTF-8")
                    ));

            LinkedList<MedicineModel> documents = dbHelper.getAllDocuments();
            request.setAttribute(ATTR_DOC_LIST, documents);
        } else if (request.getParameter(PARAM_ACTION).equals(ACTION_SEARCH)) {
            LinkedList<MedicineModel> documents = dbHelper.getAllDocuments();
            LinkedList<MedicineModel> filteredDocuments = new LinkedList<MedicineModel>();
            final String searchQuery = request.getParameter(PARAM_SEARCH_QUERY);

            for (MedicineModel model : documents) {
                if (model.getMedicineName().contains(searchQuery)) {
                    filteredDocuments.add(model);
                }
            }

            request.setAttribute(ATTR_DOC_LIST, filteredDocuments);
        } else {
            LinkedList<MedicineModel> documents = dbHelper.getAllDocuments();
            request.setAttribute(ATTR_DOC_LIST, documents);
        }

        getServletContext().getRequestDispatcher("/doc_list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LinkedList<MedicineModel> documents = dbHelper.getAllDocuments();
        request.setAttribute(ATTR_DOC_LIST, documents);

        getServletContext().getRequestDispatcher("/doc_list.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        dbHelper.closeConnection();

        super.destroy();
    }
}