package sample.ws.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide.completedStatement;

public class ApplicantProcessor extends ReadSideProcessor<WsEvent> {

    private final CassandraSession session;
    private final CassandraReadSide readSide;

    private PreparedStatement appstate = null; // initialized in prepare

    @Inject
    public ApplicantProcessor(CassandraSession session, CassandraReadSide readSide) {
        this.session = session;
        this.readSide = readSide;
    }


    private void setAppState(PreparedStatement appstate) {
        this.appstate = appstate;
    }


    @Override
    public PSequence<AggregateEventTag<WsEvent>> aggregateTags() {
        return TreePVector.singleton(WsEventTag.INSTANCE);
    }

    @Override
    public ReadSideHandler<WsEvent> buildHandler() {
        return readSide.<WsEvent>builder("ws_event")
                .setGlobalPrepare(this::prepareCreateTables)
                .setPrepare((ignored) -> prepareWriteAppState())
                .setEventHandler(WsEvent.StartedApp.class, this::processStartedApp)

                .build();
    }

    private CompletionStage<Done> prepareCreateTables() {
        // @formatter:off
        return session.executeCreateTable(
                "CREATE TABLE IF NOT EXISTS appState ("
                        + "name text, approved boolean, "
                        + "PRIMARY KEY (name))");
        // @formatter:on
    }

    private CompletionStage<Done> prepareWriteAppState() {
        return session.prepare("INSERT INTO appState (name, approved) VALUES (?, ?)").thenApply(ps -> {
            setAppState(ps);
            return Done.getInstance();
        });
    }


    private CompletionStage<List<BoundStatement>> processStartedApp(WsEvent.StartedApp event){


        // do ws call


        BoundStatement bindWriteAppState = appstate.bind();
        bindWriteAppState.setString("name", event.name);
        bindWriteAppState.setBool("approved", false);
        return completedStatement(bindWriteAppState);

    }

}
