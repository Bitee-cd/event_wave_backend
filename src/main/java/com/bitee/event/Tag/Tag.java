package com.bitee.event.Tag;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name="tag")
@DynamicUpdate
@DynamicInsert
public class Tag {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="category")
    private TagCategory category;

}
