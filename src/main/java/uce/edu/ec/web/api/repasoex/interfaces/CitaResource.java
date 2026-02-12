package uce.edu.ec.web.api.repasoex.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.ec.web.api.repasoex.application.CitaService;
import uce.edu.ec.web.api.repasoex.application.representation.CitaRepresentation;
import uce.edu.ec.web.api.repasoex.application.representation.LinkDto;

@Path("/citas")
public class CitaResource {

    @Inject
    private CitaService citaService;

    @Inject
    private UriInfo uriInfo;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCitas() {
        List<CitaRepresentation> citas = citaService.buscarTodos();
        citas.forEach(cita -> construirLinks(cita));
        return Response.ok(citas).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agendarCita(CitaRepresentation cita) {
        this.citaService.guardar(cita);
        return Response.status(Response.Status.CREATED).entity(cita).build();
    }

    private CitaRepresentation construirLinks(CitaRepresentation cita) {
        String self = uriInfo.getBaseUriBuilder()
                .path(CitaResource.class)
                .path(String.valueOf(cita.getId()))
                .build().toString();

        cita.setLinks(List.of(new LinkDto("self", self)));
        return cita;
    }
}
