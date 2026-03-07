package com.sergio.hotelsearch.adapter.input.rest;
import com.sergio.hotelsearch.adapter.input.rest.dto.SearchRequestDTO;
import com.sergio.hotelsearch.adapter.input.rest.dto.SearchResponseDTO;
import com.sergio.hotelsearch.application.usecase.CreateSearchUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final CreateSearchUseCase createSearchUseCase;

    public SearchController(CreateSearchUseCase createSearchUseCase) {
        this.createSearchUseCase = createSearchUseCase;
    }

    @PostMapping
    public SearchResponseDTO search(@Valid @RequestBody SearchRequestDTO request) {

        String searchId = createSearchUseCase.execute(request.toDomain());

        return new SearchResponseDTO(searchId);
    }
}