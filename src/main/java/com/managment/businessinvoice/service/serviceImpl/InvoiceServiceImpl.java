package com.managment.businessinvoice.service.serviceImpl;

import com.managment.businessinvoice.dto.ProductsDto;
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
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
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
    /*public void generakteReports() {
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
    }*/
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
    String apiKey=null;
    public String sendWithAttchment(String id,Email from, Email to, String subject) {
        Optional<Order> order = orderRepository.findById(Long.parseLong(id));
        if(order.isPresent()) {
        Content content = new Content("text/html", "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      background-color: #f4f4f4;\n" +
                "      margin: 0;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "    .email-container {\n" +
                "      max-width: 600px;\n" +
                "      background-color: #fff;\n" +
                "      margin: 0 auto;\n" +
                "      padding: 20px;\n" +
                "      border: 1px solid #ddd;\n" +
                "      border-radius: 8px;\n" +
                "      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\n" +
                "    }\n" +
                "    h1 {\n" +
                "      color: #2c3e50;\n" +
                "      font-size: 24px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    p {\n" +
                "      font-size: 16px;\n" +
                "      color: #555;\n" +
                "      line-height: 1.5;\n" +
                "      margin: 10px 0;\n" +
                "    }\n" +
                "    .footer {\n" +
                "      text-align: center;\n" +
                "      padding: 10px 0;\n" +
                "      font-size: 12px;\n" +
                "      color: #888;\n" +
                "    }\n" +
                "    .button {\n" +
                "      display: inline-block;\n" +
                "      padding: 10px 20px;\n" +
                "      margin: 20px 0;\n" +
                "      background-color: #2c3e50;\n" +
                "      color: white;\n" +
                "      text-decoration: none;\n" +
                "      border-radius: 5px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"email-container\">\n" +
                "  <h1>Thank you for your business!</h1>\n" +
                "    <p>Dear "+order.get().getCustomer().getName() +",</p>\n" +
                "    <p>We appreciate your continued support. Please find your invoice attached to this email. If you have any questions or require further assistance, feel free to reach out to us.</p>\n" +
                "    <p>We look forward to serving you again!</p>\n" +
                "    <p>Best regards,<br>[D-MART]</p>\n" +
                "    <div class=\"footer\">\n" +
                "      &copy; [2024] [D-MART]. All rights reserved.\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n");

            String customerEmail = order.get().getCustomer().getEmail();
            from.setEmail("rpathare332@gmail.com");
            to.setEmail(customerEmail);

        String fileContent = "";
        try {
            byte[] fileBytes =generateReport(Long.parseLong(id));
            fileContent = Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException ex) {
            return "Error reading file: " + ex.getMessage();


        } catch (JRException e) {
            throw new RuntimeException(e);
        }
            Attachments attachments = new Attachments();
        attachments.setFilename("Invoice");
        attachments.setType("application/pdf");
        attachments.setContent(fileContent);

            Mail mail = new Mail(from, subject, to, content);
            mail.addAttachments(attachments);


            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                return "Mail sent successfully" + response.getBody();


            } catch (IOException ex) {
                return "Error sending email: " + ex.getMessage();
            }
        }
        throw new CustomerNotFoundException("No customer found for this Invoice");
    }
}
