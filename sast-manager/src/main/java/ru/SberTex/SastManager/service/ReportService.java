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

public interface ReportService {

    /**
     * Сохраняет отчет для проекта.
     *
     * @param reportDto данные отчета в виде DTO
     * @return сохраненный отчет в виде DTO
     */
    ReportOutDto saveProjectReports(ReportDto reportDto);
}
