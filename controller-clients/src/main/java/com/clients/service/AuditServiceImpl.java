package com.clients.service;

import com.clients.entities.Audit;
import com.clients.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService{
    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void audit(String user, String action) {
        Audit audit = new Audit();
        audit.setUser(user);
        audit.setAction(action);
        audit.setTimestamp(LocalDateTime.now());
        auditRepository.save(audit);
    }


    @Override
    public List<Audit> findByUser(String user) {
        return auditRepository.findByUser(user);
    }

    public List<Audit> findAll() {
        return auditRepository.findAll();
    }
}
