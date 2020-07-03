package com.zzzyt.jade.ui;

public interface GridComponent {
	public GridComponent activate();

	public GridComponent deactivate();
	
	public boolean isActive();

	public void update();
	
	public int getGridX();
	
	public int getGridY();
	
	public void trigger();
}
