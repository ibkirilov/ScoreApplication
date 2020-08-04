package webproject.score.web.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import webproject.score.event.PlayMatchPublisher;
import webproject.score.services.services.MatchesService;

@Controller
public class PlayController {
    private final MatchesService matchesService;
    private final PlayMatchPublisher playMatchPublisher;

    @Autowired
    public PlayController(MatchesService matchesService, PlayMatchPublisher playMatchPublisher) {
        this.matchesService = matchesService;
        this.playMatchPublisher = playMatchPublisher;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/play")
    public String playNextMatches() {
        this.matchesService.play();
        playMatchPublisher.makeChangesAfterTheMatches();

        return "redirect:/team/matches";
    }
}
