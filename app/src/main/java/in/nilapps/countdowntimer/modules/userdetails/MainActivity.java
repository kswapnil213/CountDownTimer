package in.nilapps.countdowntimer.modules.userdetails;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import in.nilapps.countdowntimer.R;
import in.nilapps.countdowntimer.common.BaseActivity;
import in.nilapps.countdowntimer.common.data.AppConstants;
import in.nilapps.countdowntimer.common.models.User;
import in.nilapps.countdowntimer.common.services.BackgroundService;

public class MainActivity extends BaseActivity implements LifecycleOwner {

    MainActivity context;
    UserViewModel viewModel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvNoRecords)
    TextView tvNoRecords;
    CountDownAdapter countDownAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        startBackgroundService();

    }

    private void init() {

        context = this;

        viewModel = ViewModelProviders.of(context).get(UserViewModel.class);
        viewModel.getUserMutableLiveData().observe(context, userListUpdateObserver);

    }

    private void startBackgroundService() {

        Intent serviceIntent = new Intent(this, BackgroundService.class);
        serviceIntent.putExtra(AppConstants.TAG_COUNT_DOWN_COUNTER, viewModel.getCounter());
        ContextCompat.startForegroundService(this, serviceIntent);

    }

    Observer<ArrayList<User>> userListUpdateObserver = new Observer<ArrayList<User>>() {
        @Override
        public void onChanged(ArrayList<User> userArrayList) {

            if (userArrayList.size() > 0) {
                if (countDownAdapter == null) {
                    countDownAdapter = new CountDownAdapter(context);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(countDownAdapter);
                }
                countDownAdapter.setUserList(userArrayList);
            } else {
                recyclerView.setVisibility(View.GONE);
                tvNoRecords.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(AppConstants.TAG_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(AppConstants.TAG_RESULT_CODE, RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                long resultValue = intent.getLongExtra(AppConstants.TAG_RESULT_VALUE, 0);
                viewModel.updateList(resultValue);
            }
        }
    };

}