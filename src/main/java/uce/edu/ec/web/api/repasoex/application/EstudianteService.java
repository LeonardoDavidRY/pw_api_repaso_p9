package uce.edu.ec.web.api.repasoex.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.ec.web.api.repasoex.application.representation.EstudianteRepresentation;
import uce.edu.ec.web.api.repasoex.domain.Estudiante;
import uce.edu.ec.web.api.repasoex.infraestucture.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<EstudianteRepresentation> buscarTodos() {
        List<Estudiante> estudiantes = estudianteRepository.listAll();
        return estudiantes.stream()
                .map(this::convertirARepresentation)
                .toList();
    }

    @Transactional
    public void guardar(EstudianteRepresentation estudiante) {
        this.estudianteRepository.persist(convertirAEntidad(estudiante));
    }

    public EstudianteRepresentation buscarPorCedula(String cedula) {
        Estudiante estudiante = estudianteRepository.find("cedula", cedula).firstResult();
        return convertirARepresentation(estudiante);
    }

    public EstudianteRepresentation convertirARepresentation(Estudiante estudiante) {
        EstudianteRepresentation representation = new EstudianteRepresentation();
        representation.setId(estudiante.getId());
        representation.setNombre(estudiante.getNombre());
        representation.setApellido(estudiante.getApellido());
        representation.setCedula(estudiante.getCedula());
        return representation;
    }

    public Estudiante convertirAEntidad(EstudianteRepresentation representation) {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(representation.getId());
        estudiante.setNombre(representation.getNombre());
        estudiante.setApellido(representation.getApellido());
        estudiante.setCedula(representation.getCedula());
        return estudiante;
    }

}
