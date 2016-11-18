package sample.ws.impl.persistence;

public class WsBEvent implements ApplicationEvent {

    public String name;
    public String result;


    public WsBEvent(String name, String result) {
        this.name = name;
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WsBEvent wsBEvent = (WsBEvent) o;

        if (name != null ? !name.equals(wsBEvent.name) : wsBEvent.name != null) return false;
        return result != null ? result.equals(wsBEvent.result) : wsBEvent.result == null;

    }

    @Override
    public int hashCode() {
        int result1 = name != null ? name.hashCode() : 0;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "WsBEvent{" +
                "name='" + name + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
