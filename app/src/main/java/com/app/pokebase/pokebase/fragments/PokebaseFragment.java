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
import android.widget.AdapterView;
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
public class PokebaseFragment extends Fragment implements ApiCallback, AdapterView.OnItemSelectedListener {
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
        mTypeSpinner.setOnItemSelectedListener(this);
        mRegionSpinner = (Spinner) view.findViewById(R.id.region_spinner);
        mRegionSpinner.setOnItemSelectedListener(this);
        mPokemonList = (RecyclerView) view.findViewById(R.id.pokemon_list);
        mPokemonList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onApiCallback(QueryResult result) {
        String command = result.getType();
        switch(command) {
            case QueryTask.ALL_TYPES_REGIONS:
                List<String> types = result.getStringInfo();
                types.add(0,"Types");
                List<String> regions = result.getMoreStringInfo();
                regions.add(0, "Regions");
                mTypeSpinner.setAdapter(new TextViewSpinnerAdapter(getContext(), types.toArray(new String[types.size()])));
                mRegionSpinner.setAdapter(new TextViewSpinnerAdapter(getContext(), regions.toArray(new String[regions.size()])));
                break;
            default:
                List<Integer> ids = result.getIntInfo();
                List<String> names = result.getStringInfo();
                if (ids != null && ids.size() > 0) {
                    PokemonListItem[] items = new PokemonListItem[ids.size()];
                    for (int i = 0; i < ids.size(); i++) {
                        items[i] = new PokemonListItem(ids.get(i), names.get(i));
                    }
                    mPokemonList.setAdapter(new PokemonRecyclerViewAdapter(getContext(), items));
                }
                else {
                    mPokemonList.setAdapter(new PokemonRecyclerViewAdapter(getContext(), new PokemonListItem[0]));
                }

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type = (String) mTypeSpinner.getSelectedItem();
        String region = (String) mRegionSpinner.getSelectedItem();

        if (type.equals("Types") && !region.equals("Regions")) {
            String[] regionArray = new String[2];
            regionArray[0] = QueryTask.POKEMON_BY_REGION;
            regionArray[1] = region;
            new QueryTask().execute(new Pair<Context, String[]>(getActivity(), regionArray));
        }
        else if (!type.equals("Types") && region.equals("Regions")) {
            String[] typeArray = new String[2];
            typeArray[0] = QueryTask.POKEMON_BY_TYPE;
            typeArray[1] = type;
            new QueryTask().execute(new Pair<Context, String[]>(getActivity(), typeArray));
        }
        else if (!type.equals("Types") && !region.equals("Regions")){
            String[] typeRegionArray = new String[3];
            typeRegionArray[0] = QueryTask.POKEMON_BY_TYPE_AND_REGION;
            typeRegionArray[1] = type;
            typeRegionArray[2] = region;
            new QueryTask().execute(new Pair<Context, String[]>(getActivity(), typeRegionArray));
        }
        else {
            String[] pokemonArray = new String[1];
            pokemonArray[0] = QueryTask.ALL_POKEMON;
            new QueryTask().execute(new Pair<Context, String[]>(getActivity(), pokemonArray));
        }
    }
}
