package cn.yyl.room.controller;


import cn.yyl.room.service.IRoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Resource
    IRoomService roomService;

    @RequestMapping("/joinRoom")
    public String joinRoom(String roomId, String userId){
        return roomService.joinRoom(roomId,userId);
    }

    @RequestMapping("/leaveRoom")
    public String leaveRoom(String roomId, String userId){
        return roomService.leaveRoom(roomId,userId);
    }
}
