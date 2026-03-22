package com.smartcv.builder.service;

import com.smartcv.builder.dto.CVDto;
import com.smartcv.builder.entity.CVDetail;
import com.smartcv.builder.entity.Template;
import com.smartcv.builder.repository.CVDetailRepository;
import com.smartcv.builder.repository.TemplateRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PDFService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private CVDetailRepository cvDetailRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private CVService cvService;

    public byte[] generatePdfFromCv(Long cvId, String userEmail) throws IOException {
        CVDto cvDto = cvService.getCVById(cvId, userEmail);
        Template templateEntity = templateRepository.findById(cvDto.getTemplateId())
                .orElseThrow(() -> new RuntimeException("Template not found"));

        Context context = new Context();
        context.setVariable("cv", cvDto);
        context.setVariable("style", templateEntity.getCssStyle());

        // Process the HTML structure from the database
        // Assuming templateEntity.getHtmlStructure() is a valid Thymeleaf fragment or complete HTML
        String htmlContent = "<html><head><style>" + templateEntity.getCssStyle() + "</style></head><body>" 
                + templateEntity.getHtmlStructure() + "</body></html>";
        
        // However, standard Thymeleaf engine expects templates in file system for fragments
        // We might need to use StringTemplateResolver if we want to store entire templates in DB
        // For simplicity now, let's assume we have physical template files matching template names
        String processedHtml = templateEngine.process("cv-templates/" + templateEntity.getName().toLowerCase(), context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(processedHtml, target, converterProperties);

        return target.toByteArray();
    }
}
