package sample.ws.impl.persistence;

public class ApplicationState {

    public String wsA;
    public String wsB;

    public ApplicationState(String wsA, String wsB) {
        this.wsA = wsA;
        this.wsB = wsB;
    }

    public ApplicationState update(ApplicationEvent evt) {
        if(evt instanceof WsAEvent){
           return new ApplicationState(((WsAEvent)evt).result, this.wsB);
        }else if(evt instanceof WsBEvent){
            return new ApplicationState(this.wsA, ((WsBEvent)evt).result);
        }
        else{
            return this;
        }
    }


}
