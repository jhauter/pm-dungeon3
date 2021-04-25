package de.fhbielefeld.pmdungeon.quibble.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that can be used to chose actions based on a probability table.
 * @author Andreas
 */
public class RNGTable
{
	private static class Option
	{
		private final double position;
		private final int id;
		
		private Option(double position, int id)
		{
			this.position = position;
			this.id = id;
		}
	}
	
	private final Random rng;
	
	private final List<Option> options;
	
	private double positionTotal;
	
	/**
	 * Creates an empty <code>RNGDecision</code> object that uses the given <code>Random</code> object
	 * to generate its results.
	 * @param rng the <code>Random</code> to use for calculations
	 */
	public RNGTable(Random rng)
	{
		this.rng = rng;
		this.options = new ArrayList<>();
	}
	
	/**
	 * Add an option that can chosen by the RNG by calling {@link #chance()}.
	 * Each option has a probability of getting chosen.
	 * Returns a unique id value which can be used to identify an option returned by {@link #chance()}.
	 * @param probability the probability of getting chosen
	 * @return the unique id of that option
	 */
	public int addOption(double probability)
	{
		if(this.positionTotal + probability > 1.0D)
		{
			throw new IllegalStateException("adding this option would cause the total probability to be " + (this.positionTotal + probability) + " which is not allowed");
		}
		this.options.add(new Option(this.positionTotal += probability, this.options.size() + 1));
		return this.options.size();
	}
	
	/**
	 * Chooses a random option based on their probabilities. A returned value of 0 means that none of
	 * the options was chosen and thus stands for the rest of the 0 - 100% scale that was not assigned.
	 * @return the id of the chosen option
	 */
	public int chance()
	{
		double rngValue = this.rng.nextDouble();
		for(int i = 0; i < this.options.size(); ++i)
		{
			if(rngValue < this.options.get(i).position)
			{
				return this.options.get(i).id;
			}
		}
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		for(Option o : this.options)
		{
			b.append(o.position + "\n");
		}
		return b.toString();
	}
}
