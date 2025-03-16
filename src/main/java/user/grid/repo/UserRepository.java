package user.grid.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.grid.model.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
	@EntityGraph(attributePaths = { "hair", "address", "bank", "company", "crypto" })
	List<UserData> findByRole(String role);

	@EntityGraph(attributePaths = { "hair", "address", "bank", "company", "crypto" })
	List<UserData> findByAgeGreaterThanEqual(Integer age);

	@EntityGraph(attributePaths = { "hair", "address", "bank", "company", "crypto" })
	List<UserData> findAllByOrderByAgeDesc();

	@EntityGraph(attributePaths = { "hair", "address", "bank", "company", "crypto" })
	List<UserData> findAllByOrderByAgeAsc();

	@EntityGraph(attributePaths = { "hair", "address", "bank", "company", "crypto" })
	Optional<UserData> findById(Long id);

	@EntityGraph(attributePaths = { "hair", "address", "bank", "company", "crypto" })
	Optional<UserData> findBySsn(String ssn);
}
