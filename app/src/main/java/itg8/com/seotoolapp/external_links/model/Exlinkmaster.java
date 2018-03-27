
package itg8.com.seotoolapp.external_links.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exlinkmaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("projectmaster_id")
    @Expose
    private String projectmasterId;
    @SerializedName("dateof")
    @Expose
    private String dateof;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("postedurl")
    @Expose
    private String postedurl;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Exlinkmaster> CREATOR = new Creator<Exlinkmaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Exlinkmaster createFromParcel(Parcel in) {
            Exlinkmaster instance = new Exlinkmaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.projectmasterId = ((String) in.readValue((String.class.getClassLoader())));
            instance.dateof = ((String) in.readValue((String.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.postedurl = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Exlinkmaster[] newArray(int size) {
            return (new Exlinkmaster[size]);
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
     *     The projectmasterId
     */
    public String getProjectmasterId() {
        return projectmasterId;
    }

    /**
     * 
     * @param projectmasterId
     *     The projectmaster_id
     */
    public void setProjectmasterId(String projectmasterId) {
        this.projectmasterId = projectmasterId;
    }

    /**
     * 
     * @return
     *     The dateof
     */
    public String getDateof() {
        return dateof;
    }

    /**
     * 
     * @param dateof
     *     The dateof
     */
    public void setDateof(String dateof) {
        this.dateof = dateof;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The postedurl
     */
    public String getPostedurl() {
        return postedurl;
    }

    /**
     * 
     * @param postedurl
     *     The postedurl
     */
    public void setPostedurl(String postedurl) {
        this.postedurl = postedurl;
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
        dest.writeValue(projectmasterId);
        dest.writeValue(dateof);
        dest.writeValue(title);
        dest.writeValue(postedurl);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
