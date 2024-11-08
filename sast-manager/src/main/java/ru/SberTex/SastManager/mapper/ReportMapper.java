package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.model.Report;

import java.util.Set;

/**
 * Интерфейс для маппинга объектов класса Report и его DTO.
 *
 * <p>Содержит методы для преобразования сущностей Report в различные представления DTO и обратно.
 * Поле data игнорируется при маппинге ReportDto в Report.</p>
 *
 * @see Report
 * @see ReportDto
 * @see ReportOutDto
 *
 * <p>Основной используемый компонент - Spring.</p>
 *
 * <p>Включает параметры для игнорирования нецелевых полей.</p>
 *
 * @version 1.0
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
    @Mapping(target = "data", ignore = true)
    Report toReport(ReportDto report);

    /**
     * Преобразует объект Report в ReportOutDto.
     *
     * @param report отчет для преобразования
     * @return объект ReportOutDto
     */
    ReportOutDto toReportOutDto(Report report);

    /**
     * Преобразует множество объектов Report в множество ReportOutDto.
     *
     * @param reports отчеты для преобразования
     * @return множество ReportOutDto
     */
    Set<ReportOutDto> toSetReportOutDto(Set<Report> reports);
}
