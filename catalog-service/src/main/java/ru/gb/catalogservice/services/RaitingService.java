package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Raiting;
import ru.gb.catalogservice.repositories.RaitingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaitingService {
    private final RaitingRepository raitingRepository;
    public List<Raiting> findAll(){
        return raitingRepository.findAll();
    }
}
