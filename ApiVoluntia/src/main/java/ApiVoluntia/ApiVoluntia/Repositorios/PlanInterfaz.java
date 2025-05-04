package ApiVoluntia.ApiVoluntia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiVoluntia.ApiVoluntia.Dtos.PlanDtos;

public interface PlanInterfaz extends JpaRepository<PlanDtos, Long> {

	PlanDtos findByIdPlan(long id_plan);

	void deleteByIdPlan(long id);

}
