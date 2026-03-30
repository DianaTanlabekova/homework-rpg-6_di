package com.narxoz.rpg.chain;
import com.narxoz.rpg.arena.ArenaFighter;


public class BlockHandler extends DefenseHandler {
    private final double blockPercent;

    public BlockHandler(double blockPercent) {
        this.blockPercent = blockPercent;
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int reduction = (int) (incomingDamage * blockPercent);
        int remainder = incomingDamage - reduction;
        System.out.println("[Block] Braced for impact! " + reduction + " damage negated. Remaining sting: " + remainder + ".");
   
        passToNext(remainder, target);


        // TODO: Calculate how much damage is blocked: (int)(incomingDamage * blockPercent).
        // TODO: Subtract the blocked amount from incomingDamage to get the remainder.
        // TODO: Print a block message showing how much was blocked.
        // TODO: Always pass the remainder to the next handler (block reduces but never stops the chain).
        // Design question: what should happen if the remainder reaches 0 or below?
       
    }
}
