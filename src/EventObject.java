public class EventObject {
    public enum Type {
        NONE,
        IMG_UPDATED,
        RUN_CMD,
    }
    public  Object obj;
    public  Type type;

    public EventObject(Type _type, Object _obj) {
        obj = _obj;
        type = _type;
    }
}
