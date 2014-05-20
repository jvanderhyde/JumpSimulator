import java.awt.Color;

//Created by Joseph Rioux, 14 July 2013

public class LevelManager extends SceneObject
{	
	String level, prev;
	
	public LevelManager(Scene scn, String level, String prev) 
	{
		super(scn, 1);
		this.level = level;
		this.prev = prev;
	}
	
	public void initiate()
	{
		if (level.equals("Grass") || level.equals("Forest") || level.equals("Metroid") || level.equals("Playground") || level.equals("pictest"))
		{
			
			WorldObject wo = new WorldObject(scn, 1, 0, -1024.0, 576.0, -120.0, 0.0, scn.store.nebulaImages[3]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 1024.0, 576.0, -120.0, 0.0, scn.store.nebulaImages[3]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);

			wo = new WorldObject(scn, 1, 0, -960.0, 544.0, -90.0, 0.0, scn.store.nebulaImages[2]);
			wo.setScreenObject(true);
			wo.flipX = true;
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 1088.0, 544.0, -90.0, 0.0, scn.store.nebulaImages[2]);
			wo.setScreenObject(true);
			wo.flipX = true;
			wo.setBounds(-2048, 2048, -2048, 2048);

			wo = new WorldObject(scn, 1, 0, -1056.0, 512.0, -60.0, 0.0, scn.store.nebulaImages[1]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 992.0, 512.0, -60.0, 0.0, scn.store.nebulaImages[1]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			
			wo = new WorldObject(scn, 1, 0, -1056.0, 480.0, -30.0, 0.0, scn.store.nebulaImages[0]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 992.0, 480.0, -30.0, 0.0, scn.store.nebulaImages[0]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			
			wo = new WorldObject(scn, 1, 0, 640.0, 240.0, 0.0, 0.0, scn.store.mountain);
			wo.setScreenObject(true);
			
			wo = new WorldObject(scn, 1, 0, 320.0, 320.0, 0.0, 0.0, scn.store.mountain);
			wo.setScreenObject(true);
		}
	}
	
	public void update()
	{
		super.update();
	}
}
