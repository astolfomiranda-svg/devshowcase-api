package com.devshowcase.api.dto;
import jakarta.validation.constraints.*;
public class TechnologyDtos { public record Create(@NotBlank @Size(max=80) String name){} public record Response(Long id,String name){} }
