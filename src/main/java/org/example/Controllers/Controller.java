package org.example.Controllers;

import org.example.DAO.AbonementDao;
import org.example.DAO.TrainerDao;
import org.example.DAO.VisitorDao;
import org.example.Models.Abonement;
import org.example.Models.Trainer;
import org.example.Models.Visitor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@RestController
@RequestMapping("/BillysGym")
public class Controller {
    VisitorDao visitorDao=new VisitorDao();
    TrainerDao trainerDao =new TrainerDao();
    AbonementDao abonementDao=new AbonementDao();

    @PostMapping("/renew")
    public ModelAndView renewSubscription(@RequestParam("originalId") int id,
                                          @RequestParam("abonementId") int abonementId ) {


        abonementDao.selectAbonement(abonementId,id);
        abonementDao.sale(abonementId);

        ModelAndView mav = new ModelAndView("renewSuccess");
        return mav;
    }
    @PostMapping("/renew/number")
    public ModelAndView renewSubscription(@RequestParam("originalNumber") String number,
                                          @RequestParam("abonementId") int abonementId) {




        Visitor visitor =visitorDao.getOneByNumber(number);
       abonementDao.selectAbonement(abonementId,visitor.getId());
        abonementDao.sale(abonementId);
        ModelAndView mav = new ModelAndView("renewSuccess");
        return mav;
    }
    @GetMapping("/check/number")
    public ModelAndView findByNumber() {
        ModelAndView mav = new ModelAndView("findByNumber");

        return mav;
    }
    @PostMapping("/check/number")
    public ModelAndView checkSubscriptionByNumber(@RequestParam("number")  String number
    )
    {

        Visitor visitor =visitorDao.getOneByNumber(number);
        if (visitor!=null) {
            if (visitorDao.checkSubscription(visitor.getName(), visitor.getLastname(), visitor.getSurname()) == "абонемент ще дійсний") {
                ModelAndView mav = new ModelAndView("subscriptionOk");

                String photoAsBase64 = Base64.getEncoder().encodeToString(visitor.getPhotoPath());

                mav.addObject("visitor", visitor);
                mav.addObject("photoAsBase64", photoAsBase64);
                return mav;
            } else {
                ModelAndView mav = new ModelAndView("subscriptionNoNumber");
                mav.addObject("number", number);
                mav.addObject("abonements",abonementDao.getAllAbonements());

                return mav;
            }
        }
        else { ModelAndView mav = new ModelAndView("notfound");

            return mav;}

    }

    @PostMapping("/check")
    public ModelAndView checkSubscription(@RequestParam("id")  int id)
    {
        Visitor visitor =visitorDao.getOneById(id);
        if (visitor!=null)
        {
            if (visitorDao.checkSubscription(visitor.getName(), visitor.getLastname(), visitor.getSurname()) == "абонемент ще дійсний") {
                ModelAndView mav = new ModelAndView("subscriptionOk");

                // Convert byte array to Base64 encoded string
                String photoAsBase64 = Base64.getEncoder().encodeToString(visitor.getPhotoPath());

                mav.addObject("visitor", visitor);
                mav.addObject("photoAsBase64", photoAsBase64);
                return mav;
            } else {
                ModelAndView mav = new ModelAndView("subscriptionNo");
                mav.addObject("id", id);
                mav.addObject("abonements",abonementDao.getAllAbonements());
                return mav;

            }
        }
        else { ModelAndView mav = new ModelAndView("notfound");

            return mav;}

    }


    @GetMapping("")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("startpage");
        mav.addObject("name", "");
        mav.addObject("surname", "");
        mav.addObject("lastname", "");
        return mav;
    }

    @PostMapping("/add/visitor")
    public RedirectView createVisitor(@ModelAttribute("visitor") Visitor visitor, @RequestParam("photo") MultipartFile photo) {
        visitor.setSubscription(LocalDateTime.now().minusDays(1));

        try {
            visitor.setPhotoPath(photo.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        visitorDao.visitorSave(visitor);

            return new RedirectView("/BillysGym", true);

    }

    @GetMapping("/visitors")
    public ModelAndView visitors(Model model) {
        model.addAttribute("visitors",visitorDao.getAllVisitors());
        ModelAndView mav = new ModelAndView("visitors");
        return mav;
    }
    @GetMapping("/add/visitor")
    public ModelAndView addvisitor(@ModelAttribute("visitor")Visitor visitor) {

        ModelAndView mav = new ModelAndView("addvisitor");
        return mav;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
//                                                    TRAINERS


    @PostMapping("/trainers/present/False")
    public RedirectView changePresentFalse(@RequestParam("id") int id) {


          trainerDao.changePresentFalse((long) id);
        return new RedirectView("/BillysGym/trainers", true);

    }
    @PostMapping("/trainers/present/True")
    public RedirectView changePresentTrue(@RequestParam("id") int id) {


        trainerDao.changePresentTrue((long) id);
        return new RedirectView("/BillysGym/trainers", true);

    }

    @GetMapping("/trainers")
    public ModelAndView trainers(Model model) {
        model.addAttribute("trainers",trainerDao.getAllTrainers());
        ModelAndView mav = new ModelAndView("trainers");
        return mav;
    }

}
