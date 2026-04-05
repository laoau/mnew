package com.mnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.mnew.databinding.F2Binding;


public class F2 extends Fragment {
  private F2Binding bind;
  private Recy_adp adp;

  @Override
  public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
    // TODO: Implement this method
    bind = F2Binding.inflate(arg0, arg1, false);
    return bind.getRoot();
  }

  @Override
  public void onViewCreated(View arg0, Bundle arg1) {
    super.onViewCreated(arg0, arg1);
    adp = new Recy_adp(requireContext());
    bind.f2Reclview.setLayoutManager(new LinearLayoutManager(getContext()));
    bind.f2Reclview.setAdapter(adp);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    this.bind = null;
    // TODO: Implement this method
  }
}
