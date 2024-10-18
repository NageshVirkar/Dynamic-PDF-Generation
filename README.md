# Dynamic-PDF-Generation
Overview
A Spring Boot application that provides a REST API for generating and downloading dynamic PDFs based on provided data. The application utilizes Java Template Engine to create PDFs and stores them locally for future retrieval.

Features
Generate PDFs from provided data via a REST API.
Download the generated PDFs.
Store generated PDFs locally and retrieve them if the same data is provided again.
Uses Java Template Engine (iText) for PDF generation.

API URL : http://localhost:8085/api/pdf/generate

Request JSON Data : 
{
    "seller": "XYZ Pvt. Ltd.",
    "sellerGstin": "29AABBCCDD121ZD",
    "sellerAddress": "New Delhi, India",
    "buyer": "Vedant Computers",
    "buyerGstin": "29AABBCCDD131ZD",
    "buyerAddress": "New Delhi, India",
    "items": [
        {
            "name": "Product 1",
            "quantity": "12 Nos",
            "rate": 123.00,
            "amount": 1476.00
        }
    ]
}
