package itg8.com.seotoolapp.traffic.controller;

import java.util.HashMap;
import java.util.List;

import itg8.com.seotoolapp.external_links.model.ExternalLinksModel;

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
        void onExtLinkAvail(HashMap<String, List<ExternalLinksModel>> t, int type);
        void onDownloadFail(String message, int type);
    }
    public interface SocialMediaFragmentListener<T>{
        void onSocMediaAvail(List<T> t);
        void onDownloadFail();

        void onSocMediaAvail(HashMap<String, List<ExternalLinksModel>> stringListHashMap, int type);
    }
}
