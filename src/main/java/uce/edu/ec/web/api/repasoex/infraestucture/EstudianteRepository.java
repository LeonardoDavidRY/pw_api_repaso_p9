package uce.edu.ec.web.api.repasoex.infraestucture;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.ec.web.api.repasoex.domain.Estudiante;

@ApplicationScoped
public class EstudianteRepository  implements PanacheRepository<Estudiante> {

}
