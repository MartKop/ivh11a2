package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Model.User;
import avans.ivh11.mart.demo.Repository.UserRepository;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private UserRepository userRepository;

    public UserController() {

    }

    @GetMapping
    public ModelAndView list() {
        Iterable<User> users = this.userRepository.findAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - List");
        mav.addObject("users", users);
        mav.setViewName("views/user/list");

        return mav;
    }

    @GetMapping(value = "{id}")
    public ModelAndView view(@PathVariable("id") String userId) {
        User user = this.userRepository.findOne(Long.parseLong(userId));
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - " + userId);
        mav.addObject("user", user);
        mav.setViewName("views/user/view");

        return mav;
    }

    @GetMapping(value = "create")
    public ModelAndView createForm(@ModelAttribute User user) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - Create");
        mav.setViewName("views/user/form");

        return mav;
    }

    @PostMapping(value = "create")
    public ModelAndView create(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "User - Create");
            mav.addObject("form_errors", result.getAllErrors());
            mav.setViewName("views/user/form");

            return mav;
        }

        user = this.userRepository.save(user);
        mav.addObject("user.id", user.getId());
        mav.setViewName("redirect:/user/{user.id}");
        redirect.addFlashAttribute("flash", this.createFlash("success", "Successfully created a new user"));

        return mav;
    }

    @GetMapping(value = "{id}/edit")
    public ModelAndView modifyForm(@PathVariable("id") String userId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - Edit");
        mav.addObject("user", this.userRepository.findOne(Long.parseLong(userId)));
        mav.addObject("edit", true);
        mav.setViewName("views/user/form");

        return mav;
    }

    @PostMapping(value = "{id}/edit")
    public ModelAndView updateUser(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "User - Edit");
            mav.addObject("form_errors", result.getAllErrors());
            mav.addObject("edit", true);
            mav.setViewName("views/user/form");

            return mav;
        }

        user = this.userRepository.save(user);
        mav.addObject("user.id", user.getId());
        mav.setViewName("redirect:/user/{user.id}");
        redirect.addFlashAttribute("flash", this.createFlash("success", "Successfully updated user " + user.getId()));

        return mav;
    }

    @DeleteMapping(value = "{id}/delete")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.userRepository.delete(id);
        Iterable<User> users = this.userRepository.findAll();

        return new ModelAndView("views/user/list", "users", users);
    }

    private HashMap<String, String> createFlash(String type, String text)
    {
        HashMap<String, String> flash = new HashMap<>();
        flash.put("type", type);
        flash.put("text", text);

        return flash;
    }
}
