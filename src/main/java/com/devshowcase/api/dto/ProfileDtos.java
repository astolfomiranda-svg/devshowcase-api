package com.devshowcase.api.dto;
import jakarta.validation.constraints.*;
public class ProfileDtos { public record Create(@NotBlank String name,@NotBlank @Email String email,@Size(max=1000) String bio){} public record Response(Long id,String name,String email,String bio){} }
