package com.dautev.courseware.xml;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CoursewareController {

    @GetMapping()
    public String index(Model model) {
        return "index";
    }
    @GetMapping("/sender")
    public String sender(Model model) {
        return "sender";
    }

}
