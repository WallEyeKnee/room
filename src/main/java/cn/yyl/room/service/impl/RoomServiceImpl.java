package cn.yyl.room.service.impl;

import cn.yyl.room.service.IRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl. class);
    private Lock lock = new ReentrantLock();
    public static final Map<String, List<String>> ROOM_POLL = new HashMap<>();

    @Override
    public String joinRoom(String roomId, String userId) {
        try {
            lock.lock();
            if (ROOM_POLL.containsKey(roomId)){
                //如果存在聊天室，就加入聊天室
                List<String> list = ROOM_POLL.get(roomId);
                if (!list.contains(userId)){
                    list.add(userId);
                }
            }else{
                //如果聊天室不存在就创建
                ArrayList<String> userList = new ArrayList<>();
                userList.add(userId);
                ROOM_POLL.put(roomId,userList);
            }
            List<String> list = ROOM_POLL.get(roomId);
            List<String> collect = list.stream().filter(e -> !e.equals(userId)).collect(Collectors.toList());
            if (collect==null||collect.size()==0){
                logger.info("[]");
                return "[]";
            }else{
                logger.info(collect.toString());
            }
            return collect.toString();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String leaveRoom(String roomId, String userId) {
        if (!ROOM_POLL.containsKey(roomId)){
            logger.info("不存在该聊天室");
            return "不存在该聊天室";
        }
        try {
            lock.lock();
            List<String> userList = ROOM_POLL.get(roomId);
            if (userList.contains(userId)){
                userList.remove(userId);
            }else{
                logger.info("该用户不在聊天室");
                return "该用户不在聊天室";
            }
            if (userList.size()==0){
                //如果聊天室的人数为0则删除聊天室
                ROOM_POLL.remove(roomId);
                logger.info("[]");
                return "[]";
            }
            logger.info(userList.toString());
            return userList.toString();
        }finally {
            lock.unlock();
        }
    }
}
