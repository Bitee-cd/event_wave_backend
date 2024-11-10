package com.bitee.event.UserPrivilegeAssignment;

import com.bitee.event.User.User;
import com.bitee.event.Privilege.Privilege;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property="id")
public class UserPrivilegeAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userid",insertable = false,updatable = false)
    @JsonBackReference
    private User user;
    private Long userid;

    @ManyToOne
    @JoinColumn(name="privilegeid",insertable = false,updatable = false)
    @JsonBackReference
    private Privilege privilege;
    private Long privilegeid;
}
