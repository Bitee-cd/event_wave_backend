package com.bitee.event.UserPrivilegeAssignment;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPrivilegeAssignmentRepository extends JpaRepository<UserPrivilegeAssignment,Long> {

    public void deleteByUserId(Long userid);

    public List<UserPrivilegeAssignment> findByUserId(Long userid);

    public List<UserPrivilegeAssignment> findByPrivilegeId(Long privilegeid);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserPrivilegeAssignment u WHERE u.userid = :userId AND u.privilegeid IN :privilegeIds")
    void deleteByUserIdAndPrivilegeIds(Long userId,  List<Long> privilegeIds);

}
