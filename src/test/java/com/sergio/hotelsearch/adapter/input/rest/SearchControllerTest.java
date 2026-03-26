package com.sergio.hotelsearch.adapter.input.rest;

import com.sergio.hotelsearch.application.usecase.CountSearchUseCase;
import com.sergio.hotelsearch.application.usecase.CreateSearchUseCase;
import com.sergio.hotelsearch.domain.exception.DomainValidationException;
import com.sergio.hotelsearch.domain.exception.SearchNotFoundException;
import com.sergio.hotelsearch.domain.model.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

        private static final String VALID_BODY = """
                        {
                          "hotelId": "hotel1",
                          "checkIn": "10/01/2025",
                          "checkOut": "12/01/2025",
                          "ages": [30]
                        }
                        """;

        private static final String INVALID_HOTEL_BODY = """
                        {
                          "hotelId": "",
                          "checkIn": "10/01/2025",
                          "checkOut": "12/01/2025",
                          "ages": [30]
                        }
                        """;

        private static final String INVERTED_DATES_BODY = """
                        {
                          "hotelId": "hotel1",
                          "checkIn": "12/01/2025",
                          "checkOut": "10/01/2025",
                          "ages": [30]
                        }
                        """;

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private CreateSearchUseCase createSearchUseCase;

        @MockitoBean
        private CountSearchUseCase countSearchUseCase;

        private Search search;

        @BeforeEach
        void setUp() {
                search = new Search(
                                "123",
                                "hotel1",
                                LocalDate.of(2025, 1, 10),
                                LocalDate.of(2025, 1, 12),
                                List.of(30));
        }

        @Test
        void shouldCreateSearch() throws Exception {
                when(createSearchUseCase.execute(any())).thenReturn("123");

                mockMvc.perform(post("/search")
                                .contentType("application/json")
                                .content(VALID_BODY))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.searchId").value("123"));
        }

        @Test
        void shouldReturnCount() throws Exception {
                when(countSearchUseCase.execute("123"))
                                .thenReturn(new CountSearchUseCase.Result(search, 10L));

                mockMvc.perform(get("/count").param("searchId", "123"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.searchId").value("123"))
                                .andExpect(jsonPath("$.count").value(10))
                                .andExpect(jsonPath("$.search.hotelId").value("hotel1"));
        }

        @Test
        void shouldReturnBadRequestWhenBodyIsInvalid() throws Exception {
                mockMvc.perform(post("/search")
                                .contentType("application/json")
                                .content(INVALID_HOTEL_BODY))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnNotFoundWhenSearchDoesNotExist() throws Exception {
                when(countSearchUseCase.execute("999"))
                                .thenThrow(new SearchNotFoundException("999"));

                mockMvc.perform(get("/count").param("searchId", "999"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.error").value("Search not found for id=999"));
        }

        @Test
        void shouldReturnBadRequestOnDomainValidation() throws Exception {
                when(createSearchUseCase.execute(any()))
                                .thenThrow(new DomainValidationException("checkIn must be before checkOut"));

                mockMvc.perform(post("/search")
                                .contentType("application/json")
                                .content(INVERTED_DATES_BODY))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.error").value("checkIn must be before checkOut"));
        }
}
