package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Login;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.Role;
import avans.ivh11.mart.demo.Domain.UnregisteredUser;
import avans.ivh11.mart.demo.Repository.RoleRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);

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

    @GetMapping(value = "/login")
    public ModelAndView createForm(@ModelAttribute Login login) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("login", new Login());
        mav.addObject("title", "Login");
        mav.setViewName("views/login/login");

        logger.info("login form");

        return mav;
    }

    @PostMapping(value = "/login")
    public ModelAndView login(@ModelAttribute("login") Login login, BindingResult result, RedirectAttributes redirect) {
        logger.info("login post form");
        result = this.userService.loginUser(login, result);
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            logger.info("login error");
            mav.addObject("title", "Login");
            mav.addObject("login", login);
            mav.addObject("form_errors", result.getAllErrors());
            mav.setViewName("/views/login/login");

            return mav;
        }
        logger.info("login succes");
        RegisteredUser user = this.userService.getUserByUsername(login.getUsername());
        List<GrantedAuthority> authorities = new ArrayList<>();


        // DO SOME MORE LOGGIN SHIZZLE
        authorities.add(new SimpleGrantedAuthority(user.getRole() != null ? user.getRole() : "ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        mav.setViewName("redirect:/welcome");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully logged in"));

        return mav;
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome(Model model) {
        ModelAndView mav = new ModelAndView("views/index");
        mav.addObject("title", "Login");

        return mav;
    }

}
