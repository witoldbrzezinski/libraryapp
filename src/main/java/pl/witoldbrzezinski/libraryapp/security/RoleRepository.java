package pl.witoldbrzezinski.libraryapp.security;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByRole(Role role);
}
