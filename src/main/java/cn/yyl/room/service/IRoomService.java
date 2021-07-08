package cn.yyl.room.service;

import java.util.Map;

public interface IRoomService {
    String joinRoom(String roomId, String userId);

    String leaveRoom(String roomId, String userId);
}
