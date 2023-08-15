package constants;

import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Report;

import java.util.List;
public class ReportConstants {

    public static final Report REPORT=new Report(
            1,
            "C:\\Users\\Пользователь\\IdeaProjects\\web-library-hw\\report1.json");
    public static final ReportDTO REPORT_DTO= new ReportDTO(
            "1",
            2L,
            50000,
            100000,
            50000);
    public static final List<ReportDTO> REPORT_DTO_LIST = List.of(
            REPORT_DTO
    );
}