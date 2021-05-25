package web.service.forum.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreatedDate
    private Date createdAt = new Date();

    @CreatedDate
    private Date updatedAt = new Date();

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "post")
    @JsonIgnore
    private List<Report> reports;

    public Post(){
    }

    public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("required format");

            LocalDate localDate = null;
            localDate = LocalDate.parse(p.getText(), formatter);

            return localDate;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
