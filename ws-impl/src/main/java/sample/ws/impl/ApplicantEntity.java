package sample.ws.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class ApplicantEntity extends PersistentEntity<WsCommand, WsEvent, ApplicantState> {


    @Override
    public Behavior initialBehavior(Optional<ApplicantState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(new ApplicantState()));


        b.setCommandHandler(WsCommand.StartApp.class, (cmd, ctx) ->
                // In response to this command, we want to first persist it as a
                // GreetingMessageChanged event
                ctx.thenPersist(new WsEvent.StartedApp(cmd.name),
                        // Then once the event is successfully persisted, we respond with done.
                        evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(WsEvent.StartedApp.class, evt -> new ApplicantState());


        return b.build();

    }

}
