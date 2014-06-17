//Modified by Joseph Rioux, 12 January 2013
//	Note: x or y speed of 0.0015625 = 1 pixel per frame (at standard resolution, 1024 x 640)

package edu.benedictine.game.gui;

import edu.benedictine.game.engine.*;
import edu.benedictine.game.engine.collision.*;
import edu.benedictine.game.input.InputManager;
import edu.benedictine.jump.GraphicsResourceJump;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Scene
{
	public String currentLevel, previousLevel;
	private PriorityQueue<SceneObject> objs;
	private PriorityQueue<GameObject> games;
	private PriorityQueue<WorldObject> draws;
	private PriorityQueue<Hit> hits;
	private ArrayList<BoundingBox> boxes;
	private SceneObject[] objCurrents;
	private GameObject[] gameCurrents;
	private WorldObject[] drawCurrents;
	private BoundingBox[] boxCurrents;

	public TestPlayer player;
	public Camera camera;
	public CollisionManager physics;
	public PicLoader loader;
	public GraphicsResourceJump store;
    
	public Scene(InputManager input, String source, String prev)
	{
		objs = new PriorityQueue<SceneObject>(16);
		games = new PriorityQueue<GameObject>(16);
		draws = new PriorityQueue<WorldObject>(32);
		drawCurrents = new WorldObject[16];
		boxes = new ArrayList<BoundingBox>();
		hits = new PriorityQueue<Hit>(25);
		currentLevel = source;
		previousLevel = prev;

		constructScene(input);
	}
	
	private void constructScene(InputManager input)
	{
		physics = new CollisionManager(this);
		camera = new Camera(this, 0.0, 0.0, true);
		camera.setRadialSpeed(30, 0);
		//WorldObject o = new WorldObject(this, 10, 10, 0.0, 0.0, 30.0, 0.0, null);
		//o = new WorldObject(this, 10, 10, 0.0, 0.0, 0.0, 0.0, null);
		loader = new PicLoader(this);
		loader.load(currentLevel);
		player = new TestPlayer(input, this, 96.0, 128.0);
	}
	
	public void updateScene()
	{
		physics.prepTerrain();
		physics.getReady();

		objCurrents = new SceneObject[objs.size()];
		drawCurrents = new WorldObject[draws.size()];
		gameCurrents = new GameObject[games.size()];
		objs.toArray(objCurrents);
		games.toArray(gameCurrents);
		draws.toArray(drawCurrents);

		updateSceneObjects();
		moveObjects();
		postMove();
		checkSceneCollision();
		postCollision();
		checkSceneHits();
		handleSceneHits();
		sceneCleanUp();
		camera.setX(player.getX());
		camera.setY(player.getY());
	}
	
	public void updateSceneObjects()
	{
		objs.toArray(objCurrents);
		for (int i=0; i<objCurrents.length; i++)
		{
			objCurrents[i].update();
		}
	}
	
	public void moveObjects()
	{
		objs.toArray(objCurrents);
		for (int i=0; i<objCurrents.length; i++)
		{
			objCurrents[i].move(objCurrents[i].getXSpeed(), objCurrents[i].getYSpeed());
		}
	}
	
	public void postMove()
	{
		games.toArray(gameCurrents);
		for (int i=0; i<gameCurrents.length; i++)
		{
			gameCurrents[i].postMove();
		}
	}
	
	public void checkSceneCollision()
	{
		for (int i=0; i<gameCurrents.length; i++)
		{
			if (gameCurrents[i].hitTerrain)
				gameCurrents[i].checkCollision();
		}
	}
	
	public void postCollision()
	{
		for (int i=0; i<gameCurrents.length; i++)
		{
			gameCurrents[i].postCollision();
		}
	}
	
	public void checkSceneHits()
	{
		hits = new PriorityQueue<Hit>(25);
		boxCurrents = new BoundingBox[boxes.size()];
		boxes.toArray(boxCurrents);
		for (int i=0; i<boxCurrents.length; i++)
		{
			boxCurrents[i].getReady();
		}
		for (int i=0; i<boxCurrents.length; i++)
		{
			boxCurrents[i].checkHits();
		}
	}
	
	public void handleSceneHits()
	{
		for (int i=0; i<hits.size(); i++)
		{
			Hit currentHit = hits.poll();
			currentHit.a.owner.handleHit(currentHit);
		}
	}
	
	public void sceneCleanUp()
	{
		objs.toArray(objCurrents);
		for (int i=0; i<objCurrents.length; i++)
		{
			objCurrents[i].cleanUp();
		}
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public WorldObject[] getDraws()
	{
		return drawCurrents;
	}
	
	public void addObj(SceneObject o)
	{
		objs.add(o);
	}
	
	public void addDraw(WorldObject o)
	{
		draws.add(o);
	}
	
	public void addGame(GameObject o)
	{
		games.add(o);
	}
	
	public void addBox(BoundingBox o)
	{
		boxes.add(o);
	}
	
	public void addHit(Hit o)
	{
		hits.add(o);
	}
	
	public void removeObj(SceneObject o)
	{
		objs.remove(o);
	}
	
	public void removeDraw(WorldObject o)
	{
		draws.remove(o);
	}
	
	public void removeGame(GameObject o)
	{
		games.remove(o);
	}
	
	public void removeBox(BoundingBox o)
	{
		boxes.remove(o);
	}
	
	public void removeHit(Hit o)
	{
		hits.remove(o);
	}
}
