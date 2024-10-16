package com.learning.tinderaibackend.swipes;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.tinderaibackend.validation.SwipeValidationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/swipes")
@RequiredArgsConstructor
public class SwipeController {

    private final SwipeService swipeService;
    private final SwipeValidationService swipeValidationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> recordSwipe(@RequestBody SwipeRequest swipeRequest) {
        // Validation
        swipeValidationService.validateProfileExists(swipeRequest.toProfileId(),swipeRequest.fromProfileId());
        swipeService.recordSwipe(swipeRequest);
        return ResponseEntity.ok("Swipe recorded");
    }

    @GetMapping(path = "/{profileId}/likes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getLikedProfiles(@PathVariable String profileId) {
        List<String> likedProfiles = swipeService.getLikedProfiles(profileId);
        return ResponseEntity.ok(likedProfiles);
    }

}
