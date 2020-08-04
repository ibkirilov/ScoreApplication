package webproject.score.web.view.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.score.services.models.LeaguesServiceModel;
import webproject.score.services.models.MatchServiceModel;
import webproject.score.services.models.PlayerServiceModel;
import webproject.score.services.models.TeamServiceModel;
import webproject.score.services.services.LeaguesService;
import webproject.score.services.services.MatchesService;
import webproject.score.services.services.PlayersService;
import webproject.score.services.services.TeamsService;
import webproject.score.web.view.models.MatchDetailsViewModel;
import webproject.score.web.view.models.MatchViewModel;
import webproject.score.web.view.models.StadiumViewModel;
import webproject.score.web.view.models.TeamViewModel;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/team")
public class TeamController {
    private final MatchesService matchesService;
    private final ModelMapper modelMapper;
    private final PlayersService playersService;
    private final TeamsService teamsService;
    private final LeaguesService leaguesService;

    @Autowired
    public TeamController(MatchesService matchesService, ModelMapper modelMapper, PlayersService playersService, TeamsService teamsService, LeaguesService leaguesService) {
        this.matchesService = matchesService;
        this.modelMapper = modelMapper;
        this.playersService = playersService;
        this.teamsService = teamsService;
        this.leaguesService = leaguesService;
    }

    @GetMapping("/info")
    public ModelAndView getTeamPage(ModelAndView modelAndView, Principal principal) {
        TeamServiceModel teamServiceModel = this.modelMapper.map(this.teamsService.getTeamByUsername(principal.getName()), TeamServiceModel.class);
        TeamViewModel teamViewModel = this.modelMapper.map(teamServiceModel, TeamViewModel.class);

        modelAndView.addObject("teamViewModel", teamViewModel);
        modelAndView.setViewName("team/team");
        return modelAndView;
    }

    @GetMapping("/players")
    public ModelAndView getMyPlayers(Principal principal, ModelAndView modelAndView) {
        String username = principal.getName();
        String teamId = this.teamsService.getTeamByUsername(username).getId();
        List<PlayerServiceModel> players = this.playersService.getPlayersByTeamId(teamId);
        players.sort(Comparator.comparing(PlayerServiceModel::getPosition));

        modelAndView.addObject("players", players);
        modelAndView.setViewName("team/players");
        return modelAndView;
    }

    @GetMapping("/matches")
    public ModelAndView getMyMatches(Principal principal, ModelAndView modelAndView) {

        String username = principal.getName();
        List<MatchServiceModel> matches = this.matchesService.getMatchesByUsername(username);
        List<MatchViewModel> matchesView = new ArrayList<>();
        matches
                .forEach(match -> {
                    MatchViewModel matchViewModel = new MatchViewModel();
                    matchViewModel.setId(match.getId());
                    matchViewModel.setHomeTeam(match.getHomeTeam().getName());
                    matchViewModel.setAwayTeam(match.getAwayTeam().getName());
                    matchViewModel.setHomeTeamPower(match.getHomeTeamPower());
                    matchViewModel.setAwayTeamPower(match.getAwayTeamPower());
                    matchViewModel.setStadium(match.getStadium().getName());
                    matchViewModel.setRound(match.getRound());
                    matchViewModel.setWinner(match.getWinner());
                    matchViewModel.setHomeGoals((int) match.getGoals()
                            .stream()
                            .distinct()
                            .filter(goal -> goal.getTeam() == match.getHomeTeam()).count());
                    matchViewModel.setAwayGoals((int) match.getGoals()
                            .stream()
                            .distinct()
                            .filter(goal -> goal.getTeam() == match.getAwayTeam()).count());

                    matchesView.add(matchViewModel);
                });
        matchesView.sort(Comparator.comparing(MatchViewModel::getRound));

        modelAndView.addObject("matches", matchesView);
        modelAndView.setViewName("/team/matches");
        return modelAndView;
    }

