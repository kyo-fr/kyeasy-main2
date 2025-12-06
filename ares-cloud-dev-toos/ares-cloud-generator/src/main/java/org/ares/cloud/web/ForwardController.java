package org.ares.cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/25 01:23
 */
@Controller
public class ForwardController {
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        // Forward to /index.html to let the front-end router handle the request
        return "forward:/index.html";
    }
}
