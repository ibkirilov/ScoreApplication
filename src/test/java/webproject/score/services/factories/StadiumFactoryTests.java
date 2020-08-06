package webproject.score.services.factories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import webproject.score.base.TestBase;
import webproject.score.data.models.Stadium;

import static org.junit.Assert.*;

public class StadiumFactoryTests extends TestBase {

    @Autowired
    private StadiumFactory stadiumFactory;

    @Test
    public void create_createsStadiumWithGivenName() {
        String stadiumName = "Test Stadium Name";
        Stadium stadium = new Stadium();
        stadium.setName(stadiumName);

        Stadium stadiumFromFactory = this.stadiumFactory.create(stadiumName);

        assertEquals(stadium, stadiumFromFactory);
        assertNotNull(stadiumFromFactory.getCapacity());
    }
}
