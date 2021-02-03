package com.easyconv.easyconvserver.core.repository;

import com.easyconv.easyconvserver.core.entity.ConvertHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvertHistoryRepository extends JpaRepository<ConvertHistory, String> {
}
