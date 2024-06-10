package sw1.hosp;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PDF_printer {

    public static void printPDF(Patient patient, Date date) throws SQLException {



        String patient_name = patient.getName();
        String patient_callNUM = patient.getContact();

        MedicalRecord medicalRecords = DatabaseUtil.getMedicalRecordsByPatientIdAndDate(patient.getId(), date);

        int Dr_id = medicalRecords.getEmployeeId();
        int Md_id = medicalRecords.getMedicalRecordId();

        String diagnosis_name = DatabaseUtil.searchDiagnosis(Md_id);
        String docter_name = DatabaseUtil.searchDrName(Dr_id);
        String medication_name = DatabaseUtil.searchMedicationName(Md_id);
        String medication_dosage = DatabaseUtil.searchMedicationDosage(Md_id);
        //String medication_description = "";

        String htmlContent = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\"></meta>" +
                "<title>처방전</title>" +
                "<style>" +
                "@font-face {" +
                "  font-family: 'Noto Sans KR';" +
                "  src: url('file:src/main/resources/NotoSansKR-Regular.ttf');" +
                "}" +
                "body {" +
                "  font-family: 'Noto Sans KR';" +
                "  margin: 0;" +
                "  padding: 20px;" +
                "}" +
                ".info-table {" +
                "  border-collapse: collapse;" +
                "  width: 100%;" +
                "  max-width: 800px;" +
                "  margin: 0 auto;" +
                "  border: 1px solid #000;" +
                "}" +
                ".info-table th, .info-table td {" +
                "  border: 1px solid #000;" +
                "  padding: 8px;" +
                "  text-align: center;" +
                "  font-size: 12px;" +
                "}" +
                ".info-table th {" +
                "  background-color: #f2f2f2;" +
                "  font-weight: bold;" +
                "}" +
                ".info-table .rowspan {" +
                "  text-align: center;" +
                "  font-weight: bold;" +
                "}" +
                ".info-table .medical-header {" +
                "  text-align: center;" +
                "  font-weight: bold;" +
                "}" +
                ".info-table .cell-large {" +
                "  width: 200px;" +
                "  height: 10px;" +
                "}" +
                ".info-table .cell-small {" +
                "  width: 50px;" +
                "  height: 10px;" +
                "}" +
                "pre {" +
                "  font-family: 'Noto Sans KR';" +
                "  src: url('file:src/main/resources/NotoSansKR-Regular.ttf');" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1 style=\"text-align: center;\">처 방 전</h1>" +
                "<table class=\"info-table\">" +
                "    <tr>" +
                "        <th class=\"rowspan cell-small\" rowspan=\"2\">환자정보</th>" +
                "        <th class=\"cell-small\">이름</th>" +
                "        <td class=\"cell-large\">" + patient_name + "</td>" +
                "        <th class=\"medical-header cell-small\" rowspan=\"2\">의료기관</th>" +
                "        <th class=\"cell-small\">명칭</th>" +
                "        <td class=\"cell-large\">1조 의료 병원</td>" +
                "    </tr>" +
                "    <tr>" +
                "        <th class=\"cell-small\">전화번호</th>" +
                "        <td class=\"cell-large\">" + patient_callNUM + "</td>" +
                "        <th class=\"cell-small\">전화번호</th>" +
                "        <td class=\"cell-large\">031-8282-7676</td>" +
                "    </tr>" +
                "</table>" +
                "<table class=\"info-table\">" +
                "    <tr>" +
                "        <th class=\"cell-small\">진단명</th>" +
                "        <td class=\"cell-large\">" + diagnosis_name + "</td>" +
                "        <th class=\"cell-small\">의사 이름</th>" +
                "        <td class=\"cell-large\">" + docter_name + "</td>" +
                "    </tr>" +
                "</table>" +
                "<table class=\"info-table\">" +
                "    <tr>" +
                "        <th class=\"cell-large\">처방 의약품의 명칭</th>" +
                "        <th class=\"cell-small\">투약량</th>" +
                "        <th class=\"cell-small\">용법</th>" +
                "    </tr>" +
                "    <tr>" +
                "        <td style=\"text-align: center; vertical-align: top; font-size:12px; height: 400px;\">" +
                "<pre>" +
                medication_name +
                "</pre>" +
                "        </td>" +
                "        <td style=\"text-align: center; vertical-align: top; font-size:12px;\">" +
                "<pre>" +
                medication_dosage +
                "</pre>" +
                "        </td>" +
                "        <td style=\"text-align: center; vertical-align: top; font-size:12px;\">" +
                //medication_description +
                "        </td>" +
                "    </tr>" +
                "</table>" +
                "<p style=\"text-align: center; font-size:12px;\">사용기간 : 교부일로 부터 (___)일간 사용기간 내에 약국에 제출해야 합니다.</p>" +
                "<table class=\"info-table\">" +
                "    <tr>" +
                "        <th class=\"rowspan cell-small\" rowspan=\"3\">제조내역</th>" +
                "        <th class=\"cell-small\">제조기관</th>" +
                "        <td class=\"cell-large\"></td>" +
                "        <td style=\"width: 50px; font-size:8px;\">처방의 변경, 수령, 확인, 대체시 그 내용 등</td>" +
                "    </tr>" +
                "    <tr>" +
                "        <th class=\"cell-small\">제조약사</th>" +
                "        <td class=\"cell-large\"></td>" +
                "    </tr>" +
                "    <tr>" +
                "        <th class=\"cell-small\">조제날자</th>" +
                "        <td class=\"cell-large\"></td>" +
                "    </tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        String outputPath = "patient_info_"+ patient_name +".pdf";
        String fontPath = "src/main/resources/NotoSansKR-Regular.ttf"; // 폰트 파일 경로

        try {
            convertHtmlToPdf(htmlContent, outputPath, fontPath);
            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertHtmlToPdf(String htmlContent, String outputPath, String fontPath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputPath)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.useFont(new java.io.File(fontPath), "Noto Sans KR");
            builder.toStream(os);
            builder.run();
        }
    }
}
