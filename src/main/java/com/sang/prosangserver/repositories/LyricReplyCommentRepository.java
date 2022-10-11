package com.sang.prosangserver.repositories;

import com.sang.prosangserver.entities.lyric.LyricReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface LyricReplyCommentRepository extends JpaRepository<LyricReplyComment, Long>  {

    @Query("FROM LyricReplyComment c WHERE c.id = :replyId and c.accountId = :accountId and c.comment.id = :commentId and c.isDeleted = false")
    Optional<LyricReplyComment> getReplyByIdAndAccountIdAndIsDeletedIsFalse(Long replyId, Long accountId, Long commentId);
}
