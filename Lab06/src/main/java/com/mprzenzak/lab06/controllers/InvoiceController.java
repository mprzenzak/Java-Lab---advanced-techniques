package com.mprzenzak.lab06.controllers;

import com.mprzenzak.lab06.models.Invoice;
import com.mprzenzak.lab06.models.InvoiceWrapper;
import com.mprzenzak.lab06.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/invoices")
    public String showInvoices(Model model) {
        List<Invoice> invoices = invoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        return "invoices";
    }

    @PostMapping("/invoices/save")
    public String saveInvoices(@ModelAttribute("invoicesWrapper") InvoiceWrapper updatedInvoicesWrapper) {
        List<Invoice> updatedInvoices = updatedInvoicesWrapper.getInvoices();
        for (Invoice updatedInvoice : updatedInvoices) {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(updatedInvoice.getId());
            if (invoiceOptional.isPresent()) {
                Invoice invoice = invoiceOptional.get();
                invoice.setAmount(updatedInvoice.getAmount());
                invoice.setPaymentDate(updatedInvoice.getPaymentDate());
                invoice.setInstallation(updatedInvoice.getInstallation());
                invoiceRepository.save(invoice);
            }
        }
        return "redirect:/invoices";
    }

    @ModelAttribute("invoicesWrapper")
    public InvoiceWrapper invoicesWrapper() {
        InvoiceWrapper invoiceWrapper = new InvoiceWrapper();
        invoiceWrapper.setInvoices(invoiceRepository.findAll());
        return invoiceWrapper;
    }

}
