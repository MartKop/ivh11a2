package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.UnregisteredUser;
import avans.ivh11.mart.demo.Repository.BaseUserRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private FlashService flashService;

    @Autowired
    private BaseUserRepository<UnregisteredUser> unregisteredUserRepository;

    @Autowired
    private BaseUserRepository<RegisteredUser> registeredUserRepository;

    @Autowired
    private BaseUserRepository<BaseUser> baseUserBaseUserRepository;

    @GetMapping
    public ModelAndView list() {
        Iterable<BaseUser> users = this.baseUserBaseUserRepository.findAll();

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - List");
        mav.addObject("users", users);
        mav.setViewName("views/user/list");

        return mav;
    }

    @GetMapping(value = "{id}")
    public ModelAndView view(@PathVariable("id") String userId) {
        BaseUser user = this.baseUserBaseUserRepository.findOne(Long.parseLong(userId));
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - " + userId);
        mav.addObject("user", user);
        mav.addObject("orders", user.getOrders());
        mav.setViewName("views/user/view");

        return mav;
    }

    @GetMapping(value = "create")
    public ModelAndView createForm(@ModelAttribute UnregisteredUser user) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", new UnregisteredUser());
        mav.addObject("title", "User - Create");
        mav.setViewName("views/user/form");

        return mav;
    }
    @PostMapping(value = "create")
    public ModelAndView create(@Valid @ModelAttribute("user") UnregisteredUser user, BindingResult result, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "User - Create");
            mav.addObject("form_errors", result.getAllErrors());
            mav.setViewName("views/user/form");
            return mav;
        }

        this.baseUserBaseUserRepository.save(user);
        mav.addObject("user.id", user.getId());
        mav.setViewName("redirect:/user/{user.id}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully created a new user"));

        return mav;
    }

    @GetMapping(value = "{id}/edit")
    public ModelAndView modifyForm(@PathVariable("id") String userId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - Edit");
        mav.addObject("user", this.unregisteredUserRepository.findOne(Long.parseLong(userId)));
        mav.addObject("edit", true);
        mav.setViewName("views/user/form");

        return mav;
    }

    @PostMapping(value = "{id}/edit")
    public ModelAndView updateUser(@Valid UnregisteredUser user, BindingResult result, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "User - Edit");
            mav.addObject("form_errors", result.getAllErrors());
            mav.addObject("edit", true);
            mav.setViewName("views/user/form");
            return mav;
        }

        this.unregisteredUserRepository.save(user);
        mav.addObject("user.id", user.getId());
        mav.setViewName("redirect:/user/{user.id}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully updated user " + user.getId()));

        return mav;
    }

    @DeleteMapping(value = "{id}/delete")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.unregisteredUserRepository.delete(id);

        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "User - List");
        mav.addObject("users", this.unregisteredUserRepository.findAll());
        mav.addObject("flash", this.flashService.createFlash("success", "Succesfully deleted user" + id));
        mav.setViewName("redirect:/user/");

        return mav;
    }


}
