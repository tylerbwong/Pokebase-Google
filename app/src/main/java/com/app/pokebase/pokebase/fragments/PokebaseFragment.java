package com.app.pokebase.pokebase.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.PokemonRecyclerViewAdapter;
import com.app.pokebase.pokebase.adapters.TextViewSpinnerAdapter;
import com.app.pokebase.pokebase.components.PokemonListItem;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Wong
 */
public class PokebaseFragment extends Fragment implements ApiCallback {
    private Spinner mTypeSpinner;
    private Spinner mRegionSpinner;
    private RecyclerView mPokemonList;
    private List<AsyncTask> mCurrentTasks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentTasks = new ArrayList<>();
        String[] typeRegionCommand = new String[1];
        typeRegionCommand[0] = QueryTask.ALL_TYPES_REGIONS;
        new QueryTask().execute(new Pair<Context, String[]>(getActivity(), typeRegionCommand));
        String[] allCommand = new String[1];
        allCommand[0] = QueryTask.ALL_POKEMON;
        new QueryTask().execute(new Pair<Context, String[]>(getActivity(), allCommand));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTheme(R.style.PokemonEditorTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pokebase_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTypeSpinner = (Spinner) view.findViewById(R.id.type_spinner);
        mRegionSpinner = (Spinner) view.findViewById(R.id.region_spinner);
        mPokemonList = (RecyclerView) view.findViewById(R.id.pokemon_list);
        mPokemonList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onApiCallback(QueryResult result) {
        String command = result.getType();
        switch(command) {
            case QueryTask.ALL_TYPES_REGIONS:
                List<String> types = result.getStringInfo();
                types.add(0,"-- Types");
                List<String> regions = result.getStringInfo();
                regions.add(0, "-- Regions");
                mTypeSpinner.setAdapter(new TextViewSpinnerAdapter(getContext(), types.toArray(new String[types.size()])));
                mRegionSpinner.setAdapter(new TextViewSpinnerAdapter(getContext(), regions.toArray(new String[types.size()])));
                break;
            default:
                List<Integer> ids = result.getIntInfo();
                List<String> names = result.getStringInfo();
                PokemonListItem[] items = new PokemonListItem[ids.size()];
                for (int i = 0; i < ids.size(); i++) {
                    items[i] = new PokemonListItem(ids.get(i), names.get(i));
                }
                mPokemonList.setAdapter(new PokemonRecyclerViewAdapter(getContext(), items));
                break;
        }
    }
}
