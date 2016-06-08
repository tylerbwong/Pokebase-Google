package com.app.pokebase.pokebase.components;

import java.util.ArrayList;

/**
 * @author Tyler Wong
 */
public class Team {
   private String mName;
   private String mDescription;
   private ArrayList<PokemonTeamMember> mTeam;

   public Team(String name, String description, ArrayList<PokemonTeamMember> team) {
      this.mName = name;
      this.mDescription = description;
      this.mTeam = team;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getName() {
      return mName;
   }

   public void addPokemon(PokemonTeamMember pokemon) {
      mTeam.add(pokemon);
   }

   public void removePokemon(int index) {
      mTeam.remove(index);
   }

   public ArrayList<PokemonTeamMember> getTeam() {
      return mTeam;
   }
}
