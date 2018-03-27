
package itg8.com.seotoolapp.external_links.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ExternalLinksModel implements Parcelable
{

    @SerializedName("Exlinkmaster")
    @Expose

    private itg8.com.seotoolapp.external_links.model.Exlinkmaster Exlinkmaster;
    @SerializedName("Projectmaster")
    @Expose

    private itg8.com.seotoolapp.external_links.model.Projectmaster Projectmaster;
    @SerializedName("Liveurlmaster")
    @Expose

    private List<itg8.com.seotoolapp.external_links.model.Liveurlmaster> Liveurlmaster = new ArrayList<itg8.com.seotoolapp.external_links.model.Liveurlmaster>();
    public final static Parcelable.Creator<ExternalLinksModel> CREATOR = new Creator<ExternalLinksModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ExternalLinksModel createFromParcel(Parcel in) {
            ExternalLinksModel instance = new ExternalLinksModel();
            instance.Exlinkmaster = ((itg8.com.seotoolapp.external_links.model.Exlinkmaster) in.readValue((Exlinkmaster.class.getClassLoader())));
            instance.Projectmaster = ((itg8.com.seotoolapp.external_links.model.Projectmaster) in.readValue((Projectmaster.class.getClassLoader())));
            in.readList(instance.Liveurlmaster, (itg8.com.seotoolapp.external_links.model.Liveurlmaster.class.getClassLoader()));
            return instance;
        }

        public ExternalLinksModel[] newArray(int size) {
            return (new ExternalLinksModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The Exlinkmaster
     */
    public itg8.com.seotoolapp.external_links.model.Exlinkmaster getExlinkmaster() {
        return Exlinkmaster;
    }

    /**
     * 
     * @param Exlinkmaster
     *     The Exlinkmaster
     */
    public void setExlinkmaster(itg8.com.seotoolapp.external_links.model.Exlinkmaster Exlinkmaster) {
        this.Exlinkmaster = Exlinkmaster;
    }

    /**
     * 
     * @return
     *     The Projectmaster
     */
    public itg8.com.seotoolapp.external_links.model.Projectmaster getProjectmaster() {
        return Projectmaster;
    }

    /**
     * 
     * @param Projectmaster
     *     The Projectmaster
     */
    public void setProjectmaster(itg8.com.seotoolapp.external_links.model.Projectmaster Projectmaster) {
        this.Projectmaster = Projectmaster;
    }

    /**
     * 
     * @return
     *     The Liveurlmaster
     */
    public List<itg8.com.seotoolapp.external_links.model.Liveurlmaster> getLiveurlmaster() {
        return Liveurlmaster;
    }

    /**
     * 
     * @param Liveurlmaster
     *     The Liveurlmaster
     */
    public void setLiveurlmaster(List<itg8.com.seotoolapp.external_links.model.Liveurlmaster> Liveurlmaster) {
        this.Liveurlmaster = Liveurlmaster;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Exlinkmaster);
        dest.writeValue(Projectmaster);
        dest.writeList(Liveurlmaster);
    }

    public int describeContents() {
        return  0;
    }

}
