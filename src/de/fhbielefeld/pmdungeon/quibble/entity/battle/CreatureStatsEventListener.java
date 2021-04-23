package de.fhbielefeld.pmdungeon.quibble.entity.battle;

@FunctionalInterface
public interface CreatureStatsEventListener
{
	public static class CreatureStatsEvent
	{
		private final CreatureStatsAttribs stat;
		private final double oldVal;
		private double newVal;
		private boolean cancelled;
		
		public CreatureStatsEvent(CreatureStatsAttribs stat, double oldVal, double newVal)
		{
			this.stat = stat;
			this.oldVal = oldVal;
			this.newVal = newVal;
		}
		
		public CreatureStatsAttribs getStat()
		{
			return this.stat;
		}
		
		public void setNewValue(double newVal)
		{
			this.newVal = newVal;
		}
		
		public double getNewValue()
		{
			return this.newVal;
		}
		
		public double getOldValue()
		{
			return this.oldVal;
		}
		
		public boolean isCancelled()
		{
			return this.cancelled;
		}
		
		public void setCancelled(boolean cancelled)
		{
			this.cancelled = cancelled;
		}
	}
	
	/**
	 * This is executed on a listener every time a stat value is changed.
	 * This listener is allowed to alter the new stat value by changing
	 * the new value in the <code>CreatureStatsEvent</code> with {@link #setNewValue(double)}.
	 * @param event object that contains stat that was changed, old value and new value
	 */
	public void onStatValueChange(CreatureStatsEvent event);
}
