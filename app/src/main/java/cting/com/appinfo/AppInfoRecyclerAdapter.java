package cting.com.appinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cting.com.appinfo.model.AppInfo;

/**
 * Created by cting on 2018/2/7.
 */

public class AppInfoRecyclerAdapter extends RecyclerView.Adapter<AppInfoRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<AppInfo> mList;
    private LayoutInflater mInflater;

    public AppInfoRecyclerAdapter(Context context, List<AppInfo> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.app_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppInfo info = getItem(position);
        if (info.getIcon() != null) {
            holder.appIcon.setImageDrawable(info.getIcon());
        }
        holder.appLabel.setText(info.getLabel());
        holder.appVersion.setText(info.getVersionName());
        holder.appPath.setText(info.getInstallPath());

    }

    private AppInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        @BindView(R.id.app_icon)
        ImageView appIcon;
        @BindView(R.id.app_label)
        TextView appLabel;
        @BindView(R.id.app_path)
        TextView appPath;
        @BindView(R.id.app_version)
        TextView appVersion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;


        }
    }
}
