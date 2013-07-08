package view;

import model.GameMaster;

/**
 * Interface that requires update(GameMaster model)
 * @author Jimmy, Vikrant, Kyle, Josh
 *
 */
public interface IGameObserver {

	//used to update views
	public void update(GameMaster model);
}