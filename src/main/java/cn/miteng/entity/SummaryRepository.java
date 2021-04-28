package cn.miteng.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Integer>, JpaSpecificationExecutor<Summary> {
    List<Summary> findByUserid(Integer userid);
}