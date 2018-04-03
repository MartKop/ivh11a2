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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public ModelAndView view() {
        RegisteredUser user = this.userService.getUserById(userService.getCurrentUser().getId());
        ModelAndView mav = new ModelAndView();

        mav.addObject("title", "profile - " + user.getFullName());
        mav.addObject("user", user);
        mav.setViewName("views/profile/view");

        return mav;
    }

    @GetMapping(value = "{id}")
    public ModelAndView viewById(@PathVariable("id") String userId, HttpServletRequest request) {
        RegisteredUser user = this.userService.getUserById(Long.parseLong(userId));
        ModelAndView mav = new ModelAndView();

        if (userService.getCurrentUser().getId() != Long.parseLong(userId) &&!request.isUserInRole(RegisteredUser.ROLE_SUPER_ADMIN)) {
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
