package com.denis.appcrafttest.android;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.denis.appcrafttest.Constants;
import com.denis.appcrafttest.android.listeners.Event;
import com.denis.appcrafttest.android.listeners.EventData;
import com.denis.appcrafttest.android.listeners.Listener;
import com.example.denis.appcrafttest.R;

import java.util.ArrayList;

public class SwipeRefreshFragment extends Fragment {

  private boolean isRefreshed;

  private SwipeRefreshLayout swipeRefreshLayout;

  public ListView listView;

  private ArrayAdapter<String> listAdapter;


  Listener listener;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      listener = (Listener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + " must implement Listener");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_sample, container, false);

    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

    listView = (ListView) view.findViewById(android.R.id.list);

    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    listAdapter = new ArrayAdapter<String>(
            getActivity(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1);

    listView.setAdapter(listAdapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        EventData listItemClick = new EventData(Event.LIST_ITEM_CLICK, i);
        listener.handleEvent(listItemClick);
      }
    });

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        initiateRefresh();
      }
    });
  }

  private void initiateRefresh() {
    isRefreshed = false;
    new DummyBackgroundTask().execute();
  }

  private void onRefreshComplete(boolean result) {
    swipeRefreshLayout.setRefreshing(false);
    isRefreshed = false;

    if (!result) {
      EventData refreshError = new EventData(Event.REFRESH_ERROR, null);
      listener.handleEvent(refreshError);
    }
  }

  public void initializeList(ArrayList<String> postsList) {
    if (postsList != null) {
      listAdapter.clear();
      listAdapter.addAll(postsList);
    }
  }

  public void setIsRefreshed(boolean isRefreshed) {
    this.isRefreshed = isRefreshed;
  }

  private class DummyBackgroundTask extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... params) {
      int time = 0;

      while (!isRefreshed) {
        EventData initiateRefresh = new EventData(Event.INITIATE_REFRESH, null);
        listener.handleEvent(initiateRefresh);

        try {
          Thread.sleep(Constants.TASK_TICK);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (time >= Constants.TASK_DURATION) {
          return false;
        }
        time += Constants.TIME_TICK;
      }
      return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
      super.onPostExecute(result);

      onRefreshComplete(result);
    }
  }
}
