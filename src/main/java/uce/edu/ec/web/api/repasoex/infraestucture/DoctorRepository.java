package uce.edu.ec.web.api.repasoex.infraestucture;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.ec.web.api.repasoex.domain.Doctor;

@ApplicationScoped
public class DoctorRepository implements PanacheRepository<Doctor> {

}
