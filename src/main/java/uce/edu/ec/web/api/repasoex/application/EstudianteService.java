package uce.edu.ec.web.api.repasoex.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.ec.web.api.repasoex.domain.Estudiante;
import uce.edu.ec.web.api.repasoex.infraestucture.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> buscarTodos() {
        return estudianteRepository.listAll();
    }

    public void guardar(Estudiante estudiante) {
        estudianteRepository.persist(estudiante);
    }

    public Estudiante buscarPorCedula(String cedula) {
        return estudianteRepository.find("cedula", cedula).firstResult();
    }

}
