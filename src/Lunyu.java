import static spark.Spark.get;
import static spark.Spark.post;

public class Lunyu {
    public static void main(String[] args) {
        get("/", (req, res) -> "");

        post("/", (req, res) -> "");
    }
}