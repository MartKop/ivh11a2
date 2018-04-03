package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.BaseUser;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Repository.BaseUserRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private BaseUserRepository<BaseUser> baseUserBaseUserRepository;

    @Autowired
    private FlashService flashService;


    @GetMapping(value = "")
    public ModelAndView view() {
        RegisteredUser user = this.userService.getUserById(userService.getCurrentUser().getId());
        ModelAndView mav = new ModelAndView();

        mav.addObject("title", "Profiel - " + user.getFullName());
        mav.addObject("user", user);
        mav.setViewName("views/profile/view");

        return mav;
    }

    @GetMapping(value = "{id}")
    public ModelAndView viewById(@PathVariable("id") String userId, HttpServletRequest request) {
        RegisteredUser user = this.userService.getUserById(Long.parseLong(userId));
        ModelAndView mav = new ModelAndView();

        if (userService.getCurrentUser().getId() != Long.parseLong(userId) && !request.isUserInRole(RegisteredUser.ROLE_SUPER_ADMIN)) {
            mav.setViewName("error/403");
            mav.addObject("exception", new AccessDeniedException("Je hebt geen toegang tot deze pagina."));
            return mav;
        }

        mav.addObject("title", "Profiel - " + user.getFullName());
        mav.addObject("user", user);
        mav.setViewName("views/profile/view");

        return mav;
    }

    @GetMapping(value = "{id}/edit")
    public ModelAndView modifyForm(@PathVariable("id") String userId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("title", "Profiel - Wijzigen");
        mav.addObject("user", this.baseUserBaseUserRepository.findOne(Long.parseLong(userId)));
        mav.addObject("edit", true);
        mav.setViewName("views/user/form");

        return mav;
    }

    @Transactional
    @PostMapping(value = "{id}/edit")
    public ModelAndView updateProfile(@PathVariable("id") String userId, @Valid BaseUser user,
                                      BindingResult result, RedirectAttributes redirect, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        BaseUser oldUser = this.baseUserBaseUserRepository.findOne(Long.parseLong(userId));

        if (userService.getCurrentUser().getId() != user.getId() && !request.isUserInRole(RegisteredUser.ROLE_SUPER_ADMIN)) {
            mav.setViewName("error/403");
            mav.addObject("exception", new AccessDeniedException("Je hebt geen toegang tot deze pagina."));
            return mav;
        }

        if (result.hasErrors()) {
            mav.addObject("title", "Profile - Edit");
            mav.addObject("form_errors", result.getAllErrors());
            mav.addObject("edit", true);
            mav.setViewName("views/user/form");
            return mav;
        }

        oldUser.updateBaseUser(user);
        mav.addObject("user.id", user.getId());
        mav.setViewName("redirect:/profile/{user.id}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Gebruiker gewijzigd: " + user.getFullName()));

        return mav;
    }
}
