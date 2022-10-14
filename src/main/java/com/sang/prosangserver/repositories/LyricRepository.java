package com.sang.prosangserver.repositories;

import com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo;
import com.sang.prosangserver.dto.response.lyric.LyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricListItem;
import com.sang.prosangserver.models.LyricDetailModel;
import com.sang.prosangserver.models.LyricRepliedCommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sang.prosangserver.entities.lyric.Lyric;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface LyricRepository extends JpaRepository<Lyric, Long> {

    @Query("select new com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse(lyric.id, lyric.title, lyric.content, lyric.description, lyric.rate, lyric.status, lyric.isDeleted, lyric.createdDate, lyric.updatedDate) " +
            "from Lyric lyric where lyric.account is not null and lyric.account.id = :accountId and lyric.isDeleted = false")
    Page<AccountLyricItemResponse> findAllByCurrentUserId(Pageable pageable, Long accountId);

    @Query("select new com.sang.prosangserver.dto.response.lyric.LyricItemResponse(lyric.id, lyric.title, lyric.content, lyric.description, lyric.stars, lyric.createdDate, lyric.updatedDate) " +
        "from Lyric lyric where lyric.account is not null and lyric.account.id = :accountId and lyric.isDeleted = false and lyric.status = com.sang.prosangserver.enums.lyric.LyricStatuses.PUBLISH")
    List<LyricItemResponse> findAllByAccountId(Long accountId);

    @Query("from Lyric l where l.id = :id and (l.account.id = :accountId or (l.isDeleted = false and l.status = com.sang.prosangserver.enums.lyric.LyricStatuses.PUBLISH))")
    Optional<Lyric> findLyricWithCurrentAccountId(Long id, Long accountId);

    @Query("from Lyric l where l.id = :id and l.isDeleted = false and l.status = com.sang.prosangserver.enums.lyric.LyricStatuses.PUBLISH")
    Optional<Lyric> findLyricWithId(Long id);
    @Query("from Lyric l where (l.title = :ref) and l.isDeleted = false and l.status = com.sang.prosangserver.enums.lyric.LyricStatuses.PUBLISH")
    Optional<Lyric> findLyricWithRef(String ref);

    @Query("select new com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo(acc.id, acc.username, acc.email) from Account acc where acc.id IN (:ids)")
    List<LyricCommentUserInfo> getLyricCommentUserInfoList(Set<Long> ids);

    @Query(name = "lyric_detail_query", nativeQuery = true)
    Optional<LyricDetailModel> getLyricDetailById(@Param("lyricId") Long lyricId, @Param("limit") Integer limit,
                                                  @Param("accountId") Long accountId, @Param("likeAccountId") String likeAccountId);

    @Query(name = "lyric_detail_query_by_title", nativeQuery = true)
    Optional<LyricDetailModel> getLyricDetailByTitle(@Param("title") String title, @Param("limit") Integer limit,
                                                  @Param("accountId") Long accountId, @Param("likeAccountId") String likeAccountId);

    @Query(name = "lyric_replied_comment_query", nativeQuery = true)
    List<LyricRepliedCommentModel> getRepliedComments(@Param("commentId") Long lyricId, @Param("limit") Integer limit, @Param("offset") Integer offset, @Param("accountId") Long accountId);

    Optional<Lyric> getByIdAndAccountIdAndIsDeletedIsFalse(Long id, Long accountId);
    Optional<Lyric> getByIdAndIsDeletedIsFalse(Long id);

    Boolean existsByIdAndIsDeletedIsFalse(Long id);

    @Query("FROM Lyric l WHERE lower(l.title) = lower(:title) and l.isDeleted = false")
    Lyric existsByTitleAndIsDeletedIsFalse(String title);
    @Query("FROM Lyric l WHERE lower(l.title) = lower(:title) and l.id <> :id and l.isDeleted = false")
    Lyric existsByTitleAndIdIsNotEqualsAndIsDeletedIsFalse(String title, Long id);

    @Query("SELECT new com.sang.prosangserver.dto.response.lyric.LyricListItem(lyric.id, lyric.title, lyric.rate, lyric.status, lyric.lyricComments.size, lyric.isDeleted, lyric.createdDate, lyric.updatedDate" +
        ", lyric.account.id, lyric.account.username, lyric.account.email, lyric.account.detail.accountPhotoUrl, lyric.account.detail.firstName, lyric.account.detail.lastName) " +
        "from Lyric lyric " +
        "WHERE lyric.isDeleted = false AND lyric.status = com.sang.prosangserver.enums.lyric.LyricStatuses.PUBLISH" +
        " AND " +
        "(:searchText is null OR :searchText = '' OR CONCAT(case when lyric.account.detail.firstName is null then '' else lyric.account.detail.firstName end, ' ',case when lyric.account.detail.lastName is null then '' else lyric.account.detail.lastName end) like :searchText " +
        "OR lyric.title like :searchText OR lyric.account.username like :searchText OR lyric.account.email like :searchText) ")
    Page<LyricListItem> getPublicLyricList(Pageable pageable, String searchText);
}
