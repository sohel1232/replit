package com.replit.replit.repository;

import com.replit.replit.entity.Replit;
import com.replit.replit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplitRepository extends JpaRepository<Replit,Integer> {
    Replit findReplitById(Integer id);

    Replit findReplitByName(String replitName);

    List<Replit> findAllByUser(User user);
}
