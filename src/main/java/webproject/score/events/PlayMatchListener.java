package webproject.score.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import webproject.score.services.services.LeaguesService;
import webproject.score.services.services.MatchesService;
import webproject.score.services.services.PlayersService;

@Component
public class PlayMatchListener {
    private final LeaguesService leaguesService;
    private final MatchesService matchesService;
    private final PlayersService playersService;

    @Autowired
    public PlayMatchListener(LeaguesService leaguesService, MatchesService matchesService, PlayersService playersService) {
        this.leaguesService = leaguesService;
        this.matchesService = matchesService;
        this.playersService = playersService;
    }

    @EventListener(PlayMatchEvent.class)
    public void onMatchPlayed() {
        this.leaguesService.roundPlayed();

        if (leaguesService.getRoundsPlayed() == 6) {
            matchesService.generateNext6Rounds();
        }

        this.playersService.changePlayersFormAfterTheMatches();
    }
}
