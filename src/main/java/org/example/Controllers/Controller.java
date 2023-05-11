package org.example.Controllers;

import org.example.DAO.TrainerDao;
import org.example.DAO.VisitorDao;
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
@RequestMapping("/")
public class Controller {
    VisitorDao visitorDao=new VisitorDao();
    TrainerDao trainerDao =new TrainerDao();

    @PostMapping("/Billy`sGym/renew")
    public ModelAndView renewSubscription(@RequestParam("id") int id,
                                          @RequestParam("period") String period) {

        Visitor visitor =visitorDao.getOneById(id);
        if("day".equals(period))
        {
            visitorDao.updateForOneDay(visitorDao.getOne(visitor.getName(), visitor.getLastname(), visitor.getSurname()));
        }
        else {
            visitorDao.updateSubscriptionVisitor(visitorDao.getOne(visitor.getName(), visitor.getLastname(), visitor.getSurname()));

        }
        ModelAndView mav = new ModelAndView("renewSuccess");
        mav.addObject("id",id);
        mav.addObject("period", period);
        return mav;
    }
    @PostMapping("/Billy`sGym/renew/number")
    public ModelAndView renewSubscription(@RequestParam("number") String number,
                                          @RequestParam("period") String period) {

        Visitor visitor =visitorDao.getOneByNumber(number);
        if("day".equals(period))
        {
            visitorDao.updateForOneDay(visitorDao.getOne(visitor.getName(), visitor.getLastname(), visitor.getSurname()));
        }
        else {
            visitorDao.updateSubscriptionVisitor(visitorDao.getOne(visitor.getName(), visitor.getLastname(), visitor.getSurname()));

        }
        ModelAndView mav = new ModelAndView("renewSuccess");
        mav.addObject("number",number);
        mav.addObject("period", period);
        return mav;
    }
    @GetMapping("/Billy`sGym/check/number")
    public ModelAndView findByNumber() {
        ModelAndView mav = new ModelAndView("findByNumber");

        return mav;
    }
    @PostMapping("/Billy`sGym/check/number")
    public ModelAndView checkSubscriptionByNumber(@RequestParam("number")  String number
    )
    {
        Visitor visitor =visitorDao.getOneByNumber(number);
        if(visitorDao.checkSubscription(visitor.getName(), visitor.getLastname(), visitor.getSurname())=="абонемент ще дійсний")
        {
            ModelAndView mav = new ModelAndView("subscriptionOk");

            // Convert byte array to Base64 encoded string
            String photoAsBase64 = Base64.getEncoder().encodeToString(visitor.getPhotoPath());

            mav.addObject("visitor", visitor);
            mav.addObject("photoAsBase64", photoAsBase64);
            return mav;
        }
        else {
            ModelAndView mav = new ModelAndView("subscriptionNoNumber");
            mav.addObject("number", number);
            return mav;
        }

    }

    @PostMapping("/Billy`sGym/check")
    public ModelAndView checkSubscription(@RequestParam("id")  int id)
    {
        Visitor visitor =visitorDao.getOneById(id);
        if(visitorDao.checkSubscription(visitor.getName(), visitor.getLastname(), visitor.getSurname())=="абонемент ще дійсний")
        {
            ModelAndView mav = new ModelAndView("subscriptionOk");

            // Convert byte array to Base64 encoded string
            String photoAsBase64 = Base64.getEncoder().encodeToString(visitor.getPhotoPath());

            mav.addObject("visitor", visitor);
            mav.addObject("photoAsBase64", photoAsBase64);
            return mav;
        }
        else
        {
            ModelAndView mav = new ModelAndView("subscriptionNo");
            mav.addObject("id", id);
            return mav;

        }



    }


    @GetMapping("/Billy`sGym")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("startpage");
        mav.addObject("name", "");
        mav.addObject("surname", "");
        mav.addObject("lastname", "");
        return mav;
    }
    @PostMapping("/Billy`sGym/add/visitor")
    public RedirectView createVisitor(@ModelAttribute("visitor") Visitor visitor, @RequestParam("photo") MultipartFile photo) {
        visitor.setSubscription(LocalDateTime.now().minusDays(1));

        try {
            visitor.setPhotoPath(photo.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
    @GetMapping("/Billy`sGym/add/trainer")
    public ModelAndView addtrainer(@ModelAttribute("trainer")Trainer trainer) {

        ModelAndView mav = new ModelAndView("addtrainer");
        return mav;
    }
    @PostMapping("/Billy`sGym/add/trainer")
    public RedirectView createTrainer(@ModelAttribute("trainer") Trainer trainer,@RequestParam("photo") MultipartFile photo) {

        try {
           trainer.setPhotoPath(photo.getBytes());
        } catch (IOException e) {

        }

       trainerDao.trainerSave(trainer);

        return new RedirectView("/Billy`sGym", true);

    }
    @PostMapping("/trainers/present/False")
    public RedirectView changePresentFalse(@RequestParam("id") int id) {


          trainerDao.changePresentFalse((long) id);
        return new RedirectView("/Billy`sGym/trainers", true);

    }
    @PostMapping("/trainers/present/True")
    public RedirectView changePresentTrue(@RequestParam("id") int id) {


        trainerDao.changePresentTrue((long) id);
        return new RedirectView("/Billy`sGym/trainers", true);

    }

    @GetMapping("/Billy`sGym/trainers")
    public ModelAndView trainers(Model model) {
        model.addAttribute("trainers",trainerDao.getAllTrainers());
        ModelAndView mav = new ModelAndView("trainers");
        return mav;
    }

}
