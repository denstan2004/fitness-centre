package org.example.Controllers;

import org.example.DAO.VisitorDao;
import org.example.Models.Visitor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class VisitorController {
    VisitorDao visitorDao=new VisitorDao();
    @PostMapping("/BillsGym/renew")
    public ModelAndView renewSubscription(@RequestParam("name") String name,
                                          @RequestParam("surname") String surname,
                                          @RequestParam("lastname") String lastname,
                                          @RequestParam("period") String period) {
        ModelAndView mav;

      if(period=="Renew for 1 day")
       visitorDao.updateForOneDay(name, lastname, surname);
      else  visitorDao.updateSubscriptionVisitor(name, lastname, surname);

            mav = new ModelAndView("redirect:/Billy'sGym");


        return mav;
    }

    @PostMapping("/Billy`sGym/check")
    public ModelAndView checkSubscription(@RequestParam("name") String name,
                                          @RequestParam("surname") String surname,
                                          @RequestParam("lastname") String lastname)
    {
        if(visitorDao.checkSubscription(name, lastname, surname)=="абонемент ще дійсний")
        {
            ModelAndView mav = new ModelAndView("subscriptionOk");
            mav.addObject(visitorDao.getOne(name,lastname,surname));
            return mav;
        }
        else
        {
            ModelAndView mav = new ModelAndView("subscriptionNo");
            mav.addObject("name", name);
            mav.addObject("surname", surname);
            mav.addObject("lastname", lastname);
            return mav;
        }



    }

    @GetMapping("/Billy`sGym")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("startpage");
        return mav;
    }
    @PostMapping("/Billy`sGym/add/visitor")
    public RedirectView create(@ModelAttribute("visitor") Visitor visitor) {
        visitor.setSubscription(LocalDateTime.now().minusDays(1));


        visitorDao.visitorSave(visitor);

            return new RedirectView("/Billy`sGym", true);

    }
    @GetMapping("/Billy`sGym/visitors")
    public ModelAndView visitors(Model model) {
        model.addAttribute("visitors",visitorDao.getAllVisitors());
        ModelAndView mav = new ModelAndView("visitors");
        return mav;
    }
    @GetMapping("/Billy`sGym/add/visitor")
    public ModelAndView addvisitor(@ModelAttribute("vsistor")Visitor visitor) {

        ModelAndView mav = new ModelAndView("addvisitor");
        return mav;
    }
}
