package uce.edu.ec.web.api.repasoex.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.ec.web.api.repasoex.application.representation.CitaRepresentation;
import uce.edu.ec.web.api.repasoex.domain.Cita;
import uce.edu.ec.web.api.repasoex.domain.Doctor;
import uce.edu.ec.web.api.repasoex.domain.Paciente;
import uce.edu.ec.web.api.repasoex.infraestucture.CitaRepository;
import uce.edu.ec.web.api.repasoex.infraestucture.DoctorRepository;
import uce.edu.ec.web.api.repasoex.infraestucture.PacienteRepository;

@ApplicationScoped
public class CitaService {

    @Inject
    private CitaRepository citaRepository;

    @Inject
    private DoctorRepository doctorRepository;

    @Inject
    private PacienteRepository pacienteRepository;

    public List<CitaRepresentation> buscarTodos() {
        List<Cita> citas = citaRepository.listAll();
        return citas.stream()
                .map(this::convertirARepresentation)
                .toList();
    }

    @Transactional
    public void guardar(CitaRepresentation cita) {
        Cita entidad = convertirAEntidad(cita);
        this.citaRepository.persist(entidad);
    }

    public CitaRepresentation convertirARepresentation(Cita cita) {
        if (cita == null) {
            return null;
        }
        CitaRepresentation representation = new CitaRepresentation();
        representation.setId(cita.getId());
        representation.setFechaHora(cita.getFechaHora());
        
        if (cita.getDoctor() != null) {
            representation.setCedulaDoctor(cita.getDoctor().getCedula());
            representation.setNombreDoctor(cita.getDoctor().getNombre());
            representation.setApellidoDoctor(cita.getDoctor().getApellido());
        }
        
        if (cita.getPaciente() != null) {
            representation.setCedulaPaciente(cita.getPaciente().getCedula());
            representation.setNombrePaciente(cita.getPaciente().getNombre());
            representation.setApellidoPaciente(cita.getPaciente().getApellido());
        }
        
        return representation;
    }

    public Cita convertirAEntidad(CitaRepresentation representation) {
        Cita cita = new Cita();
        cita.setId(representation.getId());
        cita.setFechaHora(representation.getFechaHora());
        
        // Buscar doctor por cédula
        Doctor doctor = doctorRepository.find("cedula", representation.getCedulaDoctor()).firstResult();
        if (doctor != null) {
            cita.setDoctor(doctor);
        }
        
        // Buscar paciente por cédula
        Paciente paciente = pacienteRepository.find("cedula", representation.getCedulaPaciente()).firstResult();
        if (paciente != null) {
            cita.setPaciente(paciente);
        }
        
        return cita;
    }
}
