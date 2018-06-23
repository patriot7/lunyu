import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.MongoClient.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {

        CodecRegistry codecRegistry = fromRegistries(
                getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .automatic(true)
                        .build())
        );

        // TODO: database connection exception

        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(codecRegistry).build());

        MongoDatabase database = mongoClient.getDatabase("lunyu");

        MongoCollection<Tweet> tweetCollection = database.getCollection("tweet", Tweet.class);

        tweetCollection.insertOne(new Tweet("java's so complex"));

        Gson gson = new Gson();

        get("/", (req, res) -> {
            FindIterable<Tweet> tweets = tweetCollection.find().sort(descending("_id")).limit(50);

            ArrayList<Tweet> tweetList = new ArrayList<>();

            for (Tweet tweet : tweets) {
                tweetList.add(tweet);
            }

            return gson.toJson(tweetList);
        });

        post("/", (req, res) -> {
            Tweet newTweet = gson.fromJson(req.body(), Tweet.class);
            newTweet.setId(new ObjectId());
            newTweet.setDate(new Date());

            tweetCollection.insertOne(newTweet);

            return gson.toJson(newTweet.getId());
        });

        delete("/:id", (req, res) -> {
            Tweet deletedTweet = tweetCollection.findOneAndDelete(eq("_id", req.params(":id")));

            return gson.toJson(deletedTweet);
        });
    }
}
