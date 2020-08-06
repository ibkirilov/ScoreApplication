package webproject.score.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestBase {

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        this.beforeEach();
    }

    private void beforeEach() {

    }
}
