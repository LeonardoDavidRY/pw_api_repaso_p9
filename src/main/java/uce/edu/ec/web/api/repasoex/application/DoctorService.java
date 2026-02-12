package uce.edu.ec.web.api.repasoex.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.ec.web.api.repasoex.application.representation.DoctorRepresentation;
import uce.edu.ec.web.api.repasoex.domain.Doctor;
import uce.edu.ec.web.api.repasoex.infraestucture.DoctorRepository;

@ApplicationScoped
public class DoctorService {

    @Inject
    private DoctorRepository doctorRepository;

    public List<DoctorRepresentation> buscarTodos() {
        List<Doctor> doctores = doctorRepository.listAll();
        return doctores.stream()
                .map(this::convertirARepresentation)
                .toList();
    }

    @Transactional
    public void guardar(DoctorRepresentation doctor) {
        this.doctorRepository.persist(convertirAEntidad(doctor));
    }

    public DoctorRepresentation buscarPorCedula(String cedula) {
        Doctor doctor = doctorRepository.find("cedula", cedula).firstResult();
        return convertirARepresentation(doctor);
    }

    public DoctorRepresentation convertirARepresentation(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        DoctorRepresentation representation = new DoctorRepresentation();
        representation.setId(doctor.getId());
        representation.setCedula(doctor.getCedula());
        representation.setNombre(doctor.getNombre());
        representation.setApellido(doctor.getApellido());
        representation.setGenero(doctor.getGenero());
        return representation;
    }

    public Doctor convertirAEntidad(DoctorRepresentation representation) {
        Doctor doctor = new Doctor();
        doctor.setId(representation.getId());
        doctor.setCedula(representation.getCedula());
        doctor.setNombre(representation.getNombre());
        doctor.setApellido(representation.getApellido());
        doctor.setGenero(representation.getGenero());
        return doctor;
    }
}
