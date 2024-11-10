package com.bitee.event.Privilege;

import com.bitee.event.Config.Auditable;
import com.bitee.event.Role.Role;
import com.bitee.event.UserPrivilegeAssignment.UserPrivilegeAssignment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property="id")
public class Privilege extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String data;



    @ManyToOne
    @JoinColumn(name="roleid",insertable = false,updatable = false)
    @JsonBackReference
    private Role role;
    private Long roleid;

    @JsonIgnore
    @OneToMany(mappedBy = "privilege")
    private List<UserPrivilegeAssignment> users;
}
