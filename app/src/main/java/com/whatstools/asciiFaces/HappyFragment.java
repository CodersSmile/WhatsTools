package com.whatstools.asciiFaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whatstools.R;

public class HappyFragment extends Fragment {
    private String[] happyAscii = new String[]{"¯\\_(ツ)_/¯", "(☞ﾟヮﾟ)☞", "(◕‿◕)", "(｡◕‿◕｡)", "( ಠ ͜ʖರೃ)", "(⊙﹏⊙)", "(◠﹏◠)", "(ﾉ◕ヮ◕)ﾉ", "( ͡° ͜ʖ ͡°)", "( ͡°( ͡° ͜ʖ( ͡° ͜ʖ ͡°)ʖ ͡°) ͡°)", "┬┴┬┴┤ ͜ʖ ͡°) ├┬┴┬┴", "(◕‿◕✿)", "ᕕ(✿◕‿◕)ᕗ", "ᕕ(  ◕‿◕)ᕗ", "(ᵔᴥᵔ)", "(づ￣ ³￣)づ", "\\ (•◡•) /", "\\(◕◡◕)/", "(☞ﾟヮﾟ)☞ ☜(ﾟヮﾟ☜)", "☜(ﾟヮﾟ☜)", "♪~ ᕕ(ᐛ)ᕗ", "༼ʘ̚ل͜ʘ̚༽", "ʘ‿ʘ", "~ (˘▾˘~)", "(~˘▾˘)~", "(͡o‿O͡)", "(❍ᴥ❍ʋ)", "| (• ◡•)| (❍ᴥ❍ʋ)", "─=≡Σᕕ( ͡° ͜ʖ ͡°)ᕗ", "( ͡° ͜ʖ ͡°)>⌐■-■", "( ͡ᶢ ͜ʖ ͡ᶢ)", "( ͡^ ͜ʖ ͡^)", "( ͡ᵔ ͜ʖ ͡ᵔ )", "( ͡° ͜ ͡°)", "(˵ ͡° ͜ʖ ͡°˵)", "(∩ ͡° ͜ʖ ͡°)⊃━☆ﾟ", "ᕦ( ͡° ͜ʖ ͡°)ᕤ", "（╯ ͡° ▽ ͡°）╯︵ ┻━┻", "( ͡°╭͜ʖ╮͡° )", "༼ つ  ͡° ͜ʖ ͡° ༽つ", "(｡◕‿‿◕｡)", "(ง°ل͜°)ง", "ಠ⌣ಠ", "♡‿♡", "(●´ω｀●)", "(╹◡╹)", "ლ(╹◡╹ლ)", "(づ｡◕‿‿◕｡)づ", "┏(＾0＾)┛┗(＾0＾) ┓"};

    public void onCreate(Bundle saveInstantceState) {
        super.onCreate(saveInstantceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.happy_fragment, container, false);
        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        list.setHasFixedSize(true);
        HappyRecycerAdaptersGallery adapter = new HappyRecycerAdaptersGallery(getActivity(), this.happyAscii);
        list.setAdapter(adapter);
        return view;
    }
}
