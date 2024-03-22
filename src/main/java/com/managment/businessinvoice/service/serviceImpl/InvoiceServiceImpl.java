package com.managment.businessinvoice.service.serviceImpl;

import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.entity.Invoice;
import com.managment.businessinvoice.entity.Order;
import com.managment.businessinvoice.entity.Product;
import com.managment.businessinvoice.exception.CustomerNotFoundException;
import com.managment.businessinvoice.repository.CustomerRepository;
import com.managment.businessinvoice.repository.InvoiceRepository;
import com.managment.businessinvoice.repository.OrderRepository;
import com.managment.businessinvoice.repository.ProductRepository;
import com.managment.businessinvoice.service.InvoiceService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Invoice createInvoice(Invoice invoice, Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            invoice.setCustomer(customer);
            return invoiceRepository.save(invoice);
        }
        throw new CustomerNotFoundException();
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice newInvoiceData) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setInvoiceDate(newInvoiceData.getInvoiceDate());
            invoice.setTotalAmount(newInvoiceData.getTotalAmount());
            return invoiceRepository.save(invoice);
        }).orElse(null);
    }

    @Override
    public boolean deleteInvoice(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public void generakteReports() {
        try {
            List<Product> products = new ArrayList<>();
            products.add(new Product(1L, "Product A", "Brand A", 10L, 100));
            products.add(new Product(2L, "Product B", "Brand B", 20L, 2000));

            // Load JRXML file
          //  InputStream inputStream = ReportGenerator.class.getResourceAsStream("/path/to/your/jrxml/file.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport("C:/Users/Dell/JaspersoftWorkspace/MyReports/Blank_A4_1.jrxml");

            // Convert data source to JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);

            // Set parameters for the report (if any)
            Map<String, Object> parameters = new HashMap<>();
            // Add parameters as needed

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export report to PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, "output.pdf");

            System.out.println("Report generated successfully.");

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    @Override
    public byte[] generateReport(Long id) throws JRException, IOException {
        // Step 2: Compile JRXML file
        File f= ResourceUtils.getFile("classpath:Blank_A4_1.jrxml");
        URL resourceUrl = f.toURI().toURL();
        JasperReport jasperReport = JasperCompileManager.compileReport(resourceUrl.openStream());

        Optional<Order> order = orderRepository.findById(id);
        Order getOrder=order.get();

        List<Product> products = getOrder.getProducts();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("order", getOrder);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        // Step 3: Export the report to PDF
        return pdfBytes;
    }
}
