package cting.com.appinfo.searchable.model;

import java.util.ArrayList;

/**
 * Created by cting on 2018/2/13.
 */

public interface IDataImportExport<I extends ISearchableItem> {

    void exportData(ArrayList<I> items);

    void importData();
}
