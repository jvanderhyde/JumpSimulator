package edu.benedictine.game.media;

//updated: 5/16/2014

import edu.benedictine.game.gui.PixelCanvas;
import java.awt.image.BufferedImage;

public class GraphicsResource
{
    public AnimationStorage store;
    public String source = "images/";
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

    public GraphicsResource(PixelCanvas gameCanvas)
    {
        store = new AnimationStorage(gameCanvas);
        tiles = store.makeSourceArray(store.makeSpriteSheetB(store.toScale(source+"Tiles.png"), 32, 32));
        jetRaw = store.toScale(source+"Jet.png");
        mountainRaw = store.toScale(source+"Mountain.png");
        nebulaCloudsRaw = store.toScale(source+"NebulaClouds.png");
        retroHeroRaw = store.toScale(source+"RetroHero.png");
        tilesRaw = store.toScale(source+"Tiles.png");
        vialRaw = store.toScale(source+"Vial.png");
        
        heroSheet = store.makeSourceArray(store.makeSpriteSheet(retroHeroRaw, 1, 9));
        nebulaSheet = store.makeSourceArray(store.makeSpriteSheet(nebulaCloudsRaw, 4, 1));
        vialSheet = store.makeSourceArray(store.makeSpriteSheet(vialRaw, 1, 8));
        jetSheet = store.makeSourceArray(store.makeSpriteSheet(jetRaw, 2, 4));
        
        mountain = new ImageSource(mountainRaw);
        
        heroImages = store.makeSourceArray(store.makeImages(heroSheet));
        nebulaImages = store.makeSourceArray(store.makeImages(nebulaSheet));
        
        
        heroAir = new ImageSource(heroImages[2]);
        heroEmptyAir = new ImageSource(heroImages[8]);
        heroStatic = new ImageSource(heroImages[1]);
        heroEmptyStatic = new ImageSource(heroImages[7]);
        heroEdge = new ImageSource(heroImages[0]);
        heroSlide = new ImageSource(heroImages[3]);
        heroHit = new ImageSource(heroImages[4]);
        heroDown = new ImageSource(heroImages[5]);
        heroSlam = new ImageSource(heroImages[5]);
        
        
        heroWalk = new ImageSource(store.createAnimation(new ImageSource[]{heroImages[0],heroImages[1]},true,0.125,-1));
        heroWalkEdge = new ImageSource(store.createAnimation(new ImageSource[]{heroImages[1],heroImages[0]},true,0.125,-1));
        heroEmptyWalk = new ImageSource(store.createAnimation(new ImageSource[]{heroImages[6],heroImages[7]},true,0.125,-1));
        heroEmptyWalkEdge = new ImageSource(store.createAnimation(new ImageSource[]{heroImages[7],heroImages[6]},true,0.125,-1));
        heroRun = new ImageSource(store.createAnimation(new ImageSource[]{heroImages[1],heroImages[0]},true,0.25,-1));
        jetStream = new ImageSource(store.createAnimation(new ImageSource[]{jetSheet[0],jetSheet[1],jetSheet[2],jetSheet[3]},true,0.75,-1));
        
        heroWalkk = new ImageSource(store.createAnimation(new ImageSource[]{heroSheet[0],heroSheet[1]},true,0.5,-1));
        heroWalkkk = new ImageSource(store.createAnimation(new ImageSource[]{heroSheet[1],heroSheet[0]},true,0.5,-1));
    }

    public BufferedImage searchRaw(String target)
    {
        if (target.equals("jetRaw"))
            return jetRaw;
        if (target.equals("mountainRaw"))
            return mountainRaw;
        if (target.equals("nebulaCloudsRaw"))
            return nebulaCloudsRaw;
        if (target.equals("retroHeroRaw"))
            return retroHeroRaw;
        if (target.equals("tilesRaw"))
            return tilesRaw;
        if (target.equals("vialRaw"))
            return vialRaw;
        else
            return null;
    }

