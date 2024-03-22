package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.entity.Invoice;
import com.managment.businessinvoice.response.ResponseHandler;
import com.managment.businessinvoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("{id}")
    public ResponseEntity<Object> createInvoice(@RequestBody Invoice invoice,@PathVariable Long id) {
        Invoice createdInvoice = invoiceService.createInvoice(invoice,id);
        return ResponseHandler.generateResponse("Invoice created successfully", HttpStatus.CREATED, createdInvoice);
    }

    @GetMapping
    public ResponseEntity<Object> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseHandler.generateResponse("All invoices retrieved successfully", HttpStatus.OK, invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            return ResponseHandler.generateResponse("Invoice retrieved successfully", HttpStatus.OK, invoice.get());
        } else {
            return ResponseHandler.generateResponse("Invoice not found", HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInvoice(@PathVariable Long id, @RequestBody Invoice newInvoiceData) {
        Invoice updatedInvoice = invoiceService.updateInvoice(id, newInvoiceData);
        if (updatedInvoice != null) {
            return ResponseHandler.generateResponse("Invoice updated successfully", HttpStatus.OK, updatedInvoice);
        } else {
            return ResponseHandler.generateResponse("Invoice not found", HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvoice(@PathVariable Long id) {
        boolean deleted = invoiceService.deleteInvoice(id);
        if (deleted) {
            return ResponseHandler.generateResponse("Invoice deleted successfully", HttpStatus.NO_CONTENT, null);
        } else {
            return ResponseHandler.generateResponse("Invoice not found", HttpStatus.NOT_FOUND, null);
        }
    }
    @GetMapping("/generateReport/invoice/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable Long id) {
        try {
            byte[] reportBytes = invoiceService.generateReport(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report.pdf");
            headers.setContentLength(reportBytes.length);
            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
