package web.service.forum.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import web.service.forum.entity.Categorie;
import web.service.forum.entity.User;

import java.util.List;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Boolean locked = false;

    @ManyToOne
    private Categorie categorie;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "topic")
    @JsonIgnore
    private List<Post> posts;

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
