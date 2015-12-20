package api;

public class Model {

    private String id, title, description, created, thumbnailUrl;

    public Model(){

    }

    public Model(String name, String thumbnailUrl) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Model(String id, String title, String description, String created, String thumbnailUrl){
        this.id = id;
        this.title = title;
        this.description = description;
        this.created = created;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getCreated(){
        return created;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCreated(String created){
        this.created = created;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
