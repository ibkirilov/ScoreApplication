package webproject.score.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.score.data.models.*;
import webproject.score.data.repositories.LeagueRepository;
import webproject.score.data.repositories.TeamRepository;
import webproject.score.errors.AllGroupsAreFullException;
import webproject.score.services.factories.TeamFactory;
import webproject.score.services.models.*;
import webproject.score.services.services.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamsServiceImpl implements TeamsService {
    private final LeaguesService leaguesService;
    private final StadiumsService stadiumsService;
    private final PlayersService playersService;
    private final TeamRepository teamRepository;
    private final TeamFactory teamFactory;
    private final LeagueRepository leagueRepository;
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamsServiceImpl(LeaguesService leaguesService, StadiumsService stadiumsService, PlayersService playersService, TeamRepository teamRepository, TeamFactory teamFactory, LeagueRepository leagueRepository, UsersService usersService, ModelMapper modelMapper) {
        this.leaguesService = leaguesService;
        this.stadiumsService = stadiumsService;
        this.playersService = playersService;
        this.teamRepository = teamRepository;
        this.teamFactory = teamFactory;
        this.leagueRepository = leagueRepository;
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCreateTeams() throws Exception {
        if (this.teamRepository.count() == 0) {
            int teamsCount = 0;
            for (int z = 0; z < 2; z++) {
                for (int i = 0; i < 4; i++) {
                    String teamName = String.format("Robo team %d", ++teamsCount);
                    League league = this.leaguesService.getLeague();
                    String stadiumName = String.format("Stadium of %s", teamName);
                    Team team = this.teamFactory.create(teamName, league);

                    this.teamRepository.save(team);

                    team.setPlayers(playersService.createSetOfPlayersForNewTeam(team));
                    team.setStadium(stadiumsService.createStadium(stadiumName));

                    this.teamRepository.saveAndFlush(team);
                }
            }
        }
    }

    @Transactional
    @Override
    public Team create(TeamCreateServiceModel teamCreateServiceModel, String username) {

        Team team = this.getTeamToReplace();
        if (team == null) {
            throw new AllGroupsAreFullException("All groups are full. Please contact the admin to add more groups!");
        }
        team.setName(teamCreateServiceModel.getTeamName());
        team.setTown(teamCreateServiceModel.getTown());

        Stadium stadium = team.getStadium();
        stadium.setName(teamCreateServiceModel.getStadiumName());

        team.setStadium(this.stadiumsService.saveNewStadium(stadium));

        team.setFanClubName(teamCreateServiceModel.getFanClub());

        this.playersService.deletePlayersByTeam(team);
        Set<Player> players = playersService.createSetOfPlayersForNewTeam(team);

        team.setPlayers(players);

        team.setUser(this.usersService.getUserByUsername(username));

        team.setLogoLink(teamCreateServiceModel.getLogoLink());

        teamRepository.save(team);
        return team;
    }

    public Double getTeamPower(Set<PlayerServiceModel> teamPlayers) {
        double power = 0.0;
        for (PlayerServiceModel player : teamPlayers) {
            if (player.getPosition() == Position.GOALKEEPER) {
                power += (player.getGoalkeeping() * 4 + player.getDefending() + player.getPlaymaking() / 2d + player.getScoring() / 4d) * (1 + (player.getForm() - 5) / 100d);
            } else if (player.getPosition() == Position.DEFENDER) {
                power += (player.getGoalkeeping() / 1.5d + player.getDefending() * 4 + player.getPlaymaking() * 2d + player.getScoring()) * (1 + (player.getForm() - 5) / 100d);
            } else if (player.getPosition() == Position.MIDFIELDER) {
                power += (player.getGoalkeeping() / 4d + player.getDefending() * 2 + player.getPlaymaking() * 4 + player.getScoring() * 2) * (1 + (player.getForm() - 5) / 100d);
            } else if (player.getPosition() == Position.FORWARD) {
                power += (player.getGoalkeeping() / 4d + player.getDefending() + player.getPlaymaking() * 2 + player.getScoring() * 4) * (1 + (player.getForm() - 5) / 100d);
            }
        }
        return power;
    }

    @Override
    public Team getTeamByUsername(String username) {
        return this.teamRepository.findTeamByUserUsername(username);
    }

    @Override
    public void addPointsToTeams(MatchServiceModel match) {
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();

        if (match.getWinner().equals("home")) {
            homeTeam.setPoints(homeTeam.getPoints() + 3);
            this.teamRepository.save(homeTeam);
        } else if (match.getWinner().equals("away")) {
            awayTeam.setPoints(awayTeam.getPoints() + 3);
            this.teamRepository.save(awayTeam);
        } else if (match.getWinner().equals("tie")) {
            homeTeam.setPoints(homeTeam.getPoints() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
            this.teamRepository.save(homeTeam);
            this.teamRepository.save(awayTeam);
        }
    }

    @Override
    public List<TeamServiceModel> getTeamsByLeague(LeaguesServiceModel leaguesServiceModel) {

        return leaguesServiceModel.getTeams()
                .stream()
                .map(team -> this.modelMapper.map(team, TeamServiceModel.class))
                .collect(Collectors.toList());
    }

    private Team getTeamToReplace() {
        Team team = null;
        List<League> leagues = this.leagueRepository.findAll();

        for (League league : leagues) {
            for (Team te : league.getTeams()) {
                if (te.getUser() == null) {
                    team = te;
                    break;
                }
            }
            if (team != null) {
                break;
            }
        }
        return team;
    }
}
