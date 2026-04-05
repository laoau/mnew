package com.mnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.mnew.databinding.RecyItemBinding;

import java.util.ArrayList;
import java.util.List;

public class Recy_adp extends RecyclerView.Adapter<Recy_adp.ViewHolder> {

  private Context context;
  private List<Item_bean_recy> dataList;

  // 点击事件接口
  public interface OnItemClickListener {
    void onItemClick(int position, Item_bean_recy item);
  }

  private OnItemClickListener itemClickListener;

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.itemClickListener = listener;
  }

  public Recy_adp(Context context) {
    this(context, null);
  }

  public Recy_adp(Context context, List<Item_bean_recy> dataList) {
    this.context = context;
    this.dataList = dataList != null ? new ArrayList<>(dataList) : new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    RecyItemBinding binding = RecyItemBinding.inflate(LayoutInflater.from(context), parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Item_bean_recy item = dataList.get(position);
    holder.binding.itemTvTitle.setText(item.getTitle());
    holder.binding.itemTvTime.setText(item.getTime());

    // 加载图片
    if (item.getImageurl() != null && !item.getImageurl().isEmpty()) {
      Glide.with(context)
          .load(item.getImageurl())
          .placeholder(R.drawable.ic_launcher_background) // 占位图
          .error(R.drawable.ic_launcher_foreground) // 错误图
          .into(holder.binding.itemImgImg);
    }

    // 设置点击事件
    holder.itemView.setOnClickListener(
        v -> {
          if (itemClickListener != null) {
            itemClickListener.onItemClick(position, item);
          }
        });
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    final RecyItemBinding binding;

    public ViewHolder(RecyItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

  // ================== 动态操作方法 ==================

  /** 1. 添加单个数据到末尾 */
  public void addItem(Item_bean_recy item) {
    dataList.add(item);
    notifyItemInserted(dataList.size() - 1);
  }

  /** 2. 在指定位置插入单个数据 */
  public void addItem(int position, Item_bean_recy item) {
    if (position < 0 || position > dataList.size()) {
      position = dataList.size();
    }
    dataList.add(position, item);
    notifyItemInserted(position);
  }

  /** 3. 添加多个数据到末尾 */
  public void addItems(List<Item_bean_recy> items) {
    if (items == null || items.isEmpty()) return;

    int startPosition = dataList.size();
    dataList.addAll(items);
    notifyItemRangeInserted(startPosition, items.size());
  }

  /** 4. 在指定位置插入多个数据 */
  public void addItems(int position, List<Item_bean_recy> items) {
    if (items == null || items.isEmpty()) return;

    if (position < 0 || position > dataList.size()) {
      position = dataList.size();
    }

    dataList.addAll(position, items);
    notifyItemRangeInserted(position, items.size());
  }

  /** 5. 更新指定位置的数据 */
  public void updateItem(int position, Item_bean_recy item) {
    if (position < 0 || position >= dataList.size()) return;

    dataList.set(position, item);
    notifyItemChanged(position);
  }

  /** 6. 删除指定位置的数据 */
  public void removeItem(int position) {
    if (position < 0 || position >= dataList.size()) return;

    dataList.remove(position);
    notifyItemRemoved(position);
    // 如果需要更新后续位置，可以调用
    // notifyItemRangeChanged(position, dataList.size() - position);
  }

  /** 7. 删除指定数据 */
  public void removeItem(Item_bean_recy item) {
    int position = dataList.indexOf(item);
    if (position != -1) {
      removeItem(position);
    }
  }

  /** 8. 清空所有数据 */
  public void clearAll() {
    int size = dataList.size();
    dataList.clear();
    notifyItemRangeRemoved(0, size);
  }

  /** 9. 替换所有数据（刷新） */
  public void replaceAll(List<Item_bean_recy> newList) {
    dataList.clear();
    if (newList != null) {
      dataList.addAll(newList);
    }
    notifyDataSetChanged();
  }

  /** 10. 获取指定位置的数据 */
  public Item_bean_recy getItem(int position) {
    if (position < 0 || position >= dataList.size()) return null;
    return dataList.get(position);
  }

  /** 11. 获取所有数据 */
  public List<Item_bean_recy> getAllData() {
    return new ArrayList<>(dataList);
  }

  /** 12. 更新部分数据（智能刷新） */
  public void updateItems(List<Item_bean_recy> newItems) {
    if (newItems == null) return;

    // 简单实现：先清除再添加
    dataList.clear();
    dataList.addAll(newItems);
    notifyDataSetChanged();

    // 或者实现更智能的 DiffUtil 更新
    // notifyWithDiffUtil(newItems);
  }
}
