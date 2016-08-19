package com.denis.appcrafttest.android.common.data;

import com.denis.appcrafttest.Constants;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class Posts {

  private VKList<VKApiPost> items;

  public Posts() {
   this.items = new VKList<>();
  }

  public boolean add(VKApiPost object) {
    return items.add(object);
  }

  public VKApiPost get(int location) {
    return items.get(location);
  }

  public int size() {
    return items.size();
  }

  public ArrayList<String> getTextListPreview() {
    ArrayList<String> textList = new ArrayList<>();

    for (VKApiPost post : items) {
      if (post.text.length() > Constants.TEXT_LENGTH) {
        textList.add(post.text.substring(0, Constants.TEXT_LENGTH) + "...");
      } else {
        textList.add(post.text);
      }
    }
    return textList;
  }
}
