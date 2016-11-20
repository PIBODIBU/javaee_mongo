package api;

import helper.Config;
import model.ServerInfoModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("server")
public class ServerService {
    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getAllDocuments() {
        ServerInfoModel model = new ServerInfoModel(
                Config.DATABASE_HOST,
                String.valueOf(Config.DATABASE_PORT),
                Config.DATABASE_NAME,
                Config.COLLECTION_NAME
        );

        return Response.status(200).entity(model).build();
    }
}
