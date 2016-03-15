package im.brianoneill.chatmap.controller.map_chat;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.Person;

/**
 * Created by brianoneill on 15/03/16.
 */
public class ChatFragment extends Fragment {

    View chatListFragmentView;
    ArrayList<Person> persons;
    ListView chatListView;
    ChatFragmentListAdapter chatFragmentListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chatListFragmentView = inflater.inflate(R.layout.chat_fragment, container, false);

        persons = new ArrayList<>();
        persons.add(new Person("bob", getActivity()));
        persons.add(new Person("bob", getActivity()));
        persons.add(new Person("bob", getActivity()));

        chatListView =(ListView)chatListFragmentView.findViewById(R.id.chatFragmentListView);
        chatFragmentListAdapter = new ChatFragmentListAdapter(getActivity(), persons);
        chatListView.setAdapter(chatFragmentListAdapter);

        return chatListFragmentView;
    }
}
