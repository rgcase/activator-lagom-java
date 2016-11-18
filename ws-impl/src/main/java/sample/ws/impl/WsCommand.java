package sample.ws.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;

import akka.Done;

public interface WsCommand {

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class StartApp implements WsCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        public final String name;

        @JsonCreator
        public StartApp(String name) {
            this.name = Preconditions.checkNotNull(name, "name");
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof StartApp && equalTo((StartApp) another);
        }

        private boolean equalTo(StartApp another) {
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
            return MoreObjects.toStringHelper("StartApp").add("name", name).toString();
        }
    }

}
