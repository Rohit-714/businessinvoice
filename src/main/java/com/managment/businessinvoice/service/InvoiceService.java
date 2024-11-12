package com.managment.businessinvoice.service;

import com.managment.businessinvoice.entity.Invoice;
import com.sendgrid.helpers.mail.objects.Email;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
     Invoice createInvoice(Invoice invoice,Long id);
     List<Invoice> getAllInvoices();
     Optional<Invoice> getInvoiceById(Long id);
     Invoice updateInvoice(Long id, Invoice newInvoiceData);
     boolean deleteInvoice(Long id);
     byte[] generateReport(Long id) throws JRException, IOException;
     String sendWithAttchment(String id,Email from, Email to, String subject);
}
