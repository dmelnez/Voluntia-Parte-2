package ApiVoluntia.ApiVoluntia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiVoluntia.ApiVoluntia.Dtos.ContratoDtos;

public interface ContratoInterfaz extends JpaRepository<ContratoDtos, Long> {

}
