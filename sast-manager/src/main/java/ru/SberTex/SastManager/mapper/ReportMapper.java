package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.model.Report;
import ru.SberTex.SastManager.model.User;

import java.util.Set;

/**
 * Интерфейс для маппинга объектов класса Report и его DTO.
 *
 * <p>Содержит методы для преобразования сущностей Report в различные представления DTO и обратно.
 * Поле data игнорируется при маппинге ReportDto в Report.</p>
 *
 * @version 1.0
 * @see Report
 * @see ReportDto
 * @see ReportOutDto
 *
 * <p>Основной используемый компонент - Spring.</p>
 *
 * <p>Включает параметры для игнорирования нецелевых полей.</p>
 * @since 2024
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    /**
     * Преобразует объект ReportDto в Report, игнорируя поле data.
     *
     * @param report DTO отчета для преобразования
     * @return объект Report
     */
    Report toReport(ReportDto report);

    /**
     * Преобразует объект Report в ReportOutDto.
     *
     * @param report отчет для преобразования
     * @return объект ReportOutDto
     */

    ReportOutDto toReportOutDto(Report report);

    @Mapping(target = "project",ignore = true)
    Report toReport(ReportOutDto report);



}
