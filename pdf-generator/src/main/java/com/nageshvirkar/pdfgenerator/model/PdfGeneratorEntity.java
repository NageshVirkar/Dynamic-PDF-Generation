package com.nageshvirkar.pdfgenerator.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PdfGeneratorEntity {

	private String seller;
	private String sellerGstin;
	private String sellerAddress;
	private String buyer;
	private String buyerGstin;
	private String buyerAddress;
	private List<Item> items;

	public PdfGeneratorEntity() {
		this.items = new ArrayList<>();
	}
}
