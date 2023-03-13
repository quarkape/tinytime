package club.hue.service.impl;

import club.hue.mapper.ZoneMapper;
import club.hue.pojo.*;
import club.hue.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneMapper zoneMapper;

    public void setZoneMapper(ZoneMapper zoneMapper) {
        this.zoneMapper = zoneMapper;
    }

    @Override
    public List<PostNotify> getAllNotify(String id) {
        return zoneMapper.getAllNotify(id);
    }

    @Override
    public int changeNotifyStatus(String notifyid) {
        return zoneMapper.changeNotifyStatus(notifyid);
    }

    @Override
    public int addMessage(HashMap<String, Object> map) {
        return zoneMapper.addMessage(map);
    }

    @Override
    public int changeMessageStatus(String roleb, String messageid) {
        return zoneMapper.changeMessageStatus(roleb, messageid);
    }

    @Override
    public List<PostMessage> getAllMessage(String roleb) {
        return zoneMapper.getAllMessage(roleb);
    }

    @Override
    public int getUnreadNotify(String id) {
        return zoneMapper.getUnreadNotify(id);
    }

    @Override
    public int getUnreadMessage(String roleb) {
        return zoneMapper.getUnreadMessage(roleb);
    }

    @Override
    public List<MyPost> getMyPosts(String authorid) {
        return zoneMapper.getMyPosts(authorid);
    }

    @Override
    public List<MyComment> getMyComment(String clientid) {
        return zoneMapper.getMyComment(clientid);
    }

    @Override
    public List<MyCollect> getMyCollect(String clientid) {
        return zoneMapper.getMyCollect(clientid);
    }
}
