/**
 * Интерфейс ReportService предоставляет методы для управления отчетами проектов.
 * Обеспечивает функциональность для сохранения отчетов.
 *
 * @author Даниил
 * @version 1.0
 * @since 2024
 */

package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.model.Project;

import java.util.Set;

public interface ReportService {






    void saveProjectReports(Set<ReportOutDto> reportDto, Project project);
}
