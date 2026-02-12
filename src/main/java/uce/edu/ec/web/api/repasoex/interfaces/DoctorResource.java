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
import uce.edu.ec.web.api.repasoex.application.DoctorService;
import uce.edu.ec.web.api.repasoex.application.representation.DoctorRepresentation;
import uce.edu.ec.web.api.repasoex.application.representation.LinkDto;

@Path("/doctores")
public class DoctorResource {

    @Inject
    private DoctorService doctorService;

    @Inject
    private UriInfo uriInfo;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDoctores() {
        List<DoctorRepresentation> doctores = doctorService.buscarTodos();
        doctores.forEach(doctor -> construirLinks(doctor));
        return Response.ok(doctores).build();
    }

    @GET
    @Path("/buscar/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDoctorPorCedula(@PathParam("cedula") String cedula) {
        DoctorRepresentation doctor = doctorService.buscarPorCedula(cedula);
        if (doctor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(construirLinks(doctor)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarDoctor(DoctorRepresentation doctor) {
        this.doctorService.guardar(doctor);
        return Response.status(Response.Status.CREATED).entity(doctor).build();
    }

    private DoctorRepresentation construirLinks(DoctorRepresentation doctor) {
        String self = uriInfo.getBaseUriBuilder()
                .path(DoctorResource.class)
                .path(String.valueOf(doctor.getId()))
                .build().toString();

        doctor.setLinks(List.of(new LinkDto("self", self)));
        return doctor;
    }
}
