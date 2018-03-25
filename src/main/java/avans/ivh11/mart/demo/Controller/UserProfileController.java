package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "{id}")
    public ModelAndView view(@PathVariable("id") String userId) {
        RegisteredUser user = this.userService.getUserById(Long.parseLong(userId));
        ModelAndView mav = new ModelAndView();

        if (userService.getCurrentUser().getId() != Long.parseLong(userId) && !userService.getRole().equals(RegisteredUser.ROLE_SUPER_ADMIN)) {
            mav.setViewName("error/403");
            mav.addObject("exception", new AccessDeniedException("You do not have access to this page"));
            return mav;
        }

        mav.addObject("title", "profile - " + user.getFullName());
        mav.addObject("user", user);
        mav.setViewName("views/profile/view");

        return mav;
    }
}
