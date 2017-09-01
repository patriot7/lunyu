import org.bson.types.ObjectId;

import java.util.Date;

public final class Tweet {
    private ObjectId id;
    private Date date;
    private String body;

    public Tweet() {

    }

    Tweet(String body) {
        this.date = new Date();
        this.body = body;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }
}
