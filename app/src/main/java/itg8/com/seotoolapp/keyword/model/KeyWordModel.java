
package itg8.com.seotoolapp.keyword.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class KeyWordModel implements Parcelable
{

    @SerializedName("Keywordstatusmaster")
    @Expose
    private itg8.com.seotoolapp.keyword.model.Keywordstatusmaster Keywordstatusmaster;
    @SerializedName("Projectmaster")
    @Expose

    private itg8.com.seotoolapp.keyword.model.Projectmaster Projectmaster;
    public final static Parcelable.Creator<KeyWordModel> CREATOR = new Creator<KeyWordModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public KeyWordModel createFromParcel(Parcel in) {
            KeyWordModel instance = new KeyWordModel();
            instance.Keywordstatusmaster = ((itg8.com.seotoolapp.keyword.model.Keywordstatusmaster) in.readValue((Keywordstatusmaster.class.getClassLoader())));
            instance.Projectmaster = ((itg8.com.seotoolapp.keyword.model.Projectmaster) in.readValue((Projectmaster.class.getClassLoader())));
            return instance;
        }

        public KeyWordModel[] newArray(int size) {
            return (new KeyWordModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The Keywordstatusmaster
     */
    public itg8.com.seotoolapp.keyword.model.Keywordstatusmaster getKeywordstatusmaster() {
        return Keywordstatusmaster;
    }

    /**
     * 
     * @param Keywordstatusmaster
     *     The Keywordstatusmaster
     */
    public void setKeywordstatusmaster(itg8.com.seotoolapp.keyword.model.Keywordstatusmaster Keywordstatusmaster) {
        this.Keywordstatusmaster = Keywordstatusmaster;
    }

    /**
     * 
     * @return
     *     The Projectmaster
     */
    public itg8.com.seotoolapp.keyword.model.Projectmaster getProjectmaster() {
        return Projectmaster;
    }

    /**
     * 
     * @param Projectmaster
     *     The Projectmaster
     */
    public void setProjectmaster(itg8.com.seotoolapp.keyword.model.Projectmaster Projectmaster) {
        this.Projectmaster = Projectmaster;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Keywordstatusmaster);
        dest.writeValue(Projectmaster);
    }

    public int describeContents() {
        return  0;
    }

}
