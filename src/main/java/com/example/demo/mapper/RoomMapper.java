package com.example.demo.mapper;

import com.example.demo.entity.Room;
import com.example.demo.web.model.request.UpsertRoomRequest;
import com.example.demo.web.model.response.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    RoomResponse roomToResponse(Room room);
    UpsertRoomRequest roomToRequest(Room room);
    Room requestToRoom(UpsertRoomRequest request);
}
