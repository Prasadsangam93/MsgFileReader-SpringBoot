package com.msgfilereader.repository;

import com.msgfilereader.entity.MsgFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgFileRepository extends JpaRepository<MsgFile,Long> {
}
