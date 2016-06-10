package com.app.pokebase.pokebase.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.PokemonRecyclerViewAdapter;
import com.app.pokebase.pokebase.adapters.TextViewSpinnerAdapter;
import com.app.pokebase.pokebase.components.PokemonListItem;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;

import java.util.List;

/**
 * @author Tyler Wong
 */
public class PokebaseFragment extends Fragment implements ApiCallback {
    private Spinner mTypeSpinner;
    private Spinner mRegionSpinner;
    private RecyclerView mPokemonList;

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

        String[] types = new String[5];
        types[0] = "Type";
        types[1] = "Normal";
        types[2] = "Ice";
        types[3] = "Electric";
        types[4] = "Water";

        String[] regions = new String[5];
        regions[0] = "Region";
        regions[1] = "Normal";
        regions[2] = "Ice";
        regions[3] = "Electric";
        regions[4] = "Water";
        mTypeSpinner.setAdapter(new TextViewSpinnerAdapter(getContext(), types));
        mRegionSpinner.setAdapter(new TextViewSpinnerAdapter(getContext(), regions));
        PokemonListItem[] items = new PokemonListItem[2];
        items[0] = new PokemonListItem(25, "Pikachu");
        items[1] = new PokemonListItem(1, "Bulbasaur");
        mPokemonList.setAdapter(new PokemonRecyclerViewAdapter(getContext(), items));
    }

    @Override
    public void onApiCallback(QueryResult result) {
        List<Integer> ids = result.getIntInfo();
        List<String> names = result.getStringInfo();
        PokemonListItem[] items = new PokemonListItem[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            items[i] = new PokemonListItem(ids.get(i), names.get(i));
        }
        mPokemonList.setAdapter(new PokemonRecyclerViewAdapter(getContext(), items));
    }
}
