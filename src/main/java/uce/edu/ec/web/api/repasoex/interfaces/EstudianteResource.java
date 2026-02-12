package uce.edu.ec.web.api.repasoex.interfaces;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.ec.web.api.repasoex.application.EstudianteService;
import uce.edu.ec.web.api.repasoex.application.representation.EstudianteRepresentation;
import uce.edu.ec.web.api.repasoex.application.representation.LinkDto;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @Inject
    private UriInfo uriInfo;

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstudiantes() {
        List<EstudianteRepresentation> estudiantes = estudianteService.buscarTodos();
        estudiantes.forEach(estudiante -> construirLinks(estudiante));
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/buscar/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstudiantePorCedula(@PathParam("cedula") String cedula) {

        return Response.ok(construirLinks(estudianteService.buscarPorCedula(cedula))).build();
    }

    @POST
    @Path("/guardar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response guardarEstudiante(EstudianteRepresentation estudiante) {
        this.estudianteService.guardar(estudiante);
        return Response.status(Response.Status.CREATED).entity(estudiante).build(); 
    }

    private EstudianteRepresentation construirLinks(EstudianteRepresentation estudiante) {
        String self = uriInfo.getBaseUriBuilder()
                .path(EstudianteResource.class)
                .path(String.valueOf(estudiante.getId()))
                .build().toString();

        estudiante.setLinks(List.of(new LinkDto("self", self)));
        return estudiante;
    }

}
