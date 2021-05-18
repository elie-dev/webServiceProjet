package web.service.forum.entity;

import javax.persistence.*;


@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    public enum ReportReason{
        SPAM, RACISM, NUDITY, OTHER
    }
    @Enumerated(EnumType.STRING)
    private Report.ReportReason reportReason;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


}
