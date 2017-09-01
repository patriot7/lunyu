import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;

import static com.mongodb.MongoClient.getDefaultCodecRegistry;
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

        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(codecRegistry).build());

        MongoDatabase database = mongoClient.getDatabase("lunyu");

        MongoCollection<Tweet> tweetCollection = database.getCollection("tweet", Tweet.class);

        tweetCollection.insertOne(new Tweet("java's so complex"));

        Gson gson = new Gson();

        //        TODO:
        //        imageCollection
        //        ArticleCollection
        //        videoCollection

        get("/", (req, res) -> {
            FindIterable<Tweet> test = tweetCollection.find().sort(descending("_id")).limit(50);

            ArrayList<Tweet> tweetList = new ArrayList<>();

            for (Tweet tweet : test) {
                tweetList.add(tweet);
            }

            System.out.println(gson.toJson(tweetList));

            return gson.toJson(tweetList);
        });

        post("/", (req, res) -> "NOT IMPLEMENTED");

        delete("/", (req, res) -> "NOT IMPLEMENTED");
    }
}
