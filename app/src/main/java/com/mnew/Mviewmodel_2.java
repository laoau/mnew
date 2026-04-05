package com.mnew;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Mviewmodel_2 extends ViewModel {
  private final MutableLiveData<List<Item_bean_recy>> _bean = new MutableLiveData<>();
  private ExecutorService executor = Executors.newSingleThreadExecutor();

  // ② 对外只读的 LiveData
  public final LiveData<List<Item_bean_recy>> bean = _bean;

  public void fs() {

    try {
      executor.execute(() -> {});

    } catch (Exception err) {

    }
  }

  
}
