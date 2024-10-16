package com.learning.tinderaibackend.matches;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.learning.tinderaibackend.notification.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final NotificationService notificationService;

    public void processMatch(String profileId1, String profileId2) {
        Match match = new Match(
                UUID.randomUUID().toString(),
                profileId1,
                profileId2,
                LocalDateTime.now());

        matchRepository.save(match);

        // Notify both users of the match
        notificationService.sendMatchNotification(profileId1, profileId2);
    }

    public List<MatchResponse> getMatches(String profileId) {
        List<Match> matches = matchRepository.findMatchesByProfileId(profileId);
        return matches.stream()
                .map(match -> new MatchResponse(
                        match.matchId(),
                        match.profileId1(),
                        match.profileId2(),
                        match.matchedAt()
                ))
                .collect(Collectors.toList());
    }

}
