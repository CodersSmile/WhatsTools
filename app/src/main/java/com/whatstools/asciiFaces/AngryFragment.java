package com.whatstools.asciiFaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.whatstools.R;

public class AngryFragment extends Fragment {
    private String[] angryAscii = new String[]{"(≖︿≖ )", "(ง ͠° ͟ل͜ ͡°)ง", "ಠ_ಠ", "( ͠°Д°͠ )", "ლ(ಠ益ಠლ)", "(>ლ)", "(ง'-̀'́)ง", "(ノಠ益ಠ)ノ彡┻━┻", "(╯°□°）╯︵( .o.)", "(╯°□°）╯︵ /( ‿⌓‿ )\\", "(╯°□°）╯︵ ┻━┻", "┻━┻ ︵ヽ(`Д´)ﾉ︵ ┻━┻", "(┛◉Д◉)┛彡┻━┻ ", "(¬_¬)", "ಠoಠ", "ᕙ(⇀‸↼‶)ᕗ", "ᕦ(ò_óˇ)ᕤ", "¯\\_(ʘ_ʘ)_/¯", "( ͡°_ʖ ͡°)", "( ° ͜ʖ͡°)╭∩╮", "( ͡°Ĺ̯ ͡° )", "( ͠° ͟ʖ °͠ )", "¯\\_( ͠° ͟ʖ °͠ )_/¯", "( ͡°⊖ ͡°)", "ರ_ರ", "(>人<)", "ಠ╭╮ಠ", "(◣_◢)", "(⋋▂⋌)", "〴⋋_⋌〵", "(╹◡╹)凸", "ლ(͠°◞౪◟°͠ლ)", "╭∩╮(︶︿︶)╭∩╮", "(ㆆ_ㆆ)"};

    public void onCreate(Bundle saveInstantceState) {
        super.onCreate(saveInstantceState);
    }

    //When view is created
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.angry_fragment, container, false);
        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        list.setHasFixedSize(true);
        AngryRecycerAdaptersGallery adapter = new AngryRecycerAdaptersGallery(getActivity(), this.angryAscii);
        list.setAdapter(adapter);
        return view;
    }
}
