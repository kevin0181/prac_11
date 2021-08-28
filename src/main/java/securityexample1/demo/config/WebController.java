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

import java.security.Principal;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("loginPage")
    public String loginPage() {
        return "login-page";
    }

    @GetMapping("signupPage")
    public String signupPage() {
        return "signup-page";
    }

    //회원가입
    @PostMapping("signupUser")
    public String signupUser(UserModel userModel) {

        userService.save(userModel);

        return "/index";
    }

    //로그인
    @PostMapping("loginUser")
    public String loginUser() {
        return "home";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDT userDT, UserModel userModel, Model model) {
        if (userDT == null) {
            return "index";
        } else {
            userModel = userService.findMember(userDT.getUsername());
            model.addAttribute("userInfo", userModel);
            return "home";
        }
//        return "home";
    }


    //어드민 로그인
    @PostMapping("loginAdmin")
    public String adminlogin() {
        return "/admin/home";
    }

    @GetMapping("/admin/home")
    public String adminLoginPage() {
        return "/admin/home";
    }

}
