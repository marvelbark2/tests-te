package json;

public class Request {
    private String event;
    private Object data;

    public String getEvent() {
        return event;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "eventName='" + event + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
