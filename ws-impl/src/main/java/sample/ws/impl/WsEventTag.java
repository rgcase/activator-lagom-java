package sample.ws.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class WsEventTag {

    public static final AggregateEventTag<WsEvent> INSTANCE = AggregateEventTag.of(WsEvent.class);


}
