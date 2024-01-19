package com.github.proyulia.web.user;

import com.github.proyulia.service.VoteService;
import com.github.proyulia.to.VoteTo;
import com.github.proyulia.web.AuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = UserVoteController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "user-vote-controller")
public class UserVoteController {
    static final String REST_URL = "/api/vote";

    private final VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@AuthenticationPrincipal AuthUser authUser,
                                         @Valid @RequestBody VoteTo voteTo) {
        VoteTo vote = service.create(voteTo, authUser);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource)
                .body(vote);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @Valid @RequestBody VoteTo voteTo) {
        int userId = authUser.id();
        service.update(voteTo, userId);
    }
}
