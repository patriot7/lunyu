import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        String test = gson.toJson(model);
        System.out.println("wtf????");
        System.out.println(test);
        return gson.toJson(model);
    }

}