package webproject.score.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import webproject.score.services.services.LeaguesService;
import webproject.score.services.services.MatchesService;
import webproject.score.services.services.RolesService;
import webproject.score.services.services.TeamsService;

@Component
public class DataInit implements CommandLineRunner {
    private final LeaguesService leaguesService;
    private final RolesService rolesService;
    private final TeamsService teamsService;
    private final MatchesService matchesService;

    @Autowired
    public DataInit(LeaguesService leaguesService, RolesService rolesService, TeamsService teamsService, MatchesService matchesService) {
        this.leaguesService = leaguesService;
        this.rolesService = rolesService;
        this.teamsService = teamsService;
        this.matchesService = matchesService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.leaguesService.initCreateLeagues();
        this.teamsService.initCreateTeams();
        this.matchesService.initGenerateMatches();
        this.rolesService.initRoles();
    }
}
