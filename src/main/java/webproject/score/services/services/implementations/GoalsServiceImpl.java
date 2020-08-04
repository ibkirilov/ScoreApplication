package webproject.score.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webproject.score.data.models.*;
import webproject.score.data.repositories.GoalRepository;
import webproject.score.services.models.GoalServiceModel;
import webproject.score.services.models.MatchServiceModel;
import webproject.score.services.services.GoalsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GoalsServiceImpl implements GoalsService {
    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;

    public GoalsServiceImpl(GoalRepository goalRepository, ModelMapper modelMapper) {
        this.goalRepository = goalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Goal> createGoalsForTeam(MatchServiceModel match, Team team, int homeGoals) {

        Match theMatch = this.modelMapper.map(match, Match.class);
        List<Goal> teamGoals = new ArrayList<>();
        for (int i = 0; i < homeGoals; i++) {
            List<Player> players = team.getPlayers()
                    .stream()
                    .filter(pl -> pl.getPosition() == Position.FORWARD)
                    .collect(Collectors.toList());

            Player player = players.get(getRandomNumber(0, 1));

            Goal goal = new Goal(theMatch, player, team);
            teamGoals.add(goal);
            this.goalRepository.save(goal);
        }

        return teamGoals;
    }

    @Override
    public List<GoalServiceModel> getGoalsByMatchIdAndTeamId(String id, String id1) {

        return this.goalRepository.findByMatchIdAndTeamId(id, id1)
                .stream()
                .map(goal -> this.modelMapper.map(goal, GoalServiceModel.class))
                .distinct()
                .collect(Collectors.toList());
    }

    private Integer getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }


}
