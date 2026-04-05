package com.mnew;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mnew.databinding.ActivityMainBinding;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private final List<String> tabTitles = Arrays.asList("tab1", "tab2");

  private ActivityMainBinding bind;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bind = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());

    bind.mainViewPager.setAdapter(new ScreenSlidePagerAdapter(this));
    new TabLayoutMediator(
            bind.mainTabLayout,
            bind.mainViewPager,
            (tab, position) -> tab.setText(tabTitles.get(position)))
        .attach();
  }

  private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    public ScreenSlidePagerAdapter(AppCompatActivity activity) {
      super(activity);
    }

    @Override
    public Fragment createFragment(int position) {
      switch (position) {
        case 0:
          return new F1();
        case 1:
          return new F2();

        default:
          throw new IllegalStateException("Invalid position " + position);
      }
    }

    @Override
    public int getItemCount() {
      return 2; // 与 tabTitles 长度保持一致
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // TODO: Implement this method
    this.bind = null;
  }
}
