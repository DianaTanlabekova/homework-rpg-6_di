package com.narxoz.rpg.tournament;

import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.ArmorHandler;
import com.narxoz.rpg.chain.BlockHandler;
import com.narxoz.rpg.chain.DefenseHandler;
import com.narxoz.rpg.chain.DodgeHandler;
import com.narxoz.rpg.chain.HpHandler;
import com.narxoz.rpg.command.ActionQueue;
import com.narxoz.rpg.command.AttackCommand;
import com.narxoz.rpg.command.DefendCommand;
import com.narxoz.rpg.command.HealCommand;
import java.util.Random;

public class TournamentEngine {
    private final ArenaFighter hero;
    private final ArenaOpponent opponent;
    private Random random = new Random(1L);

    public TournamentEngine(ArenaFighter hero, ArenaOpponent opponent) {
        this.hero = hero;
        this.opponent = opponent;
    }

    public TournamentEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public TournamentResult runTournament() {
        TournamentResult result = new TournamentResult();
        int round = 0;
        final int maxRounds = 20;

        DefenseHandler dodge = new DodgeHandler(hero.getDodgeChance(), random.nextLong());
        DefenseHandler block = new BlockHandler(hero.getBlockRating() / 100.0);
        DefenseHandler armor = new ArmorHandler(hero.getArmorValue());
        DefenseHandler hp = new HpHandler();

        dodge.setNext(block).setNext(armor).setNext(hp);
        ActionQueue actionQueue = new ActionQueue();

        while (hero.isAlive() && opponent.isAlive() && round < maxRounds) {
            round++;
    
            actionQueue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
            if (hero.getHealPotions() > 0 && hero.getHealth() < hero.getMaxHealth() * 0.7) {
                actionQueue.enqueue(new HealCommand(hero, 25));
            }
            actionQueue.enqueue(new DefendCommand(hero, 0.10));
            
         
            result.addLine("=== ROUND " + round + " ===");
            result.addLine("Queued: " + actionQueue.getCommandDescriptions());
            
         
            actionQueue.executeAll();
            
            if (opponent.isAlive()) {
                result.addLine(opponent.getName() + " attacks with " + opponent.getAttackPower() + " power!");
                dodge.handle(opponent.getAttackPower(), hero);
            }
            
            
            String statusLine = String.format("[ROUND %d] %s: %d HP | %s: %d HP", 
                round, hero.getName(), hero.getHealth(), opponent.getName(), opponent.getHealth());
            result.addLine(statusLine);
        }
        
        
        if (!hero.isAlive() && !opponent.isAlive()) {
            result.setWinner("DRAW - Both defeated!");
        } else if (hero.isAlive() && !opponent.isAlive()) {
            result.setWinner(hero.getName());
            result.addLine(hero.getName() + " VICTORY!");
        } else if (!hero.isAlive() && opponent.isAlive()) {
            result.setWinner(opponent.getName());
            result.addLine(opponent.getName() + " VICTORY!");
        } else {
            result.setWinner("DRAW (MAX ROUNDS)");
            result.addLine("Tournament ended after " + maxRounds + " rounds!");
        }
        
        result.setRounds(round);
        return result;
    }
}

