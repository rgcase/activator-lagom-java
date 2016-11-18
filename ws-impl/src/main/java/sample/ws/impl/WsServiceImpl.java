package sample.ws.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import sample.ws.api.WsService;

import javax.inject.Inject;


public class WsServiceImpl implements WsService {


    private final PersistentEntityRegistry persistentEntityRegistry;

    @Inject
    public WsServiceImpl(PersistentEntityRegistry persistentEntityRegistry){
        this.persistentEntityRegistry = persistentEntityRegistry;


    }

    public ServiceCall<NotUsed, String> ws(String id){
        throw new RuntimeException("DSF");
    }

}