    @GetMapping("/match/{id}")
    public ModelAndView getMatch(@PathVariable("id") String id, ModelAndView modelAndView) {
        MatchServiceModel matchServiceModel = this.matchesService.getMatchById(id);
        List<String> homeScorers = matchServiceModel.getGoals()
                .stream()
                .distinct()
                .filter(goal -> goal.getTeam() == matchServiceModel.getHomeTeam())
                .map(goal -> goal.getPlayer().getFirstName() + " " + goal.getPlayer().getLastName())
                .collect(Collectors.toList());

        List<String> awayScorers = matchServiceModel.getGoals()
                .stream()
                .distinct()
                .filter(goal -> goal.getTeam() == matchServiceModel.getAwayTeam())
                .map(goal -> goal.getPlayer().getFirstName() + " " + goal.getPlayer().getLastName())
                .collect(Collectors.toList());


        MatchDetailsViewModel matchDetailsViewModel = new MatchDetailsViewModel();
        matchDetailsViewModel.setHomeTeam(matchServiceModel.getHomeTeam().getName());
        matchDetailsViewModel.setAwayTeam(matchServiceModel.getAwayTeam().getName());
        matchDetailsViewModel.setHomeTeamPower(matchServiceModel.getHomeTeamPower());
        matchDetailsViewModel.setAwayTeamPower(matchServiceModel.getAwayTeamPower());
        matchDetailsViewModel.setStadium(matchServiceModel.getStadium().getName());
        matchDetailsViewModel.setRound(matchServiceModel.getRound());
        matchDetailsViewModel.setWinner(matchServiceModel.getWinner());
        matchDetailsViewModel.setHomeGoals((int) matchServiceModel.getGoals()
                .stream()
                .distinct()
                .filter(goal -> goal.getTeam() == matchServiceModel.getHomeTeam()).count());
        matchDetailsViewModel.setAwayGoals((int) matchServiceModel.getGoals()
                .stream()
                .distinct()
                .filter(goal -> goal.getTeam() == matchServiceModel.getAwayTeam()).count());

        modelAndView.addObject("match", matchDetailsViewModel);
        modelAndView.addObject("homeScorers", homeScorers);
        modelAndView.addObject("awayScorers", awayScorers);
        modelAndView.setViewName("/team/match-details");
        return modelAndView;
    }

    @GetMapping("/group")
    public ModelAndView getMyLeague(Principal principal, ModelAndView modelAndView) {

        String username = principal.getName();
        LeaguesServiceModel leaguesServiceModel = this.leaguesService.getLeagueByTeam(this.teamsService.getTeamByUsername(username));
        List<TeamServiceModel> teamsFromGroup = teamsService.getTeamsByLeague(leaguesServiceModel);
        List<TeamViewModel> teams = teamsFromGroup
                .stream()
                .map(team -> this.modelMapper.map(team, TeamViewModel.class))
                .sorted(Comparator.comparing(TeamViewModel::getPoints)
                        .reversed())
                .distinct()
                .collect(Collectors.toList());

        modelAndView.addObject("teams", teams);
        modelAndView.addObject("league", leaguesServiceModel);
        modelAndView.setViewName("/team/group");

        return modelAndView;
    }

    @GetMapping("/stadium")
    public ModelAndView getMyStadium(Principal principal, ModelAndView modelAndView) {
        TeamServiceModel teamServiceModel = this.modelMapper.map(this.teamsService.getTeamByUsername(principal.getName()), TeamServiceModel.class);
        TeamViewModel teamViewModel = this.modelMapper.map(teamServiceModel, TeamViewModel.class);

        StadiumViewModel stadiumViewModel = this.modelMapper.map(teamViewModel.getStadium(), StadiumViewModel.class);
        stadiumViewModel.setTeamName(teamViewModel.getName());
        stadiumViewModel.setTeamLogoLink(teamViewModel.getLogoLink());
        stadiumViewModel.setTown(teamViewModel.getTown());

        modelAndView.addObject("teamStadiumModel", stadiumViewModel);

        modelAndView.setViewName("/team/stadium");

        return modelAndView;
    }
}
