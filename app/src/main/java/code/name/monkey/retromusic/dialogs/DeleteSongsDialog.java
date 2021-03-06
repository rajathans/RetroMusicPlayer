package code.name.monkey.retromusic.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import code.name.monkey.appthemehelper.ThemeStore;
import code.name.monkey.retromusic.R;
import code.name.monkey.retromusic.model.Song;
import code.name.monkey.retromusic.util.MusicUtil;
import code.name.monkey.retromusic.views.RoundedBottomSheetDialogFragment;

import java.util.ArrayList;

public class DeleteSongsDialog extends RoundedBottomSheetDialogFragment {

    @BindView(R.id.action_delete)
    TextView delete;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_cancel)
    TextView cancel;

    @NonNull
    public static DeleteSongsDialog create(Song song) {
        ArrayList<Song> list = new ArrayList<>();
        list.add(song);
        return create(list);
    }

    @NonNull
    public static DeleteSongsDialog create(ArrayList<Song> songs) {
        DeleteSongsDialog dialog = new DeleteSongsDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("songs", songs);
        dialog.setArguments(args);
        return dialog;
    }

    @OnClick({R.id.action_cancel, R.id.action_delete})
    void actions(View view) {
        //noinspection ConstantConditions
        final ArrayList<Song> songs = getArguments().getParcelableArrayList("songs");
        switch (view.getId()) {
            case R.id.action_delete:
                if (getActivity() == null) {
                    return;
                }
                if (songs != null) {
                    MusicUtil.deleteTracks(getActivity(), songs);
                }
                break;
            default:
        }
        dismiss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        delete.setTextColor(ThemeStore.textColorPrimary(getContext()));
        title.setTextColor(ThemeStore.textColorPrimary(getContext()));
        cancel.setTextColor(ThemeStore.textColorPrimary(getContext()));


        //noinspection unchecked,ConstantConditions
        final ArrayList<Song> songs = getArguments().getParcelableArrayList("songs");
        int title;
        CharSequence content;
        if (songs != null) {
            if (songs.size() > 1) {
                title = R.string.delete_songs_title;
                content = Html.fromHtml(getString(R.string.delete_x_songs, songs.size()));
            } else {
                title = R.string.delete_song_title;
                content = Html.fromHtml(getString(R.string.delete_song_x, songs.get(0).title));
            }
            this.title.setText(title);
            this.delete.setText(content);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dialog_delete_songs, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }
}

