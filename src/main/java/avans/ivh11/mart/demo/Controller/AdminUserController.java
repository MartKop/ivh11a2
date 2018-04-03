package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.UnregisteredUser;
import avans.ivh11.mart.demo.Repository.BaseUserRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.TypeChecker;
import avans.ivh11.mart.demo.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("admin/user/")
public class AdminUserController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private FlashService flashService;

    @Autowired
    private BaseUserRepository<BaseUser> baseUserBaseUserRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView list(HttpServletRequest request) {
        Iterable<BaseUser> users = this.baseUserBaseUserRepository.findAll();
        ModelAndView mav = new ModelAndView();

        mav.addObject("title", "User - List");
        mav.addObject("users", users);
        mav.setViewName("views/user/list");

        return mav;
    }

    @GetMapping(value = "{id}")
    public ModelAndView view(@PathVariable("id") String userId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        BaseUser user = this.baseUserBaseUserRepository.findOne(Long.parseLong(userId));

        mav.addObject("title", "User - " + userId);
        mav.addObject("user", user);
        mav.addObject("checker", new TypeChecker());
        mav.setViewName("views/user/view");

        return mav;
    }

    @GetMapping(value = "create")
    public ModelAndView createForm(@ModelAttribute UnregisteredUser user, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("user", new UnregisteredUser());
        mav.addObject("title", "User - Create");
        mav.setViewName("views/user/form");

        return mav;
    }

    @PostMapping(value = "create")
    public ModelAndView create(@Valid @ModelAttribute("user") UnregisteredUser user, BindingResult result,
                   RedirectAttributes redirect, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "User - Create");
            mav.addObject("form_errors", result.getAllErrors());
            mav.setViewName("views/user/form");
            return mav;
        }

        this.baseUserBaseUserRepository.save(user);
        mav.addObject("user.id", user.getId());
        mav.setViewName("redirect:/profile/{user.id}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully created a new user"));

        return mav;
    }

    @GetMapping(value = "{id}/edit")
    public ModelAndView modifyForm(@PathVariable("id") String userId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("title", "User - Edit");
        mav.addObject("user", this.baseUserBaseUserRepository.findOne(Long.parseLong(userId)));
        mav.addObject("edit", true);
        mav.setViewName("views/user/form");

        return mav;
    }

    @Transactional
    @PostMapping(value = "{id}/edit")
    public ModelAndView updateUser(@PathVariable("id") String userId, @Valid BaseUser user,
                   BindingResult result, RedirectAttributes redirect, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        BaseUser oldUser = this.baseUserBaseUserRepository.findOne(Long.parseLong(userId));

        if (userService.getCurrentUser().getId() != user.getId() && !request.isUserInRole(RegisteredUser.ROLE_SUPER_ADMIN)) {
            mav.setViewName("error/403");
            mav.addObject("exception", new AccessDeniedException("You do not have access to this page"));
            return mav;
        }

        if (result.hasErrors()) {
            mav.addObject("title", "User - Edit");
            mav.addObject("form_errors", result.getAllErrors());
            mav.addObject("edit", true);
            mav.setViewName("views/user/form");
            return mav;
        }

        oldUser.updateBaseUser(user);
        mav.addObject("user.id", user.getId());
        mav.setViewName("redirect:/profile/{user.id}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully updated user " + user.getId()));

        return mav;
    }

    @DeleteMapping(value = "{id}/delete")
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest request) {
        this.baseUserBaseUserRepository.delete(id);
        ModelAndView mav = new ModelAndView();

        if (!request.isUserInRole(RegisteredUser.ROLE_ADMIN)) {
            mav.setViewName("error/403");
            mav.addObject("exception", new AccessDeniedException("You do not have access to this page"));
            return mav;
        }

        mav.addObject("title", "User - List");
        mav.addObject("users", this.baseUserBaseUserRepository.findAll());
        mav.addObject("flash", this.flashService.createFlash("success", "Succesfully deleted user" + id));
        mav.setViewName("redirect:/user/");

        return mav;
    }
}
