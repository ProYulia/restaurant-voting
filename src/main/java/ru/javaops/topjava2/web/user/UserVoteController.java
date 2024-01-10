package ru.javaops.topjava2.web.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.to.VoteRequestTo;
import ru.javaops.topjava2.to.VoteResponseTo;

import java.net.URI;

@RestController
@RequestMapping(value = UserVoteController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "vote")
public class UserVoteController {
    static final String REST_URL = "/api/vote";
    private final VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteResponseTo> create(@Valid @RequestBody VoteRequestTo voteTo, int userId) {

        VoteResponseTo vote = service.create(voteTo, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource)
                .body(vote);
    }
}