    public ImageSource search(String target)
    {
        if (target.equals("heroSheet[0]"))
            return heroSheet[0];
        if (target.equals("heroSheet[1]"))
            return heroSheet[1];
        if (target.equals("heroSheet[2]"))
            return heroSheet[2];
        if (target.equals("heroSheet[3]"))
            return heroSheet[3];
        if (target.equals("heroSheet[4]"))
            return heroSheet[4];
        if (target.equals("heroSheet[5]"))
            return heroSheet[5];
        if (target.equals("heroSheet[6]"))
            return heroSheet[6];
        if (target.equals("heroSheet[7]"))
            return heroSheet[7];
        if (target.equals("heroSheet[8]"))
            return heroSheet[8];
        if (target.equals("nebulaSheet[0]"))
            return nebulaSheet[0];
        if (target.equals("nebulaSheet[1]"))
            return nebulaSheet[1];
        if (target.equals("nebulaSheet[2]"))
            return nebulaSheet[2];
        if (target.equals("nebulaSheet[3]"))
            return nebulaSheet[3];
        if (target.equals("vialSheet[0]"))
            return vialSheet[0];
        if (target.equals("vialSheet[1]"))
            return vialSheet[1];
        if (target.equals("vialSheet[2]"))
            return vialSheet[2];
        if (target.equals("vialSheet[3]"))
            return vialSheet[3];
        if (target.equals("vialSheet[4]"))
            return vialSheet[4];
        if (target.equals("vialSheet[5]"))
            return vialSheet[5];
        if (target.equals("vialSheet[6]"))
            return vialSheet[6];
        if (target.equals("vialSheet[7]"))
            return vialSheet[7];
        if (target.equals("jetSheet[0]"))
            return jetSheet[0];
        if (target.equals("jetSheet[1]"))
            return jetSheet[1];
        if (target.equals("jetSheet[2]"))
            return jetSheet[2];
        if (target.equals("jetSheet[3]"))
            return jetSheet[3];
        if (target.equals("jetSheet[4]"))
            return jetSheet[4];
        if (target.equals("jetSheet[5]"))
            return jetSheet[5];
        if (target.equals("jetSheet[6]"))
            return jetSheet[6];
        if (target.equals("jetSheet[7]"))
            return jetSheet[7];
        if (target.equals("mountain"))
            return mountain;
        if (target.equals("heroImages[0]"))
            return heroImages[0];
        if (target.equals("heroImages[1]"))
            return heroImages[1];
        if (target.equals("heroImages[2]"))
            return heroImages[2];
        if (target.equals("heroImages[3]"))
            return heroImages[3];
        if (target.equals("heroImages[4]"))
            return heroImages[4];
        if (target.equals("heroImages[5]"))
            return heroImages[5];
        if (target.equals("heroImages[6]"))
            return heroImages[6];
        if (target.equals("heroImages[7]"))
            return heroImages[7];
        if (target.equals("heroImages[8]"))
            return heroImages[8];
        if (target.equals("nebulaImages[0]"))
            return nebulaImages[0];
        if (target.equals("nebulaImages[1]"))
            return nebulaImages[1];
        if (target.equals("nebulaImages[2]"))
            return nebulaImages[2];
        if (target.equals("nebulaImages[3]"))
            return nebulaImages[3];
        if (target.equals("heroAir"))
            return heroAir;
        if (target.equals("heroEmptyAir"))
            return heroEmptyAir;
        if (target.equals("heroStatic"))
            return heroStatic;
        if (target.equals("heroEmptyStatic"))
            return heroEmptyStatic;
        if (target.equals("heroEdge"))
            return heroEdge;
        if (target.equals("heroSlide"))
            return heroSlide;
        if (target.equals("heroHit"))
            return heroHit;
        if (target.equals("heroDown"))
            return heroDown;
        if (target.equals("heroSlam"))
            return heroSlam;
        if (target.equals("heroWalk"))
            return heroWalk;
        if (target.equals("heroWalkEdge"))
            return heroWalkEdge;
        if (target.equals("heroEmptyWalk"))
            return heroEmptyWalk;
        if (target.equals("heroEmptyWalkEdge"))
            return heroEmptyWalkEdge;
        if (target.equals("heroRun"))
            return heroRun;
        if (target.equals("jetStream"))
            return jetStream;
        if (target.equals("heroWalkk"))
            return heroWalkk;
        if (target.equals("heroWalkkk"))
            return heroWalkkk;
        else
            return null;
    }
}
