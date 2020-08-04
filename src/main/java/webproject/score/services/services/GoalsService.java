package webproject.score.services.services;

import webproject.score.data.models.Goal;
import webproject.score.data.models.Team;
import webproject.score.services.models.GoalServiceModel;
import webproject.score.services.models.MatchServiceModel;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GoalsService {

    List<Goal> createGoalsForTeam(MatchServiceModel match, Team homeTeam, int homeGoals);

    List<GoalServiceModel> getGoalsByMatchIdAndTeamId(String id, String id1);
}
