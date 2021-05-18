package web.service.forum.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private Boolean locked = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
    @JsonIgnore
    private List<Topic> topics;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private Report report;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
    @JsonIgnore
    private List<Report> reports;


    public enum Role{
        ANONYMOUS, USER, MODERATOR, ADMIN
    }
    @Enumerated(EnumType.STRING)
    private Role role;
}
