package com.hescha.medicalappointment.controller;

import com.hescha.medicalappointment.model.Appointment;
import com.hescha.medicalappointment.model.AppointmentStatus;
import com.hescha.medicalappointment.model.Category;
import com.hescha.medicalappointment.model.SecurityService;
import com.hescha.medicalappointment.model.TimeSlot;
import com.hescha.medicalappointment.model.User;
import com.hescha.medicalappointment.service.AppointmentService;
import com.hescha.medicalappointment.service.CategoryService;
import com.hescha.medicalappointment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping(AppointmentController.CURRENT_ADDRESS)
public class AppointmentController {
    public static final String API_PATH = "appointment";
    public static final String CURRENT_ADDRESS = "/" + API_PATH;
    public static final String MESSAGE = "message";
    public static final String THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE = API_PATH;
    public static final String THYMELEAF_TEMPLATE_ONE_ITEM_PAGE = THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE + "-one";
    public static final String THYMELEAF_TEMPLATE_EDIT_PAGE = THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE + "-edit";
    public static final String REDIRECT_TO_ALL_ITEMS = "redirect:" + CURRENT_ADDRESS;

    private final AppointmentService service;

    private final UserService userService;
    private final CategoryService categoryService;
    private final SecurityService securityService;

    @GetMapping
    public String readAll(Model model) {
        User loggedIn = securityService.getLoggedIn();
        model.addAttribute("user", loggedIn);
        model.addAttribute("list", service.readAll());
        return THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE;
    }

    @GetMapping("/{id}")
    public String read(@PathVariable("id") Long id, Model model) {
        model.addAttribute("entity", service.read(id));
        return THYMELEAF_TEMPLATE_ONE_ITEM_PAGE;
    }
    @GetMapping("/{id}/changeStatus")
    public String changeStatus(@PathVariable("id") Long id, AppointmentStatus status) {
        Appointment read = service.read(id);
        read.setStatus(status);
        service.update(read);
        return REDIRECT_TO_ALL_ITEMS;
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String editPage(Model model, @PathVariable(name = "id", required = false) Long id) {
        if (id == null) {
            model.addAttribute("entity", new Appointment());
        } else {
            model.addAttribute("entity", service.read(id));
        }

        model.addAttribute("user_list", userService.readAll());
        model.addAttribute("category_list", categoryService.readAll());
        model.addAttribute("appointmentStatus_list", AppointmentStatus.values());
        model.addAttribute("timeSlot_list", TimeSlot.values());

        return THYMELEAF_TEMPLATE_EDIT_PAGE;
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public String save(@ModelAttribute Appointment entity, RedirectAttributes ra) {
        if (entity.getId() == null) {
            try {
                User loggedIn = securityService.getLoggedIn();
                entity.setOwner(loggedIn);
                Appointment createdEntity = service.create(entity);
                loggedIn.getAppointments().add(createdEntity);
                userService.update(loggedIn);
                ra.addFlashAttribute(MESSAGE, "Creating is successful");
                return REDIRECT_TO_ALL_ITEMS + "/" + createdEntity.getId();
            } catch (Exception e) {
                ra.addFlashAttribute(MESSAGE, "Creating failed");
                e.printStackTrace();
            }
            return REDIRECT_TO_ALL_ITEMS;
        } else {
            try {
                service.update(entity.getId(), entity);
                ra.addFlashAttribute(MESSAGE, "Editing is successful");
            } catch (Exception e) {
                e.printStackTrace();
                ra.addFlashAttribute(MESSAGE, "Editing failed");
            }
            return REDIRECT_TO_ALL_ITEMS + "/" + entity.getId();
        }
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


    @GetMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestParam("categoryId") Long categoryId,
                                               @RequestParam("timeSlot") TimeSlot timeSlot,
                                               @RequestParam("date")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       LocalDate date) {
        Category category = categoryService.read(categoryId);
        Optional<Appointment> existingAppointment = service.findByCategoryAndTimeSlotAndDate(category, timeSlot, date);

        if (existingAppointment.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The selected TimeSlot and Category for the given date are already booked.");
        } else {
            return ResponseEntity.ok("The selected TimeSlot and Category for the given date are available.");
        }
    }

    @GetMapping("/available-time-slots")
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots(@RequestParam("categoryId") Long categoryId,
                                                                @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Category category = categoryService.read(categoryId);
        Set<Appointment> bookedAppointments = service.findAllByCategoryAndDate(category, date);

        List<TimeSlot> availableTimeSlots = Arrays.stream(TimeSlot.values())
                .collect(Collectors.toList());

        for (Appointment appointment : bookedAppointments) {
            availableTimeSlots.remove(appointment.getTimeSlot());
        }

        return ResponseEntity.ok(availableTimeSlots);
    }

}
