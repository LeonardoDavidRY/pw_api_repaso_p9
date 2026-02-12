package uce.edu.ec.web.api.repasoex.infraestucture;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.ec.web.api.repasoex.domain.Paciente;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository<Paciente> {

}
