package webproject.score.services.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webproject.score.data.models.Stadium;
import webproject.score.data.repositories.StadiumRepository;
import webproject.score.services.factories.StadiumFactory;
import webproject.score.services.services.StadiumsService;

@Service
public class StadiumsServiceImpl implements StadiumsService {
    private final StadiumFactory stadiumFactory;
    private final StadiumRepository stadiumRepository;

    @Autowired
    public StadiumsServiceImpl(StadiumFactory stadiumFactory, StadiumRepository stadiumRepository) {
        this.stadiumFactory = stadiumFactory;
        this.stadiumRepository = stadiumRepository;
    }

    @Override
    public Stadium createStadium(String name) {
        Stadium stadium = stadiumFactory.create(name);

        stadiumRepository.saveAndFlush(stadium);

        return stadium;
    }

    @Override
    public Stadium saveNewStadium(Stadium stadium) {

        Stadium oldStadium = this.stadiumRepository.findById(stadium.getId()).orElse(null);
        stadium.setId(oldStadium.getId());
        return this.stadiumRepository.saveAndFlush(stadium);
    }
}
