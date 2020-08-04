package webproject.score.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import webproject.score.data.models.Goal;
import webproject.score.data.models.Match;
import webproject.score.data.models.Team;
import webproject.score.data.repositories.MatchRepository;
import webproject.score.services.models.LeaguesServiceModel;
import webproject.score.services.models.MatchServiceModel;
import webproject.score.services.models.PlayerServiceModel;
import webproject.score.services.services.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchesServiceImpl implements MatchesService {
    private final LeaguesService leaguesService;
    private final MatchRepository matchRepository;
    private final TeamsService teamsService;
    private final ModelMapper modelMapper;
    private final GoalsService goalsService;

    @Autowired
    public MatchesServiceImpl(LeaguesService leaguesService, MatchRepository matchRepository, TeamsService teamsService, ModelMapper modelMapper, GoalsService goalsService) {
        this.leaguesService = leaguesService;
        this.matchRepository = matchRepository;
        this.teamsService = teamsService;
        this.modelMapper = modelMapper;
        this.goalsService = goalsService;
    }

    @Override
    public void initGenerateMatches() {

        if (this.matchRepository.count() == 0) {
            generateNext6Rounds();
        }
    }

    @Scheduled(cron = "0 0 9,21 * * ?")
    @Override
    public void play() {

        Integer round = this.leaguesService.getRoundsPlayed() + 1;
        List<Match> nextMatches = this.loadNextMatches(round);
        List<MatchServiceModel> matchesForPlay = nextMatches
                .stream()
                .map(match -> this.modelMapper.map(match, MatchServiceModel.class))
                .collect(Collectors.toList());

        setTeamPowerToTeams(matchesForPlay);

        matchesForPlay
                .forEach(match -> {
                    double homeTeamPower = match.getHomeTeamPower();
                    double awayTeamPower = match.getAwayTeamPower();

                    double homeTeamDiff = homeTeamPower - awayTeamPower;
                    double awayTeamDiff = awayTeamPower - homeTeamPower;

                    int homeGoals = 0;
                    int awayGoals = 0;

                    if (homeTeamDiff >= 0 && homeTeamDiff <= 100) {
                        homeGoals = getRandomGoals(0, 3);
                        awayGoals = getRandomGoals(0, 2);
                    } else if (awayTeamDiff >= 0 && awayTeamDiff <= 100) {
                        awayGoals = getRandomGoals(0, 3);
                        homeGoals = getRandomGoals(0, 2);
                    }

                    if (homeTeamDiff > 100 && homeTeamDiff <= 250) {
                        homeGoals = getRandomGoals(1, 4);
                        awayGoals = getRandomGoals(0, 2);
                    } else if (awayTeamDiff > 100 && awayTeamDiff <= 250) {
                        awayGoals = getRandomGoals(1, 4);
                        homeGoals = getRandomGoals(0, 2);
                    }

                    if (homeTeamDiff > 250) {
                        homeGoals = getRandomGoals(0, 7);
                        awayGoals = getRandomGoals(0, 1);
                    } else if (awayTeamDiff > 250) {
                        awayGoals = getRandomGoals(0, 7);
                        homeGoals = getRandomGoals(0, 1);
                    }

                    List<Goal> goals = this.goalsService.createGoalsForTeam(match, match.getHomeTeam(), homeGoals);
                    goals.addAll(this.goalsService.createGoalsForTeam(match, match.getAwayTeam(), awayGoals));
                    match.setGoals(goals);
                    if (homeGoals > awayGoals) {
                        match.setWinner("home");
                    } else if (awayGoals > homeGoals) {
                        match.setWinner("away");
                    } else {
                        match.setWinner("tie");
                    }

                    this.matchRepository.save(this.modelMapper.map(match, Match.class));
                    this.teamsService.addPointsToTeams(match);
                });


    }

    @Override
    public List<MatchServiceModel> getMatchesByUsername(String username) {

        Team team = this.teamsService.getTeamByUsername(username);
        List<Match> matches = this.matchRepository.findAll();

        return matches
                .stream()
                .filter(match -> match.getHomeTeam().getId().equals(team.getId()) || match.getAwayTeam().getId().equals(team.getId()))
                .map(match -> this.modelMapper.map(match, MatchServiceModel.class))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public MatchServiceModel getMatchById(String id) {
        List<Match> matches = this.matchRepository.findAll();

        return matches
                .stream()
                .filter(match -> match.getId().equals(id))
                .map(match -> this.modelMapper.map(match, MatchServiceModel.class))
                .distinct()
                .findFirst()
                .orElse(null);
    }

    private void setTeamPowerToTeams(List<MatchServiceModel> matchesForPlay) {

        matchesForPlay
                .forEach(match -> {
                    match.setHomeTeamPower(this.teamsService
                            .getTeamPower(match
                                    .getHomeTeam()
                                    .getPlayers()
                                    .stream()
                                    .map(player -> this.modelMapper.map(player, PlayerServiceModel.class))
                                    .collect(Collectors.toSet())));

                    match.setAwayTeamPower(this.teamsService
                            .getTeamPower(match
                                    .getAwayTeam()
                                    .getPlayers()
                                    .stream()
                                    .map(player -> this.modelMapper.map(player, PlayerServiceModel.class))
                                    .collect(Collectors.toSet())));
                });
    }

    @Override
    public void generateNext6Rounds() {

        List<LeaguesServiceModel> leagues = this.leaguesService.getAllLeagues();
        leagues
                .forEach(league -> {
                    List<Team> teams = league.getTeams();
                    Team team1 = teams.get(0);
                    Team team2 = teams.get(1);
                    Team team3 = teams.get(2);
                    Team team4 = teams.get(3);

                    int roundsPlayed = league.getRoundsPlayed();

                    this.matchRepository.save(makeMatch(team1, team2, roundsPlayed + 1));
                    this.matchRepository.save(makeMatch(team3, team4, roundsPlayed + 1));
                    this.matchRepository.save(makeMatch(team4, team1, roundsPlayed + 2));
                    this.matchRepository.save(makeMatch(team2, team3, roundsPlayed + 2));
                    this.matchRepository.save(makeMatch(team1, team3, roundsPlayed + 3));
                    this.matchRepository.save(makeMatch(team2, team4, roundsPlayed + 3));
                    this.matchRepository.save(makeMatch(team4, team2, roundsPlayed + 4));
                    this.matchRepository.save(makeMatch(team3, team1, roundsPlayed + 4));
                    this.matchRepository.save(makeMatch(team3, team2, roundsPlayed + 5));
                    this.matchRepository.save(makeMatch(team1, team4, roundsPlayed + 5));
                    this.matchRepository.save(makeMatch(team4, team3, roundsPlayed + 6));
                    this.matchRepository.save(makeMatch(team2, team1, roundsPlayed + 6));
                });
    }

    private Match makeMatch(Team team1, Team team2, Integer round) {

        Match match = new Match();
        match.setHomeTeam(team1);
        match.setAwayTeam(team2);
        match.setStadium(team1.getStadium());
        match.setRound(round);

        return match;
    }

    private List<Match> loadNextMatches(Integer round) {
        return this.matchRepository.findMatchesByRound(round);
    }

    private Integer getRandomGoals(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
