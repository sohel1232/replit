package com.replit.replit.service;

import com.replit.replit.entity.Replit;
import com.replit.replit.entity.User;

import java.util.List;

public interface ReplitService {
    Replit findById(Integer replitId);

    void save(Replit replit);

    Replit findByName(String replitName);

    List<Replit> findReplitsByUser(User user);
}
