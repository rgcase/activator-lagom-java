package sample.ws.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface WsService extends Service {

    ServiceCall<NotUsed, String> ws(String id);

    @Override
    default Descriptor descriptor() {
        return named("wsservice").withCalls(
                pathCall("/api/ws/:id",  this::ws)
        ).withAutoAcl(true);
    }
}
