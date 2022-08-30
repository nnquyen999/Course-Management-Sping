package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myclass.dto.TargetDto;
import com.myclass.entity.Target;

@Repository
public interface TargetRepository extends JpaRepository<Target, Integer> {
	@Query("SELECT new com.myclass.dto.TargetDto(t.id, t.title, t.courseId, c.title) FROM Target t JOIN t.course c")
	public List<TargetDto> getAllDto();
}
