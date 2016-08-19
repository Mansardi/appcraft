package com.denis.appcrafttest.android.common.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.denis.appcrafttest.Constants;
import com.denis.appcrafttest.android.SwipeRefreshFragment;
import com.denis.appcrafttest.android.common.data.Posts;
import com.denis.appcrafttest.android.listeners.Event;
import com.denis.appcrafttest.android.listeners.EventData;
import com.denis.appcrafttest.android.listeners.Listener;
import com.example.denis.appcrafttest.R;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

public class MainActivity extends AppCompatActivity implements Listener {

  private static final String FRAGMENT_TAG = "response_view";

  private Posts postsList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      SwipeRefreshFragment fragment = new SwipeRefreshFragment();
      transaction.replace(R.id.sample_content_fragment, fragment, FRAGMENT_TAG);
      transaction.commit();
    }

    getPostsList();
  }

  private SwipeRefreshFragment getFragment() {
    return (SwipeRefreshFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
  }

  private void getPostsList() {
    VKRequest request = Constants.REQUEST;
    request.attempts = Constants.REQUEST_ATTEMPTS;
    request.executeWithListener(mRequestListener);
  }

  VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener() {
    @Override
    public void onComplete(VKResponse response) {
      postsList = new Posts();

      VKList<VKApiPost> responseList = (VKList<VKApiPost>) response.parsedModel;

      for(int i = 0; i < responseList.size(); i++) {
        postsList.add(responseList.get(i));
      }
      setResponseList();
      getFragment().setIsRefreshed(true);
    }

    @Override
    public void onError(VKError error) {
      super.onError(error);
      getFragment().setIsRefreshed(false);
    }
  };

  protected void setResponseList() {
    SwipeRefreshFragment fragment = getFragment();
    if (fragment != null && postsList != null) {
      fragment.initializeList(postsList.getTextListPreview());
    }
  }

  private void listItemClick(Object i) {
    Intent intent = new Intent(this, TextViewActivity.class);
    intent.putExtra("post_text", this.postsList.get((int) i).text);

    startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }


  @Override
  public void handleEvent(EventData eventData) {
    Event event = eventData.getEvent();
    Object data = eventData.getData();

    if (event == Event.INITIATE_REFRESH) {
      getPostsList();
    }
    if (event == Event.LIST_ITEM_CLICK) {
      listItemClick(data);
    }
    if (event == Event.REFRESH_ERROR) {
      Toast toast = Toast.makeText(this, Constants.HTTP_ERROR, Toast.LENGTH_SHORT);
      toast.show();
    }
  }
}

