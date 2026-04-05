package com.mnew;



import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;

import com.mnew.databinding.F1Binding;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class F1 extends Fragment {
  private F1Binding bind;
  private Recy_adp adp;
  private Myviewmodel mviewmodel;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    bind = F1Binding.inflate(inflater, container, false);

    return bind.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // 1. 初始化适配器（初始为空数据）
    adp = new Recy_adp(requireContext());
    bind.f1Reclview.setLayoutManager(new LinearLayoutManager(getContext()));
    bind.f1Reclview.setAdapter(adp);

    // 2. 获取 ViewModel
    mviewmodel = new ViewModelProvider(this).get(Myviewmodel.class);

    // 3. 观察数据变化
    
    mviewmodel.bean.observe(
        getViewLifecycleOwner(),
        new Observer<List<Item_bean_recy>>() {
          @Override
          public void onChanged(List<Item_bean_recy> itemList) {
            if (itemList != null && !itemList.isEmpty()) {
              // 清除旧数据，添加新数据
              adp.addItems(itemList);

              // Toast.makeText(getContext(),itemList.get(1).toString(),Toast.LENGTH_SHORT).show();
            }
          }
        });

    // 4. 设置点击事件
    adp.setOnItemClickListener(
        (position, item) -> {
          // 显示加载对话框
          Dialog dialog = new Dialog(requireContext());
          dialog.setContentView(R.layout.tc);
          TextView tv = dialog.findViewById(R.id.tc_tv1);
          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
          dialog.getWindow().setWindowAnimations(R.style.Base_AppTheme);
          tv.setText("加载中...");
          dialog.show();
          // 在子线程进行网络请求
          new Thread(
                  () -> {
                    try {
                      Document doc =
                          Jsoup.connect(item.getLianjie())
                              .header(
                                  "User-Agent",
                                  "Mozilla/5.0 (Linux; Android 6.0.1; SM901 Build/MXB48T; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/62.0.3202.84 Mobile Safari/537.36")
                              .get();

                      Elements content = doc.select("div.index_smallFont_3Pwv1>p");
                      StringBuilder a = new StringBuilder();
                      for (Element e : content) {
                        a.append(e.text()).append("\n");
                      }

                      // 在主线程更新UI
                      requireActivity()
                          .runOnUiThread(
                              () -> {
                                tv.setText(a.toString());
                                // 如果需要，可以调整dialog大小等
                              });

                    } catch (Exception err) {
                      requireActivity()
                          .runOnUiThread(
                              () -> {
                                tv.setText("加载失败: " + err.getMessage());
                              });
                      err.printStackTrace();
                    }
                  })
              .start();
        });

    // 5. 触发数据加载
    loadData();
  }

  private void loadData() {
    // 检查是否已经有数据
    if (mviewmodel.bean.getValue() == null || mviewmodel.bean.getValue().isEmpty()) {
      // 加载网络数据
      mviewmodel.fs();

    } else {
      // 如果已经有数据，直接使用
      adp.replaceAll(mviewmodel.bean.getValue());
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView(); // 注意：Fragment 中应该使用 onDestroyView
    bind = null;
  }
}
