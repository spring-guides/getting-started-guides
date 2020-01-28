package com.example.persistence;

import java.util.List;

import com.example.domain.Room;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {

    @Override
    List<Room> findAll();

}
