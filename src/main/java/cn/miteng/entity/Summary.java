package cn.miteng.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "summary")
@Data
public class Summary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "userid")
    private Integer userid;

    @Column(name = "time")
    private Date time;

    /**
     * 课程
     */
    @Column(name = "course")
    private String course;

    /**
     * 分
     */
    @Column(name = "branch")
    private Integer branch;

    /**
     * 评语
     */
    @Column(name = "comment")
    private String comment;

}
