package com.devshowcase.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class FeedbackDtos {
    public record Create(@NotNull @Min(1) @Max(5) Integer rating, @NotBlank @Size(max=1000) String comment) {}
    public record Response(Long id, Integer rating, String comment) {}
    public record ProjectRating(BigDecimal averageRating, long totalFeedbacks) {}
}
