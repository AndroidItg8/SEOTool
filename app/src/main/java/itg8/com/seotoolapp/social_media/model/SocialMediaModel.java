
package itg8.com.seotoolapp.social_media.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SocialMediaModel implements Parcelable
{

    @SerializedName("Sociallinkmaster")
    @Expose

    private itg8.com.seotoolapp.social_media.model.Sociallinkmaster Sociallinkmaster;
    @SerializedName("Projectmaster")
    @Expose

    private itg8.com.seotoolapp.social_media.model.Projectmaster Projectmaster;
    @SerializedName("Socialkeywordmaster")
    @Expose

    private List<itg8.com.seotoolapp.social_media.model.Socialkeywordmaster> Socialkeywordmaster = new ArrayList<itg8.com.seotoolapp.social_media.model.Socialkeywordmaster>();
    public final static Parcelable.Creator<SocialMediaModel> CREATOR = new Creator<SocialMediaModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SocialMediaModel createFromParcel(Parcel in) {
            SocialMediaModel instance = new SocialMediaModel();
            instance.Sociallinkmaster = ((itg8.com.seotoolapp.social_media.model.Sociallinkmaster) in.readValue((Sociallinkmaster.class.getClassLoader())));
            instance.Projectmaster = ((itg8.com.seotoolapp.social_media.model.Projectmaster) in.readValue((Projectmaster.class.getClassLoader())));
            in.readList(instance.Socialkeywordmaster, (itg8.com.seotoolapp.social_media.model.Socialkeywordmaster.class.getClassLoader()));
            return instance;
        }

        public SocialMediaModel[] newArray(int size) {
            return (new SocialMediaModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The Sociallinkmaster
     */
    public itg8.com.seotoolapp.social_media.model.Sociallinkmaster getSociallinkmaster() {
        return Sociallinkmaster;
    }

    /**
     * 
     * @param Sociallinkmaster
     *     The Sociallinkmaster
     */
    public void setSociallinkmaster(itg8.com.seotoolapp.social_media.model.Sociallinkmaster Sociallinkmaster) {
        this.Sociallinkmaster = Sociallinkmaster;
    }

    /**
     * 
     * @return
     *     The Projectmaster
     */
    public itg8.com.seotoolapp.social_media.model.Projectmaster getProjectmaster() {
        return Projectmaster;
    }

    /**
     * 
     * @param Projectmaster
     *     The Projectmaster
     */
    public void setProjectmaster(itg8.com.seotoolapp.social_media.model.Projectmaster Projectmaster) {
        this.Projectmaster = Projectmaster;
    }

    /**
     * 
     * @return
     *     The Socialkeywordmaster
     */
    public List<itg8.com.seotoolapp.social_media.model.Socialkeywordmaster> getSocialkeywordmaster() {
        return Socialkeywordmaster;
    }

    /**
     * 
     * @param Socialkeywordmaster
     *     The Socialkeywordmaster
     */
    public void setSocialkeywordmaster(List<itg8.com.seotoolapp.social_media.model.Socialkeywordmaster> Socialkeywordmaster) {
        this.Socialkeywordmaster = Socialkeywordmaster;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Sociallinkmaster);
        dest.writeValue(Projectmaster);
        dest.writeList(Socialkeywordmaster);
    }

    public int describeContents() {
        return  0;
    }

}
