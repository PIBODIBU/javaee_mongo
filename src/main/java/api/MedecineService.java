package api;

import db.DBHelper;
import db.implementation.DBHelperImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/documents")
public class MedecineService {
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getAllDocuments() {
        DBHelper dbHelper = new DBHelperImpl();
        return Response.status(200).entity(dbHelper.getAllDocuments()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getDocumentById(@PathParam("id") String id) {
        DBHelper dbHelper = new DBHelperImpl();
        return Response.status(200).entity(dbHelper.getDocumentById(id)).build();
    }
}