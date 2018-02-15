package cting.com.robin.support.searchablerecyclerview;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import cting.com.robin.support.commom.utils.TextHighlighterHelper;

public class SearchableBindingComponent {

    public static final String TAG = "cting/search/cn";

    @BindingAdapter({"searchableText", "keywords"})
    public static void setTextForHighlight(@NonNull TextView textView, String searchableText, String keywords) {
        if (!TextUtils.isEmpty(searchableText)) {
//            Log.i(TAG, "setTextForHighlight: text=" + searchableText+",keywords="+keywords);
            if (!TextUtils.isEmpty(keywords)) {
                TextHighlighterHelper.getInstance().updateHighliteInText(textView, searchableText, keywords);
            } else {
                textView.setText(searchableText);
            }
        }
    }

}
