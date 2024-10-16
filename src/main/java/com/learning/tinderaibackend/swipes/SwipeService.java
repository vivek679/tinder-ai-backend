package com.learning.tinderaibackend.swipes;

import static com.learning.tinderaibackend.enums.Action.LIKED;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.learning.tinderaibackend.matches.MatchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SwipeService {

    private final MatchService matchService;
    private final SwipeRepository swipeRepository;

    public void recordSwipe(SwipeRequest swipeRequest) {

        Swipe swipe = new Swipe(
                UUID.randomUUID().toString(),
                swipeRequest.fromProfileId(),
                swipeRequest.toProfileId(),
                swipeRequest.action(),
                LocalDateTime.now()
        );
        swipeRepository.save(swipe);

        // If swipe is 'like', check for a potential match
        if (LIKED.equals(swipeRequest.action())) {
            // profileId1 has liked profileId2 ==>> check if profile2 has like back profile1
            boolean isMutualLike = swipeRepository.existsByFromProfileIdAndToProfileId(
                    swipeRequest.toProfileId(), swipeRequest.fromProfileId()
            );

            if (isMutualLike) {
                // Process the match asynchronously
                matchService.processMatch(swipeRequest.fromProfileId(), swipeRequest.toProfileId());
            }
        }
    }

    public List<String> getLikedProfiles(String profileId) {
        return swipeRepository.findLikedProfilesByProfile(profileId)
                .stream()
                .filter(swipe -> !StringUtils.isEmpty(swipe.fromProfileId()))
                .map(Swipe::toProfileId)
                .collect(Collectors.toList());
    }
}
