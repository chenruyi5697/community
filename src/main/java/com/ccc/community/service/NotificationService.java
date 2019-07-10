package com.ccc.community.service;

import com.ccc.community.dto.NotificationDTO;
import com.ccc.community.dto.PageDTO;
import com.ccc.community.model.User;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 10 18:43
 */
public interface NotificationService {
    PageDTO list(Long uesrId , Integer page , Integer size);
    Long unreadCount(Long userId);
    NotificationDTO read(Long id, User user);
}
