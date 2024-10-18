package com.nageshvirkar.pdfgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nageshvirkar.pdfgenerator.model.PdfGeneratorEntity;
import com.nageshvirkar.pdfgenerator.service.PdfService;

import java.io.*;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

	@Autowired
	private PdfService pdfService;

	@PostMapping("/generate")
	public ResponseEntity<InputStreamResource> generateOrDownloadPdf(@RequestBody PdfGeneratorEntity entity) {
		try {
			File pdfFile = pdfService.checkIfPdfExists(entity);
			if (pdfFile != null) {
				return createResponseEntity(pdfFile);
			}
			File newPdf = pdfService.generatePdf(entity);
			return createResponseEntity(newPdf);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	private ResponseEntity<InputStreamResource> createResponseEntity(File pdfFile) throws FileNotFoundException {
		InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + pdfFile.getName());
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);
	}
}
