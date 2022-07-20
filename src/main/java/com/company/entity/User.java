package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "find" , query = "select u from User u left join fetch u.nationalityId left join fetch u.birthPlaceId where u.id = :id"),
        @NamedQuery(name="like" , query = "select u from User u where u.name like '%:param%' ")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password ;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_date")
    @Temporal(TemporalType.DATE)
    private Date birthOfDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "nationality_id")
    private Country nationalityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_place_id")
    private Country birthPlaceId;

    @OneToMany(mappedBy = "user")
    private List<EmploymentHistory> employmentHistorys;

    @OneToMany(mappedBy = "user")
    private List<UserSkill> userSkillList;


    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String surname, String email,String phone , String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    public User(int id,String name,String surname,String email,String phone ,Date birthOfDate ,Country  nationalityId ,Country birthPlaceId,String password ) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthOfDate = birthOfDate;
        this.nationalityId = nationalityId;
        this.birthPlaceId = birthPlaceId;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surname + '\'' +
                ", email='" + email + '\'' +
        //    ", c='" + nationalityId + '\'' +
          //      ", n='" + birthPlaceId + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +              '}';
    }




}
