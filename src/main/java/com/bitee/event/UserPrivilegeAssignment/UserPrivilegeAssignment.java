package com.bitee.event.UserPrivilegeAssignment;

import com.bitee.event.User.User;
import com.bitee.event.Privilege.Privilege;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserPrivilegeAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userid",insertable = false,updatable = false)
    private User user;
    private Long userid;

    @ManyToOne
    @JoinColumn(name="privilegeid",insertable = false,updatable = false)
    private Privilege privilege;
    private Long privilegeid;
}
