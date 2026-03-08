package com.sergio.hotelsearch.adapter.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergio.hotelsearch.application.usecase.CreateSearchUseCase;
import com.sergio.hotelsearch.application.usecase.CountSearchUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        when(createSearchUseCase.execute(org.mockito.ArgumentMatchers.any()))
                .thenReturn("123");

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
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCount() throws Exception {

        when(countSearchUseCase.execute("123")).thenReturn(10L);

        mockMvc.perform(get("/search/count")
                        .param("searchId","123"))
                .andExpect(status().isOk());
    }
}