package Domain;

/**
 * Created by Danicormu on 10/11/2015.
 */
public class Track {

    private int id;
    private String name;

    public Track(int id, String name){
        this.id = id;
        this.name = name;
    }
    public Track(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
