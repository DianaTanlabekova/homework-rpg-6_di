package com.narxoz.rpg.command;
import com.narxoz.rpg.arena.ArenaFighter;

public class HealCommand implements ActionCommand {
    private final ArenaFighter target;
    private final int healAmount;
    private int actualHealApplied;

    public HealCommand(ArenaFighter target, int healAmount) {
        this.target = target;
        this.healAmount = healAmount;
    }

    @Override
    public void execute() {
     if (target.getHealPotions() > 0) {
        int initialVitality = target.getHealth();
        target.heal(healAmount);
        this.actualHealApplied = target.getHealth() - initialVitality;
    } else {
        this.actualHealApplied = 0;
    }
       
    }

    @Override
    public void undo() {
        if (this.actualHealApplied > 0) {
        target.takeDamage(this.actualHealApplied);
    }
       
    }

    @Override
    public String getDescription() {
        
        return "Consume elixir to recover up to " + healAmount + " units of health";
       
    }
}


