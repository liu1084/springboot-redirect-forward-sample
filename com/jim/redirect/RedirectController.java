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
 * 一：为什么要重定向?
 * 1:防止二次提交(并不能完全阻止,比如在重定向之前进行刷新页面);
 * 2:一个工作流中,执行完一个步骤后转向另外一个控制器方法;
 * 3:在控制器中,当遇到异常后,跳转到错误页面,而不是正常完成后的显示页.
 *
 * spring的跳转可以通过以下3中方式实现:
 * 1:RedirectView("redirectUrl")，这相当于HttpServletResponse.sendRedirect()
 * 2:ModelAndView("redirect:/redirectUrl")
 * 3:ModelAndView("forward:/redirectUrl")
 *
 * 二：跳转路径
 * 跳转的地址是相对于当期的servlet上下文,也就是当前控制器的路径.比如:当前的路径是/api/v1.1/user/create
 * 如果跳转地址写:/login(注册成功后自动登录)
 * 那么这里的/login实际访问的地址是:/api/v1.1/user/login，
 * 也可以写绝对地址。
 *
 * 三：跳转参数
 * 1:RedirectView("redirectUrl")
 * 使用RedirectAttributes,添加的参数会以get参数放在URL后面.
 * 还有一种flash参数，会偷偷的传递到跳转的控制器，可以使用@ModelAttribute("flashName")接收参数值
 *
 * 2:ModelAndView("redirect:/url", ModelMap)
 *
 * 四：redirect和forwar的区别
 * redirect返回302（永久性跳转），新地址会在header中，由客户端请求新地址，地址栏将会显示新地址；
 * forward只发生在服务器端，客户端的URL地址不会发生变化；
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
