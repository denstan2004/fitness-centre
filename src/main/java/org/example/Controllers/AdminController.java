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
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController

{
    AbonementDao abonementDao=new AbonementDao();
    TrainerDao trainerDao = new TrainerDao();
    VisitorDao visitorDao =new VisitorDao();
    @GetMapping("")
    public ModelAndView showTrainerManagementPage() {
        ModelAndView mav = new ModelAndView("adminStartPage");
        mav.addObject("id",visitorDao.getAllVisitorsCount());

        return mav;
    }
    @GetMapping("/delete/trainer")
    public ModelAndView trainerDelete() {
        ModelAndView mav = new ModelAndView("deletetrainer");
        return mav;
    }
    @GetMapping("/refresh/trainer")
    public ModelAndView trainerRefresh() {
        ModelAndView mav = new ModelAndView("trainerrefresh");
        return mav;
    }
    @GetMapping("/add/trainer")
    public ModelAndView addtrainer(@ModelAttribute("trainer")Trainer trainer) {

        ModelAndView mav = new ModelAndView("addtrainer");
        return mav;
    }
    @GetMapping("/trainers")
    public ModelAndView trainers(Model model) {
        model.addAttribute("trainers",trainerDao.getAllTrainers());
        ModelAndView mav = new ModelAndView("trainersAllADMIN");
        return mav;
    }
    @PostMapping("/add/trainer")
    public RedirectView createTrainer(@ModelAttribute("trainer") Trainer trainer,@RequestParam("photo") MultipartFile photo) {

        try {
            trainer.setPhotoPath(photo.getBytes());
        } catch (IOException e) {

        }

        trainerDao.trainerSave(trainer);

        return new RedirectView("/admin", true);

    }

    @PostMapping("/refresh/trainer")
    public RedirectView trainerRefresh(@RequestParam("id") long id) {
        trainerDao.trainerRefresh(id);
        return new RedirectView("/admin", true);
    }
    @PostMapping("/delete/trainer")
    public RedirectView deleteTrainer(@RequestParam("id") long id) {
        trainerDao.trainerDelete(id);
        return new RedirectView("/admin", true);
    }

    @GetMapping("/add/abonement")
    public ModelAndView addAbonement(@ModelAttribute("abonement")Abonement abonement) {

        ModelAndView mav = new ModelAndView("addabonement");
        return mav;
    }
    @PostMapping("/add/abonement")
    public RedirectView createAbonement(@ModelAttribute("abonement") Abonement abonement) {

       abonementDao.abonementSave(abonement);
        return new RedirectView("/admin", true);

    }
    @GetMapping("/abonements")
    public ModelAndView abonements(Model model) {
        model.addAttribute("abonements",abonementDao.getAllAbonements());
        ModelAndView mav = new ModelAndView("abonements");
        return mav;
    }
    @GetMapping("/delete/abonement")
    public ModelAndView abonementDelete() {
        ModelAndView mav = new ModelAndView("deleteabonement");
        return mav;
    }
    @PostMapping("/delete/abonement")
    public RedirectView deleteAbonement(@RequestParam("id") long id) {
       abonementDao.abonementDelete(id);
        return new RedirectView("/admin", true);
    }
    @GetMapping("/visitors/all/by-age")
    public ModelAndView visitorsByAge() {
        ModelAndView mav = new ModelAndView("allVisitorsByAgeGender");
        return mav;
    }
    @PostMapping("/visitors/all/by/age")
    public ModelAndView visitorsByAge(@RequestParam("ageFrom") long ageFrom,
                                      @RequestParam("ageTo") long ageTo,  @RequestParam("gender") String gender) {
        ModelAndView mav = new ModelAndView("visitorsAgeGender");
        mav.addObject("visitors",visitorDao.getVisitorsByAgeRangeAndGender(ageFrom,ageTo,gender));
//        List<Visitor> visitorList=visitorDao.getVisitorsByAgeRangeAndGender(ageFrom,ageTo,gender);
//        System.out.println(visitorList);
        return mav;

    }
}
