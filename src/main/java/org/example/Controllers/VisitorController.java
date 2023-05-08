package org.example.Controllers;

import org.example.DAO.VisitorDao;
import org.example.Models.Visitor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class VisitorController {
    VisitorDao visitorDao=new VisitorDao();


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
            mav.addObject(visitorDao.getOne(visitor.getName(), visitor.getLastname(), visitor.getSurname()));
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
            mav.addObject(visitorDao.getOne(visitor.getName(), visitor.getLastname(), visitor.getSurname()));
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
    public RedirectView create(@ModelAttribute("visitor") Visitor visitor,@RequestParam("photo") MultipartFile photo) {
        visitor.setSubscription(LocalDateTime.now().minusDays(1));

        try {
            String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
            Path filePath = tempDir.resolve(fileName);
            Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            visitor.setPhotoPath(filePath.toString());
        } catch (IOException e) {
            // обробити помилку збереження зображення
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
}
