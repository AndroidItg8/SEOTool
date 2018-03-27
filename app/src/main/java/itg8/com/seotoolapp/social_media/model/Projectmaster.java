
package itg8.com.seotoolapp.social_media.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Projectmaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("projecttitle")
    @Expose
    private String projecttitle;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Projectmaster> CREATOR = new Creator<Projectmaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Projectmaster createFromParcel(Parcel in) {
            Projectmaster instance = new Projectmaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.projecttitle = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Projectmaster[] newArray(int size) {
            return (new Projectmaster[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The projecttitle
     */
    public String getProjecttitle() {
        return projecttitle;
    }

    /**
     * 
     * @param projecttitle
     *     The projecttitle
     */
    public void setProjecttitle(String projecttitle) {
        this.projecttitle = projecttitle;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(projecttitle);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
