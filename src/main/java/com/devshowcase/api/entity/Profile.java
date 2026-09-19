package com.devshowcase.api.entity;
import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="profiles")
public class Profile {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false, unique=true) private String email;
 private String bio;
 @OneToMany(mappedBy="profile", cascade=CascadeType.ALL, orphanRemoval=true) private List<Project> projects=new ArrayList<>();
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getBio(){return bio;} public void setBio(String v){bio=v;} public List<Project> getProjects(){return projects;}
}
