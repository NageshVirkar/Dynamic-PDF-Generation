package com.nageshvirkar.pdfgenerator.service;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.nageshvirkar.pdfgenerator.model.PdfGeneratorEntity;
import com.nageshvirkar.pdfgenerator.util.PdfContent;

@Service
public class PdfService {

	private static final String PDF_STORAGE_FOLDER = "generated_pdfs";
	private MessageDigest messageDigest;

	public PdfService() throws NoSuchAlgorithmException {
		this.messageDigest = MessageDigest.getInstance("SHA-256");
	}

	private String generateHash(String input) {
		byte[] hash = messageDigest.digest(input.getBytes());
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			hexString.append(String.format("%02x", b));
		}
		return hexString.toString();
	}

	private void ensureDirectoryExists(String directoryPath) {
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	public File checkIfPdfExists(PdfGeneratorEntity entity) {
		String dataHash = generateHash(entity.toString());
		String pdfPath = PDF_STORAGE_FOLDER + File.separator + dataHash + ".pdf";
		File pdfFile = new File(pdfPath);
		return pdfFile.exists() ? pdfFile : null;
	}

	public File generatePdf(PdfGeneratorEntity entity) throws IOException {
		ensureDirectoryExists(PDF_STORAGE_FOLDER);
		String dataHash = generateHash(entity.toString());
		String pdfPath = PDF_STORAGE_FOLDER + File.separator + dataHash + ".pdf";
		File pdfFile = new File(pdfPath);

		if (pdfFile.exists()) {
			return pdfFile;
		}

		try (PdfWriter writer = new PdfWriter(pdfPath);
				PdfDocument pdfDocument = new PdfDocument(writer);
				Document document = new Document(pdfDocument)) {

			document.setFont(PdfContent.loadFont());
			PdfContent.addInvoiceContent(document, entity);
		}

		return pdfFile;
	}
}
