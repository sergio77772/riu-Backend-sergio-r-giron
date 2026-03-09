package com.sergio.hotelsearch.adapter.input.rest;

import com.sergio.hotelsearch.adapter.input.rest.dto.CountResponseDTO;
import com.sergio.hotelsearch.adapter.input.rest.dto.SearchRequestDTO;
import com.sergio.hotelsearch.adapter.input.rest.dto.SearchResponseDTO;
import com.sergio.hotelsearch.application.usecase.CountSearchUseCase;
import com.sergio.hotelsearch.application.usecase.CreateSearchUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Search API", description = "Hotel search operations")
public class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    private final CreateSearchUseCase createSearchUseCase;
    private final CountSearchUseCase countSearchUseCase;

    public SearchController(CreateSearchUseCase createSearchUseCase,
            CountSearchUseCase countSearchUseCase) {
        this.createSearchUseCase = createSearchUseCase;
        this.countSearchUseCase = countSearchUseCase;
    }

    @PostMapping("/search")
    @Operation(summary = "Create a hotel search request")
    public SearchResponseDTO search(@Valid @RequestBody SearchRequestDTO request) {

        log.info("Received search request for hotelId={}", request.hotelId());

        String searchId = createSearchUseCase.execute(request.toDomain());

        log.info("Search created with id={}", searchId);

        return new SearchResponseDTO(searchId);
    }

    @GetMapping("/count")
    @Operation(summary = "Get number of searches matching the given searchId")
    public CountResponseDTO count(@RequestParam("searchId") String searchId) {

        log.info("Counting searches for searchId={}", searchId);

        CountSearchUseCase.Result result = countSearchUseCase.execute(searchId);

        log.info("Count result for searchId={} is {}", searchId, result.count());

        return CountResponseDTO.from(searchId, result.search(), result.count());
    }
}
