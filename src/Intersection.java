import java.util.ArrayList;
import java.util.List;

public class Intersection//node/vertex
{
    List<Street> outbounds, inbounds;

    public Intersection(){
        this.outbounds = new ArrayList<>();
        this.inbounds = new ArrayList<>();
    }
}
