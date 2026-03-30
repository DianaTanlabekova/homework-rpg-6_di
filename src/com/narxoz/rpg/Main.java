package com.narxoz.rpg;

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
import com.narxoz.rpg.tournament.TournamentEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 6 Demo: Chain of Responsibility + Command ===\n");

        ArenaFighter hero = new ArenaFighter("Sir Lancelot", 120, 0.25, 30, 7, 20, 4);
        ArenaOpponent opponent = new ArenaOpponent("Dark Knight", 100, 16);

        System.out.println("--- Command Queue Demo ---");
        System.out.println(" ");
        System.out.println("  PART 1 - Command Queue Demonstration");
        System.out.println(" ");
        System.out.println("Warrior: " + hero.getName() + "  |  Health: " + hero.getHealth() + "  |  Evasion: " + hero.getDodgeChance());
        System.out.println(" ");

        ActionQueue queue = new ActionQueue();

        queue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
        queue.enqueue(new HealCommand(hero, 20));
        queue.enqueue(new DefendCommand(hero, 0.15));

        System.out.println("Scheduled actions (" + queue.getCommandDescriptions().size() + " total):");
        for (String desc : queue.getCommandDescriptions()) {
            System.out.println("  Action: " + desc);
        }
        System.out.println(" ");

        System.out.println("Removing last scheduled action...");
        queue.undoLast();
        System.out.println(" ");

        System.out.println("Queue after removal (" + queue.getCommandDescriptions().size() + " total):");
        for (String desc : queue.getCommandDescriptions()) {
            System.out.println("  Action: " + desc);
        }
        System.out.println(" ");

        queue.enqueue(new DefendCommand(hero, 0.15));
        System.out.println("Executing all scheduled commands...");
        queue.executeAll();
        System.out.println("Remaining commands in queue: " + queue.getCommandDescriptions().size());

        System.out.println("\n--- Defense Chain Demo ---");
        System.out.println(" ");
        System.out.println("  PART 2 - Defense Chain Demonstration");
        System.out.println(" ");

        DefenseHandler dodge = new DodgeHandler(0.50, 99L);
        DefenseHandler block = new BlockHandler(0.30);
        DefenseHandler armor = new ArmorHandler(5);
        DefenseHandler hp = new HpHandler();
        dodge.setNext(block).setNext(armor).setNext(hp);

        System.out.println("Incoming strike: 20 damage through defense layers");
        System.out.println("Defense sequence: Evasion(50%) → Block(30%) → Armor(5) → Vitality");
        System.out.println("Warrior health before impact: " + hero.getHealth());
        System.out.println();
        dodge.handle(20, hero);
        System.out.println();
        System.out.println("Warrior health after impact: " + hero.getHealth());

        System.out.println("\n--- Full Arena Tournament ---");
        System.out.println(" ");
        System.out.println("  PART 3 - Grand Arena Tournament");
        System.out.println(" ");

        ArenaFighter tournamentHero = new ArenaFighter("Valiant", 135, 0.22, 28, 9, 24, 4);
        ArenaOpponent tournamentOpponent = new ArenaOpponent("Shadowblade", 115, 20);

        System.out.println(tournamentHero.getName() + " vs " + tournamentOpponent.getName());
        System.out.println(tournamentHero.getName() + " - Health: " + tournamentHero.getHealth() + 
                          ", Attack: " + tournamentHero.getAttackPower() + 
                          ", Evasion: " + tournamentHero.getDodgeChance() +
                          ", Block: " + tournamentHero.getBlockRating() + "%");
        System.out.println(tournamentOpponent.getName() + " - Health: " + tournamentOpponent.getHealth() + 
                          ", Attack: " + tournamentOpponent.getAttackPower());
        System.out.println(" ");

        TournamentResult result = new TournamentEngine(tournamentHero, tournamentOpponent)
                .setRandomSeed(42L)
                .runTournament();

        System.out.println(" ");
        System.out.println("TOURNAMENT CONCLUSION");
        System.out.println(" ");
        System.out.println("Champion : " + result.getWinner());
        System.out.println("Duration : " + result.getRounds() + " rounds");
        System.out.println("Battle Chronicle:");
        for (String line : result.getLog()) {
            System.out.println("  " + line);
        }

        System.out.println(" ");
        System.out.println("=== Demonstration Complete ===");
    }
}