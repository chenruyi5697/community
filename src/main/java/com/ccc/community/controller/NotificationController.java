package com.ccc.community.controller;

import com.ccc.community.dto.NotificationDTO;
import com.ccc.community.enums.NotificationTypeEnum;
import com.ccc.community.model.User;
import com.ccc.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 10 20:45
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id ,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (null == user){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
