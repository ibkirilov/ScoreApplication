package webproject.score.services.services;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import webproject.score.base.TestBase;
import webproject.score.data.models.Stadium;
import webproject.score.data.repositories.StadiumRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class StadiumsServiceTests extends TestBase {
    @Autowired
    StadiumsService stadiumsService;

    @MockBean
    StadiumRepository stadiumRepository;

    @Test
    public void createStadium_shouldReturnStadiumWithGivenName() {
        String name = "stadium name";

        Mockito.when(stadiumRepository.saveAndFlush(any(Stadium.class))).thenReturn(null);

        Stadium stadium = this.stadiumsService.createStadium(name);

        assertEquals(name, stadium.getName());
    }

    @Test
    public void saveNewStadium_shouldReturnStadiumWithSameId() {
        Stadium mockedStadium = new Stadium();
        mockedStadium.setId("id");
        Mockito.when(this.stadiumRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(mockedStadium));

        Stadium stadium = new Stadium();
        stadium.setName("Stadium name");
        stadium.setId("noId");

        Mockito.when(this.stadiumRepository.saveAndFlush(any(Stadium.class))).thenReturn(null);

        this.stadiumsService.saveNewStadium(stadium);

        assertEquals(stadium.getId(), mockedStadium.getId());
    }
}
