package de.fhbielefeld.pmdungeon.quibble.entity.battle;

@FunctionalInterface
public interface CreatureStatsEventListener
{
	/**
	 * Class that represents an event that if fired when the stat values of creatures change.
	 * This class allows to get hold of the event values and also allows to alter the event values.
	 * @author Andreas
	 */
	public static class CreatureStatsEvent
	{
		private final CreatureStatsAttribs stat;
		private final double oldVal;
		private double newVal;
		private boolean cancelled;
		
		CreatureStatsEvent(CreatureStatsAttribs stat, double oldVal, double newVal)
		{
			this.stat = stat;
			this.oldVal = oldVal;
			this.newVal = newVal;
		}
		
		/**
		 * @return the stat to change that this event was triggered by
		 */
		public CreatureStatsAttribs getStat()
		{
			return this.stat;
		}
		
		/**
		 * Sets the value the stat should be changed to.
		 * @param newVal the new value
		 */
		public void setNewValue(double newVal)
		{
			this.newVal = newVal;
		}
		
		/**
		 * @return the value the stat will be changed to after the event
		 */
		public double getNewValue()
		{
			return this.newVal;
		}
		
		/**
		 * @return the previous value of the stat
		 */
		public double getOldValue()
		{
			return this.oldVal;
		}
		
		/**
		 * @return whether the stat will be changed at all or not
		 */
		public boolean isCancelled()
		{
			return this.cancelled;
		}
		
		/**
		 * Sets whether the stat will be changed at all.
		 * Setting this to true will prevent the stat from being changed unless another
		 * event handler sets this to true
		 * @param cancelled whether to cancel the stat change
		 */
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
