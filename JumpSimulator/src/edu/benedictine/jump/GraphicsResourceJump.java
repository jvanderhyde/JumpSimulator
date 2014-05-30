//updated: 5/16/2014

package edu.benedictine.jump;

import edu.benedictine.game.media.*;
import edu.benedictine.game.gui.PixelCanvas;
import java.awt.image.BufferedImage;

public class GraphicsResourceJump extends GraphicsResource
{
    public ImageSource[] tiles;
    public BufferedImage jetRaw;
    public BufferedImage mountainRaw;
    public BufferedImage nebulaCloudsRaw;
    public BufferedImage retroHeroRaw;
    public BufferedImage tilesRaw;
    public BufferedImage vialRaw;
    public ImageSource[] heroSheet;
    public ImageSource[] nebulaSheet;
    public ImageSource[] vialSheet;
    public ImageSource[] jetSheet;
    public ImageSource mountain;
    public ImageSource[] heroImages;
    public ImageSource[] nebulaImages;
    public ImageSource heroAir;
    public ImageSource heroEmptyAir;
    public ImageSource heroStatic;
    public ImageSource heroEmptyStatic;
    public ImageSource heroEdge;
    public ImageSource heroSlide;
    public ImageSource heroHit;
    public ImageSource heroDown;
    public ImageSource heroSlam;
    public ImageSource heroWalk;
    public ImageSource heroWalkEdge;
    public ImageSource heroEmptyWalk;
    public ImageSource heroEmptyWalkEdge;
    public ImageSource heroRun;
    public ImageSource jetStream;
    public ImageSource heroWalkk;
    public ImageSource heroWalkkk;

    public GraphicsResourceJump(PixelCanvas gameCanvas)
    {
		super(gameCanvas);
		source = "images/";
        tiles = GraphicsMethods.makeSourceArray(GraphicsMethods.makeSpriteSheetB(GraphicsMethods.toScale(source+"Tiles.png", gameCanvas.getWidth()), 32, 32));
        jetRaw = GraphicsMethods.toScale(source+"Jet.png", gameCanvas.getWidth());
        mountainRaw = GraphicsMethods.toScale(source+"Mountain.png", gameCanvas.getWidth());
        nebulaCloudsRaw = GraphicsMethods.toScale(source+"NebulaClouds.png", gameCanvas.getWidth());
        retroHeroRaw = GraphicsMethods.toScale(source+"RetroHero.png", gameCanvas.getWidth());
        tilesRaw = GraphicsMethods.toScale(source+"Tiles.png", gameCanvas.getWidth());
        vialRaw = GraphicsMethods.toScale(source+"Vial.png", gameCanvas.getWidth());
        
        heroSheet = GraphicsMethods.makeSourceArray(GraphicsMethods.makeSpriteSheet(retroHeroRaw, 1, 9));
        nebulaSheet = GraphicsMethods.makeSourceArray(GraphicsMethods.makeSpriteSheet(nebulaCloudsRaw, 4, 1));
        vialSheet = GraphicsMethods.makeSourceArray(GraphicsMethods.makeSpriteSheet(vialRaw, 1, 8));
        jetSheet = GraphicsMethods.makeSourceArray(GraphicsMethods.makeSpriteSheet(jetRaw, 2, 4));
        
        mountain = new ImageSource(mountainRaw);
        
        heroImages = GraphicsMethods.makeSourceArray(GraphicsMethods.makeImages(heroSheet));
        nebulaImages = GraphicsMethods.makeSourceArray(GraphicsMethods.makeImages(nebulaSheet));
        
        
        heroAir = new ImageSource(heroImages[2]);
        heroEmptyAir = new ImageSource(heroImages[8]);
        heroStatic = new ImageSource(heroImages[1]);
        heroEmptyStatic = new ImageSource(heroImages[7]);
        heroEdge = new ImageSource(heroImages[0]);
        heroSlide = new ImageSource(heroImages[3]);
        heroHit = new ImageSource(heroImages[4]);
        heroDown = new ImageSource(heroImages[5]);
        heroSlam = new ImageSource(heroImages[5]);
        
        
        heroWalk = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{heroImages[0],heroImages[1]},true,0.125,-1));
        heroWalkEdge = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{heroImages[1],heroImages[0]},true,0.125,-1));
        heroEmptyWalk = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{heroImages[6],heroImages[7]},true,0.125,-1));
        heroEmptyWalkEdge = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{heroImages[7],heroImages[6]},true,0.125,-1));
        heroRun = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{heroImages[1],heroImages[0]},true,0.25,-1));
        jetStream = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{jetSheet[0],jetSheet[1],jetSheet[2],jetSheet[3]},true,0.75,-1));
        
        heroWalkk = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{heroSheet[0],heroSheet[1]},true,0.5,-1));
        heroWalkkk = new ImageSource(GraphicsMethods.createAnimation(new ImageSource[]{heroSheet[1],heroSheet[0]},true,0.5,-1));
    }
}
