package securityexample1.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import securityexample1.demo.detail.UserDT;
import securityexample1.demo.model.UserModel;
import securityexample1.demo.service.UserService;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "/user/index";
    }

    @GetMapping("/user/index")
    public String index2() {
        return "/user/index";
    }

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "/user/login-page";
    }

    @GetMapping("/user/signup-page")
    public String signupPage() {
        return "user/signup-page";
    }

    //회원가입
    @PostMapping("signupUser")
    public String signupUser(UserModel userModel) {

        userService.save(userModel);

        return "/user/index";
    }

    @GetMapping("/user/home")
    public String home(@AuthenticationPrincipal UserDT userDT, UserModel userModel, Model model) {
        if (userDT == null) {
            return "/user/index";
        } else {
            userModel = userService.findMember(userDT.getUsername());
            model.addAttribute("userInfo", userModel);
            return "/user/home";
        }
//        return "home";
    }


    @GetMapping("/admin/admin-home")
    public String adminHome() {
        return "/admin/admin-home";
    }

    @GetMapping("/admin/admin-login")
    public String adminLoginPage() {
        return "/admin/admin-login";
    }

}
