package com.narxoz.rpg.command;
import com.narxoz.rpg.arena.ArenaOpponent;

public class AttackCommand implements ActionCommand {
    private final ArenaOpponent target;
    private final int attackPower;
    private int damageDealt;

    public AttackCommand(ArenaOpponent target, int attackPower) {
        this.target = target;
        this.attackPower = attackPower;
    }

    @Override
    public void execute() {
        int victimHealthBeforeHit = target.getHealth();
        target.takeDamage(attackPower);
        this.damageDealt = victimHealthBeforeHit - target.getHealth();
        
    }

    @Override
    public void undo() {
        target.restoreHealth(this.damageDealt);
        
    }

    @Override
    public String getDescription() {
        
        return "Strike dealing " + attackPower + " damage to " + target.getName();
    }
}



