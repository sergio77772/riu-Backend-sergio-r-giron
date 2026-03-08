package com.sergio.hotelsearch.adapter.input.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergio.hotelsearch.application.usecase.CountSearchUseCase;
import com.sergio.hotelsearch.application.usecase.CreateSearchUseCase;
import com.sergio.hotelsearch.domain.model.Search;
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

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateSearchUseCase createSearchUseCase;

    @MockitoBean
    private CountSearchUseCase countSearchUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateSearch() throws Exception {

        when(createSearchUseCase.execute(any())).thenReturn("123");

        String body = """
                {
                  "hotelId": "hotel1",
                  "checkIn": "10/01/2025",
                  "checkOut": "12/01/2025",
                  "ages": [30]
                }
                """;

        mockMvc.perform(post("/search")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("123"));
    }

    @Test
    void shouldReturnCount() throws Exception {

        Search search = new Search(
                "123",
                "hotel1",
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 12),
                List.of(30)
        );

        when(countSearchUseCase.execute("123"))
                .thenReturn(new CountSearchUseCase.Result(search, 10L));

        mockMvc.perform(get("/count")
                        .param("searchId", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("123"))
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.search.hotelId").value("hotel1"));
    }
}