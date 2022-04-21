package com.sang.prosangserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sang.prosangserver.entities.lyric.Lyric;

@Repository
@Transactional
public interface LyricRepository extends JpaRepository<Lyric, Long> {

}
