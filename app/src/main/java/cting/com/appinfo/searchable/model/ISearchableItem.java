package cting.com.appinfo.searchable.model;

/**
 * Created by cting on 2018/2/10.
 */

public interface ISearchableItem {
    boolean containKeywords(String queryText);
}
