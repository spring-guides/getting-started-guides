package com.example.persistence;

import java.util.List;

import com.example.domain.Timeslot;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TimeslotRepository extends PagingAndSortingRepository<Timeslot, Long> {

    @Override
    List<Timeslot> findAll();

}
