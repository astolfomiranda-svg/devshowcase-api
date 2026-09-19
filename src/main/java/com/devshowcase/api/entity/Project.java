package com.devshowcase.api.entity;
import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="projects")
public class Project {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(nullable=false, length=2000) private String description;
 private String repositoryUrl;
 @ManyToOne(optional=false) @JoinColumn(name="profile_id") private Profile profile;
 @ManyToMany @JoinTable(name="project_technology", joinColumns=@JoinColumn(name="project_id"), inverseJoinColumns=@JoinColumn(name="technology_id")) private Set<Technology> technologies=new HashSet<>();
 @OneToMany(mappedBy="project", cascade=CascadeType.ALL, orphanRemoval=true) private List<Feedback> feedbacks=new ArrayList<>();
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getRepositoryUrl(){return repositoryUrl;} public void setRepositoryUrl(String v){repositoryUrl=v;} public Profile getProfile(){return profile;} public void setProfile(Profile v){profile=v;} public Set<Technology> getTechnologies(){return technologies;} public List<Feedback> getFeedbacks(){return feedbacks;}
}
