package ru.SberTex.SastDto.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.SberTex.SastDto.enumeration.Status;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    Long id;
    @NotNull
    String content;
    @NotNull
    Long projectId;
    Status status;

}
