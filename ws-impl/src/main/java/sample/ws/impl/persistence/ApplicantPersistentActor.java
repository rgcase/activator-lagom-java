package sample.ws.impl.persistence;

import akka.japi.pf.ReceiveBuilder;
import akka.persistence.AbstractPersistentActor;
import akka.persistence.SnapshotOffer;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by dave on 2016-11-18.
 */
public class ApplicantPersistentActor extends AbstractPersistentActor {
    private int intState = 0;
    private ApplicationState state = new ApplicationState(null, null);


    public ApplicantPersistentActor() {

    }

    @Override
    public String persistenceId() {
        return self().path().name();
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receiveRecover() {
        return ReceiveBuilder.
                match(StartedApplication.class, evt -> {
                    intState++;
                    state =  state.update(evt);
                }).
                match(SnapshotOffer.class, ss -> state = (ApplicationState) ss.snapshot()).build();
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receiveCommand() {
        return ReceiveBuilder.
                match(ApplicationCommand.StartApp.class, c -> {

                    StartedApplication evt = new StartedApplication(c.name);


                    persist(evt, (e) -> {
                        state.update(e);
                        System.out.println("----------------------------  persisted " + e);
                        intState++;
                        System.out.println("**************************** intState = " + intState);

                    });
                }).
//                match(String.class, s -> s.equals("snap"), s -> saveSnapshot(state.copy())).
                match(String.class, s -> s.equals("print"), s -> System.out.println(state)).
                build();
    }
}
