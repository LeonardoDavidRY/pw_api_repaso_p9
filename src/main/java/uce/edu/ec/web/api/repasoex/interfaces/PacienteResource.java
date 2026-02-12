package uce.edu.ec.web.api.repasoex.interfaces;

import java.util.List;

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
import uce.edu.ec.web.api.repasoex.application.PacienteService;
import uce.edu.ec.web.api.repasoex.application.representation.LinkDto;
import uce.edu.ec.web.api.repasoex.application.representation.PacienteRepresentation;

@Path("/pacientes")
public class PacienteResource {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private UriInfo uriInfo;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPacientes() {
        List<PacienteRepresentation> pacientes = pacienteService.buscarTodos();
        pacientes.forEach(paciente -> construirLinks(paciente));
        return Response.ok(pacientes).build();
    }

    @GET
    @Path("/buscar/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPacientePorCedula(@PathParam("cedula") String cedula) {
        PacienteRepresentation paciente = pacienteService.buscarPorCedula(cedula);
        if (paciente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(construirLinks(paciente)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarPaciente(PacienteRepresentation paciente) {
        this.pacienteService.guardar(paciente);
        return Response.status(Response.Status.CREATED).entity(paciente).build();
    }

    private PacienteRepresentation construirLinks(PacienteRepresentation paciente) {
        String self = uriInfo.getBaseUriBuilder()
                .path(PacienteResource.class)
                .path(String.valueOf(paciente.getId()))
                .build().toString();

        paciente.setLinks(List.of(new LinkDto("self", self)));
        return paciente;
    }
}
