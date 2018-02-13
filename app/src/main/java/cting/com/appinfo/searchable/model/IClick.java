package cting.com.appinfo.searchable.model;


/**
 * Created by cting on 2018/2/13.
 */

public interface IClick<I extends ISearchableItem> {
    void onItemClick(I item);

    boolean onItemLongClick(I item);
}
