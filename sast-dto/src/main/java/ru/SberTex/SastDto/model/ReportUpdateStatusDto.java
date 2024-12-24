package ru.SberTex.SastDto.model;

import ru.SberTex.SastDto.enumeration.Status;


public record ReportUpdateStatusDto(Long id,
                                    Status status) {


}
