package webproject.score.events;

import org.springframework.context.ApplicationEvent;

public class PlayMatchEvent extends ApplicationEvent {

    public PlayMatchEvent(Object source) {
        super(source);
    }
}
