package com.replit.replit.service;

import com.replit.replit.entity.Replit;
import com.replit.replit.entity.User;
import com.replit.replit.repository.ReplitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplitServiceImplementation implements ReplitService{

    ReplitRepository replitRepository;

    public ReplitServiceImplementation(ReplitRepository replitRepository) {
        this.replitRepository = replitRepository;
    }

    @Override
    public Replit findById(Integer replitId) {
        return replitRepository.findReplitById(replitId);
    }

    @Override
    public void save(Replit replit) {
        replitRepository.save(replit);
    }

    @Override
    public Replit findByName(String replitName) {
        return replitRepository.findReplitByName(replitName);
    }

    @Override
    public List<Replit> findReplitsByUser(User user) {
        return replitRepository.findAllByUser(user);
    }
}
