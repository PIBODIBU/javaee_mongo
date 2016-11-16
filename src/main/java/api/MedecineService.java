package api;

import db.DBHelper;
import db.implementation.DBHelperImpl;
import helper.Config;
import model.ErrorModel;
import model.MedicineModel;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

@Path("/documents")
public class MedecineService {
    private static final String PARAM_ACTION = "action";
    private static final String ACTION_DELETE = "action_delete";

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getAllDocuments() {
        DBHelper dbHelper = new DBHelperImpl();
        LinkedList<MedicineModel> models = dbHelper.getAllDocuments();

        return Response.status(200).entity(models).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getDocumentById(@PathParam("id") String docId) {
        DBHelper dbHelper = new DBHelperImpl();
        MedicineModel model = dbHelper.getDocumentById(docId);

        return Response.status(200).entity(model).build();
    }

    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteDocument(@PathParam("id") String docId) {
        DBHelper dbHelper = new DBHelperImpl();
        dbHelper.deleteDocument(docId);

        return Response.status(200).entity(new ErrorModel(false)).build();
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteDocuments(@QueryParam("ids") String ids) {
        DBHelper dbHelper = new DBHelperImpl();

        dbHelper.deleteDocuments(ids.split(","));

        return Response.status(200).entity(new ErrorModel(false)).build();
    }

    @POST
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateDocument(@PathParam("id") String docId,
                                   @FormParam("name") String name,
                                   @FormParam("indication") String indication,
                                   @FormParam("contraindication") String contraindication,
                                   @FormParam("sales_form") String salesForm) {
        DBHelper dbHelper = new DBHelperImpl();
        MedicineModel model = new MedicineModel(new ObjectId(docId), name, indication, contraindication, salesForm);
        dbHelper.updateDocument(docId, model);

        return Response.status(200).entity(model).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addDocument(@FormParam("name") String name,
                                @FormParam("description") String description,
                                @FormParam("indication") String indication,
                                @FormParam("contraindication") String contraindication,
                                @FormParam("sales_form") String salesForm) {
        DBHelper dbHelper = new DBHelperImpl();
        MedicineModel model = new MedicineModel(null, name, indication, contraindication, salesForm);
        dbHelper.addDocument(model);

        return Response.status(200).entity(new ErrorModel(false)).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response searchDocuments(@QueryParam("query") String searchQuery) {
        DBHelper dbHelper = new DBHelperImpl();
        LinkedList<MedicineModel> documents = dbHelper.getAllDocuments();
        LinkedList<MedicineModel> filteredDocuments = new LinkedList<MedicineModel>();

        for (MedicineModel model : documents) {
            if (model.getMedicineName().contains(searchQuery)) {
                filteredDocuments.add(model);
            }
        }

        return Response.status(200).entity(filteredDocuments).build();
    }
}