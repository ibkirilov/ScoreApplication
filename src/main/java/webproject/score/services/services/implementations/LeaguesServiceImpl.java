package webproject.score.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webproject.score.data.models.League;
import webproject.score.data.models.Team;
import webproject.score.data.repositories.LeagueRepository;
import webproject.score.errors.AllGroupsAreFullException;
import webproject.score.services.factories.LeagueFactory;
import webproject.score.services.models.LeaguesServiceModel;
import webproject.score.services.services.LeaguesService;
import webproject.score.services.services.TeamsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaguesServiceImpl implements LeaguesService {
    private final LeagueRepository leagueRepository;
    private final LeagueFactory leagueFactory;
    private final ModelMapper modelMapper;

    @Autowired
    public LeaguesServiceImpl(LeagueRepository leagueRepository, LeagueFactory leagueFactory, ModelMapper modelMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueFactory = leagueFactory;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCreateLeagues() {
        if (this.leagueRepository.count() == 0) {
            League league1 = leagueFactory.create("Premier League");
            leagueRepository.saveAndFlush(league1);

            League league2 = leagueFactory.create("Championship League");
            leagueRepository.saveAndFlush(league2);
        }
    }

    @Override
    public League getLeague() throws AllGroupsAreFullException {
        League league1 = leagueRepository.getLeagueByName("Premier League");
        if (league1.getTeams().size() == 4) {
            League league2 = leagueRepository.getLeagueByName("Championship League");
            if (league2.getTeams().size() == 4) {
                throw new AllGroupsAreFullException("All groups are full");
            } else {
                return league2;
            }
        }
        return league1;
    }

    @Override
    public List<LeaguesServiceModel> getAllLeagues() {

        return this.leagueRepository.findAll()
                .stream()
                .map(league -> modelMapper.map(league, LeaguesServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getRoundsPlayed() {
        List<League> leagues = this.leagueRepository.findAll();
        return leagues.get(0).getRoundsPlayed();

    }

    @Override
    public void roundPlayed() {
        this.leagueRepository.findAll()
                .forEach(league -> {
                    league.setRoundsPlayed(league.getRoundsPlayed() + 1);
                    this.leagueRepository.save(league);
                });
    }

    @Override
    public LeaguesServiceModel getLeagueByTeam(Team team) {

        League league = team.getLeague();
        return this.modelMapper.map(league, LeaguesServiceModel.class);
    }
}
