package com.nageshvirkar.pdfgenerator.util;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.nageshvirkar.pdfgenerator.model.PdfGeneratorEntity;
import com.nageshvirkar.pdfgenerator.model.Item;

public class PdfContent {

	public static PdfFont loadFont() throws IOException {
		String fontPath = new ClassPathResource("templates/CascadiaCodeNF.ttf").getPath();
		return PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
	}

	public static void addInvoiceContent(Document document, PdfGeneratorEntity entity) throws IOException {
		PdfFont font = loadFont();

		Table table = new Table(new float[] { 1, 1 });
		table.setWidth(UnitValue.createPercentValue(100));
		table.setHorizontalAlignment(HorizontalAlignment.CENTER);

		table.addCell(createCell("Seller:\n" + entity.getSeller() + "\nGSTIN: " + entity.getSellerGstin() + "\n"
				+ entity.getSellerAddress(), font));

		table.addCell(createCell(
				"Buyer:\n" + entity.getBuyer() + "\nGSTIN: " + entity.getBuyerGstin() + "\n" + entity.getBuyerAddress(),
				font));

		document.add(table);

		Table itemTable = new Table(new float[] { 3, 1, 1, 1 });
		itemTable.setWidth(UnitValue.createPercentValue(100));
		itemTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

		itemTable.addHeaderCell(createHeaderCell("Item", font));
		itemTable.addHeaderCell(createHeaderCell("Quantity", font));
		itemTable.addHeaderCell(createHeaderCell("Rate", font));
		itemTable.addHeaderCell(createHeaderCell("Amount", font));

		for (Item item : entity.getItems()) {
			itemTable.addCell(createCell(item.getName(), font));
			itemTable.addCell(createCell(String.valueOf(item.getQuantity()), font));
			itemTable.addCell(createCell(String.valueOf(item.getRate()), font));
			itemTable.addCell(createCell(String.valueOf(item.getAmount()), font));
		}

		document.add(itemTable);

		Table blankRowTable = new Table(new float[] { 1 });
		blankRowTable.setWidth(UnitValue.createPercentValue(100));
		blankRowTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

		Cell blankCell = new Cell();
		blankCell.add(new Paragraph(""));
		blankCell.setHeight(40);
		blankCell.setPadding(0);
		blankCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		blankCell.setVerticalAlignment(VerticalAlignment.MIDDLE);

		blankRowTable.addCell(blankCell);
		document.add(blankRowTable);
	}

	private static Cell createCell(String content, PdfFont font) {
		Cell cell = new Cell();
		cell.add(new Paragraph(content).setFont(font).setMultipliedLeading(1.0f));
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setPadding(10);
		return cell;
	}

	private static Cell createHeaderCell(String content, PdfFont font) {
		return createCell(content, font);
	}
}
