package com.clients.service;

import com.clients.entities.Audit;

import java.util.Date;
import java.util.List;

public interface AuditService {
    void audit(String user, String action);

    List<Audit> findByUser(String user);
}
