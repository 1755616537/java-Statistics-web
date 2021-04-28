package cn.miteng.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 类型 1=学生 2=管理员
     */
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 用户名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 性别 1=男 2=女
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 学号
     */
    @Column(name = "StudentID")
    private String studentID;

    /**
     * 专业
     */
    @Column(name = "major")
    private String major;

    /**
     * 班级
     */
    @Column(name = "class")
    private String _Class;

}
