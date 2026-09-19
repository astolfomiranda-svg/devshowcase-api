package com.devshowcase.api.entity;
import jakarta.persistence.*;
@Entity @Table(name="feedbacks")
public class Feedback { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String comment; private Integer rating; @ManyToOne(optional=false) @JoinColumn(name="project_id") private Project project; public Long getId(){return id;} public String getComment(){return comment;} public void setComment(String v){comment=v;} public Integer getRating(){return rating;} public void setRating(Integer v){rating=v;} public Project getProject(){return project;} public void setProject(Project v){project=v;} }
