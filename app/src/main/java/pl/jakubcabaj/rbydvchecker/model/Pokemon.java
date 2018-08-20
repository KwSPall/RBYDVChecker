package pl.jakubcabaj.rbydvchecker.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "hp", "attack", "defense", "speed", "special"})
public class Pokemon {

    public int id;
    public String name;
    public int hp;
    public int attack;
    public int defense;
    public int speed;
    public int special;
}
