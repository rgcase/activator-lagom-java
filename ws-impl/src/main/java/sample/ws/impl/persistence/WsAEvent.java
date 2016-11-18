package sample.ws.impl.persistence;

public class WsAEvent implements ApplicationEvent {

    public String name;
    public String result;


    public WsAEvent(String name, String result) {
        this.name = name;
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WsAEvent wsAEvent = (WsAEvent) o;

        if (name != null ? !name.equals(wsAEvent.name) : wsAEvent.name != null) return false;
        return result != null ? result.equals(wsAEvent.result) : wsAEvent.result == null;

    }

    @Override
    public int hashCode() {
        int result1 = name != null ? name.hashCode() : 0;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "WsAEvent{" +
                "name='" + name + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
