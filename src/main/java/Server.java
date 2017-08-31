import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Sorts.descending;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        MongoDatabase database = mongoClient.getDatabase("lunyu");

        MongoCollection<Tweet> tweetCollection = database.getCollection("tweet", Tweet.class);

        //        TODO:
        //        imageCollection
        //        ArticleCollection
        //        videoCollection

        get("/", (req, res) -> {
            FindIterable<Tweet> test = tweetCollection.find().sort(descending("_id")).limit(50);
            Gson gson = new Gson();
            System.out.println((gson.toJson(test)));
            return gson.toJson(test);
        });

        post("/", (req, res) -> {
            return "NOT IMPLEMENTED";
        });

        delete("/", (req, res) -> {
            return "NOT IMPLEMENTED";
        });
    }
}
