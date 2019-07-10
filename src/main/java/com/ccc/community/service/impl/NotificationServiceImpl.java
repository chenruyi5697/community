package com.ccc.community.service.impl;

import com.ccc.community.dto.NotificationDTO;
import com.ccc.community.dto.PageDTO;
import com.ccc.community.enums.NotificationStatusEnum;
import com.ccc.community.enums.NotificationTypeEnum;
import com.ccc.community.exception.CustomizeException;
import com.ccc.community.exception.impl.CustomizeErrorCodeImpl;
import com.ccc.community.mapper.NotificationMapper;
import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.*;
import com.ccc.community.service.NotificationService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 10 18:44
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageDTO list(Long uesrId, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO();
        if (page < 1){
            page = 1;
        }
        //分页查询偏移量
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(uesrId);
        Integer totalCount = (int) notificationMapper.countByExample(example);
        Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        if (page > totalPage){
            page = totalPage;
        }
        pageDTO.setPage(page);
        pageDTO.setTotalPage(totalPage);
        //修改判断信息
        pageDTO.setPageInfo(totalCount , page , size);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset , size));
        if (notificationList.size() == 0 || notificationList == null){
            return pageDTO;
        }
        Set<Long> disUsers = notificationList.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
        List<Long> list = new ArrayList<>(disUsers);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(list);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));

        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification , notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.getNameByType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        pageDTO.setData(notificationDTOList);
        return pageDTO;
    }
    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCodeImpl.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCodeImpl.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.getNameByType(notification.getType()));
        return notificationDTO;
    }
}

