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

       
    }
}
