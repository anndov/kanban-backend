package com.kanban.repositories.sec;

import com.kanban.domain.sec.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
	
	@Query("select a.role from UserRole a, User b where b.userName=?1 and a.userid=b.userId")
    public List<String> findRoleByUserName(String username);

    public List<UserRole> findByUserid(Long userid);
    long countAllByUseridAndRole(Long userId, String username);
	
}