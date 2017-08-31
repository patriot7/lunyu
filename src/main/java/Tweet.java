import org.bson.types.ObjectId;

import java.util.Date;

public final class Tweet {
    private ObjectId id;
    private Date date;
    private String body;

    public Tweet() {

    };

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
