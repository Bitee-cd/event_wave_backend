package com.bitee.event.User;

import com.bitee.event.Event.Event;
import com.bitee.event.UserPrivilegeAssignment.UserPrivilegeAssignment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;

@NamedQuery(name="User.findByUserEmail",query = "SELECT u from User u WHERE email = :email")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="other_names")
    private String otherNames;
    @Column(name="address")
    private String address;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="status")
    private AccountStatus status;
    @Column(name="role")
    private UserRole role;
    @Column(name="phone_number")
    private String phoneNumber;

    @ManyToMany(mappedBy = "attendees")
    private List<Event> eventsAttended;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<UserPrivilegeAssignment> privileges;

}
