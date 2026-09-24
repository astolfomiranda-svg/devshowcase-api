package com.devshowcase.api.controller;

import com.devshowcase.api.dto.*;
import com.devshowcase.api.entity.*;
import com.devshowcase.api.repository.*;
import com.devshowcase.api.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@Tag(name = "DevShowcase API")
public class ApiController {
    private final ProfileRepository profiles;
    private final TechnologyRepository technologies;
    private final ProjectService projectService;

    public ApiController(ProfileRepository p, TechnologyRepository t, ProjectService s) {
        profiles=p; technologies=t; projectService=s;
    }

    @PostMapping("/profiles")
    @Operation(summary="Cadastrar perfil")
    public ResponseEntity<ProfileDtos.Response> createProfile(@Valid @RequestBody ProfileDtos.Create d){
        Profile p=new Profile(); p.setName(d.name()); p.setEmail(d.email()); p.setBio(d.bio()); p=profiles.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProfileDtos.Response(p.getId(),p.getName(),p.getEmail(),p.getBio()));
    }

    @GetMapping("/profiles/{id}")
    @Operation(summary="Buscar perfil")
    public ProfileDtos.Response getProfile(@PathVariable Long id){
        Profile p=profiles.findById(id).orElseThrow(()->new NoSuchElementException("Perfil não encontrado: "+id));
        return new ProfileDtos.Response(p.getId(),p.getName(),p.getEmail(),p.getBio());
    }

    @PostMapping("/technologies")
    @Operation(summary="Cadastrar tecnologia")
    public ResponseEntity<TechnologyDtos.Response> createTechnology(@Valid @RequestBody TechnologyDtos.Create d){
        Technology t=new Technology(); t.setName(d.name()); t=technologies.save(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TechnologyDtos.Response(t.getId(),t.getName()));
    }

    @GetMapping("/technologies")
    @Operation(summary="Listar tecnologias")
    public List<TechnologyDtos.Response> listTechnologies(){
        return technologies.findAll().stream().map(t->new TechnologyDtos.Response(t.getId(),t.getName())).toList();
    }

    @PostMapping("/projects")
    @Operation(summary="Cadastrar projeto")
    public ResponseEntity<ProjectDtos.Response> createProject(@Valid @RequestBody ProjectDtos.Create d){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(d));
    }

    @GetMapping("/projects")
    @Operation(summary="Listar projetos com filtro por tecnologia e paginação")
    public Page<ProjectDtos.Response> listProjects(
            @RequestParam(required=false) String technology,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size){
        return projectService.list(technology,page,size);
    }

    @PostMapping("/projects/{id}/feedbacks")
    @Operation(summary="Cadastrar nota e comentário e atualizar média")
    public ResponseEntity<FeedbackDtos.ProjectRating> addFeedback(
            @PathVariable Long id, @Valid @RequestBody FeedbackDtos.Create d){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addFeedback(id,d));
    }

    @PutMapping("/projects/{id}/upvote")
    @Operation(summary="Incrementar curtidas do projeto")
    public ProjectDtos.Response upvote(@PathVariable Long id){
        return projectService.upvote(id);
    }
}
