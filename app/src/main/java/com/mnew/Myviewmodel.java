package com.mnew;

import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Myviewmodel extends ViewModel {
  private OkHttpClient ok = new OkHttpClient();
  // ① 内部可变的 LiveData
  private final MutableLiveData<List<Item_bean_recy>> _bean = new MutableLiveData<>();
  private ExecutorService executor = Executors.newSingleThreadExecutor();

  // ② 对外只读的 LiveData
  public final LiveData<List<Item_bean_recy>> bean = _bean;

  // ③ 更新整个列表（主线程）
  public void setBean(List<Item_bean_recy> list) {
    _bean.setValue(list);
  }

  public void fs() {
    List<String> lianjie = new ArrayList<>();
    List<String> img = new ArrayList<>();

    List<String> title = new ArrayList<>();
    executor.execute(
        () -> {
          try {
            Document doc =
                Jsoup.connect("https://news.ifeng.com/shanklist/3-35201-/")
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (Linux; Android 6.0.1; SM901 Build/MXB48T; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/62.0.3202.84 Mobile Safari/537.36")
                    .get();

            Elements titles = doc.select("div.index_titleImg_4nAwo>div>a.index_title_s7Mql");
            for (Element t : titles) {
              title.add(t.text());
              
              String a = t.attr("href");
              lianjie.add(a.toString());
              // Toast.makeText(,t.text().toString(),Toast.LENGTH_SHORT).show();

            }
           // Log.d("title",titles.text());
            Elements lianjires = doc.select("div.index_titleImg_4nAwo>div>a>img");

            for (Element l : lianjires) {
              String a = l.attr("data-lazy-src");
              // Log.d("title", a);
              img.add("https:" + a);
            }
            List<Item_bean_recy> as = new ArrayList<>();
            int minSize = Math.min(Math.min(title.size(), img.size()), lianjie.size());
            for (int i = 0; i < minSize; i++) {

              as.add(new Item_bean_recy(title.get(i), img.get(i), lianjie.get(i), ""));
            }

            _bean.postValue(as);

          } catch (Exception err) {

err.printStackTrace();
          }
        });
  }

  public List<Item_bean_recy> getList() {
    List<Item_bean_recy> current = _bean.getValue();
    return current != null ? new ArrayList<>(current) : new ArrayList<>();
  }

  // ④ 在后台线程更新列表
  public void setBeanAsync(List<Item_bean_recy> list) {
    _bean.postValue(list);
  }

  // ⑤ 单个 item 的增删改（示例）
  public void addItem(Item_bean_recy item) {
    List<Item_bean_recy> current = _bean.getValue();
    if (current == null) {
      current = new java.util.ArrayList<>();
    } else {
      current = new java.util.ArrayList<>(current); // 复制一份，避免直接修改导致 UI 不更新
    }
    current.add(item);
    _bean.setValue(current);
  }
}
