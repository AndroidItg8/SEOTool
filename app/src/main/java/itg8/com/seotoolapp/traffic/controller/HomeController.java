package itg8.com.seotoolapp.traffic.controller;

import java.util.List;

/**
 * Created by swapnilmeshram on 22/03/18.
 */

public interface HomeController {
    public interface TrafficFragmentListener<T>{
        void onListOfCategoryAvailable(List<T> list);
        void onListDownloadFail();

    }

    public interface KeyWordFragmentListener<T>{
        void onKeywordDetailAvailable(List<T> t);
        void onDownloadFail();
    }

    public interface ExternalLinksFragmentListener<T>{
        void onExtLinkAvail(List<T> t);
        void onDownloadFail();
    }
    public interface SocialMediaFragmentListener<T>{
        void onSocMediaAvail(List<T> t);
        void onDownloadFail();
    }
}
