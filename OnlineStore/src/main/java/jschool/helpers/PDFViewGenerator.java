package  jschool.helpers;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jschool.dto.ProductDTO;
import org.apache.log4j.Logger;

import org.springframework.web.servlet.view.AbstractView;

abstract class NetJSAbstractViewPDF extends AbstractView {

    NetJSAbstractViewPDF() {
        setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        // Apply preferences and build metadata.
        PdfWriter writer = new PdfWriter(baos);
        Document document = new Document(new PdfDocument(writer), PageSize.A4);
        buildPdfMetadata(model, document, request);

        // Build PDF document.
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        // Flush to HTTP response.
        writeToResponse(response, baos);
    }

    private void buildPdfMetadata(Map<String, Object> model, Document document,
                                    HttpServletRequest request) {
    }

    protected abstract void buildPdfDocument(Map<String, Object> model,
                                             Document document, PdfWriter writer,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws Exception;
}

/**
 * pdf reporter class
 * */
public class PDFViewGenerator extends NetJSAbstractViewPDF {
    private static final Logger logger = Logger.getLogger(PDFViewGenerator.class);

    /**
     * builds pdf report from data
     * @param model model with data, which should be reported
     * @param document pdf document object
     * @param writer pdf writer
     * @param request httprequest
     * @param response httpresponse
     * @throws Exception
     */
    @Override
    protected void buildPdfDocument(Map<String, Object>  model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response){

        try {
            document.add(new Paragraph("Product report for given period").setFontSize(24));
            document.add(new Paragraph("Date: " + LocalDate.now()));
            document.add(new Paragraph("Period: " + model.get("to") + " - " + model.get("from")));

            Map<ProductDTO, Integer> revenueData = (Map<ProductDTO, Integer>) model.get("revenueData");

            Table table = new Table(new float[]{30F, 150F, 150F, 150F, 150F});

            table.addCell("id");
            table.addCell("Product_name");
            table.addCell("Amount");
            table.addCell("Price");
            table.addCell("Total");

            Integer cost = 0;
            for (Map.Entry<ProductDTO, Integer> entry : revenueData.entrySet()) {
                table.addCell(String.valueOf(entry.getKey().getId()));
                table.addCell(entry.getKey().getName());
                table.addCell(String.valueOf(entry.getValue()));
                table.addCell(String.valueOf(entry.getKey().getPrice()));
                table.addCell(String.valueOf(entry.getKey().getPrice() * entry.getValue()));
                cost += entry.getKey().getPrice() * entry.getValue();
            }
            document.add(table);
            document.add(new Paragraph("Total revenue: " + cost));
            logger.info("PDF generated successfully");
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

}
