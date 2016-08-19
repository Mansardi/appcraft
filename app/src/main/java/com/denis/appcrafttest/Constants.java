package com.denis.appcrafttest;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

public class Constants {
  public static final int LIST_POSTS_COUNT = 10;
  public static final int TASK_DURATION = 5 * 1000; // 3 seconds
  public static final int TEXT_LENGTH = 50;
  public static final VKRequest REQUEST = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, -1,
          VKApiConst.EXTENDED, 1, VKApiConst.COUNT, LIST_POSTS_COUNT));
  public static final int REQUEST_ATTEMPTS = 5;
  public static final String HTTP_ERROR = "Ошибка соединения.";
  public static final int TASK_TICK = 1000;
  public static final int TIME_TICK = 500;
}
