package com.narxoz.rpg.arena;

public class ArenaFighter {
    private final String name;
    private int health;
    private final int maxHealth;
    private double dodgeChance;
    private final int blockRating;
    private final int armorValue;
    private final int attackPower;
    private int healPotions;


    public ArenaFighter(String name, int health, double dodgeChance, int blockRating, int armorValue, int attackPower, int healPotions) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.dodgeChance = dodgeChance;
        this.blockRating = blockRating;
        this.armorValue = armorValue;
        this.attackPower = attackPower;
        this.healPotions = healPotions;
    }

    public String getName() {
         return name;
         }
    public int getHealth() {
         return health; 
        }
    public int getMaxHealth() { 
        return maxHealth;
     }
    public double getDodgeChance() {
         return dodgeChance; 
        }
    public int getBlockRating() {
         return blockRating; 
        }
    public int getArmorValue() {
         return armorValue;
         }
    public int getAttackPower() {
         return attackPower;
         }
    public int getHealPotions() {
         return healPotions;
         }

    public void takeDamage(int amount) {
        // TODO: Reduce health by amount; clamp health to a minimum of 0.
        this.health = Math.max(0, this.health - amount);
    }

    public void heal(int amount) {
        // TODO: Increase health by amount; do not exceed maxHealth.
        // TODO: Decide what happens when healPotions runs out — should healing be blocked?
        if (this.healPotions > 0) {
        this.health = Math.min(this.maxHealth, this.health + amount);
        this.healPotions--;
       }
    }

    public void modifyDodgeChance(double delta) {
        // TODO: Add delta to dodgeChance.
        // TODO: Decide whether to clamp dodgeChance between 0.0 and 1.0.
        this.dodgeChance += delta;
        this.dodgeChance = Math.max(0.0, Math.min(1.0, this.dodgeChance));
    }

    public boolean isAlive() {
        // TODO: Return whether the fighter still has health remaining.
        return this.health > 0;
    }
}

