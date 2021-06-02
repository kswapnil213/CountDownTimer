package in.nilapps.countdowntimer.modules.userdetails;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.nilapps.countdowntimer.R;
import in.nilapps.countdowntimer.common.models.User;

/**
 * Created by Swapnil G. on 02-06-2021.
 */

public class CountDownAdapter extends RecyclerView.Adapter<CountDownAdapter.ViewHolder> {

    Activity context;
    ArrayList<User> userList;
    boolean isFirstTime = true;

    public CountDownAdapter(Activity context) {
        this.context = context;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountDownAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CountDownAdapter.ViewHolder holder, int position) {

        if (isFirstTime) {
            holder.tvUseName.setText(userList.get(position).getFirstName() + " " + userList.get(position).getLastName());
            holder.tvUserInfo.setText(userList.get(position).getAge() + " Years " + userList.get(position).getGender());
        }

        if (userList.get(position).getCount() > 0) {
            holder.tvCountDown.setText(String.valueOf(userList.get(position).getCount()));
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvUserName)
        TextView tvUseName;
        @BindView(R.id.tvUserInfo)
        TextView tvUserInfo;
        @BindView(R.id.tvCountDown)
        TextView tvCountDown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}