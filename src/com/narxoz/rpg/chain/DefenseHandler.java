package com.narxoz.rpg.chain;
import com.narxoz.rpg.arena.ArenaFighter;


public abstract class DefenseHandler {
    private DefenseHandler next;


    public DefenseHandler setNext(DefenseHandler next) {
        this.next = next;
        return next;
    }


    protected DefenseHandler getNext() {
        return next;
    }


    protected void passToNext(int damage, ArenaFighter target) {
        // TODO: If damage is still greater than 0 and a next handler exists, forward it.
        // TODO: What should happen when there is no next handler but damage is nonzero?
        if (next != null) {
            next.handle(damage, target);
        }
    }


    public abstract void handle(int incomingDamage, ArenaFighter target);
}
