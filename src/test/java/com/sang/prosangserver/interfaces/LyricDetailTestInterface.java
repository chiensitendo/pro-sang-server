package com.sang.prosangserver.interfaces;

import com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricCommentItem;
import com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricListAccountResponse;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import com.sang.prosangserver.utils.DatetimeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface LyricDetailTestInterface {

    LyricListAccountResponse EXPECTED_OWN_LYRIC_LIST_1 = getExpectedOwnLyricList1();
    LyricListAccountResponse EXPECTED_OWN_LYRIC_LIST_2 = getExpectedOwnLyricList2();

    LyricDetailResponse EXPECTED_OWN_LYRIC_DETAIL_1 = getExpectedOwnLyricDetail1();

//    void testGetOwnLyric() throws Exception;
//
//    void testGetLyricListByAccount() throws Exception;

    void testGetOwnLyricDetail() throws Exception;

    static LyricDetailResponse getExpectedOwnLyricDetail1() {

        List<LyricCommentItem> comments = new ArrayList<>();
        comments.add(new LyricCommentItem(1011L, "This is great!! from No Name 1", null, null, new LyricCommentUserInfo("No Name 1"), true, DatetimeUtils.parseFullStringToLocalDateTIme("2022-06-13 12:00:20"), LocalDateTime.now()));

        comments.add(new LyricCommentItem(1012L, "Hi! I am User 2", 1L, null, new LyricCommentUserInfo(101L,"test_account_2", "test_account_2@gmail.com"), false, DatetimeUtils.parseFullStringToLocalDateTIme("2022-06-13 14:05:01"), LocalDateTime.now()));

        return LyricDetailResponse.builder()
            .id(101L)
            .content("Lyric content 1")
            .isDeleted(false)
            .status(LyricStatuses.PUBLISH)
            .description("Lyric Description 1")
            .title("Lyric Title 1 of User 1")
            .stars(4.)
            .comments(comments)
            .createdDate(LocalDateTime.now())
            .updatedDate(LocalDateTime.now()).
            build();
    }


    static LyricListAccountResponse getExpectedOwnLyricList1() {

        List<AccountLyricItemResponse> ownLyricList = new ArrayList<>();
        ownLyricList.add(new AccountLyricItemResponse(103L, "Lyric Title 3 of User 1", "Lyric content 3", "Lyric Description 3", 2.5, LyricStatuses.PUBLISH, false, LocalDateTime.now(), LocalDateTime.now()));
//        ownLyricList.add(new AccountLyricItemResponse(104L, "Deleted Lyric Title of User 1", "Lyric content 4", "Lyric Description 4", 0., null, true, LocalDateTime.now(), LocalDateTime.now()));
        ownLyricList.add(new AccountLyricItemResponse(102L, "Lyric Title 2 of User 1", "Lyric content 2", "Lyric Description 2", 3.5, LyricStatuses.HIDDEN, false, LocalDateTime.now(), LocalDateTime.now()));
        ownLyricList.add(new AccountLyricItemResponse(101L, "Lyric Title 1 of User 1", "Lyric content 1", "Lyric Description 1", 4., LyricStatuses.PUBLISH, false, LocalDateTime.now(), LocalDateTime.now()));
        return LyricListAccountResponse
                .builder()
                .accountId(100L)
                .lyrics(ownLyricList)
                .build();
    }

    static LyricListAccountResponse getExpectedOwnLyricList2() {

        List<LyricItemResponse> lyricList = new ArrayList<>();
        lyricList.add(new LyricItemResponse(111L, "Lyric Title 1 of User 2", "Lyric content 1", "Lyric Description 1", 4.4, LocalDateTime.now(), LocalDateTime.now()));
        return LyricListAccountResponse
            .builder()
            .accountId(101L)
            .lyrics(lyricList)
            .build();
    }


}
