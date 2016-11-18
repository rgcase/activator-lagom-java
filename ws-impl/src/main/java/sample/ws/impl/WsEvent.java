package sample.ws.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

public interface WsEvent extends Jsonable, AggregateEvent<WsEvent> {

    @Override
    default public AggregateEventTag<WsEvent> aggregateTag() {
        return WsEventTag.INSTANCE;
    }


    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class StartedApp implements WsEvent {
        public final String name;

        @JsonCreator
        public StartedApp(String name) {
            this.name = Preconditions.checkNotNull(name, "name");
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof StartedApp && equalTo((StartedApp) another);
        }

        private boolean equalTo(StartedApp another) {
            return name.equals(another.name);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + name.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("StartedApp").add("name", name).toString();
        }
    }
    
    
}
