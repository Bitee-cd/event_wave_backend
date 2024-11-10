package com.bitee.event.Privilege;

import com.bitee.event.Config.Auditable;
import com.bitee.event.UserPrivilegeAssignment.UserPrivilegeAssignment;
import com.bitee.event.User.User;
import com.bitee.event.Role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Privilege extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name="userid",insertable = false,updatable = false)
    private User user;
    private Long userid;

    @ManyToOne
    @JoinColumn(name="roleid",insertable = false,updatable = false)
    private Role role;
    private Long roleid;

    @OneToMany(mappedBy = "privilege")
    private List<UserPrivilegeAssignment> users;
}
