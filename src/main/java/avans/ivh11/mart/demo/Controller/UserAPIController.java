package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Model.User;
import avans.ivh11.mart.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/user/")
public class UserAPIController {

    @Autowired
    private UserRepository service;

    @GetMapping()
    @ResponseBody
    public Iterable<User> findAll() {
        return service.findAll();
    }
}
