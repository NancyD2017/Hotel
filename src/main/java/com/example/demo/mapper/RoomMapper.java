package com.example.demo.mapper;

import com.example.demo.entity.Room;
import com.example.demo.web.model.request.UpsertRoomRequest;
import com.example.demo.web.model.response.RoomListResponse;
import com.example.demo.web.model.response.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    RoomResponse roomToResponse(Room room);
    default RoomListResponse roomsToResponse(List<Room> room){
        RoomListResponse roomListResponse = new RoomListResponse();
        roomListResponse.setRooms(roomListToResponseList(room));
        return roomListResponse;
    }
    List<RoomResponse> roomListToResponseList(List<Room> roomList);
    UpsertRoomRequest roomToRequest(Room room);
    Room requestToRoom(UpsertRoomRequest request);
}
