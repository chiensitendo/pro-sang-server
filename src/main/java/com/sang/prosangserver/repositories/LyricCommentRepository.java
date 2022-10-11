package com.sang.prosangserver.repositories;

import com.sang.prosangserver.entities.lyric.LyricComment;
import com.sang.prosangserver.models.LyricLMCommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LyricCommentRepository extends JpaRepository<LyricComment, Long>  {

    Boolean existsByIdAndAccountIdAndIsDeletedIsFalse(Long id, Long account);

    Optional<LyricComment> getLyricCommentByIdAndAccountIdAndIsDeletedIsFalse(Long id, Long accountId);
    Optional<LyricComment> getByIdAndIsDeletedIsFalse(Long id);

    Boolean existsByIdAndIsDeletedIsFalse(Long id);

    @Query(name = "lyric_load_more_comment_query")
    List<LyricLMCommentModel> loadMoreCommentByLyricId(Long lyricId, Long accountId, Integer limit, Long offset);
}
