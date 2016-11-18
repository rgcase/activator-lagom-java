package sample.ws.impl.persistence;

public class StartedApplication implements ApplicationEvent{

    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartedApplication that = (StartedApplication) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public StartedApplication(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StartedApplication{" +
                "name='" + name + '\'' +
                '}';
    }
}
