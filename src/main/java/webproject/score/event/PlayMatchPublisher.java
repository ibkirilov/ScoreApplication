package webproject.score.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PlayMatchPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PlayMatchPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void makeChangesAfterTheMatches() {
        PlayMatchEvent playMatchEvent = new PlayMatchEvent(this);
        this.applicationEventPublisher.publishEvent(playMatchEvent);
    }
}
