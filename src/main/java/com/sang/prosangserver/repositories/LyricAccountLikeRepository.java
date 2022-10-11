package com.sang.prosangserver.repositories;

import com.sang.prosangserver.entities.lyric.LyricAccountLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface LyricAccountLikeRepository extends JpaRepository<LyricAccountLike, Long> {

    Optional<LyricAccountLike> getLyricAccountLikeByCommentIdAndAccountIdAndIsDeletedIsFalse(Long commentId, Long accountId);

    Boolean existsByCommentIdAndAccountIdAndIsDeletedIsFalse(Long commentId, Long accountId);

    Boolean existsByCommentIdAndAccountIdAndReplyIdAndIsDeletedIsFalse(Long commentId, Long accountId, Long replyId);

    void deleteByCommentIdAndAccountIdAndIsDeletedIsFalse(Long commentId, Long accountId);

    void deleteByCommentIdAndAccountIdAndReplyIdAndIsDeletedIsFalse(Long commentId, Long accountId, Long replyId);
}
