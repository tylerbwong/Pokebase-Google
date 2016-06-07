package com.app.pokebase.pokebase.components;

import java.util.ArrayList;

/**
 * @author Tyler Wong
 */
public class Team {
   private String mName;
   private ArrayList<Pokemon> mTeam;

   public Team(String name, ArrayList<Pokemon> team) {
      this.mName = name;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getName() {
      return mName;
   }

   public void addPokemon(Pokemon pokemon) {
      mTeam.add(pokemon);
   }

   public void removePokemon(int index) {
      mTeam.remove(index);
   }

   public ArrayList<Pokemon> getTeam() {
      return mTeam;
   }
}
