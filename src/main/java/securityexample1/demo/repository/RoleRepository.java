package securityexample1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import securityexample1.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
