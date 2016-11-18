package sample.ws.impl;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import sample.ws.api.WsService;
import sample.ws.impl.persistence.ApplicantPersistentActor;
import sample.ws.impl.persistence.ApplicationCommand;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;


public class WsServiceImpl implements WsService {


//    private final PersistentEntityRegistry persistentEntityRegistry;
    private final ActorSystem system;
    private ActorRef region;

    @Inject
    public WsServiceImpl(ActorSystem system) {
//        this.persistentEntityRegistry = persistentEntityRegistry;
        this.system = system;
        ClusterShardingSettings settings = ClusterShardingSettings.create(system);
        region = ClusterSharding.get(system).start("abcd",
                Props.create(ApplicantPersistentActor.class), settings, messageExtractor);
//        persistentEntityRegistry.register(ApplicantEntity.class);

    }


    public ServiceCall<NotUsed, String> ws(String name){
        return request -> {
            region.tell(new ApplicationCommand.StartApp(name), ActorRef.noSender());
            return CompletableFuture.completedFuture(name);
//            return applicantRef(name).ask(new WsCommand.StartApp(name)).thenApply(ack -> "ok");
        };
    }
//
//    private PersistentEntityRef<WsCommand> applicantRef(String name) {
//        PersistentEntityRef<WsCommand> ref = persistentEntityRegistry.refFor(ApplicantEntity.class, name);
//        return ref;
//    }


    ShardRegion.MessageExtractor messageExtractor = new ShardRegion.MessageExtractor() {

        @Override
        public String entityId(Object message) {
            if (message instanceof ApplicationCommand.StartApp)
                return String.valueOf(((ApplicationCommand.StartApp) message).name);
            else
                return null;
        }

        @Override
        public Object entityMessage(Object message) {
            return message;
        }

        @Override
        public String shardId(Object message) {
            int numberOfShards = 100;
            if (message instanceof ApplicationCommand.StartApp) {
                long id = ((ApplicationCommand.StartApp) message).name.hashCode();
                return String.valueOf(id % numberOfShards);
            } else {
                return null;
            }
        }

    };


}
