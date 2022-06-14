package com.sang.prosangserver.repositories;

import com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo;
import com.sang.prosangserver.dto.response.lyric.LyricItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sang.prosangserver.entities.lyric.Lyric;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface LyricRepository extends JpaRepository<Lyric, Long> {

    @Query("select new com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse(lyric.id, lyric.title, lyric.content, lyric.description, lyric.stars, lyric.status, lyric.isDeleted, lyric.createdDate, lyric.updatedDate) " +
            "from Lyric lyric where lyric.account is not null and lyric.account.id = :accountId")
    List<AccountLyricItemResponse> findAllByCurrentUserId(Long accountId);

    @Query("select new com.sang.prosangserver.dto.response.lyric.LyricItemResponse(lyric.id, lyric.title, lyric.content, lyric.description, lyric.stars, lyric.createdDate, lyric.updatedDate) " +
        "from Lyric lyric where lyric.account is not null and lyric.account.id = :accountId and lyric.isDeleted = false and lyric.status = com.sang.prosangserver.enums.lyric.LyricStatuses.PUBLISH")
    List<LyricItemResponse> findAllByAccountId(Long accountId);

    @Query("from Lyric l where l.id = :id and (l.account.id = :accountId or (l.isDeleted = false and l.status = com.sang.prosangserver.enums.lyric.LyricStatuses.PUBLISH))")
    Optional<Lyric> findLyricWithCurrentAccountId(Long id, Long accountId);

    @Query("select new com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo(acc.id, acc.username, acc.email) from Account acc where acc.id IN (:ids)")
    List<LyricCommentUserInfo> getLyricCommentUserInfoList(Set<Long> ids);

}
