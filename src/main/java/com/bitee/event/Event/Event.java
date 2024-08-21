package com.bitee.event.Event;


import com.bitee.event.Tag.Tag;
import com.bitee.event.User.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="event")
@DynamicInsert
@DynamicUpdate
public class Event implements Serializable {
    public static final long serialVersionUID = 1L ;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

    @Column(name="type")
    private EventType type;

    @Column(name="location")
    private String location;

    @Column(name="address")
    private String address;

    @Column(name="meeting_link")
    private String meetingLink;

    @Column(name="image")
    private String image;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private BigDecimal price;

    @Column (name="created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createdAt;

    @Column (name="start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;

    @Column (name="end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;


   @OneToMany( cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "event_attendees",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> attendees;
}
