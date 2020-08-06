package webproject.score.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webproject.score.data.models.Match;
import webproject.score.data.models.Player;
import webproject.score.data.models.Position;
import webproject.score.data.models.Team;
import webproject.score.data.repositories.MatchRepository;
import webproject.score.data.repositories.PlayerRepository;
import webproject.score.services.factories.PlayerFactory;
import webproject.score.services.models.PlayerServiceModel;
import webproject.score.services.services.LeaguesService;
import webproject.score.services.services.PlayersService;
import webproject.score.services.services.TeamsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayersServiceImpl implements PlayersService {
    private final PlayerFactory playerFactory;
    private final PlayerRepository playerRepository;
    private final LeaguesService leaguesService;
    private final MatchRepository matchRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayersServiceImpl(PlayerFactory playerFactory, PlayerRepository playerRepository, LeaguesService leaguesService, MatchRepository matchRepository, ModelMapper modelMapper) {
        this.playerFactory = playerFactory;
        this.playerRepository = playerRepository;
        this.leaguesService = leaguesService;
        this.matchRepository = matchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Player> createSetOfPlayersForNewTeam(Team team) {
        Set<Player> players = new HashSet<>();

        for (int i = 0; i < 1; i++) {
            Player player1 = playerFactory.create(Position.GOALKEEPER);
            player1.setTeam(team);
            players.add(player1);
            playerRepository.saveAndFlush(player1);
        }

        for (int i = 0; i < 4; i++) {
            Player player = playerFactory.create(Position.DEFENDER);
            player.setTeam(team);
            players.add(player);
            playerRepository.saveAndFlush(player);
        }

        for (int i = 0; i < 4; i++) {
            Player player = playerFactory.create(Position.MIDFIELDER);
            player.setTeam(team);
            players.add(player);
            playerRepository.saveAndFlush(player);
        }

        for (int i = 0; i < 2; i++) {
            Player player = playerFactory.create(Position.FORWARD);
            player.setTeam(team);
            players.add(player);
            playerRepository.saveAndFlush(player);
        }

        return players;
    }

    @Override
    public void deletePlayersByTeam(Team team) {
        this.playerRepository.findPlayersByTeam(team)
                .forEach(this.playerRepository::delete);
    }

    @Override
    public void changePlayersFormAfterTheMatches() {

        int roundsPlayed = this.leaguesService.getRoundsPlayed();
        List<Match> matchesLastRound = this.matchRepository.findMatchesByRound(roundsPlayed);

        matchesLastRound
                .forEach(match -> {
                    if (match.getWinner().equals("home")) {
                        actualizePlayersForm(match.getHomeTeam(), match.getAwayTeam());
                    } else if (match.getWinner().equals("away")) {
                        actualizePlayersForm(match.getAwayTeam(), match.getHomeTeam());
                    }
                });
    }

    @Override
    public List<PlayerServiceModel> getPlayersByTeamId(String teamId) {

        return this.playerRepository.findPlayersByTeamId(teamId)
                .stream()
                .map(player -> this.modelMapper.map(player, PlayerServiceModel.class))
                .collect(Collectors.toList());
    }

    private Integer getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private void actualizePlayersForm(Team winner, Team looser) {
        winner.getPlayers()
                .forEach(player -> {
                    player.setForm(getRandomNumber(player.getForm(), 10));
                    this.playerRepository.save(player);
                });

        looser.getPlayers()
                .forEach(player -> {
                    player.setForm(getRandomNumber(0, player.getForm()));
                    this.playerRepository.save(player);
                });
    }
}
