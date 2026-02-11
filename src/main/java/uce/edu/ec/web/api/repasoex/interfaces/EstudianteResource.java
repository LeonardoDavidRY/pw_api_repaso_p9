package uce.edu.ec.web.api.repasoex.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import uce.edu.ec.web.api.repasoex.application.EstudianteService;
import uce.edu.ec.web.api.repasoex.domain.Estudiante;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @GET
    @Path("/todos")
    public List<Estudiante> obtenerEstudiantes() {
        return estudianteService.buscarTodos();
    }

    @GET
    @Path("/buscar/{cedula}")
    public Estudiante obtenerEstudiantePorCedula(@PathParam("cedula") String cedula) {
        return estudianteService.buscarPorCedula(cedula);
    }

    @POST
    @Path("/guardar")
    public void guardarEstudiante(Estudiante estudiante) {
        estudianteService.guardar(estudiante);
    }


}
