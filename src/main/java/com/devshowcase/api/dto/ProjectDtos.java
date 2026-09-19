package com.devshowcase.api.dto;
import jakarta.validation.constraints.*; import java.util.*;
public class ProjectDtos { public record Create(@NotBlank String title,@NotBlank @Size(max=2000) String description,@Size(max=500) String repositoryUrl,@NotNull Long profileId,Set<Long> technologyIds){} public record Response(Long id,String title,String description,String repositoryUrl,Long profileId,Set<TechnologyDtos.Response> technologies){} }
