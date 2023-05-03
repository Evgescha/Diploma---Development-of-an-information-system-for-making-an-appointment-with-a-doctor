package com.hescha.medicalappointment.controller;

import com.hescha.medicalappointment.model.Contact;
import com.hescha.medicalappointment.model.ContactStatus;
import com.hescha.medicalappointment.model.SecurityService;
import com.hescha.medicalappointment.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.hescha.medicalappointment.model.ContactStatus.DONE;
import static com.hescha.medicalappointment.model.ContactStatus.NEW;


@Controller
@RequiredArgsConstructor
@RequestMapping(ContactController.CURRENT_ADDRESS)
public class ContactController {
    public static final String API_PATH = "contact";
    public static final String CURRENT_ADDRESS = "/" + API_PATH;
    public static final String MESSAGE = "message";
    public static final String THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE = API_PATH;
    public static final String REDIRECT_TO_ALL_ITEMS = "redirect:" + CURRENT_ADDRESS;

    private final ContactService service;
    private final SecurityService securityService;


    @GetMapping
    public String readAll(Model model) {
        model.addAttribute("list", service.readAll());
        model.addAttribute("user", securityService.getLoggedIn());
        return THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE;
    }

    @PostMapping
    public String save(@ModelAttribute Contact entity, RedirectAttributes ra) {
        try {
            service.create(entity);
            ra.addFlashAttribute(MESSAGE, "Creating is successful");
        } catch (Exception e) {
            ra.addFlashAttribute(MESSAGE, "Creating failed");
            e.printStackTrace();
        }
        return REDIRECT_TO_ALL_ITEMS;
    }

    @PostMapping("changeStatus")
    public String save(@RequestParam Integer contactId, RedirectAttributes ra) {
        try {
            Contact read = service.read(contactId);
            ContactStatus contactStatus = read.getContactStatus();
            ContactStatus newStatus = contactStatus == NEW ? DONE : NEW;
            read.setContactStatus(newStatus);
            service.update(read);
            ra.addFlashAttribute(MESSAGE, "Status changing is successful");
        } catch (Exception e) {
            ra.addFlashAttribute(MESSAGE, "Status changing failed");
            e.printStackTrace();
        }
        return REDIRECT_TO_ALL_ITEMS;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute(MESSAGE, "Removing is successful");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute(MESSAGE, "Removing failed");
        }
        return REDIRECT_TO_ALL_ITEMS;
    }
}
