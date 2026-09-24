package com.devshowcase.api.service;

import com.devshowcase.api.dto.*;
import com.devshowcase.api.entity.*;
import com.devshowcase.api.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projects;
    private final ProfileRepository profiles;
    private final TechnologyRepository technologies;
    private final FeedbackRepository feedbacks;

    public ProjectService(ProjectRepository projects, ProfileRepository profiles, TechnologyRepository technologies, FeedbackRepository feedbacks) {
        this.projects=projects; this.profiles=profiles; this.technologies=technologies; this.feedbacks=feedbacks;
    }

    @Transactional
    public ProjectDtos.Response create(ProjectDtos.Create d) {
        Profile profile = profiles.findById(d.profileId()).orElseThrow(() -> new NoSuchElementException("Perfil não encontrado: " + d.profileId()));
        Project p = new Project(); p.setTitle(d.title()); p.setDescription(d.description()); p.setRepositoryUrl(d.repositoryUrl()); p.setProfile(profile);
        if (d.technologyIds()!=null && !d.technologyIds().isEmpty()) {
            List<Technology> found=technologies.findAllById(d.technologyIds());
            if(found.size()!=d.technologyIds().size()) throw new NoSuchElementException("Uma ou mais tecnologias não foram encontradas");
            p.getTechnologies().addAll(found);
        }
        return toResponse(projects.save(p));
    }

    @Transactional(readOnly=true)
    public Page<ProjectDtos.Response> list(String technology, int page, int size) {
        if(page<0) throw new IllegalArgumentException("page deve ser maior ou igual a 0");
        if(size<1 || size>50) throw new IllegalArgumentException("size deve estar entre 1 e 50");
        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());
        return projects.findByTechnology(blankToNull(technology),pageable).map(this::toResponse);
    }

    @Transactional
    public FeedbackDtos.ProjectRating addFeedback(Long projectId, FeedbackDtos.Create d) {
        Project p=find(projectId);
        Feedback f=new Feedback(); f.setRating(d.rating()); f.setComment(d.comment()); f.setProject(p); feedbacks.save(f);
        recalculateRating(p, projectId); projects.save(p);
        return new FeedbackDtos.ProjectRating(p.getAverageRating(), feedbacks.countByProjectId(projectId));
    }

    @Transactional
    public ProjectDtos.Response upvote(Long projectId) {
        Project p=find(projectId); p.setUpvotes(p.getUpvotes()+1); return toResponse(projects.save(p));
    }

    @Transactional(readOnly=true)
    public Project find(Long id){return projects.findById(id).orElseThrow(() -> new NoSuchElementException("Projeto não encontrado: " + id));}

    private void recalculateRating(Project p, Long projectId){
        double avg=Optional.ofNullable(feedbacks.averageRatingByProjectId(projectId)).orElse(0.0);
        p.setAverageRating(BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP));
    }
    private String blankToNull(String v){return v==null || v.isBlank()?null:v.trim();}
    private ProjectDtos.Response toResponse(Project p){
        Set<TechnologyDtos.Response> ts=p.getTechnologies().stream().map(t->new TechnologyDtos.Response(t.getId(),t.getName())).collect(Collectors.toSet());
        return new ProjectDtos.Response(p.getId(),p.getTitle(),p.getDescription(),p.getRepositoryUrl(),p.getProfile().getId(),ts,p.getUpvotes(),p.getAverageRating(),feedbacks.countByProjectId(p.getId()));
    }
}
