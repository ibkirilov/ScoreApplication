package webproject.score.services.services;

import webproject.score.services.models.MatchServiceModel;

import java.util.List;

public interface MatchesService {

    void initGenerateMatches();

    void play();

    List<MatchServiceModel> getMatchesByUsername(String username);

    MatchServiceModel getMatchById(String id);

    void generateNext6Rounds();
}
