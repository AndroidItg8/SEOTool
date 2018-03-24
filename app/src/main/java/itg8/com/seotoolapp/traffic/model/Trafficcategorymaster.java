
package itg8.com.seotoolapp.traffic.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Trafficcategorymaster implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("traffic")
    @Expose
    private String traffic;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    public final static Parcelable.Creator<Trafficcategorymaster> CREATOR = new Creator<Trafficcategorymaster>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Trafficcategorymaster createFromParcel(Parcel in) {
            Trafficcategorymaster instance = new Trafficcategorymaster();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.traffic = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Trafficcategorymaster[] newArray(int size) {
            return (new Trafficcategorymaster[size]);
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
     *     The traffic
     */
    public String getTraffic() {
        return traffic;
    }

    /**
     * 
     * @param traffic
     *     The traffic
     */
    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
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
        dest.writeValue(traffic);
        dest.writeValue(status);
        dest.writeValue(created);
    }

    public int describeContents() {
        return  0;
    }

}
