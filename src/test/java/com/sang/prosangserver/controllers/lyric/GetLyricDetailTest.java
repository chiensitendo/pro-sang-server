package com.sang.prosangserver.controllers.lyric;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricCommentItem;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricListAccountResponse;
import com.sang.prosangserver.interfaces.LyricDetailTestInterface;
import com.sang.prosangserver.models.AuthUser;
import com.sang.prosangserver.models.JWTToken;
import com.sang.prosangserver.services.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/scripts/test-account.sql","/scripts/lyric-detail.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/scripts/post-lyric-detail.sql", "/scripts/post-test-account.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetLyricDetailTest implements LyricDetailTestInterface {

    @Autowired
    JwtService jwtService;

    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private final String username1 = "test_account";


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();
    }

//    @Test
//    @Override
//    public void testGetOwnLyric() throws Exception {
//        JWTToken token = generateTokenAndSetAuthenticationContext(username1 ,100L,"Test123456");
//        MvcResult getOwnLyricListResult = this.mockMvc
//            .perform(get("/lyric/list")
//                .with(userToken(token.getToken())))
//            .andExpect(status().isOk())
//            .andDo(print()).andReturn();
//        LyricListAccountResponse lyricListResponse = getBody(getOwnLyricListResult, LyricListAccountResponse.class);
//        testLyricListResponse(EXPECTED_OWN_LYRIC_LIST_1, lyricListResponse, 3);
//    }
//
//    @Test
//    @Override
//    public void testGetLyricListByAccount() throws Exception {
//        JWTToken token = generateTokenAndSetAuthenticationContext(username1 ,100L,"Test123456");
//        Long anotherAccId = 101L;
//        MvcResult resultByCurrId = this.mockMvc
//            .perform(get(String.format("/lyric/%d/list", 100L))
//                .with(userToken(token.getToken())))
//            .andExpect(status().isOk())
//            .andDo(print()).andReturn();
//
//        LyricListAccountResponse currLyricListResponse = getBody(resultByCurrId, LyricListAccountResponse.class);
//        testLyricListResponse(EXPECTED_OWN_LYRIC_LIST_1, currLyricListResponse, 3);
//
//        MvcResult resultByAnotherId = this.mockMvc
//            .perform(get(String.format("/lyric/%d/list", anotherAccId))
//                .with(userToken(token.getToken())))
//            .andExpect(status().isOk())
//            .andDo(print()).andReturn();
//
//        LyricListAccountResponse lyricListResponse = getBody(resultByAnotherId, LyricListAccountResponse.class);
//        testLyricListResponse(EXPECTED_OWN_LYRIC_LIST_2, lyricListResponse, 1);
//
//        Long notFoundAccId = 102L;
//        this.mockMvc
//            .perform(get(String.format("/lyric/%d/list", notFoundAccId))
//                .with(userToken(token.getToken())))
//            .andExpect(status().is4xxClientError());
//    }

    @Test
    @Override
    public void testGetOwnLyricDetail() throws Exception {
        JWTToken token = generateTokenAndSetAuthenticationContext(username1 ,100L,"Test123456");
        Long lyricId = 101L;
        MvcResult result = this.mockMvc
            .perform(get(String.format("/lyric/detail/%d", lyricId))
                .with(userToken(token.getToken())))
            .andExpect(status().isOk())
            .andDo(print()).andReturn();
        LyricDetailResponse response = getBody(result, LyricDetailResponse.class);
        assertThat(response).isNotNull();
        testLyricDetailResponse(EXPECTED_OWN_LYRIC_DETAIL_1, response);

        Long hiddenLyricId = 112L;
        this.mockMvc
            .perform(get(String.format("/lyric/detail/%d", hiddenLyricId))
                .with(userToken(token.getToken())))
            .andExpect(status().is4xxClientError());

        Long notFoundLyricId = 222L;
        this.mockMvc
            .perform(get(String.format("/lyric/detail/%d", notFoundLyricId))
                .with(userToken(token.getToken())))
            .andExpect(status().is4xxClientError());
    }

    private void testLyricListResponse(LyricListAccountResponse expectedLyricListResponse, LyricListAccountResponse actualLyricListResponse, int expectedSize) {
        assertThat(actualLyricListResponse).isNotNull();
        assertThat(actualLyricListResponse.getLyrics()).hasSize(expectedSize);
        compareLyricListResponse(expectedLyricListResponse, actualLyricListResponse);
    }

    private void testLyricDetailResponse(LyricDetailResponse expectedLyricDetailResponse, LyricDetailResponse actualLyricDetailResponse) {
        assertThat(actualLyricDetailResponse.getComments()).hasSize(expectedLyricDetailResponse.getComments().size());
        assertThat(actualLyricDetailResponse.getId()).isEqualTo(actualLyricDetailResponse.getId());
        assertThat(actualLyricDetailResponse.getContent()).isEqualTo(expectedLyricDetailResponse.getContent());
        assertThat(actualLyricDetailResponse.getDescription()).isEqualTo(expectedLyricDetailResponse.getDescription());
        assertThat(actualLyricDetailResponse.getStars()).isEqualTo(expectedLyricDetailResponse.getStars());
        assertThat(actualLyricDetailResponse.getStatus()).isEqualTo(expectedLyricDetailResponse.getStatus());
        assertThat(actualLyricDetailResponse.getTitle()).isEqualTo(expectedLyricDetailResponse.getTitle());
        assertThat(isApproximateLocalDateTime(actualLyricDetailResponse.getCreatedDate(), expectedLyricDetailResponse.getCreatedDate())).isTrue();
        assertThat(isApproximateLocalDateTime(actualLyricDetailResponse.getUpdatedDate(), expectedLyricDetailResponse.getUpdatedDate())).isTrue();
        for (int i = 0; i < expectedLyricDetailResponse.getComments().size(); i++) {
            LyricCommentItem expectedComment = expectedLyricDetailResponse.getComments().get(i);
            LyricCommentItem actualComment = actualLyricDetailResponse.getComments().get(i);
            compareLyricCommentItem(expectedComment, actualComment);
        }
    }

    private void compareLyricCommentItem(LyricCommentItem expectedLyricCommentItem, LyricCommentItem actualLyricCommentItem) {
        assertThat(actualLyricCommentItem.getContent()).isEqualTo(expectedLyricCommentItem.getContent());
        assertThat(actualLyricCommentItem.getId()).isEqualTo(expectedLyricCommentItem.getId());
        assertThat(actualLyricCommentItem.getStars()).isEqualTo(expectedLyricCommentItem.getStars());
        assertThat(actualLyricCommentItem.getLikes()).isEqualTo(expectedLyricCommentItem.getLikes());
        assertThat(actualLyricCommentItem.getIsAnonymous()).isEqualTo(expectedLyricCommentItem.getIsAnonymous());
        assertThat(actualLyricCommentItem.getUserInfo()).isNotNull();
        assertThat(actualLyricCommentItem.getUserInfo().getUsername()).isEqualTo(expectedLyricCommentItem.getUserInfo().getUsername());
        if (!expectedLyricCommentItem.getIsAnonymous()) {
            assertThat(actualLyricCommentItem.getUserInfo().getId()).isEqualTo(expectedLyricCommentItem.getUserInfo().getId());
            assertThat(actualLyricCommentItem.getUserInfo().getEmail()).isEqualTo(expectedLyricCommentItem.getUserInfo().getEmail());
        }
        assertThat(actualLyricCommentItem.getCreatedDate()).isEqualTo(expectedLyricCommentItem.getCreatedDate());
        assertThat(isApproximateLocalDateTime(actualLyricCommentItem.getUpdatedDate(), expectedLyricCommentItem.getUpdatedDate())).isTrue();
    }

    private JWTToken generateTokenAndSetAuthenticationContext(String username, Long accountId, String password) {
        JWTToken token = jwtService.generateAccessToken(username1);
        Assertions.assertEquals(username, jwtService.getUsernameFromToken(token.getToken()));
        setAuthenticationContext(username, password, accountId, token.getToken());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertNotNull(auth, "authentication context is set");
        return token;
    }

    private void setAuthenticationContext(String username, String password, Long id, String jwtToken) {

        UserDetails userDetails = createUserDetails(username, password, id);
        Assertions.assertTrue(jwtService.validateToken(jwtToken, userDetails), "valid token");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    protected RequestPostProcessor userToken(String token) {
        return request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        };
    }

    protected <T> T getBody(MvcResult result, Class<T> clazz) throws Exception {

        String responseContent = result
            .getResponse()
            .getContentAsString(Charset.defaultCharset());
        GenericResponse genericResponse = objectMapper.readValue(responseContent, GenericResponse.class);
        T response = objectMapper.convertValue(genericResponse.getBody(), clazz);
        return response;
    }

    private UserDetails createUserDetails(String username, String password, Long id) {
        return new AuthUser(username, password, new ArrayList<>(), id);
    }

    private void compareLyricListResponse(LyricListAccountResponse expectedResponse, LyricListAccountResponse actualResponse) {
        assertThat(actualResponse.getLyrics().size()).isEqualTo(expectedResponse.getLyrics().size());
        for (int i = 0; i < actualResponse.getLyrics().size(); i++) {
            Object obj = expectedResponse.getLyrics().get(i);
            if (obj instanceof AccountLyricItemResponse) {
                AccountLyricItemResponse lyric = objectMapper.convertValue(actualResponse.getLyrics().get(i), AccountLyricItemResponse.class);
                AccountLyricItemResponse expectedLyric = (AccountLyricItemResponse) obj;
                compareWithAccountLyricItemResponse(expectedLyric, lyric);
            }
            if (obj instanceof LyricItemResponse) {
                LyricItemResponse lyric = objectMapper.convertValue(actualResponse.getLyrics().get(i), LyricItemResponse.class);
                LyricItemResponse expectedLyric = (LyricItemResponse) obj;
                compareWithLyricItemResponse(expectedLyric, lyric);
            }
        }
    }
    private void compareWithAccountLyricItemResponse(AccountLyricItemResponse expectedLyric, AccountLyricItemResponse actualLyric) {
        assertThat(actualLyric.getContent()).isEqualTo(expectedLyric.getContent());
        assertThat(actualLyric.getId()).isEqualTo(expectedLyric.getId());
        assertThat(actualLyric.getDescription()).isEqualTo(expectedLyric.getDescription());
        assertThat(actualLyric.getTitle()).isEqualTo(expectedLyric.getTitle());
        assertThat(actualLyric.getRate()).isEqualTo(expectedLyric.getRate());
        assertThat(actualLyric.getIsDeleted()).isEqualTo(expectedLyric.getIsDeleted());
        assertThat(actualLyric.getStatus()).isEqualTo(expectedLyric.getStatus());
        assertThat(actualLyric.getCreatedDate()).isNotNull();
        assertThat(actualLyric.getUpdatedDate()).isNotNull();
        assertThat(isApproximateLocalDateTime(expectedLyric.getCreatedDate(), actualLyric.getCreatedDate())).isTrue();
        assertThat(isApproximateLocalDateTime(expectedLyric.getUpdatedDate(), actualLyric.getUpdatedDate())).isTrue();
    }

    private void compareWithLyricItemResponse(LyricItemResponse expectedLyric, LyricItemResponse actualLyric) {
        assertThat(actualLyric.getContent()).isEqualTo(expectedLyric.getContent());
        assertThat(actualLyric.getId()).isEqualTo(expectedLyric.getId());
        assertThat(actualLyric.getDescription()).isEqualTo(expectedLyric.getDescription());
        assertThat(actualLyric.getTitle()).isEqualTo(expectedLyric.getTitle());
        assertThat(actualLyric.getStars()).isEqualTo(expectedLyric.getStars());
        assertThat(actualLyric.getCreatedDate()).isNotNull();
        assertThat(actualLyric.getUpdatedDate()).isNotNull();
        assertThat(isApproximateLocalDateTime(expectedLyric.getCreatedDate(), actualLyric.getCreatedDate())).isTrue();
        assertThat(isApproximateLocalDateTime(expectedLyric.getUpdatedDate(), actualLyric.getUpdatedDate())).isTrue();
    }

    private boolean isApproximateLocalDateTime(LocalDateTime expected, LocalDateTime actual) {
        String actualString = String.format("%d-%d-%d-%d",actual.getYear(), actual.getMonthValue(), actual.getDayOfYear(), actual.getHour());
        String expectedString = String.format("%d-%d-%d-%d",expected.getYear(), expected.getMonthValue(), expected.getDayOfYear(), expected.getHour());

        return actualString.equals(expectedString);
    }
}
