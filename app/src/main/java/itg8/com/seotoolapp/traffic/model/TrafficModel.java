
package itg8.com.seotoolapp.traffic.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrafficModel implements Parcelable
{

    @SerializedName("Trafficmaster")
    @Expose

    private itg8.com.seotoolapp.traffic.model.Trafficmaster Trafficmaster;
    @SerializedName("Projectmaster")
    @Expose

    private itg8.com.seotoolapp.traffic.model.Projectmaster Projectmaster;
    @SerializedName("Trafficcategorymaster")
    @Expose

    private itg8.com.seotoolapp.traffic.model.Trafficcategorymaster Trafficcategorymaster;
    public final static Parcelable.Creator<TrafficModel> CREATOR = new Creator<TrafficModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TrafficModel createFromParcel(Parcel in) {
            TrafficModel instance = new TrafficModel();
            instance.Trafficmaster = ((itg8.com.seotoolapp.traffic.model.Trafficmaster) in.readValue((Trafficmaster.class.getClassLoader())));
            instance.Projectmaster = ((itg8.com.seotoolapp.traffic.model.Projectmaster) in.readValue((Projectmaster.class.getClassLoader())));
            instance.Trafficcategorymaster = ((itg8.com.seotoolapp.traffic.model.Trafficcategorymaster) in.readValue((Trafficcategorymaster.class.getClassLoader())));
            return instance;
        }

        public TrafficModel[] newArray(int size) {
            return (new TrafficModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The Trafficmaster
     */
    public itg8.com.seotoolapp.traffic.model.Trafficmaster getTrafficmaster() {
        return Trafficmaster;
    }

    /**
     * 
     * @param Trafficmaster
     *     The Trafficmaster
     */
    public void setTrafficmaster(itg8.com.seotoolapp.traffic.model.Trafficmaster Trafficmaster) {
        this.Trafficmaster = Trafficmaster;
    }

    /**
     * 
     * @return
     *     The Projectmaster
     */
    public itg8.com.seotoolapp.traffic.model.Projectmaster getProjectmaster() {
        return Projectmaster;
    }

    /**
     * 
     * @param Projectmaster
     *     The Projectmaster
     */
    public void setProjectmaster(itg8.com.seotoolapp.traffic.model.Projectmaster Projectmaster) {
        this.Projectmaster = Projectmaster;
    }

    /**
     * 
     * @return
     *     The Trafficcategorymaster
     */
    public itg8.com.seotoolapp.traffic.model.Trafficcategorymaster getTrafficcategorymaster() {
        return Trafficcategorymaster;
    }

    /**
     * 
     * @param Trafficcategorymaster
     *     The Trafficcategorymaster
     */
    public void setTrafficcategorymaster(itg8.com.seotoolapp.traffic.model.Trafficcategorymaster Trafficcategorymaster) {
        this.Trafficcategorymaster = Trafficcategorymaster;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Trafficmaster);
        dest.writeValue(Projectmaster);
        dest.writeValue(Trafficcategorymaster);
    }

    public int describeContents() {
        return  0;
    }

}
