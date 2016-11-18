package sample.ws.impl.persistence;

public interface ApplicationCommand {

    public final class StartApp{

        public String name;

        public StartApp(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StartApp startApp = (StartApp) o;

            return name != null ? name.equals(startApp.name) : startApp.name == null;

        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "StartApp{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
