package com.bitee.event.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
   public List<Privilege> findByRoleId(Long roleId);
}
