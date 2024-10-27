package com.learning.tinderaibackend.blocked;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.tinderaibackend.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/blocked-profiles")
public class BlockedProfileController {

    private final BlockedProfileService blockedProfileService;

    @PostMapping(path = "/{profileId}/block")
    public ResponseEntity<?> blockProfile(@PathVariable(name = "profileId") String profileId,
                                          @RequestBody Map<String, String> blockProfileRequest) {
        if(!blockProfileRequest.containsKey("blockProfileId")) {
            throw new BadRequestException("blockProfileId required");
        }
        logger.info("Blocking profileId: {}", blockProfileRequest.get("blockProfileId"));
        blockedProfileService.addProfileToBlockedList(profileId, blockProfileRequest.get("blockProfileId"));
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{profileId}")
    public ResponseEntity<?> getAllBlockProfiles(@PathVariable(name = "profileId") String profileId) {
        return ResponseEntity.ok(Map.of("blockedProfiles", blockedProfileService.getBlockedList(profileId)));
    }

}
