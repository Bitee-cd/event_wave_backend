package com.bitee.event.Role;

import com.bitee.event.Config.Auditable;
import com.bitee.event.Privilege.Privilege;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String description;
    public String data;

    @OneToMany(mappedBy = "role")
    private List<Privilege> privileges;
}
