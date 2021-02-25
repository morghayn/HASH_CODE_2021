public class Street//arc/edge
{
    public int nameIndex, time;
    public Intersection start, end;
    public boolean light = false;
    public

    public Street(Intersection s, Intersection e, int n, int t){
        this.start = s;
        this.end = e;
        this.nameIndex = n;
        this.time = t;
    }

    public static void SetLight(Boolean newState)
    {
        light = newState;
    }

    public Boolean GetLightState()
    {
        return light;
    }

    public static void Tick(){}



}
