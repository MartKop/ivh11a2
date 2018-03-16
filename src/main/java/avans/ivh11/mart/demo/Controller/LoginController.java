package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Login;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.Role;
import avans.ivh11.mart.demo.Domain.UnregisteredUser;
import avans.ivh11.mart.demo.Repository.RoleRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private FlashService flashService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/registration")
    public ModelAndView createForm(@ModelAttribute RegisteredUser user) {
        ModelAndView mav = new ModelAndView("views/login/register");
        mav.addObject("user", new RegisteredUser());
        mav.addObject("title", "Register");

        return mav;
    }

    @PostMapping(value = "/registration")
    public ModelAndView registration(@Valid @ModelAttribute("user") RegisteredUser user, BindingResult bindingResult, RedirectAttributes redirect) {
        bindingResult = this.userService.validateUser(user, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("title", "Register");
            mav.addObject("form_errors", bindingResult.getAllErrors());
            mav.setViewName("views/login/register");

            return mav;
        }

        this.userService.save(user);
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully registered"));

        return new ModelAndView("redirect:/user");
    }
//
//
//        mav.setViewName("views/index");
//        userService.save(user);
//        securityService.autologin(user.getUsername(), user.getPasswordConfirm());
//        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully logged in"));
//
//        return mav;
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(Model model, String error, String logout) {
//        if (error != null)
//            model.addAttribute("error", "Your username and password is invalid.");
//
//        if (logout != null)
//            model.addAttribute("message", "You have been logged out successfully.");
//
//        return "views/login/login";
//    }
//
//    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
//    public String welcome(Model model) {
//        return "views/index";
//    }

}
