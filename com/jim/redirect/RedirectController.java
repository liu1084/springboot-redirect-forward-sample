package com.jim.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Jim
 * Date: 2018/11/20:下午9:21
 * Description:
 * <p>
 * 为什么要重定向?
 * 1:防止二次提交(并不能完全阻止,比如在重定向之前进行刷新页面);
 * 2:一个工作流中,执行完一个步骤后转向另外一个控制器方法;
 * 3:在控制器中,当遇到异常后,跳转到错误页面,而不是正常完成后的显示页.
 */

@Controller
public class RedirectController {

    /**
     * 使用RedirectView跳转
     */
    @GetMapping("/redirect1")
    public RedirectView redirectWithRedirectView(RedirectAttributes attributes) {
        attributes.addAttribute("param1", "p1");
        attributes.addFlashAttribute("param2", "flash1");
        return new RedirectView("redirect1Url");
    }

    @GetMapping("/redirect1Url")
    public void redirect1Url(HttpServletResponse response) throws IOException {
        System.out.println("hello");
    }

    @GetMapping("/redirect2")
    public ModelAndView redirect2(ModelMap modelMap) {
        modelMap.addAttribute("param1", "value1");
        return new ModelAndView("redirect:/redirectUrl2", modelMap);
    }

    @GetMapping("/redirectUrl2")
    public void redirectUrl2() {
        System.out.println("hello2");
    }

    @GetMapping("/forward1")
    public ModelAndView forwardToUrl2(ModelMap modelMap) {
        modelMap.addAttribute("param1", "value1");
        return new ModelAndView("forward:/redirect1Url", modelMap);
    }

}
