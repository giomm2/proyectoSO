package Domain;

/**
 * Created by Danicormu on 10/11/2015.
 */
public class Track {

    private int id;
    private String name;
    private boolean selected = false;

    public Track(String name, boolean selected){
        this.id = id;
        this.name = name;
        this.setSelected(selected);
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
