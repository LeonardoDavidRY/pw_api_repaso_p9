package uce.edu.ec.web.api.repasoex.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.ec.web.api.repasoex.application.representation.PacienteRepresentation;
import uce.edu.ec.web.api.repasoex.domain.Paciente;
import uce.edu.ec.web.api.repasoex.infraestucture.PacienteRepository;

@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteRepository pacienteRepository;

    public List<PacienteRepresentation> buscarTodos() {
        List<Paciente> pacientes = pacienteRepository.listAll();
        return pacientes.stream()
                .map(this::convertirARepresentation)
                .toList();
    }

    @Transactional
    public void guardar(PacienteRepresentation paciente) {
        this.pacienteRepository.persist(convertirAEntidad(paciente));
    }

    public PacienteRepresentation buscarPorCedula(String cedula) {
        Paciente paciente = pacienteRepository.find("cedula", cedula).firstResult();
        return convertirARepresentation(paciente);
    }

    public PacienteRepresentation convertirARepresentation(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        PacienteRepresentation representation = new PacienteRepresentation();
        representation.setId(paciente.getId());
        representation.setCedula(paciente.getCedula());
        representation.setNombre(paciente.getNombre());
        representation.setApellido(paciente.getApellido());
        representation.setFechaNacimiento(paciente.getFechaNacimiento());
        return representation;
    }

    public Paciente convertirAEntidad(PacienteRepresentation representation) {
        Paciente paciente = new Paciente();
        paciente.setId(representation.getId());
        paciente.setCedula(representation.getCedula());
        paciente.setNombre(representation.getNombre());
        paciente.setApellido(representation.getApellido());
        paciente.setFechaNacimiento(representation.getFechaNacimiento());
        return paciente;
    }
}
